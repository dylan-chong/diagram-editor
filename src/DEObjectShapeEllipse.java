import ecs100.UI;

/**
 * Created by Dylan on 17/05/16.
 */
public class DEObjectShapeEllipse extends DEObjectShape {

    public DEObjectShapeEllipse(DEBounds bounds) {
        super(bounds);
    }

    @Override
    public void draw() {
        assert fillColor != null || edgeColor != null : "Fill and edge color can't both be transparent";

        if (fillColor != null) {
            UI.setColor(fillColor);
            UI.fillOval(bounds.getLeft(), bounds.getTop(), bounds.getWidth(), bounds.getHeight());
        }
        if (edgeColor != null) {
            UI.setColor(edgeColor);
            UI.drawOval(bounds.getLeft(), bounds.getTop(), bounds.getWidth(), bounds.getHeight());
        }

        super.draw();
    }


}
