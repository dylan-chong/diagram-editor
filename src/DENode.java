import ecs100.UI;

import java.awt.*;

/**
 * Created by Dylan on 17/05/16.
 * <p>
 * A draggable rectangle that will appear when a
 * shape is selected, so that you can drag it to
 * resize the shape
 */
public class DENode implements DEDraggable {

    public static final Color COLOR = Color.GRAY;
    public static final double WIDTH = 10;
    public static final double HEIGHT = WIDTH;

    protected DEPoint point;
    protected DEBounds bounds;

    public DENode(DEPoint point) {
        setPoint(point);
    }

    public void draw() {
        double left = point.getX() - (WIDTH / 2);
        double top = point.getY() - (HEIGHT / 2);
        UI.setColor(COLOR);
        UI.drawRect(left, top, WIDTH, HEIGHT);
    }

    public void setPoint(DEPoint point) {
        this.point = point;
        this.bounds = new DEBounds(point.getX() - WIDTH / 2,
                point.getY() - WIDTH / 2, WIDTH, HEIGHT);
    }

    @Override
    public void pickUp(DEPoint mousePoint) {
// TODO: 17/05/16 copy from object class
    }

    @Override
    public void followAlong(DEPoint mousePoint) {
        // TODO
    }

    @Override
    public void putDown(DEPoint mousePoint) {
        // TODO: 17/05/16
    }

    /**
     * @param point
     * @return Always returns this if the point is within this's
     * bounds
     */
    @Override
    public DEDraggable getDraggableDraggableAtPoint(DEPoint point) {
        if (bounds.pointIsWithinBounds(point)) return this;
        return null;
    }
}

