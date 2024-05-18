package project.simulation.wsc;

import java.util.*;

//abstract class is a kind of class that cannot be directly instance using word "new", the purpose of this class is to
//enable a pattern for classes inherited by it (methods and fields are in inherited classes)
abstract public class AbstractFieldMap implements IFieldMap, IElementChangeObserver {
    protected final Map<Vector2D, MapSquare> elements;
        //test6

}
