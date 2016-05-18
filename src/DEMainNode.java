import ecs100.UI;

/**
 * Created by Dylan on 18/05/16.
 *
 * A large, invisible node that covers the
 * entire bounds of the selected object
 */
public class DEMainNode extends DENode {

    private DEBounds bounds;

    public DEMainNode(DEBounds bounds) {
        super(null);
        fitToBounds(bounds);
    }

    public void fitToBounds(DEBounds objectBounds) {
        this.bounds = new DEBounds(objectBounds.getLeft() + DENode.WIDTH / 2,
                objectBounds.getTop() + DENode.HEIGHT / 2,
                objectBounds.getWidth() - DENode.WIDTH,
                objectBounds.getHeight() - DENode.HEIGHT);
    }

    @Override
    public void draw() {
        // Do nothing

        // TODO remove after making sure it works properly
        UI.drawRect(bounds.getLeft(), bounds.getTop(),
                bounds.getWidth(), bounds.getHeight());
        return;
    }

    @Override
    public boolean pointIsWithinBounds(DEPoint point) {
        return bounds.pointIsWithinBounds(point);
    }

    @Override
    public void setPoint(DEPoint point) {
        assert true : "You can't set a DEMovement's point. Set the bounds instead";
    }
}
