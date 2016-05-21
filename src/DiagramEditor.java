import ecs100.UI;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Dylan on 17/05/16.
 * <p>
 * The controller for the app
 */
public class DiagramEditor implements Serializable {

    /**
     * Contains DEGroup and DEObjectShape objects. They
     * are sorted from top to bottom (where the top is
     * the closest to the user).
     */
    private ArrayList<DEObject> deObjects = new ArrayList<>();
    private ArrayList<DEConnector> deConnectors = new ArrayList<>();
    private DiagramEditorOutput output;

    private DEPoint mouseDownPoint;
    private DEDraggable objectBeingDragged;
    private boolean hasCheckedForObjectBeingDragged;
    private boolean shouldSelectNextClicked = false;

    /**
     * This is null unless the user has clicked "Add Connector".
     * Then it will be set to the currently selected object, and
     * after the user selects another object, they can be connected.
     */
    private DEObject firstObjectToConnect;

    public DiagramEditor(DiagramEditorOutput output) {
        draw();
        this.output = output;
    }

    public static DEBounds getRandomNewShapeBounds() {
        return new DEBounds(50 + 100 * Math.random(), 50 + 100 * Math.random(), 200, 200);
    }

    public void setOutput(DiagramEditorOutput output) {
        this.output = output;
    }

    public void draw() {
        UI.clearGraphics();

        deObjects.forEach(DEObject::draw);
        deConnectors.forEach(DEConnector::draw);

        UI.repaintAllGraphics();
    }

    // ************************* OBJECTS AND SELECTION ************************* //

    private void addNewShape(DEObject obj) {
        obj.setSelected(true);
        deObjects.add(0, obj);

        draw();
    }

    private void addNewConnector(DEConnector connector) {
        deConnectors.add(connector);
        draw();
    }

    // ------------------------- Deleting Objects and Connectors ------------------------- //

    /**
     * @return Returns true if it could delete any shapes, false
     * if there are no shapes to delete
     */
    private boolean deleteAllSelectedObjects() {
        ArrayList<DEObject> selected = getSelectedObjects();
        if (selected.size() == 0) return false;

        deleteObjects(selected);

        draw();
        return true;
    }

    private void deleteObjects(ArrayList<DEObject> objects) {
        for (DEObject obj : objects) {
            deleteObject(obj);
        }
    }

    private void deleteObject(DEObject object) {
        assert deObjects.contains(object) : "Can't delete object that isn't part of the array";
        deleteConnectorsConnectedToObject(object);
        deObjects.remove(object);
    }

    private boolean deleteConnectorsConnectedToObject(DEObject object) {
        boolean connectorsWereDeleted = false;
        for (int c = 0; c < deConnectors.size(); c++) {
            DEConnector connector = deConnectors.get(c);
            if (connector.isConnectedToObject(object)) {
                connectorsWereDeleted = true;
                deConnectors.remove(c);
                c--;
            }
        }
        if (connectorsWereDeleted) draw();
        return connectorsWereDeleted;
    }

    // ------------------------- Selection ------------------------- //

    private void deselectAllSelectedObjects() {
        ArrayList<DEObject> selected = getSelectedObjects();
        for (DEObject obj : selected) {
            obj.setSelected(false);
        }
        draw();
    }

    private ArrayList<DEObject> getSelectedObjects() {
        ArrayList<DEObject> selected = new ArrayList<>();
        for (DEObject o : deObjects) {
            if (o.isSelected()) selected.add(o);
        }
        return selected;
    }

    /**
     * @param mousePoint
     * @return True if an object could be selected
     */
    private boolean attemptSelectAtPoint(DEPoint mousePoint) {
        for (DEObject obj : deObjects) {
            if (obj.canSelectAtPoint(mousePoint)) {
                obj.setSelected(true);
                draw();
                return true;
            }
        }

        return false;
    }

    /**
     * Finds something on the screen to drag (must be a Draggable
     * that is under the mouse)
     *
     * @param mousePoint
     * @return
     */
    private DEDraggable getDraggableDraggableAtPoint(DEPoint mousePoint) {
        for (DEObject obj : deObjects) {
            DEDraggable draggable = obj.getDraggableDraggableAtPoint(mousePoint);
            if (draggable == null) continue;

            return draggable;
        }

        return null;
    }

    private ArrayList<DEObjectShape> getSelectedShapes() {
        ArrayList<DEObjectShape> selectedShapes = new ArrayList<>();
        for (DEObject obj : deObjects) {
            if (obj instanceof DEObjectShape && obj.isSelected()) {
                selectedShapes.add((DEObjectShape) obj);
            }
        }

        return selectedShapes;
    }

    // ************************* MOUSE EVENTS ************************* //

    public void doMouse(String action, double x, double y) {
        DEPoint mousePoint = new DEPoint(x, y);

        switch (action) {
            case "pressed":
                mousePressed(mousePoint);
                break;
            case "dragged":
                mouseDragged(mousePoint);
                break;
            case "released":
                mouseReleased(mousePoint);
                break;
            case "clicked":
                mouseClicked(mousePoint);
                break;
        }
    }

    private void mousePressed(DEPoint mousePoint) {
        mouseDownPoint = mousePoint;
        hasCheckedForObjectBeingDragged = false;
    }

    private void mouseDragged(DEPoint mousePoint) {
        if (objectBeingDragged == null) {
            if (hasCheckedForObjectBeingDragged) return;

            // may set it to null
            objectBeingDragged =
                    getDraggableDraggableAtPoint(mouseDownPoint);


            if (objectBeingDragged != null) {
                objectBeingDragged.pickUp(mouseDownPoint);
            }

            hasCheckedForObjectBeingDragged = true;
        } else {
            objectBeingDragged.followAlong(mousePoint);
        }

        draw();
    }

    private void mouseReleased(DEPoint mousePoint) {
        if (objectBeingDragged != null) {
            objectBeingDragged.putDown(mousePoint);
            objectBeingDragged = null;
        }
        mouseDownPoint = null;
        hasCheckedForObjectBeingDragged = false;
        draw();
    }

    /**
     * Mouse pressed and then released without drag
     * @param mousePoint
     */
    private void mouseClicked(DEPoint mousePoint) {
        if (!shouldSelectNextClicked) deselectAllSelectedObjects();
        boolean didSelect = attemptSelectAtPoint(mousePoint);

        // Disable shouldSelectNextClicked only if an object was selected
        if (didSelect && shouldSelectNextClicked) shouldSelectNextClicked = false;

        // Should connect next clicked object
        if (firstObjectToConnect != null) {
            if (didSelect) {
                DEConnector connector = new DEConnector(firstObjectToConnect,
                        getSelectedObjects().get(0));
                addNewConnector(connector);
            } else {
                output.showMessage("Cancelled connection");
            }

            firstObjectToConnect = null;
        }

        // Deselect all if the background was clicked
        if (!didSelect) {
            deselectAllSelectedObjects();
        }
        mouseDownPoint = null;
        objectBeingDragged = null;
    }

    // ************************* BUTTON EVENTS ************************* //

    public void addRectPressed() {
        deselectAllSelectedObjects();
        addNewShape(new DEObjectShapeRect(getRandomNewShapeBounds()));
    }

    public void addEllipsePressed() {
        deselectAllSelectedObjects();
        addNewShape(new DEObjectShapeEllipse(getRandomNewShapeBounds()));
    }

    public void addHexagonPressed() {
        deselectAllSelectedObjects();
        addNewShape(new DEObjectShapeHexagon(getRandomNewShapeBounds()));
    }

    public void selectAnotherPressed() {
        output.showMessage("Select another shape");
        shouldSelectNextClicked = true;
    }

    public void deletePressed() {
        if (deleteAllSelectedObjects()) {
            output.showMessage("Deleted shapes");
        } else {
            output.showMessage("Couldn't delete any shapes");
        }
    }

    public void deleteConnectorsPressed() {
        ArrayList<DEObject> selectedObjects = getSelectedObjects();
        if (selectedObjects.size() == 0) {
            output.showMessage("No objects are selected");
            return;
        }
        boolean connectorsWereDeleted = false;
        for (DEObject obj : selectedObjects) {
            if (deleteConnectorsConnectedToObject(obj))
                connectorsWereDeleted = true;
        }
        if (connectorsWereDeleted) {
            output.showMessage("Connectors were deleted");
        } else {
            output.showMessage("No connectors were deleted");
        }
    }

    public void addConnectorPressed() {
        if (firstObjectToConnect != null) {
            output.showMessage("You are already trying to connect two objects.\n"
                    + "Please select another object to connect.");
            return;
        }

        ArrayList<DEObject> selected = getSelectedObjects();
        if (selected.size() == 0) {
            output.showMessage("Please select an object first");
            return;
        } else if (selected.size() > 1) {
            output.showMessage("Please select only one object");
            return;
        }

        firstObjectToConnect = selected.get(0);
        output.showMessage("Select another object to connect to");
    }

    // ************************* TEXT EVENTS ************************* //

    /**
     * Sets the top-most selected object's text
     *
     * @param text
     */
    public void selectedObjectTextTyped(String text) {
        ArrayList<DEObjectShape> selectedShapes = getSelectedShapes();
        if (selectedShapes.size() == 0) return;

        DEObjectShape shape = selectedShapes.get(0);

        shape.setLabelText(text);
        draw();
    }
}
