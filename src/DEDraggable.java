/**
 * Created by Dylan on 17/05/16.
 * <p>
 * Functionality for something that can
 * be dragged to be moved on the screen
 */
public interface DEDraggable {

    void pickUp(DEPoint mousePoint);

    void followAlong(DEPoint mousePoint);

    void putDown(DEPoint mousePoint);

    /**
     * @param point
     * @return Returns the object itself, or a different
     * draggable that is part of this object (e.g.
     * a node). Some Draggables may not want to be
     * dragged sometimes - such as objects that
     * haven't been selected yet - so it may return
     * null even if there is a Draggable at the given
     * point.
     */
    DEDraggable getDraggableDraggableAtPoint(DEPoint point);
}