package project.simulation.wsc;

import java.util.ArrayList;
import java.util.List;

public class MapSquare {
    private final ArrayList<IMapElement> objects;
    private boolean trench;
    private int deathCounter;

    public MapSquare() {
        this.trench = false;
        this.objects = new ArrayList<>();
        this.deathCounter = 0;
    }

    public boolean didTrenchBuild() {
        return trench;
    }

    public void buildTrench() {
        trench = true;
    }

    public void damageTrench() {
        trench = false;
    }

    private void increaseDeathCounter() {
        deathCounter += 1;
    }

    public int getDeathCounter() {
        return deathCounter;
    }

    public List<IMapElement> getObjects() {
        return objects;
    }

    public void removeObject(IMapElement object) {
        objects.remove(object);
    }

    public void addObject(IMapElement object) {
        objects.add(object);
    }

    public void troopDie(IMapElement troop) {
        removeObject(troop);
        increaseDeathCounter();
    }
}
