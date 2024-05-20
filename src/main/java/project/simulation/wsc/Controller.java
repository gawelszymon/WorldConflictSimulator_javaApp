package project.simulation.wsc;

import project.simulation.wsc.gui.LaunchApp;

public class Controller implements ISimulationViewer {
    private final LaunchApp view;

    public Controller(SimulationEngine model, LaunchApp view) {
        this.view = view;
        model.setViewer(this);
    }

    @Override
    public void SimulationStep() {
        view.uploadMap();
    }
}
