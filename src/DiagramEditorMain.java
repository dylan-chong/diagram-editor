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

        DiagramEditor diagramEditor = new DiagramEditor();

        UI.initialise();
        UI.setWindowSize(1650, 1000);
        UI.setDivider(0);
        UI.setImmediateRepaint(false);



        UI.setMouseMotionListener(diagramEditor::doMouse);
        UI.addButton("Add Rect", diagramEditor::addRectPressed);

        UI.addButton("Delete Shape", diagramEditor::deletePressed);
    }

}
