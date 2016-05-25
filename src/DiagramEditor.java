import ecs100.UI;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Dylan on 17/05/16.
 * <p>
 * The controller for the app
 */
public class DiagramEditor implements Serializable {

    private static final int COLOR_TYPE_EDGE = 1;
    private static final int COLOR_TYPE_FILL = 2;
    private static final int COLOR_TYPE_TEXT = 3;
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
    private String typedColor;
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

        deConnectors.forEach(DEConnector::draw);
        deObjects.forEach(DEObject::draw);

        UI.repaintAllGraphics();
    }

    // ************************* OBJECTS AND SELECTION ************************* //

    private void addObject(DEObject object) {
        assert object != null : "The object cannot be null";
        deObjects.add(0, object);
    }

    private void addObjects(ArrayList<DEObject> objects) {
        for (DEObject obj : objects) {
            obj.setSelected(true);
            addObject(obj);
        }
    }

    private void addNewShape(DEObject obj) {
        obj.setSelected(true);
        addObject(obj);

        draw();
    }

    private boolean addNewConnector(DEConnector connector) {
        // check for duplicates
        for (DEConnector c : deConnectors) {
            if (c.equals(connector)) return false;
        }

        deConnectors.add(connector);
        draw();
        return true;
    }

    private boolean addNewConnector(DEObject objectA, DEObject objectB) {
        return addNewConnector(new DEConnector(objectA, objectB));
    }

    private void addConnectors(ArrayList<DEConnector> connectors) {
        for (DEConnector con : connectors) {
            addNewConnector(con);
        }
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

    private void deleteConnector(DEConnector connector) {
        assert deConnectors.contains(connector) : "Can't delete connector that isn't part of the array";
        deConnectors.remove(connector);
    }

    private void deleteConnectors(ArrayList<DEConnector> connectors) {
        for (DEConnector c : connectors) {
            deleteConnector(c);
        }
    }

    private boolean deleteConnectorsConnectedToObject(DEObject object) {
        ArrayList<DEConnector> toDelete = getConnectorsConnectedToObject(object);

        if (toDelete.size() == 0) return false;

        for (DEConnector c : toDelete) {
            deleteConnector(c);
        }

        draw();
        return true;
    }

    private ArrayList<DEConnector> getConnectorsConnectedToObject(DEObject object) {
        ArrayList<DEConnector> connectors = new ArrayList<>();
        for (DEConnector connector : deConnectors) {
            if (connector.isConnectedToObject(object)) connectors.add(connector);
        }
        return connectors;
    }

    private boolean deleteConnectorBetweenObjects(DEObject objectA, DEObject objectB) {
        for (DEConnector connector : deConnectors) {
            if (connector.isConnectedToObject(objectA) &&
                    connector.isConnectedToObject(objectB)) {
                deleteConnector(connector);
                draw();
                return true;
            }
        }

        return false;
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

    // ------------------------- Grouping ------------------------- //

    /**
     * Removes the connectors and objects that will be grouped, and puts them
     * into a new group
     *
     * @param objects
     */
    private void groupObjects(ArrayList<DEObject> objects) {
        ArrayList<DEConnector> connectors = new ArrayList<>();

        for (DEObject obj : objects) {
            ArrayList<DEConnector> objConnectors = getConnectorsConnectedToObject(obj);
            connectors.addAll(objConnectors);
            deleteConnectors(objConnectors);

            obj.setSelected(false);
        }

        deleteObjects(objects);

        // If there are DEGroups inside objects, then it will be ungrouped
        // and then regrouped into one layer of group
        DEGroup group = new DEGroup(objects, connectors);

        addObject(group);
        group.setSelected(true);
        draw();
    }

    /**
     * @param objects
     * @return True if anything was ungrouped
     */
    private boolean ungroupObjects(ArrayList<DEObject> objects) {
        boolean didUngroupSomething = false;

        for (DEObject obj : objects) {
            if (!(obj instanceof DEGroup)) continue;

            DEGroup group = (DEGroup) obj;
            ArrayList<DEObject> groupObjects = group.getDeObjects();
            ArrayList<DEConnector> groupConnectors = group.getDeConnectors();

            addObjects(groupObjects);
            addConnectors(groupConnectors);
            deleteObject(group);

            didUngroupSomething = true;
        }

        if (didUngroupSomething) draw();
        return didUngroupSomething;
    }

    /**
     * Called by different set color button press methods
     * to avoid code duplication
     *
     * @param colorType
     */
    private void setColorButtonEvent(int colorType) {
        if (typedColor == null || typedColor.length() == 0) {
            output.showMessage("Type in a color first (e.g. 'FF0000')");
            return;
        }
        ArrayList<DEObjectShape> selectedShapes = getSelectedShapes();
        if (selectedShapes.size() == 0) {
            output.showMessage("Please select a shape first");
            return;
        }

        Color color = DEColorCalculator.getColorForString(typedColor);
        if (color == null) {
            output.showMessage("Invalid colour. Try something like 0C39A7");
            return;
        }
        for (DEObjectShape shape : selectedShapes) {
            switch (colorType) {
                case COLOR_TYPE_EDGE:
                    shape.setEdgeColor(color);
                    break;
                case COLOR_TYPE_FILL:
                    shape.setFillColor(color);
                    break;
                case COLOR_TYPE_TEXT:
                    shape.setLabelColor(color);
                    break;
            }
        }
        draw();
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
     *
     * @param mousePoint
     */
    private void mouseClicked(DEPoint mousePoint) {
        if (!shouldSelectNextClicked) deselectAllSelectedObjects();
        boolean didSelect = attemptSelectAtPoint(mousePoint);

        // Disable shouldSelectNextClicked only if an object was selected
        if (didSelect && shouldSelectNextClicked) {
            shouldSelectNextClicked = false;
            output.showMessage("");
        }

        // Should connect next clicked object
        if (firstObjectToConnect != null) {
            if (didSelect) {
                DEConnector connector = new DEConnector(firstObjectToConnect,
                        getSelectedObjects().get(0));
                if (addNewConnector(connector)) output.showMessage("Added connector");
                else output.showMessage("There is already a connector there");
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

    // ------------------------- Alignment ------------------------- //

    /**
     * @param objects
     * @param vertically When true, aligns them to the left-most of the objects
     *                   and when false, aligns to them to the top-most.
     */
    private void alignObjects(ArrayList<DEObject> objects, boolean vertically) {
        double minimumOrdinate = 999999;

        for (DEObject obj : objects) {
            DEPoint objTopLeft = obj.getBounds().getTopLeft();
            if (vertically) {
                if (objTopLeft.getX() < minimumOrdinate) {
                    minimumOrdinate = objTopLeft.getX();
                }
            } else { // horizontally
                if (objTopLeft.getY() < minimumOrdinate) {
                    minimumOrdinate = objTopLeft.getY();
                }
            }
        }

        for (DEObject obj : objects) {
            DEBounds originalBounds = obj.getBounds();
            DEBounds newBounds;
            if (vertically) newBounds = new DEBounds(minimumOrdinate, originalBounds.getTop(),
                    originalBounds.getWidth(), originalBounds.getHeight());
            else newBounds = new DEBounds(originalBounds.getLeft(), minimumOrdinate,
                    originalBounds.getWidth(), originalBounds.getHeight());

            obj.setBounds(newBounds);
        }

        draw();
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

    public void setTextColorPressed() {
        setColorButtonEvent(COLOR_TYPE_TEXT);
    }

    public void setFillColorPressed() {
        setColorButtonEvent(COLOR_TYPE_FILL);
    }

    public void setEdgeColorPressed() {
        setColorButtonEvent(COLOR_TYPE_EDGE);
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

    public void deleteOneConnectorPressed() {
        ArrayList<DEObject> selected = getSelectedObjects();
        if (selected.size() != 2) {
            output.showMessage("Select only two objects");
            return;
        }

        if (deleteConnectorBetweenObjects(selected.get(0), selected.get(1))) {
            output.showMessage("Deleted connector");
        } else {
            output.showMessage("No connector to delete");
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
        } else if (selected.size() == 2) {
            if (addNewConnector(selected.get(0), selected.get(1))) output.showMessage("Added connector");
            else output.showMessage("There is already a connector there");
            return;
        } else if (selected.size() > 2) {
            output.showMessage("Please select only one or two objects ");
            return;
        }

        // selected.size == 1
        firstObjectToConnect = selected.get(0);
        output.showMessage("Select another object to connect to");
    }

    public void groupSelectedPressed() {
        ArrayList<DEObject> selected = getSelectedObjects();
        if (selected.size() < 2) {
            output.showMessage("You need at least 2 selected objects for grouping");
            return;
        }

        groupObjects(selected);
        output.showMessage("Selection was grouped");
    }

    public void ungroupSelectedPressed() {
        ArrayList<DEObject> selected = getSelectedObjects();
        if (selected.size() == 0) {
            output.showMessage("Nothing is selected");
            return;
        }

        if (ungroupObjects(selected)) output.showMessage("Selection was ungrouped");
        else output.showMessage("There was nothing to ungroup");
    }

    public void alignHorizontallyPressed() {
        doAlignment(false);
    }

    public void alignVerticallyPressed() {
        doAlignment(true);
    }

    private void doAlignment(boolean vertically) {
        ArrayList<DEObject> selected = getSelectedObjects();
        if (selected.size() < 2) {
            output.showMessage("Please select at least 2 objects");
            return;
        }

        alignObjects(selected, vertically);
        output.showMessage("Aligned objects horizontally");
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

        for (DEObjectShape shape : selectedShapes) {
            shape.setLabelText(text);
        }
        draw();
    }

    public void colorHexTyped(String s) {
        this.typedColor = s;
    }
}

