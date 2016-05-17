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
        UI.setWindowSize(1500, 950);

        DiagramEditorMain main = new DiagramEditorMain();

        UI.setDivider(0);
        UI.setImmediateRepaint(false);
    }

    private DiagramEditorMain() {
        DiagramEditor diagramEditor = new DiagramEditor(new DiagramEditorOutput() {
            @Override
            public void showMessage(String msg) {
                printMessage(msg);
            }

            @Override
            public void showDebugMessage(String msg) {
                showMessage("*** DEBUG *** " + msg);
            }
        });

        UI.setMouseMotionListener(diagramEditor::doMouse);
        UI.addButton("Add Rectangle", diagramEditor::addRectPressed);
        UI.addButton("Add Ellipse", diagramEditor::addEllipsePressed);
        UI.addButton("Delete Selected", diagramEditor::deletePressed);

        // TODO NEXT test node and shape drawing
    }

    private void printMessage(String msg) {
        UI.printMessage(msg);
    }
}
