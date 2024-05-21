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
    private final int troopsOverviewLength;
    private final AbstractFieldMap map;
    private final IMove troopMoving;
    private final ITroopOverview developmentVariant;

    public Settings(String configName, String[] config) throws Exception {
        name = configName;
        fieldWidth = Integer.parseInt(config[0]);
        fieldHeight = Integer.parseInt(config[1]);
        startTrenchQuantity = Integer.parseInt(config[2]);
        recoveryTrenchEnergy = Integer.parseInt(config[3]);   //eating grass energy
        startTropsQuantity = Integer.parseInt(config[4]);
        startTropsEnergy = Integer.parseInt(config[5]);
        tropsFullEnergy = Integer.parseInt(config[6]);
        supportTropsEnergy = Integer.parseInt(config[7]);       //energy lost to get a support
        int minimalOverviewChanges = Integer.parseInt(config[8]); //TODO
        int maximalOverviewChanges = Integer.parseInt(config[9]); //TODO
        troopsOverviewLength = Integer.parseInt(config[10]); //associated directly with overview
        trenchPerDay = Integer.parseInt(config[11]);
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
            throw new Exception("wrong startTroops data");
        }
        if (tropsFullEnergy < 0 || supportTropsEnergy < 0) {
            throw new Exception("wrong troopsEnergy data");
        }
        if (startTropsEnergy > tropsFullEnergy  || supportTropsEnergy > tropsFullEnergy) {
            throw new Exception("wrong troopsEnergy data");
        }
        if (minimalOverviewChanges < 0 || minimalOverviewChanges > maximalOverviewChanges || troopsOverviewLength <= 0) {
            throw new Exception("wrong troops changes and overview data");    //TODO
        }

        switch (config[12]) {
            case "Only earth's surface" -> movementDetails = new OnlySurfaceMoveAllowed();
            case "Surface and Underground" -> movementDetails = new UndergroundMoveAllowed();
            default ->  throw new Exception("Wrong movementsDetails data");
        }

        switch (config[13]){
            case "Position war" -> troopMoving = new PositionWarMove();
            case "Total war" -> troopMoving = new TotalWarMove();
            default ->  throw new Exception("wrong troopMoving data");
        }
        switch (config[14]) {
            case "Correction" -> developmentVariant = new LittleCorrectionProperties();
            case "Random" -> developmentVariant = new FullRandomProperties();
            default -> throw new Exception("wrong developmentVariant data");
        }

        switch (config[15]) {
            case "Geographic map" -> map = new GeographicMap(fieldWidth, fieldHeight, movementDetails, supportTropsEnergy);
            case "Corpses map" -> map = new CorpsesMap(fieldWidth, fieldHeight, movementDetails, supportTropsEnergy);
            default -> throw new Exception("wrong map data");
        }

    }

    public String getName() {
        return name;
    }

    public int getFieldWidth() {
        return fieldWidth;
    }

    public int getFieldHeight() {
        return fieldHeight;
    }

    public int getStartTrenchQuantity() {
        return startTrenchQuantity;
    }

    public int getRecoveryTrenchEnergy() {
        return recoveryTrenchEnergy;
    }

    public int getTrenchPerDay() {
        return trenchPerDay;
    }

    public int getEnergyLost() {
        return supportTropsEnergy;
    }

    public int getStartTropsQuantity() {
        return startTropsQuantity;
    }

    public int getTropsFullEnergy() {
        return tropsFullEnergy;
    }

    public AbstractFieldMap getMap() {
        return map;
    }

    public int getStartTropsEnergy() {
        return startTropsEnergy;
    }

    public IMove getTroopMoving() {
        return troopMoving;
    }

    public int getTroopsOverviewLength() {
        return troopsOverviewLength;
    }

    public ITroopOverview getDevelopmentVariant() {
        return developmentVariant;
    }

}

