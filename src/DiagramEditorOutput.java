import java.io.Serializable;

/**
 * Created by Dylan on 17/05/16.
 * <p>
 * Allows me to easily change how messages are displayed
 */
public interface DiagramEditorOutput extends Serializable {

    void showMessage(String msg);

    void showDebugMessage(String msg);

}
