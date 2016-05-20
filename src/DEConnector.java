import ecs100.UI;

import java.awt.*;

/**
 * Created by Dylan on 20/05/16.
 */
public class DEConnector {

    public static final Color DEFAULT_COLOR = Color.BLACK;

    private Color color = DEFAULT_COLOR;

    private DEObject connectedObjectA;
    private DEObject connectedObjectB;

    public DEConnector(DEObject connectedObjectA, DEObject connectedObjectB) {
        this.connectedObjectA = connectedObjectA;
        this.connectedObjectB = connectedObjectB;
    }

    public void draw() {
        UI.setColor(color);
        DEPoint pa = connectedObjectA.getBounds().getCenter();
        DEPoint pb = connectedObjectB.getBounds().getCenter();
        UI.drawLine(pa.getX(), pa.getY(), pb.getX(), pb.getY());
    }

    public boolean isConnectedToObject(DEObject obj) {
        if (connectedObjectA == obj) return true; // intentional pointer comparisons
        if (connectedObjectB == obj) return true;
        return false;
    }
}
