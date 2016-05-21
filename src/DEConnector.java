import ecs100.UI;

import java.awt.*;
import java.io.Serializable;

/**
 * Created by Dylan on 20/05/16.
 */
public class DEConnector implements Serializable {

    public static final Color DEFAULT_COLOR = Color.BLACK;

    private Color color = DEFAULT_COLOR;
    private DEObject connectedObjectA;
    private DEObject connectedObjectB;

    public DEConnector(DEObject connectedObjectA, DEObject connectedObjectB) {
        this.connectedObjectA = connectedObjectA;
        this.connectedObjectB = connectedObjectB;
    }

    public DEObject getConnectedObjectA() {
        return connectedObjectA;
    }

    public DEObject getConnectedObjectB() {
        return connectedObjectB;
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

    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof DEConnector)) return false;

        if (this.getConnectedObjectA() == ((DEConnector) obj).getConnectedObjectA() &&
                this.getConnectedObjectB() == ((DEConnector) obj).getConnectedObjectB())
            return true;
        return false;
    }
}
