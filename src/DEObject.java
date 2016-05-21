import java.io.Serializable;

/**
 * Created by Dylan on 17/05/16.
 * <p>
 * Can be implemented for a group and
 * shape objects
 */
public abstract class DEObject implements DEDraggable, Serializable {

    private DEBounds bounds;
    private DEObjectNodeCollection nodeCollection;
    private boolean isSelected;
    private DEDraggableBoundsCalculator boundsCalculator;

    public DEObject(DEBounds bounds) {
        this.bounds = bounds;
        nodeCollection = new DEObjectNodeCollection(bounds,
                newBounds -> setBounds(newBounds));
    }

    public DEDraggableBoundsCalculator getBoundsCalculator() {
        return boundsCalculator;
    }

    protected DEBounds getBounds() {
        return bounds;
    }

    protected void setBounds(DEBounds bounds) {
        this.bounds = bounds;
        nodeCollection.setObjectBounds(bounds);
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void draw() {
        if (isSelected()) nodeCollection.draw();
    }

    // ******************** NODES ********************

    /**
     * @param point
     * @return The node at the point, but only if this
     * is selected
     */
    private DENode getNodeAtPoint(DEPoint point) {
        if (!isSelected()) return null;
        return nodeCollection.getNodeAtPoint(point); // may be null
    }

    // ******************** DRAGGING SHAPE AROUND ********************

    /**
     * @param point
     * @return False if the object is not selected. Otherwise,
     * it will primarily return a node, otherwise itself. (Nodes
     * overlap slightly with the object)
     */
    @Override
    public DEDraggable getDraggableDraggableAtPoint(DEPoint point) {
        if (!isSelected()) return null;

        DENode nodeAtPoint = getNodeAtPoint(point);
        if (nodeAtPoint != null) return nodeAtPoint;

        if (bounds.pointIsWithinBounds(point))
            return this;

        return null;
    }

    @Override
    public void pickUp(DEPoint mousePoint) {
        assert isSelected : "Cannot pick up DEObject unless selected";
        assert pointIsWithinBounds(mousePoint) : "Can't pick up from a location not in bounds";
        boundsCalculator = getNewBoundsCalculator(mousePoint);
    }

    @Override
    public void followAlong(DEPoint mousePoint) {
        followOrPutDownAtMousePoint(mousePoint);
    }

    @Override
    public void putDown(DEPoint mousePoint) {
        followOrPutDownAtMousePoint(mousePoint);
        boundsCalculator = null;
    }

    protected DEDraggableBoundsCalculator getNewBoundsCalculator(DEPoint mousePoint) {
        return new DEDraggableBoundsCalculator(mousePoint, bounds);
    }

    protected void followOrPutDownAtMousePoint(DEPoint mousePoint) {
        setBounds(boundsCalculator.getNewBoundsForMousePoint(mousePoint));
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
        return bounds.pointIsWithinBounds(point);
    }

}