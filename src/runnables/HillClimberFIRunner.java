package runnables;

import helpers.Initializer;
import helpers.Logger;
import problem.Automata;
import searchers.HillClimberFI;
import searchers.utils.Solution;

public class HillClimberFIRunner implements Runnable {

    private int globalIteration;
    private Automata automata;
    public static final String DEFAULT_FILENAME = "hcfi_data.csv";

    public HillClimberFIRunner(int globalIteration, Automata automata) {
        this.globalIteration = globalIteration;
        this.automata = automata;
    }

    @Override
    public void run() {
        Solution s;
        for(int step = 250000; step <= 1000000; step *=2){
            for(int i = 0; i < this.globalIteration; i++){
                HillClimberFI hillClimberFI = new HillClimberFI(step);
                s = hillClimberFI.search(automata);
                Logger.saveSolutionToFile(s,step,DEFAULT_FILENAME,automata);
            }
        }
    }
}
