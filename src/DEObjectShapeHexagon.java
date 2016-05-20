import ecs100.UI;

import java.awt.*;

/**
 * Created by Dylan on 17/05/16.
 */
public class DEObjectShapeHexagon extends DEObjectShape {

    public DEObjectShapeHexagon(DEBounds bounds) {
        super(bounds);
    }

    private static void drawHexagon(DEBounds bounds, Color color, boolean shouldfill) {
        UI.setColor(color);

        // TODO NEXT make hexagon object
    }

    @Override
    public void draw() {
        assert fillColor != null || edgeColor != null : "Fill and edge color can't both be transparent";

        if (fillColor != null) drawHexagon(bounds, fillColor, true);
        if (edgeColor != null) drawHexagon(bounds, edgeColor, true);

        super.draw();
    }


}
