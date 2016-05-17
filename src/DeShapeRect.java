import ecs100.UI;

/**
 * Created by Dylan on 17/05/16.
 */
public class DeShapeRect extends DEShape {

    public DeShapeRect(DEBounds bounds) {
        super(bounds);
    }

    @Override
    public void draw() {
        UI.drawRect(10, 20, 100, 200); // TODO test
    }
}
