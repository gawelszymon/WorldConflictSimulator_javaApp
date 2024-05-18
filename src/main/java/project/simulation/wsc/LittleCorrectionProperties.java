package project.simulation.wsc;

import java.util.Random;

public class LittleCorrectionProperties implements ITroopOverview {

    private final int[] CORRECT = {-1, 1};
    private final Random randomMachine = new Random();

    @Override
    public void troopAbilitiesDevelopment(int[] overviewID) {
        int N = overviewID.length;
        int howMany = randomMachine.nextInt(N+1);

        for (int i = 0; i  < howMany; i++) {
            int chosenProperty = randomMachine.nextInt(N);
            overviewID[chosenProperty] = overviewID[chosenProperty] + CORRECT[randomMachine.nextInt(CORRECT.length)];

            if (overviewID[chosenProperty] < 0) {
                overviewID[chosenProperty] = 7;
            }

            if (overviewID[chosenProperty] > 7) {
                overviewID[chosenProperty] = 0;
            }
        }
    }
}
