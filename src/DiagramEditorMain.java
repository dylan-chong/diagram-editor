import ecs100.UI;

/**
 * Created by Dylan on 17/05/16.
 * <p>
 * Sets up all the UI components, and deals
 * with all the UI events, relaying them
 * to the diagramEditor instance.
 */
public class DiagramEditorMain {

    private DiagramEditor diagramEditor;

    private DiagramEditorMain() {
        diagramEditor = getNewDiagramEditor();

        UI.setMouseMotionListener(this::doMouse);
        UI.addButton("Instructions", this::displayInstructionsPressed);
        UI.addButton("Clear All", this::clearAllPressed);
        UI.addButton("Add Rectangle", this::addRectPressed);
        UI.addButton("Add Ellipse", this::addEllipsePressed);
        UI.addButton("Add Hexagon", this::addHexagonPressed);
        UI.addButton("Add Connector", this::addConnectorPressed);
        UI.addButton("Delete Selected", this::deletePressed);
        UI.addTextField("Selected Object Text", this::selectedObjectTextTyped);
    }

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

    private DiagramEditor getNewDiagramEditor() {
        return new DiagramEditor(new DiagramEditorOutput() {
            @Override
            public void showMessage(String msg) {
                printMessage(msg);
            }

            @Override
            public void showDebugMessage(String msg) {
                showMessage("*** DEBUG *** " + msg);
            }
        });
    }

    private void printMessage(String msg) {
        UI.printMessage(msg);
    }

    // ************************* UI EVENTS ************************* //

    private void doMouse(String action, double x, double y) {
        diagramEditor.doMouse(action, x, y);
    }

    private void displayInstructionsPressed() {
        UI.println("Welcome to Diagram Editor!\n" +
                "\n" +
                "Select and object by clicking on it. Deselect by clicking on the background.\n" +
                "For some reason, the UIMouseMotionListener doesn't always register a \"clicked\"\n" +
                "\n" +
                "event, so you may have to try clicking twice.\n" +
                "You can drag around objects and resize them like you can in Microsoft PowerPoint,\n" +
                "but the objects must be selected.");
        UI.setDivider(0.9);
    }

    private void deletePressed() {
        diagramEditor.deletePressed();
    }

    private void clearAllPressed() {
        diagramEditor = getNewDiagramEditor();
        UI.clearGraphics();
        UI.repaintAllGraphics();
    }

    private void addRectPressed() {
        diagramEditor.addRectPressed();
    }

    private void addEllipsePressed() {
        diagramEditor.addEllipsePressed();
    }

    private void addHexagonPressed() {
        diagramEditor.addHexagonPressed();
    }

    private void addConnectorPressed() {
        diagramEditor.addConnectorPressed();
    }

    private void selectedObjectTextTyped(String text) {
        diagramEditor.selectedObjectTextTyped(text);
    }
}