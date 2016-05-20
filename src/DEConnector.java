import java.awt.*;

/**
 * Created by Dylan on 20/05/16.
 */
public class DEConnector {

    public static final Color DEFAULT_COLOR = Color.BLACK;

    private Color color = DEFAULT_COLOR;

    private DEObject connectedObjectA;
    private DEObject connectedObjectB;

    public void draw() {
        // TODO: 20/05/16
    }

    public boolean isConnectedToObject(DEObject obj) {
        if (connectedObjectA == obj) return true; // intentional pointer comparisons
        if (connectedObjectB == obj) return true;
        return false;
    }
}
