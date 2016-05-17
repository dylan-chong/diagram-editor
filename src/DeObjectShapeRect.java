import ecs100.UI;

/**
 * Created by Dylan on 17/05/16.
 */
public class DeObjectShapeRect extends DEObjectShape {

    public DeObjectShapeRect(DEBounds bounds) {
        super(bounds);
    }

    @Override
    public void draw() {
        UI.drawRect(10, 20, 100, 200); // TODO test
    }

    @Override
    public void putDown(DEPoint mousePoint) {

    }

    @Override
    public void pickUp(DEPoint mousePoint) {

    }

    @Override
    public boolean pointIsOverThis(DEPoint point) {
        return false;
    }
}
