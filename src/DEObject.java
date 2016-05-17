/**
 * Created by Dylan on 17/05/16.
 *
 * Can be implemented for a group and
 * shape objects
 */
public abstract class DEObject implements DEDraggable {

    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public abstract void draw();

}