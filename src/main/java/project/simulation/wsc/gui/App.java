package project.simulation.wsc.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import project.simulation.wsc.*;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;

import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class App extends Application {

    private final BorderPane border = new BorderPane();
    private Stage primaryStage;


    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        Image backgroundImage = new Image(new FileInputStream("src/main/resources/background_photo.png"));
        BackgroundImage background = new BackgroundImage(backgroundImage,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        border.setBackground(new Background(background));

        primaryStage.getIcons().add(new Image(new FileInputStream("src/main/resources/map.png")));
        primaryStage.setTitle("WCS initialization");
        primaryStage.alwaysOnTopProperty();
        Scene scene = new Scene(border, 880, 550);
        primaryStage.setResizable(false);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initGetDate() throws IOException {
        ChoiceBox<String> confVariant = new ChoiceBox<>();
        confVariant.getItems().add("new configuration");
        confVariant.getItems().addAll(ConfigurateSelection.names());
        confVariant.setValue("new configuration");

        Button confirmButton = new Button("Confirm");
        confirmButton.getStyleClass().add("confirm-button");

        Label choiceLabel = new Label("Choose war's assumptions" );
        choiceLabel.getStyleClass().add("choiceLabel");

        HBox inputList = new HBox(10, choiceLabel, confVariant);
        inputList.setAlignment(Pos.CENTER);

        HBox confirmation = new HBox(50, confirmButton);
        BorderPane.setMargin(confirmation, new Insets(10, 0, 60, 0));

        border.setCenter(inputList);
        border.setBottom(confirmation);
        confirmation.setAlignment(Pos.BOTTOM_CENTER);
        //border.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY)));

        confirmButton.setOnAction(action -> {
            try {
                String items = confVariant.getValue();
                if (items.equals("new configuration")) {
                    new GetWarData(border, primaryStage);
                } else {
                    String[] headers = ConfigurateSelection.names();
                    for (String name : headers) {
                        if (items.equals(name)) {
                            String[] parameters = ConfigurateSelection.find(name);
                            if (parameters != null) {
                                Settings settings = new Settings(name, parameters);
                                new LaunchApp(settings);
                                this.primaryStage.close();
                            } else {
                                throw new Exception("wrong configuration");
                            }
                        }
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

    }

    @Override
    public void init() throws IOException {
        initGetDate();
    }
}

