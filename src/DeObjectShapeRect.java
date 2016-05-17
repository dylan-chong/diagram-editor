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
        double w = bounds.getRight() - bounds.getLeft();
        double h = bounds.getBottom() - bounds.getTop();
        UI.drawRect(bounds.getLeft(), bounds.getTop(), w, w); // TODO test
    }


}
