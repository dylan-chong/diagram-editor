import ecs100.UI;

import java.awt.*;

/**
 * Created by Dylan on 17/05/16.
 * <p>
 * A draggable rectangle that will appear when a
 * shape is selected, so that you can drag it to
 * resize the shape
 */
public class DENode implements DEDraggable {

    public static final Color COLOR = Color.GRAY;
    public static final double WIDTH = 10;
    public static final double HEIGHT = WIDTH;

    protected DEPoint point;
    protected DEBounds bounds;

    private DENodePositionUpdater positionUpdater;
    private DEDraggableBoundsCalculator boundsCalculator;

    public DENode(DEPoint point, DENodePositionUpdater positionUpdater) {
        this.positionUpdater = positionUpdater;
        setPoint(point);
    }

    public void draw() {
        UI.setColor(COLOR);
        UI.drawRect(bounds.getLeft(), bounds.getTop(),
                bounds.getWidth(), bounds.getHeight());
    }

    public void setPoint(DEPoint point) {
        this.point = point;
        this.bounds = new DEBounds(point.getX() - WIDTH / 2,
                point.getY() - WIDTH / 2, WIDTH, HEIGHT);
    }

    @Override
    public void pickUp(DEPoint mousePoint) {
        boundsCalculator = new DEDraggableBoundsCalculator(mousePoint, bounds);
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

    private void followOrPutDownAtMousePoint(DEPoint mousePoint) {
        DEPoint newPoint = boundsCalculator.getNewBoundsForMousePoint(mousePoint).getCenter();
        setPoint(newPoint);
        positionUpdater.positionWasUpdated(newPoint, this);
    }
    /**
     * @param point
     * @return Always returns this if the point is within this's
     * bounds
     */
    @Override
    public DEDraggable getDraggableDraggableAtPoint(DEPoint point) {
        if (bounds.pointIsWithinBounds(point)) return this;
        return null;
    }
}

