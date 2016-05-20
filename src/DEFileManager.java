import java.io.*;

/**
 * Created by Dylan on 21/05/16.
 */
public class DEFileManager {

    public static boolean saveDiagram(DiagramEditor diagramEditor, File file) throws IOException {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file))) {
            objectOutputStream.writeObject(diagramEditor);
        } catch (IOException e) {
            throw e;
        }

        return true;
    }

    public static DiagramEditor loadDiagramEditor(File file) throws IOException, ClassNotFoundException {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file))) {
            Object object = objectInputStream.readObject();
            return (DiagramEditor) object;
        } catch (IOException e) {
            throw e;
        }
    }

}
