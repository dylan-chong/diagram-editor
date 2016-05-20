import ecs100.UI;

import java.awt.*;

/**
 * Created by Dylan on 17/05/16.
 * <p>
 * The base for the shape object
 */
public abstract class DEObjectShape extends DEObject {

    public static final Color DEFAULT_FILL_COLOR = null;
    public static final Color DEFAULT_EDGE_COLOR = Color.BLACK;
    public static final double TEXT_DISTANCE_FROM_LEFT = 3.0;
    public static final double TEXT_DISTANCE_FROM_TOP = TEXT_DISTANCE_FROM_LEFT;

    protected Color fillColor = DEFAULT_FILL_COLOR;
    protected Color edgeColor = DEFAULT_EDGE_COLOR;

    private String labelText;

    public DEObjectShape(DEBounds bounds) {
        super(bounds);
    }

    @Override
    public void draw() {
        super.draw();
        if (labelText != null) drawLabelText();
    }

    @Override
    public String toString() {
        return getClass().getName() + ": " + getBounds().toString();
    }

    private void drawLabelText() {
        DEBounds b = getBounds();
        UI.drawString(labelText, b.getLeft() + TEXT_DISTANCE_FROM_LEFT,
                b.getTop() + TEXT_DISTANCE_FROM_TOP);
    }

    public void setLabelText(String text) {
        labelText = text;
    }

}
