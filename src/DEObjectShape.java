import java.awt.*;

/**
 * Created by Dylan on 17/05/16.
 * <p>
 * The base for the shape object
 */
public abstract class DEObjectShape extends DEObject {

    public static final Color DEFAULT_FILL_COLOR = null;
    public static final Color DEFAULT_EDGE_COLOR = Color.BLACK;

    protected DEBounds bounds;
    protected Color fillColor = DEFAULT_FILL_COLOR;
    protected Color edgeColor = DEFAULT_EDGE_COLOR;

    private DENode[] nodes;

    public DEObjectShape(DEBounds bounds) {
        this.bounds = bounds;
        resetAllNodePoints();
    }

    public void draw() {
        drawAllNodes();
    }

    public String toString() {
        return getClass().getName() + ": " + bounds.toString();
    }

    @Override
    public boolean pointIsWithinBounds(DEPoint point) {
        return bounds.pointIsWithinBounds(point);
    }

    @Override
    public void putDown(DEPoint mousePoint) {
        // TODO LATER
    }

    @Override
    public void pickUp(DEPoint mousePoint) {
        // TODO LATER
    }

    private void drawAllNodes() {
        for (DENode n : nodes) {
            n.draw();
        }
    }

    private void resetAllNodePoints() {
        DEPoint[] nodePoints = new DEPoint[]{
                // Corners
                bounds.getTopLeft(),
                bounds.getTopRight(),
                bounds.getBottomLeft(),
                bounds.getBottomRight(),

                // Edges
                bounds.getMidLeft(),
                bounds.getMidRight(),
                bounds.getMidTop(),
                bounds.getMidBottom(),
        };

        if (nodes == null) {
            nodes = new DENode[nodePoints.length];
            for (int n = 0; n < nodes.length; n++) {
                nodes[n] = new DENode(nodePoints[n]);
            }
            return;
        }
        for (int n = 0; n < nodes.length; n++) {
            nodes[n].setPoint(nodePoints[n]);
        }
    }
}
