import ecs100.UI;

/**
 * Created by Dylan on 17/05/16.
 *
 * Sets up all the UI components
 */
public class DiagramEditorMain {

    public static void main(String[] args) {
        if (true) {
            ManualTest.runTests();
        }

        UI.initialise();
        UI.setWindowSize(1650, 1000);
        UI.setDivider(0);

        DiagramEditor diagramEditor = new DiagramEditor();
    }

    // TODO LATER add buttons
}
