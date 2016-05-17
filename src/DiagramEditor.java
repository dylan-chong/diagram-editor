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

        //TODO REMOVE
        DeObjectShapeRect rect = new DeObjectShapeRect(
                new DEBounds(10, 20, 400, 50)
        );
        rect.draw();
    }

}
