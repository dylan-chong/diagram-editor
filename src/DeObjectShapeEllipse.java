import ecs100.UI;

/**
 * Created by Dylan on 17/05/16.
 */
public class DeObjectShapeEllipse extends DEObjectShape {

    public DeObjectShapeEllipse(DEBounds bounds) {
        super(bounds);
    }

    @Override
    public void draw() {
        double w = bounds.getRight() - bounds.getLeft();
        double h = bounds.getBottom() - bounds.getTop();
        UI.drawOval(bounds.getLeft(), bounds.getTop(), w, h);
    }


}
