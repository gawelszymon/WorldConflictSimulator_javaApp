package project.simulation.wsc;

//TODO

public class OnlySurfaceMoveAllowed implements IMoveAllowed {

    private boolean onRightSide(Vector2D position, Vector2D lowerLeft, Vector2D upperRight) {
        return canMoveTo(position, lowerLeft, upperRight) && position.x() >= upperRight.x();
    }

    private boolean onLeftSide(Vector2D position, Vector2D lowerLeft, Vector2D upperRight) {
        return canMoveTo(position, lowerLeft, upperRight) && position.x() < lowerLeft.x();
    }

    @Override
    public Vector2D  newPosition(Vector2D oldPosition, Vector2D newPosition, Vector2D lowerLeft, Vector2D upperRight) {
        if(onRightSide(newPosition, lowerLeft, upperRight)) {
            newPosition = new Vector2D(lowerLeft.x(), newPosition.y());
        } else if (onLeftSide(newPosition, lowerLeft, upperRight)) {
            newPosition = new Vector2D(upperRight.x() - 1, newPosition.y());
        } else if (!canMoveTo(newPosition, lowerLeft, upperRight)) {
            newPosition = oldPosition;
        }

        return newPosition;
    }

    @Override
    public boolean canMoveTo(Vector2D position, Vector2D lowerLeft, Vector2D upperRight){
        return position.y() < upperRight.y() && position.y() >= lowerLeft.y();
    }
    @Override
    public int lostEnergy(Vector2D position, Vector2D lowerLeft, Vector2D upperRight, int energyNeededForRecovery){
        return 1;
    }
}
