package searchers;

import helpers.Initializer;
import helpers.NeighboursHelper;
import problem.Automata;
import problem.Neighbour;
import searchers.utils.Solution;

import java.util.Random;
import java.util.Vector;

public class IteratedLocalSearch extends AbstractSearcher{

    private int iteration;
    private int pertubationNumber;

    public IteratedLocalSearch(int iteration, int pertubationNumber){
        this.iteration = iteration;
        this.pertubationNumber = pertubationNumber;
    }

    @Override
    public Solution search(Automata automata) {

        // Rules & Neighbours
        int[] rules = new int[216]; new Initializer().init(rules);
        Vector<Neighbour> neighbours;

        // Helpers
        Random generator = new Random();
        HillClimberFI hcfi = new HillClimberFI(1000000,rules);

        // Initialize first solution
        Solution toReturn = hcfi.search(automata);
        int bestFitness = toReturn.getFitness();

        // Beginning iterated local search
        for(int i = 0; i < iteration; i++){
            int[] previousRules = toReturn.getRules().clone();
            hcfi.setInitialRules(perturbate(toReturn.getRules()));
            toReturn = hcfi.search(automata);
            if(toReturn.getFitness() < bestFitness){
                toReturn.setRules(previousRules);
            }
        }
        return toReturn;
    }

    public int[] perturbate(int[] rules){
        Random generator = new Random();
        Vector<Neighbour> neighbours = NeighboursHelper.getNeightbours(rules);
        for(int i = 0; i < this.pertubationNumber; i++){
            int randomIndex = generator.nextInt(neighbours.size());
            Neighbour neighbour = neighbours.get(randomIndex);
            rules[neighbour.getIndex()] = neighbour.getState();
            neighbours.remove(randomIndex);
        }
        return rules;
    }
}
