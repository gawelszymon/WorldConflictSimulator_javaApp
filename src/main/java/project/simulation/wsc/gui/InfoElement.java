package project.simulation.wsc.gui;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
            //TODO app.setFollowingTroop(null);
        });

        //TODO this.needDate = infoData();
    }
}
