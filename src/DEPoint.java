import java.io.Serializable;

/**
 * Created by Dylan on 17/05/16.
 * <p>
 * A Point on the screen
 */
public class DEPoint implements Serializable {

    private double x, y;

    public DEPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
