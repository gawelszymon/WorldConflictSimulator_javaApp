package project.simulation.wsc;

import java.util.*;

//the abstract class is a kind of class that cannot be directly instance using word "new", the purpose of this class is to
//enable a pattern for classes inherited by it (methods and fields are in inherited classes)
abstract public class AbstractFieldMap implements IFieldMap, IElementChangeObserver {
    protected final Map<Vector2D, MapSquare> elements;
    private int troopsNumber;
    private int trenchNumber;
    private int troopsDead = 0;
    private int lifeOfDeadTroop = 0;
    protected final int mapSize;
    private final Vector2D lowerLeft;
    private final Vector2D upperRight;
    private final IMoveAllowed movementDetails;
    private final int extraTroopsEnergy;
    protected final List<Vector2D> preferredPositions = new ArrayList<>();
    protected List<Vector2D> emptyPreferred;
    protected  List<Vector2D> emptyNotPreferred;
    protected final ArrayList<Troop> troopsList = new ArrayList<>();



    protected AbstractFieldMap(int width, int height, IMoveAllowed movementDetails, int extraTroopsEnergy) {
        elements = new HashMap<>();
        this.movementDetails = movementDetails;
        this.extraTroopsEnergy = extraTroopsEnergy;
        mapSize = width * height;
        lowerLeft = new Vector2D(0, 0);
        upperRight = new Vector2D(width, height);
        troopsNumber = 0;
        trenchNumber = 0;

        initMap(width, height);
    }

    private void initMap(int width, int height) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Vector2D position = new Vector2D(i, j);
                MapSquare square = new MapSquare();
                elements.put(position, square);
                preferredPositions.add(position);
            }
        }
    }

    @Override
    public void positionChanged(Vector2D oldPosition, Vector2D newPosition, IMapElement object) {
        if (elements.containsKey(oldPosition) && elements.containsKey(newPosition)) {
            elements.get(oldPosition).removeObject(object);
            elements.get(newPosition).addObject(object);
        }
    }

    abstract void updatePreferredPositions();

    public List<Vector2D> getPreferred() {
        return new LinkedList<>(preferredPositions.subList(0, (int) Math.round(0.2 * mapSize)));
    }

    protected List<Vector2D> getNotPreferred() {
        return new LinkedList<>(preferredPositions.subList((int) Math.round(0.2 * mapSize), preferredPositions.size()));
    }

    protected boolean isEmptySquares() {
        return !emptyPreferred.isEmpty() || !emptyNotPreferred.isEmpty();
    }

    protected Vector2D drawPosition() {
        Random random = new Random();
        Vector2D position;

        if (emptyPreferred.isEmpty()) {
            position = emptyNotPreferred.get(random.nextInt(emptyNotPreferred.size()));
            emptyNotPreferred.remove(position);
            return position;
        }
        if (emptyNotPreferred.isEmpty()) {
            position = emptyPreferred.get(random.nextInt(emptyPreferred.size()));
            emptyPreferred.remove(position);
            return position;
        }

        int preference = random.nextInt(100);
        if (preference >= 20) {
            position = emptyPreferred.get(random.nextInt(emptyPreferred.size()));
            emptyPreferred.remove(position);
        } else {
            position = emptyNotPreferred.get(random.nextInt(emptyNotPreferred.size()));
            emptyNotPreferred.remove(position);
        }
        return position;
    }
    @Override
    public void troopDies(IMapElement troop) {
        Vector2D position = troop.getPosition();
        if (elements.get(position).getObjects().contains(troop)) {
            MapSquare square = elements.get(position);
            square.troopDie(troop);
            troopsList.remove((Troop) troop);
            troopsNumber -= 1;
            this.setTroopsDead();
            this.setLifeOfDeadTroop((Troop) troop);
        }
    }

    public boolean inMap(Vector2D position) {
        return position.precedes(upperRight) && position.follows(lowerLeft);
    }

    public Vector2D newPosition(Vector2D oldPosition, Vector2D newPosition) {
        return movementDetails.newPosition(oldPosition, newPosition, lowerLeft, upperRight);
    }

    public int moveEnergyLost(Vector2D position) {
        return movementDetails.lostEnergy(position, lowerLeft, upperRight, extraTroopsEnergy);
    }

    @Override
    public void place(IMapElement object) {
        Vector2D position = object.getPosition();
        if (inMap(position)) {
            elements.get(position).addObject(object);
            troopsNumber += 1;
            troopsList.add((Troop) object);
            object.setObserver(this);
        }
    }

    private void addTrench(Vector2D position) {
        MapSquare square = elements.get(position);
        square.buildTrench();
        trenchNumber += 1;
    }

    public int getTroopsNumber() {
        return troopsNumber;
    }

    public int getTrenchNumber() {
        return trenchNumber;
    }

    private void deleteTrench(Vector2D position) {
        elements.get(position).damageTrench();
        trenchNumber -= 1;

        if (getPreferred().contains(position)) {
            emptyPreferred.add(position);
        } else {
            emptyNotPreferred.add(position);
        }
    }

    private boolean isTrench(Vector2D position) {
        return elements.get(position).didTrenchBuild();
    }

    public void damageTrench(Vector2D position) {
        if (isTrench(position)) {
            deleteTrench(position);
        }
    }

    public void buildTrench(int trenchPerDay) {
        for (int i = 0; i < trenchPerDay; i++) {
            if (isEmptySquares()) {
                Vector2D position = drawPosition();
                addTrench(position);
            }
        }
    }

    public Map<Vector2D, MapSquare> getElements() {
        return elements;
    }

    public String toString() {
        return new MapVisualiser(this).draw(lowerLeft, upperRight);
    }

    public int getTroopsDead() {
        return troopsDead;
    }

    private void setTroopsDead() {
        this.troopsDead = this.troopsDead + 1;
    }

    public int getLifeOfDeadTroop() {
        return lifeOfDeadTroop;
    }

    public void setLifeOfDeadTroop(Troop troop) {
        this.lifeOfDeadTroop = this.lifeOfDeadTroop + troop.getLifeLength();
    }
}
