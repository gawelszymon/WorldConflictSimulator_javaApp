package project.simulation.wsc;

import java.util.Random;

//define of enumeration which is a special type used to define a fixed set of constants
public enum MoveDirection {
    NORTH,
    SOUTH,
    EAST,
    WEST,
    NORTH_EAST,
    SOUTH_EAST,
    SOUTH_WEST,
    NORTH_WEST;

    @Override
    public String toString() {
        return switch (this) {
            case NORTH -> "N";
            case SOUTH -> "S";
            case EAST -> "E";
            case WEST -> "W";
            case NORTH_EAST -> "NE";
            case SOUTH_EAST -> "SE";
            case SOUTH_WEST -> "SW";
            case NORTH_WEST -> "NW";
        };
    }

    public MoveDirection next() {
        return switch (this) {
            case NORTH -> NORTH_EAST;
            case NORTH_EAST -> EAST;
            case EAST -> SOUTH_EAST;
            case SOUTH_EAST -> SOUTH;
            case SOUTH -> SOUTH_WEST;
            case SOUTH_WEST -> WEST;
            case WEST -> NORTH_WEST;
            case NORTH_WEST -> NORTH;
        };
    }

    public Vector2D toUnitVector() {
        return switch (this) {
            case NORTH -> new Vector2D(0, 1);
            case SOUTH -> new Vector2D(0, -1);
            case EAST -> new Vector2D(1, 0);
            case WEST -> new Vector2D(-1, 0);
            case NORTH_EAST -> new Vector2D(1, 1);
            case SOUTH_EAST -> new Vector2D(1, -1);
            case SOUTH_WEST -> new Vector2D(-1, -1);
            case NORTH_WEST -> new Vector2D(-1, 1);
        };
    }

    public static MoveDirection randomDirection() {
        Random random = new Random();
        //values returns an array which contain alle the enum constants
        MoveDirection[] directions =  values();

        return directions[random.nextInt(directions.length)];
    }
}
