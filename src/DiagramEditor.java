import ecs100.UI;

import java.util.ArrayList;

/**
 * Created by Dylan on 17/05/16.
 * <p>
 * The controller for the app
 */
public class DiagramEditor {

    /**
     * Contains DEGroup and DEObjectShape objects
     */
    ArrayList<DEObject> deObjects = new ArrayList<>();
    DiagramEditorOutput output;

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
        deObjects.add(obj);

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
                break;
            case "moved":
                break;
            case "pressed":
                break;
            case "released":
                break;
            case "clicked":
                break;
        }
    }

    public void addRectPressed() {
        deselectAllSelectedObjects();
        addNewShape(new DeObjectShapeRect(
                new DEBounds(10, 20, 400, 50))
        );
    }

    public void addEllipsePressed() {
        deselectAllSelectedObjects();
        addNewShape(new DeObjectShapeEllipse(
                new DEBounds(10, 20, 400, 50))
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
