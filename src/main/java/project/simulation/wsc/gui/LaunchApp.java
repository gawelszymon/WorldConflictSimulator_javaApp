package project.simulation.wsc.gui;

import eu.hansolo.tilesfx.tools.Statistics;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import project.simulation.wsc.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class LaunchApp {

    private final Stage stage;
    private final BorderPane borderPane;
    private final Button startButton = new Button("Launch Simulation");
    private final Button exitButton = new Button("EXIT");
    private final Button buttonEndTracking = new Button("START/STOP");
    private final SimulationEngine engine;
    private final Thread engineThread;      //REVIEW
    private final FieldCreate newField;
    private Troop followingTroop = null;
    private final InfoElement boxAboutTroops;
    private final ChartsViewer charts;

    public LaunchApp(Settings parameters) throws FileNotFoundException {        //REVIEW

        this.stage = new Stage();
        this.borderPane = new BorderPane();
        this.boxAboutTroops = new InfoElement(stage, this);

        Scene sceneMain = new Scene(borderPane);

        //Rectlange2D is class which define a rectangle on a plane associated with screen's of my laptop
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setWidth(screenBounds.getWidth());
        stage.setHeight(screenBounds.getHeight());
        stage.setScene(sceneMain);
        stage.show();
        stage.getIcons().add(new Image(new FileInputStream("src/main/resources/field.png")));
        stage.setTitle("War's force of damage");

        Label tittle = new Label("War which does not have proper respect to existing");
        tittle.setStyle("-fx-font-family: 'Bauhaus 93'; -fx-font-size: 22pt; -fx-text-fill: #30cbc8; -fx-background-color: rgba(8,56,65,0.84);");
        borderPane.setTop(tittle); //TODO
        BorderPane.setAlignment(tittle, Pos.CENTER);
        borderPane.setBackground(new Background(new BackgroundFill(Color.PALETURQUOISE, CornerRadii.EMPTY, Insets.EMPTY)));
        BorderPane.setMargin(tittle, new Insets(20, 0, 20, 0));

        engine = new SimulationEngine(parameters);
        this.charts = new ChartsViewer(stage, engine);      //TODO
        this.newField = new FieldCreate(engine, stage, this);       //TODO

        new Controller(engine, this);       //REVIEW
        this.engineThread = new Thread(() -> {
            try {
                while (true) {
                    engine.run();
                }
            } catch (IllegalStateException e) {
                System.out.println(e.getMessage());
            }
        });

        stage.setOnCloseRequest(event -> engineThread.interrupt());

        GridPane gridPane = newField.getGridPane(); //TODO
        gridPane.setAlignment(Pos.CENTER);
        borderPane.setCenter(gridPane);

        startApp();
    }

    private void startApp() {
        startButton.setStyle("-fx-font-family: 'Bauhaus 93'; -fx-font-size: 15 pt; -fx-text-fill: #30cbc8; -fx-background-color: rgba(8,56,65,0.84)");
        borderPane.setCenter(startButton);

        startButton.setOnAction(actionEvent -> {
            engine.changeStatus();
            engineThread.start();
            addButtons();
        });
    }

    private void addButtons() {
        HBox buttons = new HBox();
        buttons.setSpacing(300);

        HBox centerButtons = new HBox(exitButton, buttonEndTracking);
        centerButtons.setSpacing(15);
        exitButton.setOnAction(action -> {
            engineThread.interrupt();
            stage.close();
        });
        buttonEndTracking.setOnAction(action -> engine.changeStatus());

        buttons.getChildren().addAll(centerButtons);
        buttons.setAlignment(Pos.CENTER);
        borderPane.setBottom(buttons);
        BorderPane.setAlignment(buttons, Pos.CENTER);
        BorderPane.setMargin(buttons, new Insets(10, 0, 10, 0));
        exitButton.setStyle("-fx-font-family: 'Bauhaus 93'; -fx-font-size: 15 pt; -fx-text-fill: #30cbc8; -fx-background-color: rgba(8,56,65,0.84);");
        buttonEndTracking.setStyle("-fx-font-family: 'Bauhaus 93'; -fx-font-size: 15 pt; -fx-text-fill: #30cbc8; -fx-background-color: rgba(8,56,65,0.84);");
    }

    public void uploadMap() {       //TODO
        Platform.runLater(() -> {
            charts.updateCharts();

            VBox stat = uploadStats();
            stat.setAlignment(Pos.CENTER);
            stat.setMaxHeight(stage.getHeight() / 1.5);
            stat.setStyle("-fx-background-color: rgba(8,56,65,0.84);");

            stat.setOnMouseClicked(event -> charts.chartsShow());

            newField.create();
            GridPane gridPane = newField.getGridPane();
            gridPane.setGridLinesVisible(true);

            HBox hbox = new HBox(10);
            hbox.getChildren().addAll(gridPane, stat);
            hbox.setAlignment(Pos.CENTER);

            updateInfo();
            borderPane.setCenter(hbox);
        });
    }

    private VBox uploadStats() {
        StatisticCounter statsCounter = engine.getStatsCounter();
        statsCounter.statisticUpdate();

        String labelStyle = "-fx-font-family: 'Bauhaus 93'; -fx-text-fill: #30cbc8; -fx-background-color: rgba(8,56,65,0.84);";

        Label title = new Label("Field Statistic");

        Label warDays = new Label("War's day: " + statsCounter.getWarDays());
        warDays.setStyle(labelStyle);

        Label numberOfSurvivedTroops = new Label("Number of survived troops: " + statsCounter.getNumberTroops());
        numberOfSurvivedTroops.setStyle(labelStyle);

        Label numberOfTrenches = new Label("Number of trenches: " + statsCounter.getNumberTrenches());
        numberOfTrenches.setStyle(labelStyle);

        Label numberOfDeadTroops = new Label("Number of dead troops: " + statsCounter.getNumberDeadTroops());
        numberOfDeadTroops.setStyle(labelStyle);

        Label avgEnergyLevel = new Label("Average of energy: " + statsCounter.getAvgEnergyLevel());
        avgEnergyLevel.setStyle(labelStyle);

        Label avgLifeDaysDeadTroops = new Label("Average of life: " + statsCounter.getAvgLife());
        avgLifeDaysDeadTroops.setStyle(labelStyle);

        Label avgSupport = new Label("Support per troop: " + statsCounter.getAvgSupportTroops());
        avgSupport.setStyle(labelStyle);

        Label mainFeature = new Label("Main feature: " + statsCounter.getMainFeature());
        mainFeature.setStyle(labelStyle);

        Label freeSpace = new Label("Free space: " + statsCounter.getFreeSpaceQuantity());
        freeSpace.setStyle(labelStyle);

        VBox statistics = new VBox(15);
        statistics.getChildren().addAll(title, warDays, numberOfSurvivedTroops, numberOfTrenches, numberOfDeadTroops,
                avgEnergyLevel, avgLifeDaysDeadTroops, avgSupport, mainFeature, freeSpace);
        statistics.setAlignment(Pos.TOP_CENTER);

        title.setStyle("-fx-font-family: 'Bauhaus 93'; -fx-text-fill: #30cbc8; -fx-background-color: rgba(8,56,65,0.84);-fx-font-weight: bold;");
        title.setFont(new Font(15));

        statistics.setStyle(String.valueOf(new Insets(0, 1, 1, 50)));
        return statistics;
    }

    public void updateInfo() {
        if (followingTroop != null) {
            boxAboutTroops.creativeInfo(followingTroop); //TODO
        }
    }

    public int getMainFeature() {
        return engine.getStatsCounter().getMainFeature();
    }

    public void setFollowingTroop(Troop followingTroop) {
        this.followingTroop = followingTroop;
    }

}
