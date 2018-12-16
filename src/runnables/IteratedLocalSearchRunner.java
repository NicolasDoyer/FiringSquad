package runnables;

import helpers.Logger;
import problem.Automata;
import searchers.IteratedLocalSearch;
import searchers.utils.Solution;

public class IteratedLocalSearchRunner implements Runnable {

    private int globalIteration;
    private Automata automata;
    public static final String DEFAULT_FILENAME = "ils_data.csv";

    public IteratedLocalSearchRunner(int globalIteration, Automata automata) {
        this.globalIteration = globalIteration;
        this.automata = automata;
    }

    @Override
    public void run() {
        Solution s;
        for(int step = 10; step <= IteratedLocalSearch.DEFAULT_ITERATIONS; step += 10){
            for(int i = 0; i < this.globalIteration; i++){
                IteratedLocalSearch iteratedLocalSearch = new IteratedLocalSearch(step);
                s = iteratedLocalSearch.search(automata);
                Logger.saveSolutionToFile(s,step,DEFAULT_FILENAME,automata);
            }
        }
    }
}
