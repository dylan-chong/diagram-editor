/**
 * Created by Dylan on 17/05/16.
 *
 * Functionality for something that can
 * be dragged to be moved on the screen
 */
public interface DEDraggable {

    void putDown(DEPoint mousePoint);
    void pickUp(DEPoint mousePoint);
    boolean pointIsWithinBounds(DEPoint point);

}
