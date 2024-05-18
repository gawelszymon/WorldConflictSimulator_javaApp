package project.simulation.wsc;

import eu.hansolo.tilesfx.tools.Statistics;

import java.util.List;

public class StatisticCounter {
    private final AbstractFieldMap map;
    private int numberTroops;
    private int numberTrenches;
    private int numberDeadTroops;
    private int warDays;
    private double avgLife;
    private double avgEnergyLevel;
    private int freeSpaceQuantity;
    private double avgSupportTroops;
    private int mainFeature;
    private final SimulationEngine engine;

    public StatisticCounter (SimulationEngine engine) {
        this.engine = engine;
        map = engine.getSettings().getMap();        //curious thread
        numberTroops = 0;
        numberTrenches = 0;
        numberDeadTroops = 0;
        warDays = 0;
        avgLife = 0;
        avgEnergyLevel = 0;
        avgSupportTroops = 0;
        mainFeature = 0;
        freeSpaceQuantity = 0; //engine.getFreeSpaceQuantity(); //REVIEW
    }

    public void statisticUpdate() {
        AbstractFieldMap map = engine.getSettings().getMap();
        numberTroops = map.getTroopsNumber();
        numberTrenches = map.getTrenchNumber();
        numberDeadTroops = map.getTroopsDead();
        warDays = engine.getCurrentWarDay();
        freeSpaceQuantity = engine.getFreeSpaceQuantity();

        calculateAvgEnergy();
        calculateAvgLifeLength();
        calculateAvgTroopSupport();
        findMainFeature();
    }

    private void findMainFeature() {
        List<Troop> troops = engine.getSettings().getMap().troopsList;
        int[] counter = new int[8];

        for (Troop troop : troops) {
            counter[troop.getMainFeatureID()] += 1;
        }

        int mainFeature = 0;
        int maxOverviewID = 0;

        for (int i = 0; i < 8; i++) {
            if (counter[i] > maxOverviewID) {
                maxOverviewID = counter[i];
                mainFeature = i;
            }

            this.mainFeature = mainFeature;
        }
    }

    private void calculateAvgLifeLength() {
        if (map.getTroopsDead() != 0) {
            avgLife = Math.round(map.getLifeOfDeadTroop() / (double) map.getTroopsDead() * 100) / 100.0;
        }
    }

    private void calculateAvgEnergy() {
        if (engine.getSettings().getMap().troopsList.size() != 0) {
            int energy = 0;

            for (Troop troop : engine.getSettings().getMap().troopsList) {
                if (troop.getEnergy() > 0) {
                    energy += troop.getEnergy();
                }
            }

            this.avgEnergyLevel = Math.round(energy / (double) numberTroops * 100) / 100.0;
        }

        if (!engine.isSimulationNotOver()) {
            this.avgEnergyLevel = 0.0;
        }
    }

    private void calculateAvgTroopSupport() {
        int numberOfSupport = 0;
        List<Troop> troops = engine.getSettings().getMap().troopsList;

        if (troops.size() == 0) {
            this.avgSupportTroops = 0;
        } else {
            for (Troop troop : troops) {
                if (troop.getExtraTroops() > 0) {
                    numberOfSupport += troop.getExtraTroops();
                }
            }

            this.avgSupportTroops = Math.round(numberOfSupport / (double) troops.size() * 100) / 100.0;
        }
    }

    public int getNumberTroops() {
        return  numberTroops;
    }

    public int getNumberTrenches() {
        return numberTrenches;
    }

    public int getNumberDeadTroops() {
        return numberDeadTroops;
    }

    public int getWarDays() {
        return warDays;
    }

    public double getAvgLife() {
        return avgLife;
    }

    public double getAvgEnergyLevel() {
        return avgEnergyLevel;
    }

    public int getFreeSpaceQuantity() {
        return freeSpaceQuantity;
    }

    public double getAvgSupportTroops() {
        return avgSupportTroops;
    }

    public int getMainFeature() {
        return mainFeature;
    }
}
