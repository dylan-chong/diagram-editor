/**
 * Created by Dylan on 17/05/16.
 */
public abstract class DEShape {

    DEBounds bounds;

    public DEShape(DEBounds bounds) {

    }

    public abstract void draw();

    public String toString() {
        return getClass().getName() + ": " + bounds.toString();
    }
}
