/**
 * Created by Dylan on 19/05/16.
 * <p>
 * Calculates the new bounds that an object when, and after,
 * it is dragged.
 */
public class DEDraggableBoundsCalculator {

    // TODO NEXT move the DEObject new position calculation code here
    // TODO AFTER use this class in DENode

    private DEBounds currentBounds;
    private DEPoint originalMousePoint, pickUpRelativePoint;

    public DEDraggableBoundsCalculator(DEPoint originalMousePoint, DEBounds currentBounds) {
        this.originalMousePoint = originalMousePoint;
        this.currentBounds = currentBounds;
        pickUpRelativePoint = new DEPoint(originalMousePoint.getX() - currentBounds.getLeft(),
                originalMousePoint.getY() - currentBounds.getTop());

    }

    /**
     * Gets new bounds for the object being dragged (assumes size doesn't
     * change)
     * @param mousePoint
     * @return
     */
    public DEBounds getNewBoundsForMousePoint(DEPoint mousePoint) {
        DEPoint newTopLeft = new DEPoint(mousePoint.getX() - pickUpRelativePoint.getX(),
                mousePoint.getY() - pickUpRelativePoint.getY());
        DEPoint newBottomRight = new DEPoint(newTopLeft.getX() + currentBounds.getWidth(),
                newTopLeft.getY() + currentBounds.getHeight());
        return new DEBounds(newTopLeft, newBottomRight);
    }
}
