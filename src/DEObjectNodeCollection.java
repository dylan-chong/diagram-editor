/**
 * Created by Dylan on 20/05/16.
 */
public class DEObjectNodeCollection {

    private static final int NODE_TOP_LEFT = 0;
    private static final int NODE_TOP_RIGHT = 1;
    private static final int NODE_BOTTOM_LEFT = 2;
    private static final int NODE_BOTTOM_RIGHT = 3;

    private static final int NODE_MID_LEFT = 4;
    private static final int NODE_MID_RIGHT = 5;
    private static final int NODE_MID_TOP = 6;
    private static final int NODE_MID_BOTTOM = 7;


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

    /**
     * @param node
     * @return Returns one of the NODE_x_x constants that matches
     * the given node. Returns -1 if the constant can't be found
     */
    private static int getNodeConstant(DENode node) {

    }

    /**
     * @param constant
     * @return Returns the constant for the node at the opposite
     * end of the bounds.
     */
    private static int getOppositeNodeConstant(int constant) {
        
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

    /**
     * Calculates the new bounds and tells this instance's
     * owner to update the size based on these new bounds.
     * This method doesn't call setObjectBounds() directly;
     * when calling boundsUpdater.updateBounds(), that calls
     * the setter anyway.
     * @param node
     */
    private void resize(DENode node) {
        int nodeConstant = getNodeConstant(node);
        int oppositeConstant = getOppositeNodeConstant(nodeConstant);

        DEPoint nodePoint = node.getPoint();
        DEPoint oppositeNodePoint = nodes[oppositeConstant].getPoint();

        DEBounds newBounds = new DEBounds(nodePoint, oppositeNodePoint);
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
