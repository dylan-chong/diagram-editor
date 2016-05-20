import java.awt.*;

/**
 * Created by Dylan on 17/05/16.
 * <p>
 * The base for the shape object
 */
public abstract class DEObjectShape extends DEObject {

    public static final Color DEFAULT_FILL_COLOR = null;
    public static final Color DEFAULT_EDGE_COLOR = Color.BLACK;

    protected Color fillColor = DEFAULT_FILL_COLOR;
    protected Color edgeColor = DEFAULT_EDGE_COLOR;

    public DEObjectShape(DEBounds bounds) {
        super(bounds);
    }

    @Override
    public void draw() {
        super.draw();
        // TODO draw text
    }

    @Override
    public String toString() {
        return getClass().getName() + ": " + getBounds().toString();
    }

}
