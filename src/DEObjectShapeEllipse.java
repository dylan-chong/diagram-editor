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

        DEBounds b = getBounds();

        if (fillColor != null) {
            UI.setColor(fillColor);
            UI.fillOval(b.getLeft(), b.getTop(), b.getWidth(), b.getHeight());
        }
        if (edgeColor != null) {
            UI.setColor(edgeColor);
            UI.drawOval(b.getLeft(), b.getTop(), b.getWidth(), b.getHeight());
        }

        super.draw();
    }


}
