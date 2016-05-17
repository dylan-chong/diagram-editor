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

    }

    public void doMouse(String action, double x, double y) {
        UI.println(action + "\n");
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

}
