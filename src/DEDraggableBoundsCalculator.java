/**
 * Created by Dylan on 19/05/16.
 * <p>
 * Calculates the new bounds that an object when, and after,
 * it is dragged.
 */
public class DEDraggableBoundsCalculator {

    private DEBounds currentBounds;
    private DEPoint originalMousePoint, pickUpRelativePoint;

    public DEDraggableBoundsCalculator(DEPoint originalMousePoint, DEBounds currentBounds) {
        this.originalMousePoint = originalMousePoint;
        this.currentBounds = currentBounds;
    }

    /**
     * Gets new bounds for the object being dragged (assumes size doesn't
     * change)
     *
     * @param mousePoint
     * @return
     */
    public DEBounds getNewBoundsForMousePoint(DEPoint mousePoint) {
        double mouseXDiff = mousePoint.getX() - originalMousePoint.getX();
        double mouseYDiff = mousePoint.getY() - originalMousePoint.getY();

        return new DEBounds(currentBounds.getLeft() + mouseXDiff,
                currentBounds.getTop() + mouseYDiff,
                currentBounds.getWidth(), currentBounds.getHeight());
    }
}
