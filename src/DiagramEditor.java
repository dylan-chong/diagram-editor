import ecs100.UI;

import java.util.ArrayList;

/**
 * Created by Dylan on 17/05/16.
 * <p>
 * The controller for the app
 */
public class DiagramEditor {

    /**
     * Contains DEGroup and DEObjectShape objects. They
     * are sorted from top to bottom (where the top is
     * the closest to the user).
     */
    private ArrayList<DEObject> deObjects = new ArrayList<>();
    private DiagramEditorOutput output;

    private DEPoint mouseDownPosition;
    private DEObject objectBeingDragged; // TODO LATER make this the node being dragged
    private boolean hasCheckedForObjectBeingDragged;

    public DiagramEditor(DiagramEditorOutput output) {
        draw();
        this.output = output;
    }

    private void draw() {
        UI.clearGraphics();
        deObjects.forEach(DEObject::draw);
        UI.repaintAllGraphics();
    }

    // ************************* OBJECTS AND SELECTION ************************* //

    private void addNewShape(DEObject obj) {
        obj.setSelected(true);
        deObjects.add(0, obj);

        draw();
    }

    /**
     * @return Returns true if it could delete any shapes, false
     * if there are no shapes to delete
     */
    private boolean deleteAllSelectedObjects() {
        ArrayList<DEObject> selected = getSelectedObjects();
        if (selected.size() == 0) return false;

        deObjects.removeAll(selected);

        draw();
        return true;
    }

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

    // ************************* EVENTS ************************* //

    public void doMouse(String action, double x, double y) {
        DEPoint mousePoint = new DEPoint(x, y);

        switch (action) {
            case "pressed":
                mouseDownPosition = mousePoint;
                hasCheckedForObjectBeingDragged = false;
                break;

            case "dragged":
                if (objectBeingDragged == null) {
                    if (hasCheckedForObjectBeingDragged) return;
                    // may set it to null
                    objectBeingDragged =
                            getObjectWithMainNodeAtPoint(mouseDownPosition, true);

                    if (objectBeingDragged != null) {
                        objectBeingDragged.pickUp(mouseDownPosition);
                    }

                    hasCheckedForObjectBeingDragged = true;
                } else {
                    // TODO LATER MAYBE move the object with the mouse
                }
                break;

            case "released":
                if (objectBeingDragged != null) {
                    objectBeingDragged.putDown(mousePoint);
                    objectBeingDragged = null;
                }
                mouseDownPosition = null;
                hasCheckedForObjectBeingDragged = false;
                draw();
                break;

            // Mouse pressed and then released without drag
            case "clicked":
                if (!attemptSelectAtPoint(mousePoint)) {
                    deselectAllSelectedObjects();
                }
                mouseDownPosition = null;
                objectBeingDragged = null;
                break;
        }
    }

    /**
     * @param mousePoint
     * @return True if an object could be selected
     */
    public boolean attemptSelectAtPoint(DEPoint mousePoint) {
        deselectAllSelectedObjects();

        DEObject obj = getObjectWithMainNodeAtPoint(mousePoint, false);
        if (obj == null) return false;

        obj.setSelected(true);
        draw();
        return true;
    }

    public DEObject getObjectWithMainNodeAtPoint(DEPoint mousePoint, boolean mustBeSelected) {
        for (DEObject obj : deObjects) {
            if (obj.isOnMainNode(mousePoint)) {
                if (!mustBeSelected || obj.isSelected())
                    return obj;
            }
        }

        return null;
    }

    public void addRectPressed() {
        deselectAllSelectedObjects();
        addNewShape(new DEObjectShapeRect(
                new DEBounds(50 + 100 * Math.random(), 50 + 100 * Math.random(), 200, 250))
        );
    }

    public void addEllipsePressed() {
        deselectAllSelectedObjects();
        addNewShape(new DEObjectShapeEllipse(
                new DEBounds(50 + 100 * Math.random(), 50 + 100 * Math.random(), 200, 180))
        );
    }

    public void deletePressed() {
        if (deleteAllSelectedObjects()) {
            output.showMessage("Deleted shapes");
        } else {
            output.showMessage("Couldn't delete shapes");
        }
    }
}
