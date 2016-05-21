import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Dylan on 17/05/16.
 */
public class DEGroup extends DEObject implements Serializable {

    private ArrayList<DEObject> deObjects = new ArrayList<>();
    private ArrayList<DEConnector> deConnectors = new ArrayList<>();

    public DEGroup(ArrayList<DEObject> deObjects, ArrayList<DEConnector> deConnectors) {
        super(calculateBounds(deObjects));
        setObjectsAndConnectors(deObjects, deConnectors);
    }

    /**
     * Calculates the bounds that is taken up by the objects
     *
     * @param objects
     * @return
     */
    private static DEBounds calculateBounds(ArrayList<DEObject> objects) {
        double mostTop = 999999;
        double mostBottom = -999999;
        double mostLeft = 999999;
        double mostRight = -999999;

        for (DEObject obj : objects) {
            DEBounds b = obj.getBounds();
            if (b.getLeft() < mostLeft) mostLeft = b.getLeft();
            if (b.getTop() < mostTop) mostTop = b.getTop();
            if (b.getBottom() > mostBottom) mostBottom = b.getBottom();
            if (b.getRight() > mostRight) mostRight = b.getRight();
        }

        double width = mostRight - mostLeft;
        double height = mostBottom - mostTop;

        return new DEBounds(mostLeft, mostTop, width, height);
    }
    
    public ArrayList<DEObject> getDeObjects() {
        return new ArrayList<>(this.deObjects);
    }

    public ArrayList<DEConnector> getDeConnectors() {
        return new ArrayList<>(this.deConnectors);
    }

    /**
     * Groups of groups aren't allowed, so if there is a group inside
     * deObjects, it will just be merged into a single "layer" group.
     *
     * @param objects
     * @param connectors
     */
    private void setObjectsAndConnectors(ArrayList<DEObject> objects,
                                         ArrayList<DEConnector> connectors) {
        // Some objects are groups
        ArrayList<DEObject> sortedObjects = new ArrayList<>();
        ArrayList<DEConnector> sortedConnectors = new ArrayList<>(connectors);

        for (DEObject obj : objects) {
            if (!(obj instanceof DEGroup)) {
                sortedObjects.add(obj);
                continue;
            }

            DEGroup group = (DEGroup) obj;
            ArrayList<DEObject> groupObjects = group.getDeObjects();
            sortedObjects.addAll(groupObjects);
            ArrayList<DEConnector> groupConnectors = group.getDeConnectors();
            sortedConnectors.addAll(groupConnectors);
        }

        this.deObjects = sortedObjects;
        this.deConnectors = sortedConnectors;
    }

    // TODO AFTER ungroup button
    // TODO prevent resizing of group

    @Override
    public void draw() {
        super.draw();
        deObjects.forEach(DEObject::draw);
        deConnectors.forEach(DEConnector::draw);
    }

    @Override
    protected void followOrPutDownAtMousePoint(DEPoint mousePoint) {
        super.followOrPutDownAtMousePoint(mousePoint);

        ArrayList<DEBounds> newObjectBounds = getBoundsCalculator()
                .getNewObjectsBoundsForMousePoint(mousePoint);

        for (int o = 0; o < deObjects.size(); o++) {
            DEObject obj = deObjects.get(o);
            obj.setBounds(newObjectBounds.get(o));
        }
    }

    @Override
    protected DEDraggableBoundsCalculator getNewBoundsCalculator(DEPoint mousePoint) {
        ArrayList<DEBounds> objectBounds = new ArrayList<>();
        for (DEObject obj : deObjects) {
            objectBounds.add(obj.getBounds());
        }
        return new DEDraggableBoundsCalculator(mousePoint, getBounds(), objectBounds);
    }

}
