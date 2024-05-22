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
import java.io.FileInputStream;
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

    private Stage statsStage;
    private VBox statsVBox;

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
        stage.getIcons().add(new Image(new FileInputStream("src/main/resources/map.png")));
        stage.setTitle("Simulation");

        Label tittle = new Label("War which does not have proper respect to existing");
        borderPane.setTop(tittle); //TODO
        BorderPane.setAlignment(tittle, Pos.CENTER);
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
    }

    private VBox createStatsVBox() {
        VBox statistics = new VBox(15);
        statistics.setAlignment(Pos.TOP_CENTER);
        statistics.setPadding(new Insets(10));
        statistics.setStyle("-fx-border-width: 1px; -fx-border-color: black;");

        return statistics;
    }

    public void uploadMap() {       //TODO
        Platform.runLater(() -> {
            charts.updateCharts();

            if (statsVBox == null) {
                statsVBox = createStatsVBox();
            }
            uploadStats(statsVBox);
            statsVBox.setAlignment(Pos.CENTER);
            statsVBox.setMaxHeight(stage.getHeight() / 1.5);

            charts.chartsShow();

            newField.create();
            GridPane gridPane = newField.getGridPane();
            gridPane.setGridLinesVisible(true);

            HBox hbox = new HBox(10);
            hbox.getChildren().addAll(gridPane);
            hbox.setAlignment(Pos.CENTER);

            updateInfo();
            borderPane.setCenter(hbox);

            try {
                openStatsWindow(statsVBox);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void uploadStats(VBox statsVBox) {
        StatisticCounter statsCounter = engine.getStatsCounter();
        statsCounter.statisticUpdate();

        statsVBox.getChildren().clear();


        Label title = new Label("Field Statistic");

        Label warDays = new Label("War's day: " + statsCounter.getWarDays());

        Label numberOfSurvivedTroops = new Label("Number of survived troops: " + statsCounter.getNumberTroops());

        Label numberOfTrenches = new Label("Number of trenches: " + statsCounter.getNumberTrenches());

        Label numberOfDeadTroops = new Label("Number of dead troops: " + statsCounter.getNumberDeadTroops());

        Label avgEnergyLevel = new Label("Average of energy: " + statsCounter.getAvgEnergyLevel());

        Label avgLifeDaysDeadTroops = new Label("Average of life: " + statsCounter.getAvgLife());

        Label avgSupport = new Label("Support per troop: " + statsCounter.getAvgSupportTroops());

        Label mainFeature = new Label("Main feature: " + statsCounter.getMainFeature());

        Label freeSpace = new Label("Free space: " + statsCounter.getFreeSpaceQuantity());

       // VBox statistics = new VBox(15);
        statsVBox.getChildren().addAll(title, warDays, numberOfSurvivedTroops, numberOfTrenches, numberOfDeadTroops,
                avgEnergyLevel, avgLifeDaysDeadTroops, avgSupport, mainFeature, freeSpace);
        statsVBox.setAlignment(Pos.TOP_CENTER);

        title.setFont(new Font(15));

        statsVBox.setStyle(String.valueOf(new Insets(0, 1, 1, 50)));
    }

    private void openStatsWindow(VBox stat) throws FileNotFoundException {
        if (statsStage == null) {
            statsStage = new Stage();
            statsStage.getIcons().add(new Image(new FileInputStream("src/main/resources/map.png")));
            statsStage.setTitle("Stats");

            Scene secondScene = new Scene(stat, 300, 400);
            statsStage.setScene(secondScene);

            statsStage.setX(stage.getX() + 200);
            statsStage.setY(stage.getY() + 100);
        }

        statsStage.show();
    }

        /*private void openNewWindow() {
            VBox statsVBox = uploadStats();

            Scene secondScene = new Scene(statsVBox, 300, 400);

            Stage newWindow = new Stage();
            newWindow.setTitle("Statistics");
            newWindow.setScene(secondScene);

            newWindow.setX(100);
            newWindow.setY(100);

            newWindow.show();
        }*/


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
