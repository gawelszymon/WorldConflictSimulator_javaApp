package project.simulation.wsc;

//TODO

import java.util.Random;

public class UndergroundMoveAllowed implements IMoveAllowed {

    private boolean onEdge(Vector2D position, Vector2D lowerLeft, Vector2D upperRight) {
        return !(position.follows(lowerLeft) && position.precedes(upperRight));
    }

    private Vector2D drawNewPosition(Vector2D upperRight) {
        Random random = new Random();
        int x = random.nextInt(upperRight.x());
        int y = random.nextInt(upperRight.y());

        return new Vector2D(x, y);
    }

    @Override
    public Vector2D newPosition(Vector2D oldPosition, Vector2D newPosition, Vector2D lowerLeft, Vector2D upperRight){
        if (onEdge(newPosition, lowerLeft, upperRight)) {
            return drawNewPosition(upperRight);
        }

        return newPosition;
    }
    @Override
    public boolean canMoveTo(Vector2D position, Vector2D lowerLeft, Vector2D upperRight){
        return true;
    }
    @Override
    public int lostEnergy(Vector2D position, Vector2D lowerLeft, Vector2D upperRight, int energyNeededForRecovery){
        if (onEdge(position, lowerLeft, upperRight)) {
            return 1 + energyNeededForRecovery;
        }

        return 1;
    }
}
