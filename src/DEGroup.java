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
        this.deObjects = deObjects;
        this.deConnectors = deConnectors;
    }

    // TODO AFTER prevent resizing of group
    // TODO AFTER get draggable draggable override

    /**
     * Calculates the bounds that is taken up by the objects
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

    @Override
    public void draw() {
        super.draw();
        deObjects.forEach(DEObject::draw);
        deConnectors.forEach(DEConnector::draw);
    }
}
