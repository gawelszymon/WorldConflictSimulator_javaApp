package project.simulation.wsc.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GetWarData {

    private final Stage stage;
    private final BorderPane borderPane = new BorderPane();

    public GetWarData() throws FileNotFoundException {
        this.stage = new Stage();
        stage.getIcons().add(new Image(new FileInputStream("src/main/resources/map.png")));
        stage.setTitle("WCS simulation");
        stage.setScene(new Scene(borderPane, 900, 500));
        stage.show();

        Label title = new Label("Notice the danger caused by war...");
        borderPane.setTop(title);
        BorderPane.setAlignment(title, Pos.CENTER);
        BorderPane.setMargin(title, new Insets(20, 0, 20, 0));
    }

    private void initWarData() {
        TextField name = new TextField("current war circumstances");
        TextField fieldWidth = new TextField("20");
        TextField fieldHeight = new TextField("15");
        TextField startTrenchQuantity = new TextField("5");
        TextField startTrenchQuality = new TextField("1");
        TextField startTropsQuantity = new TextField("10");
        TextField startTropsEnergy = new TextField("10");
        TextField tropsFullEnergy = new TextField("15");
        TextField supportTropsEnergy = new TextField("5");
        TextField minimalTropsChanges = new TextField("2");
        TextField maximalTropsChanges = new TextField("9");
        TextField tropsLegacyLength = new TextField("8");
        TextField trenchPerDay = new TextField("8");

        ChoiceBox<String> movementDetails = new ChoiceBox<>();
        movementDetails.getItems().addAll("Erth", "Portal");
        ChoiceBox<String> tropsMoving = new ChoiceBox<>();
        tropsMoving.getItems().addAll("Predestination", "Craziness");
        ChoiceBox<String> developmentVariant = new ChoiceBox<>();
        developmentVariant.getItems().addAll("Random", "Correction");
        ChoiceBox<String> fieldVariant = new ChoiceBox<>();
        fieldVariant.getItems().addAll("Equators", "Corpses");

        Button parametersConfirmation = new Button("Confirm");
        VBox listedFieldsLeft = new VBox(10);
        listedFieldsLeft.getChildren().addAll(name, fieldWidth, fieldHeight, startTrenchQuantity, startTrenchQuality,
                startTropsQuantity, startTropsEnergy, tropsFullEnergy);
        VBox listedFieldsRight = new VBox(10);
        listedFieldsRight.getChildren().addAll(supportTropsEnergy, minimalTropsChanges, maximalTropsChanges,
                tropsLegacyLength, trenchPerDay, movementDetails, tropsMoving, developmentVariant, fieldVariant);

        Label namelabel = new Label("Configuration name");
        Label fieldWidthlabel = new Label("Field width");
        Label fieldHeightlabel = new Label("Field height");
        Label startTrenchQuantitylabel = new Label("Trench quantity");
        Label startTrenchQualitylabel = new Label("Trench quality");
        Label startTropsQuantitylabel = new Label("Trops quantity");
        Label startTropsEnergylabel = new Label("Trops energy");
        Label tropsFullEnergylabel = new Label("Full Energy");
        Label supportTropsEnergylabel = new Label("Support Trops Energy");
        Label minimalTropsChangeslabel = new Label("Min trops changes");
        Label maximalTropsChangeslabel = new Label("Max trops changes");
        Label tropsLegacyLengthlabel = new Label("Trops Legacy");
        Label trenchPerDaylabel = new Label("How many trenches are built per day");
        Label movementDetailslabel = new Label("Movement details");
        Label tropsMovinglabel = new Label("Trops moving");
        Label developmentVariantlabel = new Label("Development Variants");
        Label fieldVariantlabel = new Label("Field Variants");

        VBox leftLabelsList = new VBox(18);
        VBox rightLabelsList = new VBox(18);
        leftLabelsList.getChildren().addAll(namelabel, fieldWidthlabel, fieldHeightlabel, startTrenchQuantitylabel,
                startTrenchQualitylabel, startTropsQuantitylabel, startTropsEnergylabel, tropsFullEnergylabel);
        rightLabelsList.getChildren().addAll(supportTropsEnergylabel, minimalTropsChangeslabel,
                maximalTropsChangeslabel, tropsLegacyLengthlabel, trenchPerDaylabel, movementDetailslabel,
                tropsMovinglabel, developmentVariantlabel, fieldVariantlabel);





);
    }
}
