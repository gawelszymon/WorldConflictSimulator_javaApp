package project.simulation.wsc.gui;

import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Border;
import project.simulation.wsc.IMapElement;
import project.simulation.wsc.SimulationEngine;
import project.simulation.wsc.Troop;

import static java.lang.Math.min;

public class ContentBox {
    private final IMapElement element;
    private final SimulationEngine engine;

    public ContentBox(IMapElement element, SimulationEngine engine) {
        this.element = element;
        this.engine = engine;
    }

    public ProgressBar energyInTroop() {

        ProgressBar energyBar = null;

        if (element.isTroop()) {
            Troop troop = (Troop) element;
            int energy = troop.getEnergy();

            energyBar = new ProgressBar(min(1.00, (double) energy / (double) engine.getSettings().getTropsFullEnergy()));

            if (((double) energy / (double) engine.getSettings().getTropsFullEnergy() * 100.00 >= 100)) {
                energyBar.setStyle("-fx-accent: #410000;-fx-background-insets: 0 0 0 0;-fx-padding: 0em;");
            } else if (((double) energy / (double) engine.getSettings().getTropsFullEnergy()) * 100.00 >= 80.00) {
                energyBar.setStyle("-fx-accent: #7a0000;-fx-background-insets: 0 0 0 0;-fx-padding: 0em;");
            } else if (((double) energy / (double) engine.getSettings().getTropsFullEnergy()) * 100.00 >= 60.00) {
                energyBar.setStyle("-fx-accent: #b30000;-fx-background-insets: 0 0 0 0;-fx-padding: 0em;");
            } else if (((double) energy / (double) engine.getSettings().getTropsFullEnergy()) * 100.00 >= 40.00) {
                energyBar.setStyle("-fx-accent: #ec0000;-fx-background-insets: 0 0 0 0;-fx-padding: 0em;");
            } else if (((double) energy / (double) engine.getSettings().getTropsFullEnergy()) * 100.00 >= 20.00) {
                energyBar.setStyle("-fx-accent: rgb(255,0,0);-fx-background-insets: 0 0 0 0;-fx-padding: 0em;");
            } else if (((double) energy / (double) engine.getSettings().getTropsFullEnergy()) * 100.00 >= 5.00) {
                energyBar.setStyle("-fx-accent: #ff0000;-fx-background-insets: 0 0 0 0;-fx-padding: 0em;");
            } else {
                energyBar.setStyle("-fx-accent: #ffffff;-fx-background-insets:0 0 0 0;-fx-padding: 0em;");
            }
            energyBar.setBorder(Border.EMPTY);
        }

        return energyBar;
    }

}
