package project.simulation.wsc;


import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

//when class implements Runnable it means that main class must provide method run(), which contain a code that
//should be executed in separate thread, This mechanism lets execution of different code in the same time as a class
//which implements runnable, so my SimulationEngine works independently
public class SimulationEngine implements Runnable {

    private final Settings settings;
    private final StatisticReceiver statsReceiver = new StatisticReceiver();
    private final AbstractFieldMap map;
    private final StatisticCounter statsCounter;
    private int currentWarDay;
    private int freeSpaceQuantity;
    private boolean isActive = false;
    private ISimulationViewer viewer;

    public SimulationEngine(Settings settings) {
        this.settings = settings;
        map = settings.getMap();
        currentWarDay = 0;
        statsCounter = new StatisticCounter(this); //"this" refers to this specific class
    }

    public void setViewer (ISimulationViewer viewer) {
        this.viewer = viewer;
    }

    private void moveTroops() {
        List<Troop> troops = new LinkedList<>(); //LinkedList contain some special indicator to previous and following element

        for (MapSquare square : map.elements.values()) {  //values return collection of each element from the map (without a key)
            for (IMapElement element : square.getObjects()) {
                if (element.isTroop()) {
                    troops.add((Troop) element);
                }
            }

            for (Troop troop : troops) {
                troop.move();
            }

        }
    }

    private Troop findCommanderTroop(MapSquare square) {
        Troop commanderTroop = null;

        for (IMapElement element : square.getObjects()) {
            if (element.isTroop()) {
                Troop currentTroop = (Troop) element;

                if (commanderTroop == null) {
                    commanderTroop = currentTroop;
                }

                if (currentTroop.getEnergy() > commanderTroop.getEnergy()) {
                    commanderTroop = currentTroop;
                } else if (currentTroop.getLifeLength() > commanderTroop.getLifeLength()) {
                    commanderTroop = currentTroop;
                } else if (currentTroop.getExtraTroops() > commanderTroop.getExtraTroops()) {
                    commanderTroop = currentTroop;
                }
            }
        }

        return commanderTroop;
    }

    private Troop findViceCommander(MapSquare square) {
        if (square.getObjects().size() < 2) return null;

        Troop commander = findCommanderTroop(square);
        Troop viceCommander = null;

        for (IMapElement element : square.getObjects()) {
            if (element.isTroop()) {
                Troop currentTroop = (Troop) element;
                if (viceCommander == null) {
                    viceCommander = currentTroop;
                }

                if (!currentTroop.equals(commander) && currentTroop.getEnergy() >= settings.getTropsFullEnergy()) {
                    if (currentTroop.getEnergy() > viceCommander.getEnergy()) {
                        viceCommander = currentTroop;
                    } else if (currentTroop.getLifeLength() > viceCommander.getLifeLength()) {
                        viceCommander = currentTroop;
                    } else if (currentTroop.getExtraTroops() > viceCommander.getExtraTroops()) {
                        viceCommander =currentTroop;
                    }
                }
            }
        }

        return viceCommander;
    }

    private void damageTrench() {
        for (MapSquare square : map.elements.values()) {
            Troop commanderTroop = findCommanderTroop(square);

            if (square.didTrenchBuild() && commanderTroop != null) {
                commanderTroop.increaseEnergy();
                map.damageTrench(commanderTroop.getPosition());
            }
        }
    }

    private void troopsSupport() {
        for (MapSquare square : map.elements.values()) {
            Troop commander = findCommanderTroop(square);
            Troop viceCommander = findViceCommander(square);

            if (commander != null && viceCommander != null) {
                new Troop(commander, viceCommander, settings, currentWarDay);
                commander.newExtraTroops();
                viceCommander.newExtraTroops();
            }
        }
    }

    private void buildTrench() {
        map.buildTrench(settings.getTrenchPerDay());
    }

    public boolean isSimulationNotOver() {
        for (MapSquare square : map.elements.values()) {
            for (IMapElement element : square.getObjects()) {
                if (element.isTroop() && !((Troop) element).isDead()) {
                    return true;
                }
            }
        }

        return false;
    }

    private Vector2D drawPosition() {
        Random random = new Random();
        int x = random.nextInt(settings.getFieldWidth());
        int y = random.nextInt(settings.getFieldHeight());

        return new Vector2D(x, y);
    }

    private void initSimulation() {
        for (int i = 0; i < settings.getStartTropsQuantity(); i++) {
            new Troop(drawPosition(), settings, currentWarDay);
        }

        map.buildTrench(settings.getStartTrenchQuantity());
        System.out.println(settings.getMap().toString());
    }

    public void run() {
        if (currentWarDay == 0) {
            try {
                statsReceiver.setSettingsFile(settings.getName());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            System.out.println("Simulation initialization...");
            initSimulation();
            viewer.SimulationStep();
        }
        while (isSimulationNotOver() && isActive) {
            try {
                currentWarDay += 1;
                settings.getMap().updatePreferredPositions();
                moveTroops();
                damageTrench();
                troopsSupport();
                buildTrench();
                viewer.SimulationStep();
                statsReceiver.save(statsCounter);
            } catch (Exception e) { //IOException ???
                throw new RuntimeException(e);
            }
            System.out.println(settings.getMap().toString());
        }
    }

    public Settings getSettings() {
        return settings;
    }

    public int getCurrentWarDay() {
        return currentWarDay;
    }

    public void changeStatus() {
        this.isActive = !this.isActive;
    }

    public boolean isActive() {
        return isActive;
    }

    public int getFreeSpaceQuantity() {
        return freeSpaceQuantity;
    }

    public StatisticCounter getStatsCounter() {
        return statsCounter;
    }

    public void setFreeSpaceQuantity(int freeSpaceQuantity) {
        this.freeSpaceQuantity = freeSpaceQuantity;
    }
}
