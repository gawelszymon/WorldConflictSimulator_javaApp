package project.simulation.wsc.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import project.simulation.wsc.Troop;

public class InfoElement {
    private final Stage stage;
    private final BorderPane borderPane;
    private final VBox needDate;
    private final LaunchApp app;

    public InfoElement(Stage MainStage, LaunchApp app) {
        this.stage = new Stage();
        this.borderPane = new BorderPane();
        this.stage.initOwner(MainStage);
        this.app = app;
        Scene sceneMain = new Scene(borderPane, 500, 300);

        stage.setScene(sceneMain);
        stage.setOnCloseRequest( event -> {
            this.stage.hide();
            app.setFollowingTroop(null);
        });

        this.needDate = infoData();
    }

    private VBox infoData() {
        VBox listOfLabels = new VBox();
        listOfLabels.setSpacing(10);

        Label mainFeatureLabel = new Label("Main feature:");
        Label overwiewLabel = new Label("Troop's overview:");
        Label energyLevelLabel = new Label("Energy level:");
        Label trenchesDamagedLabel = new Label("Trenches damaged:");
        Label supportLabel = new Label("Support:");
        Label liveLabel = new Label("Days survived:");
        Label deadLabel = new Label("Day of death:");

        listOfLabels.getChildren().addAll(mainFeatureLabel, overwiewLabel, energyLevelLabel, trenchesDamagedLabel,
                supportLabel, liveLabel, deadLabel);

        return listOfLabels;
    }

    public void creativeInfo(Troop troop) {
        borderPane.setCenter(null);
        borderPane.setBottom(null);
        borderPane.setTop(null);

        Label tittle = new Label("Troop summary");
        borderPane.setTop(tittle);
        BorderPane.setAlignment(tittle, Pos.CENTER);
        BorderPane.setMargin(tittle, new Insets(20, 0, 20, 0));

        StringBuilder overviewString = new StringBuilder();       //REVIEW
        overviewString.append("[");
        for (int i = 0; i < troop.getOverviewID().length; i++) {
            if (i > 0) {
                overviewString.append(", ");
            }
            overviewString.append(troop.getOverviewID()[i]);
        }
        overviewString.append("]");

        VBox listOfInformation = new VBox();
        listOfInformation.setSpacing(10);

        Label mainFeature = new Label(Integer.toString(troop.getMainFeature()));
        Label overviewID = new Label(overviewString.toString());
        Label energyLevel = new Label(Integer.toString(troop.getEnergy()));
        Label trenchesdamaged = new Label(Integer.toString(troop.getAmountOfTrenchDamaged()));
        Label support = new Label(Integer.toString(troop.getExtraTroops()));
        Label lifeLength = new Label(Integer.toString(troop.getLifeLength()));
        Label deadDay = new Label(Integer.toString(troop.getDeathDay()));
        listOfInformation.getChildren().addAll(mainFeature, overviewID, energyLevel, trenchesdamaged, support, lifeLength, deadDay);
        //TODO set style

        Button closeButton = new Button("Close");
        closeButton.setAlignment(Pos.CENTER);

        closeButton.setOnAction(action -> {
            this.stage.hide();
            app.setFollowingTroop(null);
        });

        HBox inputList = new HBox();
        inputList.getChildren().addAll(needDate, listOfInformation);
        inputList.setSpacing(20);
        inputList.setAlignment(Pos.CENTER);
        VBox labelContent = new VBox(10, inputList, closeButton);
        labelContent.setAlignment(Pos.CENTER);
        borderPane.setCenter(labelContent);
        this.stage.show();
    }
}
