package project.simulation.wsc;

public class Settings {
    private final String name;
    private final int fieldWidth;
    private final int fieldHeight;
    private final int startTrenchQuantity;
    private final int recoveryTrenchEnergy; //TODO change into recoveryTrenchEnergy
    private final int trenchPerDay;
    private final int startTropsQuantity;
    private final int startTropsEnergy;
    private final int tropsFullEnergy;
    private final int supportTropsEnergy;
    private final int troopsOverViewLength;
    private final AbstractFieldMap map;
    private final IMove troopMoving;
    private final ITroopOverview developmentVariant;

    public Settings(String configName, String[] config) throws Exception {
        name = configName;
        fieldWidth = Integer.parseInt(config[0]);
        fieldHeight = Integer.parseInt(config[1]);
        startTrenchQuantity = Integer.parseInt(config[2]);
        recoveryTrenchEnergy = Integer.parseInt(config[3]);   //eating grass energy
        trenchPerDay = Integer.parseInt(config[4]);
        startTropsQuantity = Integer.parseInt(config[5]);
        startTropsEnergy = Integer.parseInt(config[6]);
        tropsFullEnergy = Integer.parseInt(config[7]);
        supportTropsEnergy = Integer.parseInt(config[8]);
        int minimalTroopsChanges = Integer.parseInt(config[9]); //TODO
        int maximalTroopsChanges = Integer.parseInt(config[10]); //TODO
        troopsOverViewLength = Integer.parseInt(config[11]); //associated directly with overview
        IMoveAllowed movementDetails;


        if (fieldWidth <= 0 || fieldHeight <= 0) {
            throw new Exception("wrong field dimension data");
        }

        if (startTrenchQuantity < 0 || startTrenchQuantity > fieldWidth * fieldHeight) {
            throw new Exception("wrong startTrenchQuantity data");
        }

        if (recoveryTrenchEnergy < 0) {
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
        if (minimalTroopsChanges < 0 || minimalTroopsChanges > maximalTroopsChanges || troopsOverViewLength <= 0) {
            throw new Exception("wrong troops changes and legacy data");
        }

        switch (config[12]){
            case "Position war" -> troopMoving = new PositionWarMove();
            case "Total war" -> troopMoving = new TotalWarMove();
            default ->  throw new Exception("wrong troopMoving data");
        }
        switch (config[13]) {
            case "Correction" -> developmentVariant = new LittleCorrectionProperties();
            case "Random" -> developmentVariant = new FullRandomProperties();
            default -> throw new Exception("wrong developmentVariant data");
        }

        switch (config[14]) {
            case "Only earth's surface" -> movementDetails = new OnlySurfaceMoveAllowed();
            case "Surface and Underground" -> movementDetails = new UndergroundMoveAllowed();
            default ->  throw new Exception("Wrong movementsDetails data");
        }

        switch (config[15]) {
            case "Geographic map" -> map = new GeographicMap(fieldWidth, fieldHeight, movementDetails, supportTropsEnergy);
            case "Corpses map" -> map = new CorpsesMap(fieldWidth, fieldHeight, movementDetails, supportTropsEnergy);
            default -> throw new Exception("wrong map data");
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

    public int getTropsLegacyLength() {
        return tropsLegacyLength;
    }

    public int ITroopOverview getDevelopmentVariant {
        return developmentVariant;
    }

}

