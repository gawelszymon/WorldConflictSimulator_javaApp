package project.simulation.wsc;


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
        statsCounter = new StatisticCounter(this); //this refers to this specific class
    }

    @Override
    public void run() {

    }
}
