/**
 * Created by Dylan on 17/05/16.
 * <p>
 * Can be implemented for a group and
 * shape objects
 */
public abstract class DEObject implements DEDraggable {

    protected DEBounds bounds;

    private DENode[] nodes; //TODO LATER split into edge and corner nodes
    private DEMainNode mainNode;
    private boolean isSelected;

    /**
     * When the object has just started being dragged,
     * a mousePoint is passed to this.pickUp. The
     * pickUpRelativePoint is of the mouse to the top left
     * of this object's top left
     */
    private DEPoint pickUpRelativePoint;

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
        mainNode.draw();
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
            mainNode = new DEMainNode(bounds);

            nodes = new DENode[nodePoints.length];
            for (int n = 0; n < nodes.length; n++) {
                nodes[n] = new DENode(nodePoints[n]);
            }

            return;
        }

        mainNode.fitToBounds(bounds);

        for (int n = 0; n < nodes.length; n++) {
            nodes[n].setPoint(nodePoints[n]);
        }
    }

    private void setBounds(DEBounds bounds) {
        this.bounds = bounds;
        resetAllNodePoints();
    }

    public boolean isOnMainNode(DEPoint mousePoint) {
        return mainNode.pointIsWithinBounds(mousePoint);
    }

    // Linked with mainNode (moving shape)

    public void putDown(DEPoint mousePoint) {
//        mainNode.putDown(mousePoint); TODO make the mainnode move with the mouse
        DEPoint newTopLeft = new DEPoint(mousePoint.getX() - pickUpRelativePoint.getX(),
                mousePoint.getY() - pickUpRelativePoint.getY());
        DEPoint newBottomRight = new DEPoint(newTopLeft.getX() + bounds.getWidth(),
                newTopLeft.getY() + bounds.getHeight());

        setBounds(new DEBounds(newTopLeft, newBottomRight));

        System.out.println("pick up");
    }

    public void pickUp(DEPoint mousePoint) {
        pickUpRelativePoint = new DEPoint(mousePoint.getX() - bounds.getLeft(),
                mousePoint.getY() - bounds.getTop());
        System.out.println("pick up");
    }

    public boolean pointIsWithinBounds(DEPoint point) {
        return mainNode.pointIsWithinBounds(point);
    }

}