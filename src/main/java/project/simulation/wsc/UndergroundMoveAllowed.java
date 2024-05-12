package project.simulation.wsc;

//TODO

public class UndergroundMoveAllowed implements IMoveAllowed {
    @Override
    public Vector2D  newPosition(Vector2D oldPosition, Vector2D newPosition, Vector2D lowerLeft, Vector2D upperRight){
        return newPosition;
    }
    @Override
    public boolean canMoveTo(Vector2D position, Vector2D lowerLeft, Vector2D upperRight){
        return false;
    }
    @Override
    public int lostEnergy(Vector2D position, Vector2D lowerLeft, Vector2D upperRight, int energyNeededForRecovery){
        return 0;
    }
}
