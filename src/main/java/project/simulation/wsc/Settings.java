package project.simulation.wsc;

public class Settings {
    private final String name;
    private final int fieldWidth;
    private final int fieldHeight;
    private final int startTrenchQuantity;
    private final int startTrenchQuality; //TODO chaneg into recoveryTrenchEnergy
    private final int trenchPerDay;
    private final int startTropsQuantity;
    private final int startTropsEnergy;
    private final int tropsFullEnergy;
    private final int supportTropsEnergy;
    private final int tropsLegacyLength;
    //10 godzin do matury
    private final AbstractFieldMap map;
    private final IMove troopMoving;
    private final ITroopProperty propertyVariant; //ITroopPropert = IGenome

    public Settings(String configName, String[] config) throws Exception {
        name = configName;
        fieldWidth = Integer.parseInt(config[0]);
        fieldHeight = Integer.parseInt(config[1]);
        startTrenchQuantity = Integer.parseInt(config[2]);
        startTrenchQuality = Integer.parseInt(config[3]);   //eating grass energy
        trenchPerDay = Integer.parseInt(config[4]);
        startTropsQuantity = Integer.parseInt(config[5]);
        startTropsEnergy = Integer.parseInt(config[6]);
        tropsFullEnergy = Integer.parseInt(config[7]);
        supportTropsEnergy = Integer.parseInt(config[8]);
        int minimalTroopsChanges = Integer.parseInt(config[9]); //TODO
        int maximalTroopsChanges = Integer.parseInt(config[10]); //TODO
        tropsLegacyLength = Integer.parseInt(config[11]);

        if (fieldWidth <= 0 || fieldHeight <= 0) {
            throw new Exception("wrong field dimension data");
        }

        if (startTrenchQuantity < 0 || startTrenchQuantity > fieldWidth * fieldHeight) {
            throw new Exception("wrong startTrenchQuantity data");
        }

        if (startTrenchQuality < 0) {
            throw new Exception("wrong startTrenchQuality data");
        }

        if (trenchPerDay < 0) {
            throw new Exception("wrong trenchPerDay data");
        }

        if (startTropsQuantity <= 0 || startTropsEnergy <= 0) {
            throw new Exception("wrong startTrops data");
        }
        if (tropsFullEnergy < 0 || supportTropsEnergy < 0) {
            throw new Exception("wrong troopsEnergy data");
        }
        if (minimalTroopsChanges < 0 || minimalTroopsChanges > maximalTroopsChanges || tropsLegacyLength <= 0) {
            throw new Exception("wrong troops changes and legacy data");
        }

        switch (config[12]){
            case "Position war" -> troopMoving = new PositionMove;
        }
    }

    public AbstractFieldMap getMap(){
        return map;
    }

    public int getStartTropsEnergy() {
        return startTropsEnergy;
    }

    public int getRecoveryTrenchEnergy() {
        return startTrenchQuality;
    }

    public IMove getTroopMoving() {
        return troopMoving;
    }

}

