package project.simulation.wsc.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.controlsfx.control.spreadsheet.Grid;
import project.simulation.wsc.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FieldCreate {
    private final SimulationEngine engine;
    private final GridPane gridPane;
    private final Settings parameters;
    private final double size;
    private final Pictures images = new Pictures();
    private final LaunchApp app;

    public FieldCreate(SimulationEngine engine, Stage stage, LaunchApp app) {

        this.app = app;
        this.engine = engine;
        this.parameters = engine.getSettings();
        this.gridPane = new GridPane();

        double sizeScene = stage.getHeight();
        int width = parameters.getFieldWidth();
        int height = parameters.getFieldHeight();
        this.size = Math.max(width, height);

        for (int i = 0; i < parameters.getFieldWidth(); i++) {
            this.gridPane.getColumnConstraints().add(new ColumnConstraints(sizeScene / (0.8 * size)));
        }
        for (int i = 0; i < parameters.getFieldHeight(); i++) {
            this.gridPane.getRowConstraints().add(new RowConstraints(sizeScene / (1.5 * size)));
        }
        create();
    }

    public void create() {
        gridPane.getChildren().clear();
        gridPane.setGridLinesVisible(true);
        puttingObjects();
    }

    private void setButtonOnAction(StackPane till, Troop troop, SimulationEngine engine) {
        till.setOnMouseClicked(event -> {
            if (!engine.isActive()) {
                app.setFollowingTroop(troop);
                app.updateInfo();
            }
        });
    }

    private void puttingObjects() {
        AbstractFieldMap map = parameters.getMap();
        List<Vector2D> mapContain = parameters.getMap().getPreferred();

        ImageView imageView;
        Map<Vector2D, MapSquare> MapSquare = map.getElements();
        int freeSpace = 0;

        for (int i = 0; i < parameters.getFieldWidth(); i++) {

            //int x = parameters.getFieldWidth();
            //int y = parameters.getFieldHeight();

            /*
            for (int _x = 0; _x <= x; _x++) {
                Line line = new Line(0, x * 50, 300, x * 50);
                line.setStroke(Color.BLACK);
                gridPane.add(line, 0, _x, y + 1, 1);
            }

            for (int _y = 0; _y <= y; _y++) {
                Line line = new Line(0, y * 50, 300, y * 50);
                line.setStroke(Color.BLACK);
                gridPane.add(line, 0, _y, x + 1, 1);
            }
             */

            for (int j = 0; j < parameters.getFieldHeight(); j++) {
                StackPane trenches = new StackPane();
                if (mapContain.contains(new Vector2D(i, j))) {
                    //trenches.setStyle("-fx-background-color: rgba(177,234,167,0.84)");
                    gridPane.add(trenches, i, j);
                } else {
                    //trenches.setStyle("-fx-background-color: rgb(8,56,65)");
                    gridPane.add(trenches, i, j);
                }

                Vector2D position = new Vector2D(i, j);
                MapSquare square = MapSquare.get(position);
                if (square != null && square.getObjects().size() != 0) {

                    HBox hbox = new HBox(5);
                    hbox.setAlignment(Pos.CENTER);

                    int howMany = square.getObjects().size();
                    for (IMapElement troop : square.getObjects()) {
                        switch (troop.getImageID()) {
                            case 5 -> imageView = new ImageView(images.ImageID5);
                            case 4 -> imageView = new ImageView(images.ImageID4);
                            case 3 -> imageView = new ImageView(images.ImageID3);
                            case 2 -> imageView = new ImageView(images.ImageID2);
                            case 1 -> imageView = new ImageView(images.ImageID1);
                            default -> throw new IllegalStateException("Unexpected value: ");
                        }

                        double imageHeight = 500 / (1.5 * size * howMany);
                        double imageWidth = 600 / (1.5 * size * howMany);
                        imageView.setFitHeight(imageHeight);
                        imageView.setFitWidth(imageWidth);

                        Label posit = new Label(position.toString());
                        //posit.setStyle("-fx-font-family: 'Bauhaus 93'; -fx-text-fill: #30cbc8; -fx-background-color: rgba(8,56,65,0.84);");
                        posit.setFont(Font.font(20 / (0.2 * size)));

                        ContentBox imgTroop = new ContentBox(troop, engine);
                        ProgressBar lifeBar = imgTroop.energyInTroop();
                        lifeBar.setPrefHeight(80 / (size));
                        lifeBar.setPrefWidth(600 / (1.5 * size * howMany));
                        lifeBar.setMinHeight(10);

                        HBox lifeAndPosition = new HBox(lifeBar, posit);

                        Label live = new Label(String.format("%.2f%%", lifeBar.getProgress() * 100));
                        live.setVisible(false);
                        //live.setStyle("-fx-font-family: 'Bauhaus 93'; -fx-text-fill: #30cbc8; -fx-background-color: rgba(8,56,65,0.84);");

                        StackPane stackPane = new StackPane(imageView, live);
                        stackPane.setOnMouseEntered(event -> live.setVisible(true));
                        stackPane.setOnMouseExited(event -> live.setVisible(false));

                        if (troop.getMainFeatureID() == app.getMainFeature()) {
                        //    stackPane.setStyle("-fx-border-width: 3; -fx-border-color: #33e30d;");
                        }

                        setButtonOnAction(stackPane, (Troop) troop, engine);

                        VBox box = new VBox(stackPane, lifeAndPosition);
                        box.setAlignment(Pos.CENTER);

                        hbox.getChildren().addAll(box, posit);
                    }

                    gridPane.add(hbox, i, j);
                    GridPane.setHalignment(hbox, Pos.CENTER.getHpos());

                } else {
                    freeSpace += 1;

                    if (Objects.requireNonNull(square).didTrenchBuild()) {
                        Label posit = new Label(position.toString());
                        //posit.setStyle("-fx-font-family: 'Bauhaus 93'; -fx-text-fill: #30cbc8; -fx-background-color: rgba(8,56,65,0.84);");
                        posit.setFont(Font.font(20 / (0.2 * size)));

                        double imageHeight = 500 / (1.5 * size);
                        double imageWidth = 600 / (1.5 * size);

                        imageView = new ImageView(images.trenchImage); //TODO
                        imageView.setFitHeight(imageHeight);
                        imageView.setFitWidth(imageWidth);

                        VBox box = new VBox(3, imageView, posit);
                        box.setAlignment(Pos.CENTER);

                        gridPane.add(box, i, j);
                        GridPane.setHalignment(box, Pos.CENTER.getHpos());
                    }
                }
            }

            engine.setFreeSpaceQuantity(freeSpace);
            gridPane.setAlignment(Pos.CENTER);
        }
    }

    public GridPane getGridPane() {
        return this.gridPane;
    }

}