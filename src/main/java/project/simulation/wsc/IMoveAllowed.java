package project.simulation.wsc;

public interface IMoveAllowed {
    Vector2D  newPosition(Vector2D oldPosition, Vector2D newPosition, Vector2D lowerLeft, Vector2D upperRight);
    boolean canMoveTo(Vector2D position, Vector2D lowerLeft, Vector2D upperRight);
    int lostEnergy(Vector2D position, Vector2D lowerLeft, Vector2D upperRight, int energyNeededForRecovery);
}
