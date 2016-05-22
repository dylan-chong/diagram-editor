import ecs100.UI;

import java.awt.*;
import java.io.Serializable;

/**
 * Created by Dylan on 17/05/16.
 * <p>
 * The base for the shape object
 */
public abstract class DEObjectShape extends DEObject implements Serializable {

    public static final Color DEFAULT_FILL_COLOR = null;
    public static final Color DEFAULT_EDGE_COLOR = Color.BLACK;
    public static final Color DEFAULT_LABEL_COLOR = Color.BLACK;
    public static final double AVERAGE_CHARACTER_HEIGHT = 10;
    public static final double AVERAGE_CHARACTER_WIDTH = 6.5;

    protected Color fillColor = DEFAULT_FILL_COLOR;
    protected Color edgeColor = DEFAULT_EDGE_COLOR;
    protected Color labelColor = DEFAULT_LABEL_COLOR;

    private String labelText;

    public DEObjectShape(DEBounds bounds) {
        super(bounds);
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public void setEdgeColor(Color edgeColor) {
        this.edgeColor = edgeColor;
    }

    public void setLabelColor(Color labelColor) {
        this.labelColor = labelColor;
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


    public void setLabelText(String text) {
        labelText = text;
    }

    /**
     * Draws the labelText in the approximate middle of the
     * shape.
     */
    private void drawLabelText() {
        DEBounds b = getBounds();

        double x = b.getCenter().getX() - (labelText.length() * AVERAGE_CHARACTER_WIDTH / 2);
        double y = b.getCenter().getY() + (AVERAGE_CHARACTER_HEIGHT / 2);

        UI.setColor(labelColor);
        UI.drawString(labelText, x, y);
    }

}
