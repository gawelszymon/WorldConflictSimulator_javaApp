package project.simulation.wsc.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import project.simulation.wsc.SimulationEngine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ChartsViewer {

    private final BorderPane borderPane;
    private final Stage stage;
    private final SimulationEngine engine;
    private final StatisticChart troopChart = new StatisticChart("Troops");
    private final StatisticChart trenchChart = new StatisticChart("Trenches");
    private final StatisticChart energyChart = new StatisticChart("Average energy level");
    private final StatisticChart avgLifeLength = new StatisticChart("Average life length");
    private final StatisticChart supportChart = new StatisticChart("Average troop's support");
    private final StatisticChart freeSpaceChart = new StatisticChart("Free space");
    private final StatisticChart deathTroopsChart = new StatisticChart("Death troops");


    public ChartsViewer(SimulationEngine engine) throws FileNotFoundException {
        this.engine = engine;
        this.borderPane = new BorderPane();
        Scene scene = new Scene(borderPane, 800, 800);
        this.stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Charts");
        stage.getIcons().add(new Image(new FileInputStream("src/main/resources/map.png")));

        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        stage.setOnCloseRequest(event -> this.stage.hide());

        Label title = new Label("Simulation Charts");
        title.getStyleClass().add("choiceLabel");

        HBox mainDescription = new HBox(10, title);
        mainDescription.setAlignment(Pos.CENTER);
        //borderPane.setBackground(new Background(new BackgroundFill(Color.PALETURQUOISE, CornerRadii.EMPTY, Insets.EMPTY)));
        BorderPane.setMargin(mainDescription, new Insets(20, 0, 20, 0));
        borderPane.setTop(mainDescription);
        BorderPane.setAlignment(mainDescription, Pos.CENTER);
    }

    public void updateCharts() {
        borderPane.setCenter(null);     //null mean any element is which is on central side is removed and is replaced with this borderPane element
        trenchChart.updateTrenchChart(engine.getStatsCounter());
        troopChart.updateTroopChart(engine.getStatsCounter());
        energyChart.updateAvgEnergyLevel(engine.getStatsCounter());
        avgLifeLength.updateAvgLifeLength(engine.getStatsCounter());
        deathTroopsChart.updateDeathTroops(engine.getStatsCounter());
        freeSpaceChart.updateFreeSpaceQuantity(engine.getStatsCounter());
        supportChart.updateAvgSupportTroops(engine.getStatsCounter());

        VBox chartsAfter = new VBox(troopChart.getChart(), trenchChart.getChart(), energyChart.getChart());
        VBox chartsVol = new VBox(avgLifeLength.getChart(), deathTroopsChart.getChart());
        VBox chartsEnd = new VBox(freeSpaceChart.getChart(), supportChart.getChart());
        HBox charts = new HBox(2, chartsVol, chartsAfter, chartsEnd);

        borderPane.setCenter(charts);
    }

    public void chartsShow() {
        stage.show();
    }
}

