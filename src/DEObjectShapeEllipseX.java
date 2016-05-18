import ecs100.UI;

/**
 * Created by Dylan on 17/05/16.
 */
public class DEObjectShapeEllipseX extends DEObjectShape {

    public DEObjectShapeEllipseX(DEBounds bounds) {
        super(bounds);
    }

    @Override
    public void draw() {
        double w = bounds.getRight() - bounds.getLeft();
        double h = bounds.getBottom() - bounds.getTop();

        assert fillColor != null || edgeColor != null : "Fill and edge color can't both be transparent";
        if (fillColor != null) {
            UI.setColor(fillColor);
            UI.fillOval(bounds.getLeft(), bounds.getTop(), w, h);
        }
        if (edgeColor != null) {
            UI.setColor(edgeColor);
            UI.drawOval(bounds.getLeft(), bounds.getTop(), w, h);
        }

        super.draw();
    }


}
