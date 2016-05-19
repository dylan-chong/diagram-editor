/**
 * Created by Dylan on 20/05/16.
 */
public class DEObjectNodeCollection {

    public static final int NODE_TOP_LEFT = 0;
    public static final int NODE_TOP_RIGHT = 1;
    public static final int NODE_BOTTOM_LEFT = 2;
    public static final int NODE_BOTTOM_RIGHT = 3;

    public static final int NODE_MID_LEFT = 4;
    public static final int NODE_MID_RIGHT = 5;
    public static final int NODE_MID_TOP = 6;
    public static final int NODE_MID_BOTTOM = 7;

    private DENode[] nodes; //TODO LATER split into edge and corner nodes
    private DEBounds objectBounds;
    private DEObjectBoundsUpdater boundsUpdater;

    public DEObjectNodeCollection(DEBounds objectBounds, DEObjectBoundsUpdater boundsUpdater) {
        this.boundsUpdater = boundsUpdater;
        setObjectBounds(objectBounds);
    }

    /**
     * The order of the points matters. It follows the order
     * of the NODE_x_x constants.
     *
     * @param bounds
     * @return
     */
    private static DEPoint[] getNodePoints(DEBounds bounds) {
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
        return nodePoints;
    }

    public void draw() {
        for (DENode n : nodes) {
            n.draw();
        }
    }

    public void setObjectBounds(DEBounds bounds) {
        this.objectBounds = bounds;
        updateNodePoints();
    }

    public DENode getNodeAtPoint(DEPoint point) {
        for (int n = 0; n < nodes.length; n++) {
            DENode node = nodes[n];
            if (node.getDraggableDraggableAtPoint(point) == node) {
                // Point is within node bounds
                return node;
            }
        }
        return null;
    }

    private void createNodes(DEPoint[] nodePoints) {
        nodes = new DENode[nodePoints.length];

        DENodePositionUpdater positionUpdate = (DENode node) -> nodeWasMoved(node);

        for (int n = 0; n < nodes.length; n++) {
            nodes[n] = new DENode(nodePoints[n], positionUpdate);
        }
    }

    private void nodeWasMoved(DENode node) {
        resize(node);

        DEBounds newBounds = new DEBounds(Math.random() * 100, Math.random() * 100, Math.random() * 100, Math.random() * 100);
        boundsUpdater.updateBounds(newBounds);
    }

    private void updateNodePoints() {
        DEPoint[] nodePoints = getNodePoints(objectBounds);

        if (nodes == null) {
            createNodes(nodePoints);
            return;
        }

        for (int n = 0; n < nodes.length; n++) {
            nodes[n].setPoint(nodePoints[n]);
        }
    }
}
