package project.simulation.wsc;

import eu.hansolo.tilesfx.tools.Statistics;

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

    public Statistics (SimulationEngine engine) {
        this.engine = engine;
        //map = engine.getSettings().getMap();
    }
}
