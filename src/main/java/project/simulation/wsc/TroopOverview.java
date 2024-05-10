package project.simulation.wsc;

import java.util.Random;
import java.util.Set;

public class TroopOverview { //Genome
    private final int[] troopOverviewID;
    private final Random random = new Random();
    private int mainFeatureID;

    public TroopOverview(Settings settings) {
        int troopOverviewLength = settings.getTropsLegacyLength();
        troopOverviewID = new int[troopOverviewLength];
        for (int i = 0; i < troopOverviewLength; i++) {
            troopOverviewID[i] = random.nextInt(7) + 1;
        }
        mainFeatureID = random.nextInt(troopOverviewLength);
    }

    public TroopOverview(Troop firstTroop, Troop secondTroop, Settings settings) {
        int side = (random.nextInt() % 2); // 0 => left, 1 => right

        Troop troopCrucialFeature = firstTroop;
        Troop troopAdditionalFeature = secondTroop;

        if (firstTroop.getEnergy() < secondTroop.getEnergy()) {
            troopCrucialFeature = secondTroop;
            troopAdditionalFeature = firstTroop;
        }

        int cut = troopCrucialFeature.getEnergy() / (troopCrucialFeature.getEnergy() + troopAdditionalFeature.getEnergy()) * troopCrucialFeature.getTr
    }

    public int[] getTroopOverviewID() {
        return troopOverviewID;
    }

    public int getMainFeatureID() {
        return mainFeatureID;
    }
}
