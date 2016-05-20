import ecs100.UI;

import java.awt.*;
import java.io.Serializable;

/**
 * Created by Dylan on 17/05/16.
 */
public class DEObjectShapeHexagon extends DEObjectShape implements Serializable {

    public DEObjectShapeHexagon(DEBounds bounds) {
        super(bounds);
    }

    private static void drawHexagon(DEBounds bounds, Color color, boolean shouldfill) {
        double l = bounds.getLeft();
        double t = bounds.getTop();
        double w = bounds.getWidth();
        double h = bounds.getHeight();
        DEPoint[] points = new DEPoint[] {
                new DEPoint(l + 0.25 * w, t), // Top left-ish
                new DEPoint(l + 0.75 * w, t), // Top right-ish
                new DEPoint(l + w, t + (h / 2)), // Middle right
                new DEPoint(l + 0.75 * w, t + h), // Bottom right-ish
                new DEPoint(l + 0.25 * w, t + h), // Bottom left-ish
                new DEPoint(l, t + (h / 2)), // Middle left
        };

        UI.setColor(color);
        if (shouldfill) UI.fillPolygon(getXOrdinates(points), getYOrdinates(points), points.length);
        else UI.drawPolygon(getXOrdinates(points), getYOrdinates(points), points.length);
    }

    private static double[] getXOrdinates(DEPoint[] points) {
        return getXOrYOrdinates(points, true);
    }

    private static double[] getYOrdinates(DEPoint[] points) {
        return getXOrYOrdinates(points, false);
    }

    private static double[] getXOrYOrdinates(DEPoint[] points, boolean shouldGetXPoints) {
        double[] ordinates = new double[points.length];
        for (int p = 0; p < points.length; p++) {
            DEPoint point = points[p];
            if (shouldGetXPoints) ordinates[p] = point.getX();
            else ordinates[p] = point.getY();
        }
        return ordinates;
    }

    @Override
    public void draw() {
        assert fillColor != null || edgeColor != null : "Fill and edge color can't both be transparent";

        if (fillColor != null) drawHexagon(getBounds(), fillColor, true);
        if (edgeColor != null) drawHexagon(getBounds(), edgeColor, false);

        super.draw();
    }

}
