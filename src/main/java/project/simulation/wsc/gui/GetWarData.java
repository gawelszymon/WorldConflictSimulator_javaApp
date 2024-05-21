package project.simulation.wsc.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.stage.Stage;
import project.simulation.wsc.ConfigurateSelection;
import project.simulation.wsc.Settings;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GetWarData {

    private final Stage stage;
    private final BorderPane borderPane = new BorderPane();

    public GetWarData() throws FileNotFoundException {
        this.stage = new Stage();
        stage.getIcons().add(new Image(new FileInputStream("src/main/resources/map.png")));
        stage.setTitle("WCS simulation");
        stage.setScene(new Scene(borderPane, 880, 550));
        stage.show();

        Label title = new Label("Notice the danger caused by war...");
        //title.setStyle("-fx-font-family: 'Bauhaus 93'; -fx-font-size: 22pt; -fx-text-fill: #30cbc8; -fx-background-color: rgba(8,56,65,0.84);");
        borderPane.setTop(title);
        BorderPane.setAlignment(title, Pos.CENTER);
        BorderPane.setMargin(title, new Insets(20, 0, 20, 0));

        initWarData();
    }

    private void initWarData() {
        TextField name = new TextField("name");
        TextField fieldWidth = new TextField("10");
        TextField fieldHeight = new TextField("15");
        TextField startTrenchQuantity = new TextField("5");
        TextField trenchRecoveryEnergy = new TextField("7");
        TextField startTropsQuantity = new TextField("5");
        TextField startTropsEnergy = new TextField("8");
        TextField tropsFullEnergy = new TextField("10");
        TextField supportTropsEnergy = new TextField("7");
        TextField minimalTropsChanges = new TextField("2"); //TODO
        TextField maximalTropsChanges = new TextField("9"); //TODO
        TextField tropsLegacyLength = new TextField("8");
        TextField trenchPerDay = new TextField("8");

        ChoiceBox<String> movementDetails = new ChoiceBox<>();
        movementDetails.getItems().addAll("Only earth's surface", "Surface and Underground");
        ChoiceBox<String> tropsMoving = new ChoiceBox<>();
        tropsMoving.getItems().addAll("Position war", "Total war");
        ChoiceBox<String> developmentVariant = new ChoiceBox<>();
        developmentVariant.getItems().addAll("Correction", "Random");
        ChoiceBox<String> fieldVariant = new ChoiceBox<>();
        fieldVariant.getItems().addAll("Geographic map", "Corpses map");

        Button parametersConfirmation = new Button("Confirm");

        VBox listedFieldsLeft = new VBox(10);
        listedFieldsLeft.getChildren().addAll(name, fieldWidth, fieldHeight, startTrenchQuantity, trenchRecoveryEnergy,
                startTropsQuantity, startTropsEnergy, tropsFullEnergy);

        VBox listedFieldsRight = new VBox(10);
        listedFieldsRight.getChildren().addAll(supportTropsEnergy, minimalTropsChanges, maximalTropsChanges,
                tropsLegacyLength, trenchPerDay, movementDetails, tropsMoving, developmentVariant, fieldVariant);

        Label namelabel = new Label("Conf name");
        Label fieldWidthlabel = new Label("Field width");
        Label fieldHeightlabel = new Label("Field height");
        Label startTrenchQuantitylabel = new Label("Trench quantity");
        Label startTrenchQualitylabel = new Label("Recovery energy");
        Label startTropsQuantitylabel = new Label("Troops quantity");
        Label startTropsEnergylabel = new Label("Trops energy");
        Label tropsFullEnergylabel = new Label("Full Energy");
        Label supportTropsEnergylabel = new Label("Support Trops Energy");
        Label minimalTropsChangeslabel = new Label("Min trops changes");
        Label maximalTropsChangeslabel = new Label("Max trops changes");
        Label tropsLegacyLengthlabel = new Label("Troops overview");
        Label trenchPerDaylabel = new Label("Trenches built per day");
        Label movementDetailslabel = new Label("Movement details");
        Label tropsMovinglabel = new Label("Troops moving");
        Label developmentVariantlabel = new Label("Development Variants");
        Label fieldVariantlabel = new Label("Field Variants");

        VBox leftLabelsList = new VBox(18);
        VBox rightLabelsList = new VBox(18);

        leftLabelsList.getChildren().addAll(namelabel, fieldWidthlabel, fieldHeightlabel, startTrenchQuantitylabel,
                startTrenchQualitylabel, startTropsQuantitylabel, startTropsEnergylabel, tropsFullEnergylabel);

        rightLabelsList.getChildren().addAll(supportTropsEnergylabel, minimalTropsChangeslabel,
                maximalTropsChangeslabel, tropsLegacyLengthlabel, trenchPerDaylabel, movementDetailslabel,
                tropsMovinglabel, developmentVariantlabel, fieldVariantlabel);

        HBox inputList = new HBox(10);
        inputList.getChildren().addAll(leftLabelsList, rightLabelsList, listedFieldsLeft, listedFieldsRight);
        inputList.setAlignment(Pos.TOP_CENTER);

        VBox confirmation = new VBox(parametersConfirmation);
        VBox.setVgrow(parametersConfirmation, Priority.ALWAYS);
        VBox.setMargin(parametersConfirmation, new Insets(60, 0, 200, 0));
        confirmation.setAlignment(Pos.TOP_CENTER);

        borderPane.setCenter(inputList);
        borderPane.setBottom(confirmation);
        borderPane.setBackground(new Background(new BackgroundFill(Color.PALETURQUOISE, CornerRadii.EMPTY, Insets.EMPTY)));

        parametersConfirmation.setOnAction(action -> {
            String configName;
            configName = name.getText();
            if (configName.contains(",") || configName.isEmpty() || configName.charAt(0) == ' '
                    || configName.charAt(configName.length()-1)  == ' ') {
                try {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("error");
                    alert.setHeaderText("Incorrect name");
                    alert.setContentText("Config name may contain sam forbidden chars");
                    alert.showAndWait();
                    throw new Exception("Incorrect config name");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            try {
                if (ConfigurateSelection.find(configName) != null) {
                    throw new Exception("This configuration name is already taken, please choose another one");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            /*
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
            */
            String[] textFieldsValues = new String[16];
            textFieldsValues[0] = fieldWidth.getText();
            textFieldsValues[1] = fieldHeight.getText();
            textFieldsValues[2] = startTrenchQuantity.getText();
            textFieldsValues[3] = trenchRecoveryEnergy.getText();
            textFieldsValues[4] = startTropsQuantity.getText();
            textFieldsValues[5] = startTropsEnergy.getText();
            textFieldsValues[6] = tropsFullEnergy.getText();
            textFieldsValues[7] = supportTropsEnergy.getText();
            textFieldsValues[8] = minimalTropsChanges.getText();
            textFieldsValues[9] = maximalTropsChanges.getText();
            textFieldsValues[10] = tropsLegacyLength.getText();
            textFieldsValues[11] = trenchPerDay.getText();
            textFieldsValues[12] = movementDetails.getValue();
            textFieldsValues[13] = tropsMoving.getValue();
            textFieldsValues[14] = developmentVariant.getValue();
            textFieldsValues[15] = fieldVariant.getValue();

            Settings parameter;

            try {
                parameter = new Settings(configName, textFieldsValues);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("error");
                alert.setHeaderText("WRONG INPUT DATA");
                alert.setContentText("Check and repair your settings and then try again");
                alert.showAndWait();

                throw new RuntimeException(e);
            }

            try {
                ConfigurateSelection.add(configName, textFieldsValues);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            try {
                new LaunchApp(parameter);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            stage.close();
        });
    }
}
