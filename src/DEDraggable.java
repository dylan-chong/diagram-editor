/**
 * Created by Dylan on 17/05/16.
 *
 * Functionality for something that can
 * be dragged to be moved on the screen
 */
public interface DEDraggable {

    void pickUp(DEPoint mousePoint);
    void followAlong(DEPoint mousePoint);
    void putDown(DEPoint mousePoint);

    boolean pointIsWithinBounds(DEPoint point);

    /**
     * Returns the object itself, or a different
     * draggable that is part of this object (e.g.
     * a node).
     */
    // TODO NEXT add a getDraggable method
}
