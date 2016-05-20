import ecs100.UI;

/**
 * Created by Dylan on 17/05/16.
 */
public class DEObjectShapeRect extends DEObjectShape {

    public DEObjectShapeRect(DEBounds bounds) {
        super(bounds);
    }

    @Override
    public void draw() {
        assert fillColor != null || edgeColor != null : "Fill and edge color can't both be transparent";
        if (fillColor != null) {
            UI.setColor(fillColor);
            UI.fillRect(bounds.getLeft(), bounds.getTop(), bounds.getWidth(), bounds.getHeight());
        }
        if (edgeColor != null) {
            UI.setColor(edgeColor);
            UI.drawRect(bounds.getLeft(), bounds.getTop(), bounds.getHeight(), bounds.getHeight());
        }

        super.draw();
    }


}
