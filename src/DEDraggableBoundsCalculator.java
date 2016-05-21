import java.io.Serializable;

/**
 * Created by Dylan on 19/05/16.
 * <p>
 * Calculates the new bounds that an object when, and after,
 * it is dragged.
 */
public class DEDraggableBoundsCalculator implements Serializable {

    private DEBounds originalBounds;
    private DEPoint originalMousePoint;

    public DEDraggableBoundsCalculator(DEPoint originalMousePoint, DEBounds originalBounds) {
        this.originalMousePoint = originalMousePoint;
        this.originalBounds = originalBounds;
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

        return new DEBounds(originalBounds.getLeft() + mouseXDiff,
                originalBounds.getTop() + mouseYDiff,
                originalBounds.getWidth(), originalBounds.getHeight());
    }
}
