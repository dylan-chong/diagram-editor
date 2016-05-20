import java.io.Serializable;

/**
 * Created by Dylan on 20/05/16.
 */
public interface DEObjectBoundsUpdater extends Serializable {

    void updateBounds(DEBounds newBounds);

}
