/**
 * Created by Dylan on 17/05/16.
 *
 * A draggable rectangle that will appear when a
 * shape is selected, so that you can drag it to
 * resize the shape
 */
public class DENode implements DEDraggable {


    @Override
    public void setTopLeft(DEPoint point) {

    }

    @Override
    public boolean pointIsOverThis(DEPoint point) {
        return false;
    }
}
