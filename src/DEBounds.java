/**
 * Created by Dylan on 17/05/16.
 * <p>
 * Rectangular bounds on the screen
 */
public class DEBounds {

    private DEPoint topLeft, bottomRight;

    /**
     * Sets the topLeft and bottomRight
     * points based on pointA and pointB
     *
     * @param pointA
     * @param pointB
     */
    public DEBounds(DEPoint pointA, DEPoint pointB) {
        double[] x = getSortedPoints(pointA.getX(), pointB.getX());
        double[] y = getSortedPoints(pointA.getY(), pointB.getY());
        topLeft = new DEPoint(x[0], y[0]);
    }

    /**
     * Sorts two points to increasing order
     * inside a new array
     * @param a
     * @param b
     * @return The two points
     */
    private double[] getSortedPoints(double a, double b) {
        if (a < b) return new double[] {a, b};
        return new double[] {b, a};
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
        return "[Top Left: " + topLeft.toString() + ", Bottom Right: " + bottomRight + "]";
    }
}
