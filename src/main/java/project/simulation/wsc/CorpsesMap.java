package project.simulation.wsc;

import java.util.Collections;

public class CorpsesMap extends AbstractFieldMap {

    protected CorpsesMap(int width, int height, IMoveAllowed movementDetails, int supportEnergy) {
        super(width, height, movementDetails, supportEnergy); //key word super mean that it invokes to a constructor of superclass (AbstractFieldMap)


        Collections.shuffle(preferredPositions);
        emptyPreferred = getPreferred();
        emptyNotPreferred = getNotPreferred();
    }

    //(o1, o2) ->    lambda expression, it represents two parameters which are compared, after -> it is an implementation of an expression

    @Override
    protected void updatePreferredPositions() {
        //it is sorted a preferred positions by a value where amount of death is the lowest
        preferredPositions.sort((o1, o2) -> Integer.compare(elements.get(o1).getDeathCounter(), elements.get(o2).getDeathCounter()));
        updateEmptyPositions();
    }

    private void updateEmptyPositions() {
        for (Vector2D positions : getPreferred()) {
            if (emptyNotPreferred.contains(positions)) {
                emptyNotPreferred.remove(positions);
                emptyPreferred.remove(positions);
                emptyPreferred.add(positions);
            }
        }
        for (Vector2D position : getNotPreferred()) {
            if (emptyPreferred.contains(position)) {
                emptyPreferred.remove(position);
                emptyNotPreferred.add(position);
            }
        }
    }
}
