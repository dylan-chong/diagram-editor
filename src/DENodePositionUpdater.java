import java.io.Serializable;

/**
 * Created by Dylan on 19/05/16.
 */
public interface DENodePositionUpdater extends Serializable {

    void positionWasUpdated(DENode node);

}
