import ecs100.UI;
import ecs100.UIFileChooser;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

/**
 * Created by Dylan on 17/05/16.
 * <p>
 * Sets up all the UI components, and deals
 * with all the UI events, relaying them
 * to the diagramEditor instance.
 */
public class DiagramEditorMain implements Serializable {

    private DiagramEditor diagramEditor;

    private DiagramEditorMain() {
        diagramEditor = getNewDiagramEditor();

        UI.setMouseMotionListener(this::doMouse);
        UI.addButton("Instructions", this::displayInstructionsPressed);
        UI.addButton("Save", this::savePressed);
        UI.addButton("Load", this::loadPressed);
        UI.addButton("Clear All", this::clearAllPressed);
        UI.addButton("Add Rectangle", this::addRectPressed);
        UI.addButton("Add Ellipse", this::addEllipsePressed);
        UI.addButton("Add Hexagon", this::addHexagonPressed);
        UI.addButton("Delete Selected", this::deletePressed);
        UI.addButton("Select another", this::selectAnotherPressed);
        UI.addButton("Add Connector", this::addConnectorPressed);
        UI.addButton("Delete One Connector", this::deleteOneConnectorPressed);
        UI.addButton("Delete Connectors", this::deleteConnectorsPressed);
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
        return new DiagramEditor(getNewOutputForDiagramEditor());
    }

    private DiagramEditorOutput getNewOutputForDiagramEditor() {
        return new DiagramEditorOutput() {
            @Override
            public void showMessage(String msg) {
                printMessage(msg);
            }

            @Override
            public void showDebugMessage(String msg) {
                showMessage("*** DEBUG *** " + msg);
            }
        };
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

    private void selectAnotherPressed() {
        diagramEditor.selectAnotherPressed();
    }

    private void deletePressed() {
        diagramEditor.deletePressed();
    }

    private void deleteOneConnectorPressed() {
        diagramEditor.deleteOneConnectorPressed();
    }

    private void deleteConnectorsPressed() {
        diagramEditor.deleteConnectorsPressed();
    }

    private void selectedObjectTextTyped(String text) {
        diagramEditor.selectedObjectTextTyped(text);
    }

    // ************************* SAVING AND LOADING UI EVENTS ************************* //

    private void savePressed() {
        String path = UIFileChooser.save("Choose where to save the diagram");
        if (path == null) {
            printMessage("Save cancelled");
            return;
        }

        File file = new File(path);
        try {
            diagramEditor.setOutput(null); // prevents needing to serialise Main
            DEFileManager.saveDiagram(diagramEditor, file);
            printMessage("Saved diagram");
            diagramEditor.setOutput(getNewOutputForDiagramEditor()); // put back a new output
            return;
        } catch (IOException e) {
            e.printStackTrace();
        }

        printMessage("Error: Could not save");
    }

    private void loadPressed() {
        String path = UIFileChooser.open("Open a diagram");
        if (path == null) {
            printMessage("Load cancelled");
            return;
        }

        File file = new File(path);
        try {
            DiagramEditor de = DEFileManager.loadDiagramEditor(file);
            diagramEditor = de;
            diagramEditor.setOutput(getNewOutputForDiagramEditor());
            diagramEditor.draw();
            printMessage("Loaded diagram");
            return;
        } catch (Exception e) {
            e.printStackTrace();
            printMessage("Error loading diagram");
        }
    }
}