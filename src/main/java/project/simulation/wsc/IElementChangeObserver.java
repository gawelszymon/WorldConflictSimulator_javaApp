package project.simulation.wsc;

public interface IElementChangeObserver {
    void positionChanged(Vector2D oldPosition, Vector2D newPosition, IMapElement object);
    void troopDies(IMapElement troop);
}
