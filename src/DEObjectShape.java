/**
 * Created by Dylan on 17/05/16.
 *
 *
 */
public abstract class DEObjectShape implements DEObject {

    protected DEBounds bounds;

    public DEObjectShape(DEBounds bounds) {
        this.bounds = bounds;
    }

    public abstract void draw();

    public String toString() {
        return getClass().getName() + ": " + bounds.toString();
    }
}
