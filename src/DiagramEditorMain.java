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
        UI.setWindowSize(1000, 750);

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
        UI.addButton("Instructions", this::displayInstructions);
        UI.addButton("Add Rectangle", diagramEditor::addRectPressed);
        UI.addButton("Add Ellipse", diagramEditor::addEllipsePressed);
        UI.addButton("Delete Selected", diagramEditor::deletePressed);
    }

    private void printMessage(String msg) {
        UI.printMessage(msg);
    }

    private void displayInstructions() {
        UI.println("Welcome to Diagram Editor!\n" +
                "\n" +
                "Select and object by clicking on it. Deselect by clicking on the background.\n" +
                "For some reason, the UIMouseMotionListener doesn't always register a \"clicked\"\n" +
                "event, so you may have to try clicking twice.\n" +
                "\n" +
                "You can drag around objects and resize them like you can in Microsoft PowerPoint,\n" +
                "but the objects must be selected.");
        UI.setDivider(0.9);
    }
}
