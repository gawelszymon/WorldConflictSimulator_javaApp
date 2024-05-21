package project.simulation.wsc.gui;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Pictures {

    public Image trenchImage;
    public Image ImageID1;
    public Image ImageID2;
    public Image ImageID3;
    public Image ImageID4;
    public Image ImageID5;

    public Pictures() {
        try {
            this.trenchImage = new javafx.scene.image.Image(new FileInputStream("src/main/resources/trench.png"));
            this.ImageID1 = new javafx.scene.image.Image(new FileInputStream("src/main/resources/id1.png"));
            this.ImageID2 = new javafx.scene.image.Image(new FileInputStream("src/main/resources/id2.png"));
            this.ImageID3 = new javafx.scene.image.Image(new FileInputStream("src/main/resources/id3.png"));
            this.ImageID4 = new javafx.scene.image.Image(new FileInputStream("src/main/resources/id4.png"));
            this.ImageID5 = new javafx.scene.image.Image(new FileInputStream("src/main/resources/id5.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
