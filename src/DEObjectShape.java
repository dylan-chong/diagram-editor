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
        UI.setColor(labelColor);
        UI.drawString(labelText,
                b.getCenter().getX() - (labelText.length() * AVERAGE_CHARACTER_WIDTH / 2),
                b.getCenter().getY() + (AVERAGE_CHARACTER_HEIGHT / 2));
    }

    public void setLabelText(String text) {
        labelText = text;
    }

}
