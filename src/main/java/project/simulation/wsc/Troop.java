package project.simulation.wsc;

import java.util.Random;

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
    private final TroopOverview troopOverviewID;
    private final Settings settings;

    public Troop(Vector2D position, Settings settings, int createNewDay) {
        Random random = new Random();
        this.settings = settings;
        this.position = position;
        this.createNewDay = createNewDay;

        recentImageID = random.nextInt(5) + 1;
        map = settings.getMap();
        troopOverviewID = new TroopOverview(settings);
        orientation = MoveDirection.randomDirection();
        energy = settings.getStartTropsEnergy();
        map.place(this);
    }

    public Troop(Troop firstTroop, Troop secondTroop, Settings settings, int createNewDay) { //gonna add extra troop when another two trops become removed
        this.settings = settings;
        this.createNewDay = createNewDay;
        map = settings.getMap();
        recentImageID = firstTroop.getImageID();
        orientation = MoveDirection.randomDirection();
        position = firstTroop.getPosition();
        troopOverviewID = new TroopOverview(firstTroop, secondTroop, settings);
        map.place(this);

        //firstTroop.loseEnergy(settings.getStartTropsEnergy());
        //secondTroop.loseEnergy(settings.getStartTropsEnergy());//TODO here we hava to find another way to replace two troop with one extra troop

        energy = settings.getStartTropsEnergy();

    }

    public void changerPosition() {
        int numberDirection = getOverviewID()[getMainFeatureID()];
        for(int i = 0; i <= numberDirection; i++) {
            orientation = orientation.next();
        }
        Vector2D oldPosition = position;
        //TODO Vector2D newPosition = map. //firstly finish AbstarctFieldMap.java
    }

    public void move() {
        lifeLength++;
        settings.getTroopMoving().moving(this);

        if(isDead()) {
            observer.troopDies(this);
        }
    }

    public void loseEnergy() {
        energy += settings.getStartTropsEnergy();
    }

    public void increaseEnergy() {
        energy += settings.getRecoveryTrenchEnergy();
        amountOfTrenchDamaged += 1;
    }

    public int getEnergy() {
        return energy;
    }

    public int getLifeLength() {
        return lifeLength;
    }

    public int getDeathDay() {
        if(isDead()) {
            return createNewDay + lifeLength;
        }
        return 0;
    }

    public void positionChanged(Vector2D oldPosition, Vector2D newPosition) {
        observer.positionChanged(oldPosition, newPosition, this);
    }

    public int getExtraTroops() {
        return extraTroops;
    }

    public void newExtraTroops() {
        extraTroops += 1;
    }

//TODO within TroopOverview.java

//    public void newChildren() {
//        children += 1;
//    }
//
    public int[] getOverviewID() {
        return troopOverviewID.getTroopOverviewID();
    }
//
//    public int getActiveGenome() {
//        return genotype.getActiveGenomeAnimal();
//    }
//
//    public void setActiveGenomeIdx(int currGen) {
//        genotype.setActiveGenome(currGen);
//    }
//
    public int getMainFeatureID() {
        return troopOverviewID.getMainFeatureID();
    }

    @Override
    public boolean isTroop() {
        return true;
    }

    public boolean isDead() {
        return energy <= 0;
    }

    @Override
    public Vector2D getPosition() {
        return position;
    }

    public void setPosition(Vector2D newPosition) {
        positionChanged(this.getPosition(), newPosition);
        position = newPosition;
    }

    @Override
    public MoveDirection getOrientation() {
        return orientation;
    }

    @Override
    public int getImageID() {
        return recentImageID;
    }

    @Override
    public void setObserver(IElementChangeObserver observer) {
        this.observer = observer;
    }

    @Override
    public int getActiveTroopID() {
        return 0;
    }

    public int getAmountOfTrenchDamaged() {
        return amountOfTrenchDamaged;
    }
}
