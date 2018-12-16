package runnables;

import helpers.Logger;
import problem.Automata;
import searchers.RandomSearch;
import searchers.utils.Solution;


public class RandomSearchRunner implements Runnable {

    private Automata automata;
    private int globalIteration;
    public static final String DEFAULT_FILENAME = "randomsearch_data.csv";

    public RandomSearchRunner(int globalIteration, Automata automata) {
        this.globalIteration = globalIteration;
        this.automata = automata;
    }

    @Override
    public void run() {
        Solution s;
        for(int i = 100; i <= this.globalIteration; i*=1.05){
            RandomSearch randomSearch = new RandomSearch(i);
            s = randomSearch.search(automata);
            Logger.saveSolutionToFile(s,i,DEFAULT_FILENAME,automata);
        }
    }
}
