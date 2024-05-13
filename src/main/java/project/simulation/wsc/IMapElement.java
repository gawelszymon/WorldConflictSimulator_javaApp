package project.simulation.wsc;

public interface IMapElement {
    boolean isTroop();
    Vector2D getPosition();
    MoveDirection getOrientation();
    int getImageID();
    void setObserver(IElementChangeObserver observer);
    int getMainFeatureID();
}
