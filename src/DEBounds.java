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
        bottomRight = new DEPoint(x[1], y[1]);

        assert topLeft.getX() <= bottomRight.getX() : "Point sorting error";
        assert topLeft.getY() <= bottomRight.getY() : "Point sorting error";
    }

    public DEBounds(double x, double y, double w, double h) {
        this(new DEPoint(x, y), new DEPoint(x + w, y + h));
    }

    /**
     * Sorts two points to increasing order
     * inside a new array
     *
     * @param a
     * @param b
     * @return The two points
     */
    private double[] getSortedPoints(double a, double b) {
        if (a < b) return new double[]{a, b};
        return new double[]{b, a};
    }

    /**
     * @param point
     * @return Returns true if the given point is within
     * the bounds (not on the edges)
     */
    public boolean pointIsWithinBounds(DEPoint point) {
        if (point.getX() > bottomRight.getX()) return false;
        if (point.getX() < topLeft.getX()) return false;
        if (point.getY() > bottomRight.getY()) return false;
        if (point.getY() < topLeft.getY()) return false;
        return true; // TODO test
    }

    public DEPoint getTopLeft() {
        return topLeft;
    }

    public DEPoint getBottomRight() {
        return bottomRight;
    }

    public double getWidth() {
        return bottomRight.getX() - topLeft.getX();
    }

    public double getHeight() {
        return bottomRight.getY() - topLeft.getY();
    }

    public double getLeft() {
        return topLeft.getX();
    }

    public double getRight() {
        return bottomRight.getX();
    }

    public double getTop() {
        return topLeft.getY();
    }

    public double getBottom() {
        return bottomRight.getY();
    }

    public String toString() {
        return "[Top Left: " + topLeft.toString() + ", Bottom Right: " + bottomRight + "]";
    }
}
