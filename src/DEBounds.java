/**
 * Created by Dylan on 17/05/16.
 * <p>
 * Rectangular bounds on the screen
 */
public class DEBounds {

    private DEPoint upperLeft, bottomRight;

    /**
     * Sets the upper left and bottom right
     * points based on pointA and pointB
     *
     * @param pointA
     * @param pointB
     */
    public DEBounds(DEPoint pointA, DEPoint pointB) {
        // TODO set upper left and bottom right points
    }

    /**
     * @param point
     * @return Returns true if the given point is within
     * the bounds
     */
    public boolean pointIsWithinBounds(DEPoint point) {
        // TODO
        return true;
    }

    public String toString() {
        return "[" + upperLeft.toString() + ", " + bottomRight + "]";
    }
}
