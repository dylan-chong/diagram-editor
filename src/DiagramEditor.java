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

    public DiagramEditor() {
        draw();
    }

    private void draw() {
        UI.clearGraphics();
        deObjects.forEach(DEObject::draw);
        UI.repaintAllGraphics();
    }

    // ************************* OBJECTS AND SELECTION ************************* //

    public void addNewShape(DEObject obj) {
        obj.setSelected(true);
        deObjects.add(obj);

        draw();
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
        addNewShape(new DeObjectShapeRect(
                new DEBounds(10, 20, 400, 50))
        );
    }

    public void addEllipsePressed() {
        addNewShape(new DeObjectShapeEllipse(
                new DEBounds(10, 20, 400, 50))
        );
    }

    public void deletePressed() {
        // TODO LATER
    }
}
