package project.simulation.wsc;

public class Settings {
    private final String name;
    private final int fieldWidth;
    private final int fieldHeight;
    private final int startTrenchQuantity;
    private final int startTrenchQuality;
    private final int trenchPerDay;
    private final int startTropsQuantity;
    private final int startTropsEnergy;
    private final int tropsFullEnergy;
    private final int supportTropsEnergy;
    private final int tropsLegacyLength;
    //10 godzin do matury
    private final AbstractFieldMap map;
    private final IMove troopMoving;
    private final ITroopProperty propertyVariant;

    public Settings(String configName, String[] config) throws Exception {
        name = configName;
        fieldWidth = Integer.parseInt(config[0]);
        fieldHeight = Integer.parseInt(config[1]);
        startTrenchQuantity = Integer.parseInt(config[2]);
        startTrenchQuality = Integer.parseInt(config[3]);
        trenchPerDay = Integer.parseInt(config[4]);
        startTropsQuantity = Integer.parseInt(config[5]);
        startTropsEnergy = Integer.parseInt(config[6]);
        tropsFullEnergy = Integer.parseInt(config[7]);
        supportTropsEnergy = Integer.parseInt(config[8]);
        int minimalTroopsChanges = Integer.parseInt(config[9]); //TODO
        int maximalTroopsChanges = Integer.parseInt(config[10]); //TODO
        tropsLegacyLength = Integer.parseInt(config[11]);
    }
}

