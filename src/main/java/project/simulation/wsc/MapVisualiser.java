package project.simulation.wsc;

public class MapVisualiser {
    private static final String EMPTY_CELL = " ";
    private static final String Trench_CELL = "*";
    private static final String PREFERRED_CELL = "P";
    private static final String CELL_SEGMENT = "|";
    private final AbstractFieldMap map;

    public MapVisualiser(AbstractFieldMap map) {
        this.map = map;
    }

    public String draw(Vector2D lowerLeft, Vector2D upperRight) {
        StringBuilder builder = new StringBuilder();

        builder.append(" y\\x ");
        for (int i = lowerLeft.x(); i < upperRight.x(); i++) {
            builder.append(String.format("%2d", i));
        }
        builder.append(System.lineSeparator());

        for (int i = lowerLeft.y(); i < upperRight.y(); i++) {
            builder.append(String.format("%3d: ", i));
            for (int j = lowerLeft.x(); j < upperRight.x(); j++) {
                Vector2D vec = new Vector2D(j, i);
                MapSquare square = map.elements.get(vec);
                if (square == null) {
                    System.out.println("square null");
                } else {
                    builder.append(CELL_SEGMENT);
                    if (!square.getObjects().isEmpty()) {
                        builder.append(square.getObjects().size());
                    } else if (square.didTrenchBuild()) {
                        builder.append(Trench_CELL);
                    } else if (map.getPreferred().contains(vec)) {
                        builder.append(PREFERRED_CELL);
                    } else {
                        builder.append(EMPTY_CELL);
                    }
                }
            }
            builder.append(CELL_SEGMENT);
            builder.append(System.lineSeparator());
        }

        //-------------------
        try {
            Thread.sleep(900);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //--------------------

        return builder.toString();
    }
}
