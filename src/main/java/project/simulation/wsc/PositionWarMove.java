package project.simulation.wsc;

//FullPredestinationMove

public class PositionWarMove implements IMove {
    @Override
    public void moving(Troop troop) {
        troop.setMainFeatureID((troop.getMainFeatureID() + 1) % troop.getOverviewID().length);
    }
}
