import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Dylan on 19/05/16.
 * <p>
 * Calculates the new bounds that an object when, and after,
 * it is dragged.
 */
public class DEDraggableBoundsCalculator implements Serializable {

    private DEBounds originalBounds;
    private DEPoint originalMousePoint;
    private ArrayList<DEBounds> originalObjectBoundses;

    public DEDraggableBoundsCalculator(DEPoint originalMousePoint, DEBounds originalBounds) {
        this.originalMousePoint = originalMousePoint;
        this.originalBounds = originalBounds;
    }

    public DEDraggableBoundsCalculator(DEPoint originalMousePoint, DEBounds originalGroupBounds,
                                       ArrayList<DEBounds> originalObjectBounds) {
        this.originalMousePoint = originalMousePoint;
        this.originalBounds = originalGroupBounds;
        this.originalObjectBoundses = originalObjectBounds;
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

    public ArrayList<DEBounds> getNewObjectsBoundsForMousePoint(DEPoint mousePoint) {
        assert originalObjectBoundses != null : "You cannot use this method unless" +
                "you use the 3 argument constructor;";

        double mouseXDiff = mousePoint.getX() - originalMousePoint.getX();
        double mouseYDiff = mousePoint.getY() - originalMousePoint.getY();

        ArrayList<DEBounds> newBoundses = new ArrayList<>(originalObjectBoundses.size());
        for (int b = 0; b < originalObjectBoundses.size(); b++) {
            DEBounds original = originalObjectBoundses.get(b);
            DEBounds newObjectBounds = new DEBounds(original.getLeft() + mouseXDiff,
                    original.getTop() + mouseYDiff, original.getWidth(), original.getHeight());
            newBoundses.add(newObjectBounds);
        }

        return newBoundses;
    }
}
