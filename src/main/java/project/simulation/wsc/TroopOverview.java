package project.simulation.wsc;

import java.util.Arrays;
import java.util.Random;
import java.util.Set;

public class TroopOverview { //Genome
    private final int[] troopOverviewID;
    private final Random random = new Random();
    private int mainFeatureID;

    public TroopOverview(Settings settings) {
        int troopOverviewLength = settings.getTroopsOverviewLength();
        troopOverviewID = new int[troopOverviewLength];
        for (int i = 0; i < troopOverviewLength; i++) {
            troopOverviewID[i] = random.nextInt(7) + 1;
        }
        mainFeatureID = random.nextInt(troopOverviewLength);
    }

    public TroopOverview(Troop firstTroop, Troop secondTroop, Settings settings) {
        int side = (random.nextInt() % 2);

        Troop troopCrucialFeature = firstTroop;
        Troop troopAdditionalFeature = secondTroop;

        if (firstTroop.getEnergy() < secondTroop.getEnergy()) {
            troopCrucialFeature = secondTroop;
            troopAdditionalFeature = firstTroop;
        }

        int cut = troopCrucialFeature.getEnergy() / (troopCrucialFeature.getEnergy() + troopAdditionalFeature.getEnergy()) * troopCrucialFeature.getOverviewID().length;
        int[] firstPiece;
        int[] secondPiece;
        int n = troopCrucialFeature.getOverviewID().length;

        if (side == 1) {
            firstPiece = Arrays.copyOfRange(troopCrucialFeature.getOverviewID(), 0, cut);
            secondPiece = Arrays.copyOfRange(troopAdditionalFeature.getOverviewID(), cut, n);
        } else {
            firstPiece = Arrays.copyOfRange(troopCrucialFeature.getOverviewID(), cut, n);
            secondPiece = Arrays.copyOfRange(troopAdditionalFeature.getOverviewID(), 0, cut);
        }

        int[] res = Arrays.copyOf(secondPiece, n);
        System.arraycopy(firstPiece, 0, res, secondPiece.length, firstPiece.length);
        settings.getDevelopmentVariant().troopAbilitiesDevelopment(res);
        troopOverviewID = res;
    }

    public int[] getTroopOverviewID() {
        return troopOverviewID;
    }

    public int getMainFeatureTroop() {
        return troopOverviewID[mainFeatureID];
    }

    public int getMainFeatureID() {
        return mainFeatureID;
    }

    public void setMainFeature(int mainFeature) {
        mainFeatureID = mainFeature;
    }
}
