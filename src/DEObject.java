/**
 * Created by Dylan on 17/05/16.
 *
 * Can be implemented for a group and
 * shape objects
 */
public abstract class DEObject implements DEDraggable {

    protected DEBounds bounds;

    private DENode[] nodes; //TODO LATER split into edge and corner nodes
    private DEMovementNode movementNode;
    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void draw() {
        if (isSelected()) drawAllNodes();
    }

    protected void drawAllNodes() {
        for (DENode n : nodes) {
            n.draw();
        }
    }

    protected void resetAllNodePoints() {
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
            movementNode = new DEMovementNode(bounds);

            nodes = new DENode[nodePoints.length];
            for (int n = 0; n < nodes.length; n++) {
                nodes[n] = new DENode(nodePoints[n]);
            }

            return;
        }

        movementNode.fitToBounds(bounds);

        for (int n = 0; n < nodes.length; n++) {
            nodes[n].setPoint(nodePoints[n]);
        }
    }

}