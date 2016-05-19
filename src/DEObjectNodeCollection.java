/**
 * Created by Dylan on 20/05/16.
 */
public class DEObjectNodeCollection {

    private DENode[] nodes; //TODO LATER split into edge and corner nodes
    private DEBounds objectBounds;
    private DEObjectBoundsUpdater boundsUpdater;

    public DEObjectNodeCollection(DEBounds objectBounds, DEObjectBoundsUpdater boundsUpdater) {
        this.boundsUpdater = boundsUpdater;
        setObjectBounds(objectBounds);
    }

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

        DENodePositionUpdater positionUpdate = (DEPoint point, DENode node) -> nodeWasMoved(point, node);

        for (int n = 0; n < nodes.length; n++) {
            nodes[n] = new DENode(nodePoints[n], positionUpdate);
        }
    }

    private void nodeWasMoved(DEPoint nodePoint, DENode node) {
        System.out.println("Update position " + nodePoint.toString());

        // TODO AFTER have proper position updates

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