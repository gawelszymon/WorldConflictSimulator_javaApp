package project.simulation.wsc;

import java.util.Random;

public class FullRandomProperties implements ITroopOverview {

    private final Random randomMachine = new Random();
    private final int minimum;
    private final int maximum;

    public FullRandomProperties(int min, int max) {
        this.minimum = min;
        this.maximum = max;
    }

    @Override
    public void troopAbilitiesDevelopment(int[] overviewID) {
        int N = overviewID.length;
        int howMany = randomMachine.nextInt(minimum + maximum) - minimum;
        for (int i = 0; i < howMany; i++) {
            int whichGenome = randomMachine.nextInt(N);
            overviewID[whichGenome] = randomMachine.nextInt(8);
        }
    }
}
