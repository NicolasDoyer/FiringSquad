package runnables;

import helpers.Logger;
import problem.Automata;
import searchers.HillClimberFI;
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
        int bestFitness = 0;
        int[] bestRules = new int[216];
        for(int i = 0; i < this.globalIteration; i++){
            IteratedLocalSearch iteratedLocalSearch = new IteratedLocalSearch(IteratedLocalSearch.DEFAULT_ITERATIONS,3);
            s = iteratedLocalSearch.search(automata);
            Logger.saveSolutionToFile(s,IteratedLocalSearch.DEFAULT_ITERATIONS,DEFAULT_FILENAME,automata);

            if(s.getFitness() > bestFitness){
                bestFitness = s.getFitness();
                bestRules = s.getRules().clone();
            }
        }
        Solution bestSolution = new Solution(bestRules);
        bestSolution.setFitness(bestFitness);
        Logger.printAndSaveSolution(bestSolution,automata);
    }
}
