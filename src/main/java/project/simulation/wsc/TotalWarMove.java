package project.simulation.wsc;

import java.util.Random;

public class TotalWarMove implements IMove{

    private final Random random = new Random();

    private void updateMainFeature (Troop troop) {
        if (random.nextInt(100) >= 85) {
            troop.setMainFeatureID(random.nextInt(troop.getOverviewID().length));
        } else {
            troop.setMainFeatureID((troop.getMainFeatureID() + 1) % troop.getOverviewID().length);
        }
    }

    @Override
    public void moving(Troop troop) {
        updateMainFeature(troop);
        troop.changerPosition();
    }
}
