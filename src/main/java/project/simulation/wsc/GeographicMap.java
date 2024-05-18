package project.simulation.wsc;

//TODO

public class GeographicMap extends AbstractFieldMap {

    protected GeographicMap(int width, int height, IMoveAllowed movementDetails, int supportEnergy) {
        super(width, height, movementDetails, supportEnergy);

        float midY = (height - 1) / (float) 2;

        preferredPositions.sort((o1, o2) -> Float.compare(Math.abs(o1.y() - midY), Math.abs(o2.y() - midY)));
        emptyPreferred = getPreferred();
        emptyNotPreferred = getNotPreferred();
    }

    @Override
    void updatePreferredPositions() {
    }
}
