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
            case "dragged":
                if (objectBeingDragged == null) {
                    // may set it to null
                    objectBeingDragged =
                            getObjectWithMainNodeAtPoint(mouseDownPosition);
                    objectBeingDragged.pickUp(mouseDownPosition);
                }
                break;

            case "pressed":
                mouseDownPosition = mousePoint;
                break;

            case "released":
                if (objectBeingDragged != null) {
                    objectBeingDragged.putDown(mousePoint);
                    objectBeingDragged = null;
                }
                mouseDownPosition = null;
                draw();
                break;

            case "clicked":
                attemptSelectAtPoint(mousePoint);
                mouseDownPosition = null;
                objectBeingDragged = null;
                break;
        }
    }

    public void attemptSelectAtPoint(DEPoint mousePoint) {
        deselectAllSelectedObjects();
        getObjectWithMainNodeAtPoint(mousePoint).setSelected(true);
        draw();
    }

    public DEObject getObjectWithMainNodeAtPoint(DEPoint mousePoint) {
        for (DEObject obj : deObjects) {
            if (obj.isOnMainNode(mousePoint)) {
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
