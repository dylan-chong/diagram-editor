import ecs100.UI;

import java.io.Serializable;

/**
 * Created by Dylan on 17/05/16.
 */
public class DEObjectShapeRect extends DEObjectShape implements Serializable {

    public DEObjectShapeRect(DEBounds bounds) {
        super(bounds);
    }

    @Override
    public void draw() {
        assert fillColor != null || edgeColor != null : "Fill and edge color can't both be transparent";

        DEBounds b = getBounds();

        if (fillColor != null) {
            UI.setColor(fillColor);
            UI.fillRect(b.getLeft(), b.getTop(), b.getWidth(), b.getHeight());
        }
        if (edgeColor != null) {
            UI.setColor(edgeColor);
            UI.drawRect(b.getLeft(), b.getTop(), b.getHeight(), b.getHeight());
        }

        super.draw();
    }


}
