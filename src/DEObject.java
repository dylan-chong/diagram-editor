/**
 * Created by Dylan on 17/05/16.
 * <p>
 * Can be implemented for a group and
 * shape objects
 */
public abstract class DEObject implements DEDraggable {

    protected DEBounds bounds;

    private DENode[] nodes; //TODO LATER split into edge and corner nodes
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

    private void setBounds(DEBounds bounds) {
        this.bounds = bounds;
        resetAllNodePoints();
    }

    // Dragging shape around

    /**
     * @param point
     * @return Primarily, a node (if this object is selected),
     * otherwise itself if under the mouse, otherwise null
     */
    @Override
    public DEDraggable getDraggableDraggableAtPoint(DEPoint point) {
        if (isSelected()) {
            DENode nodeAtPoint = getNodeAtPoint(point);
            if (nodeAtPoint != null) return nodeAtPoint;
        }

        if (bounds.pointIsWithinBounds(point))
            return this;

        return null;
    }

    public void pickUp(DEPoint mousePoint) {
        assert isSelected : "Cannot pick up DEObject unless selected";
        assert pointIsWithinBounds(mousePoint) : "Can't pick up from a location not in bounds";
        pickUpRelativePoint = new DEPoint(mousePoint.getX() - bounds.getLeft(),
                mousePoint.getY() - bounds.getTop());
    }

    @Override
    public void followAlong(DEPoint mousePoint) {
        followOrPutDownAtMousePoint(mousePoint);
    }

    public void putDown(DEPoint mousePoint) {
        followOrPutDownAtMousePoint(mousePoint);
        pickUpRelativePoint = null;
    }

    private void followOrPutDownAtMousePoint(DEPoint mousePoint) {
        DEPoint newTopLeft = new DEPoint(mousePoint.getX() - pickUpRelativePoint.getX(),
                mousePoint.getY() - pickUpRelativePoint.getY());
        DEPoint newBottomRight = new DEPoint(newTopLeft.getX() + bounds.getWidth(),
                newTopLeft.getY() + bounds.getHeight());

        setBounds(new DEBounds(newTopLeft, newBottomRight));
    }

    /**
     * @param mousePoint
     * @return Returns true if mousePoint is within the bounds
     * of this object, but not if mousePoint is on one of the
     * nodes
     */
    private boolean pointIsWithinBounds(DEPoint mousePoint) {
        if (!bounds.pointIsWithinBounds(mousePoint)) return false;

        if (isSelected()) {
            DENode nodeAtPoint = getNodeAtPoint(mousePoint);
            if (nodeAtPoint != null) return false;
        }

        return true;
    }

    public boolean canSelectAtPoint(DEPoint point) {
        if (isSelected()) return false;
        if (bounds.pointIsWithinBounds(point)) return true;
        return false;
    }

    /**
     * @param point
     * @return The node at the point, but only if this
     * is selected
     */
    private DENode getNodeAtPoint(DEPoint point) {
        if (!isSelected()) return null;

        for (int n = 0; n < nodes.length; n++) {
            DENode node = nodes[n];
            if (node.getDraggableDraggableAtPoint(point) == node) {
                // Point is within node bounds
                return node;
            }
        }

        return null;
    }

}