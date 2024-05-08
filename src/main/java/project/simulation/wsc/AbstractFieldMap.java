package project.simulation.wsc;

import java.util.*;

//abstract class is a kind of class that cannot be directly instance using word "new", the purpose of this class is to
//enable a pattern for classes inherited by it (methods and fields are in inherited classes)
abstract public class AbstractFieldMap implements IFieldMap, IElementChangeObserver {
    protected final Map<Vector2D, MapSquare> elements;
    private int troopsNumber;
    private int trenchNumber;
    private int troopsDead = 0;
    private int troopsDetritus = 0; //TODO
    protected final int mapSize;
    private final Vector2D lowerLeft;
    private final Vector2D upperRight;
    private final IMoveAllowed movementDetails;
    private final int extraTroopsEnergy; //TODO
    protected final List<Vector2D> preferredPositions = new ArrayList<>();
    protected List<Vector2D> emptyPreferred;
    protected  List<Vector2D> emptyNotPreferred;
    protected final ArrayList<Troop> troopsList = new ArrayList<>();



    protected AbstractFieldMap(Map<Vector2D, MapSquare> elements, int mapSize, Vector2D lowerLeft, Vector2D upperRight, IMoveAllowed movementDetails, int extraTroopsEnergy) {
        this.elements = elements;
        this.mapSize = mapSize;
        this.lowerLeft = lowerLeft;
        this.upperRight = upperRight;
        this.movementDetails = movementDetails;
        this.extraTroopsEnergy = extraTroopsEnergy;
    }
}
