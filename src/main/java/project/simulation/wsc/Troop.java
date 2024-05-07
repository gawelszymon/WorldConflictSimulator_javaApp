package project.simulation.wsc;

public class Troop implements IMapElement {

    private Vector2D position;
    private MoveDirection orientation;
    private IElementChangeObserver observer;
    private final AbstractFieldMap map;
    private int energy;
    private int extraTroops = 0; //TODO
    private int amountOfTrenchDamaged = 0;
    private final int createNewDay;
    private int lifeLength = 0;
    private final int recentImageID;
    //private final TroopProperty  //TODO
    private final Settings settings;

    @Override
    public boolean isTroop() {
        return true;
    }

    @Override
    public Vector2D getPosition() {
        return position;
    }

    @Override
    public MoveDirection getOrientation() {
        return orientation;
    }

    @Override
    public int getImageID() {
        return 0;
    }

    @Override
    public void setObserver(IElementChangeObserver observer) {

    }

    @Override
    public int getActiveTroopID() {
        return 0;
    }

}
