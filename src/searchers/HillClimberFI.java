package searchers;
import helpers.Initializer;
import helpers.NeighboursHelper;
import problem.Automata;
import problem.Neighbour;
import searchers.utils.Solution;

import java.util.Random;
import java.util.Vector;

public class HillClimberFI extends AbstractSearcher{

    private int iteration;
    private int[] initialRules;
    public static final int DEFAULT_ITERATIONS = 500000;

    public HillClimberFI(int iteration){
        this.iteration = iteration;
        this.initialRules = new int[216];
        new Initializer().init(this.initialRules);
    }

    public HillClimberFI(int iteration, int[] initialRules){
        this.iteration = iteration;
        this.initialRules = initialRules;
    }

    public void setInitialRules(int[] initialRules) {
        this.initialRules = initialRules;
    }

    @Override
    public Solution search(Automata automata) {
        // Helpers
        Initializer init = new Initializer();
        Random generator = new Random();

        // Rules & Neighbours
        int[] rules = this.initialRules;
        Vector<Neighbour> neighbours = NeighboursHelper.getNeighbours(rules);
        Vector<Neighbour> toDelete = new Vector<>();
        
        // Initialize first solution
        int bestFitness = automata.f(rules,30);
        Solution toReturn = new Solution(rules); toReturn.setFitness(bestFitness);

        // Beginning search
        for(int i = 0; i < iteration; i++){
            toDelete.clear();
            // When no more neighbours to explore
            if(neighbours.size() == 0){
                return toReturn;
            }

            // Choosing a random neighbour and save previous data
            int randomIndex = generator.nextInt(neighbours.size());
            Neighbour neighbour = neighbours.get(randomIndex);
            int previousState = rules[neighbour.getIndex()];
            init.setRule(rules,neighbour.getIndex(),neighbour.getState());
            int newFitness = automata.f(rules,30);

            // Backing-up previous data or saving new data
            if(newFitness < bestFitness){
                rules[neighbour.getIndex()] = previousState;
            }
            else{
                neighbours = NeighboursHelper.getNeighbours(rules);
                bestFitness = newFitness;

                // Avoid repetitions
                for(Neighbour neighbour1: neighbours){
                    if(neighbour1.getIndex() == neighbour.getIndex())
                        toDelete.add(neighbour1);
                }
                neighbours.removeAll(toDelete);
            }
        }

        // Returning solution
        toReturn.setRules(rules);
        toReturn.setFitness(bestFitness);
        return toReturn;
    }
}
