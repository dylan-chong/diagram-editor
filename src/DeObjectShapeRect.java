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

        assert fillColor != null || edgeColor != null : "Fill and edge color can't both be transparent";
        if (fillColor != null) {
            UI.setColor(fillColor);
            UI.fillRect(bounds.getLeft(), bounds.getTop(), w, h);
        }
        if (edgeColor != null) {
            UI.setColor(edgeColor);
            UI.drawRect(bounds.getLeft(), bounds.getTop(), w, h);
        }
    }


}
