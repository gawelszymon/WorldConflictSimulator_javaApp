package project.simulation.wsc.gui;

import javafx.application.Application;
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

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.getIcons().add(new Image(new FileInputStream("src/main/resources/map.png")));
        primaryStage.setTitle("WCS initialization");
        primaryStage.alwaysOnTopProperty();
        primaryStage.setScene(new Scene(border, 880, 460));
        primaryStage.show();
    }

    private void initBorder() {
        Label tittle = new Label("Choose war's assumptions");
        //tittle.setStyle();
        border.setTop(tittle);
        BorderPane.setAlignment(tittle, Pos.CENTER);
        BorderPane.setMargin(tittle, new Insets(20, 0, 20, 0));
    }

    private void initGetDate() throws FileNotFoundException {
        ChoiceBox<String> confVariant = new ChoiceBox<>();
        confVariant.getItems().add("New configuration");
        confVariant.getItems().addAll(ConfigurateSelection.names());
        confVariant.setValue("New configuration");

        Button confirmButton = new Button("Confirm");
        Button exButton = new Button("Exit");

        Label choiceLabel = new Label("Chosen label" );

        HBox inputList = new HBox(10, choiceLabel, confVariant);
        inputList.setAlignment(Pos.CENTER);

        HBox confirmation = new HBox(50, confirmButton, exButton);
        BorderPane.setMargin(confirmation, new Insets(10, 0, 60, 0));

        border.setCenter(inputList);
        border.setBottom(confirmation);
        confirmation.setAlignment(Pos.BOTTOM_CENTER);
        border.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY)));

        confirmButton.setOnAction(action -> {
            try {
                String items = confVariant.getValue();
                if (items.equals("New configuration")) {
                    new GetWarData();
                } else {
                    String[] headers = ConfigurateSelection.names();
                    for (String name : headers) {
                        if (items.equals(name)) {
                            String[] parameters = ConfigurateSelection.find(name);
                            if (parameters != null) {
                                Settings settings = new Settings(name, parameters);
                                new LaunchApp(settings);
                            } else {
                                throw new Exception("wrong data configuration");
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
        initBorder();
        initGetDate();
    }
}

