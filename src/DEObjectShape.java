/**
 * Created by Dylan on 17/05/16.
 * <p>
 * The base for the shape object
 */
public abstract class DEObjectShape extends DEObject {

    protected DEBounds bounds;

    public DEObjectShape(DEBounds bounds) {
        this.bounds = bounds;
    }

    public abstract void draw();

    public String toString() {
        return getClass().getName() + ": " + bounds.toString();
    }

    @Override
    public boolean pointIsWithinBounds(DEPoint point) {
        return bounds.pointIsWithinBounds(point);
    }

    @Override
    public void putDown(DEPoint mousePoint) {
        // TODO LATER
    }

    @Override
    public void pickUp(DEPoint mousePoint) {
        // TODO LATER
    }


    // TODO AFTER add nodes
}
