package project.simulation.wsc.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import project.simulation.wsc.SimulationEngine;

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


    public ChartsViewer(Stage mainStage, SimulationEngine engine) {
        this.engine = engine;
        this.borderPane = new BorderPane();
        Scene scene = new Scene(borderPane, 800, 800);
        this.stage = new Stage();
        stage.setScene(scene);
        stage.initOwner(mainStage);

        stage.setOnCloseRequest(event -> this.stage.hide());

        Button closeButton = new Button("Close");
        closeButton.setStyle("-fx-font-family: 'Bauhaus 93'; -fx-font-size: 15 pt; -fx-text-fill: #30cbc8; -fx-background-color: rgba(8,56,65,0.84);");
        closeButton.setAlignment(Pos.CENTER);
        closeButton.setOnAction(action -> this.stage.hide());

        Label title = new Label("Simulation Charts");

        HBox mainDescription = new HBox(10, title, closeButton);
        mainDescription.setAlignment(Pos.CENTER);
        borderPane.setBackground(new Background(new BackgroundFill(Color.PALETURQUOISE, CornerRadii.EMPTY, Insets.EMPTY)));
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

