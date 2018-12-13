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
        Initializer initializer = new Initializer();
        Random generator = new Random();
        HillClimberFI hcfi = new HillClimberFI(10000000,rules);

        // Initialize first solution
        Solution bestSolution = hcfi.search(automata);
        Solution newSolution;

        // Beginning iterated local search
        for(int i = 0; i < iteration; i++){
            initializer.init(rules);
            hcfi = new HillClimberFI(10000000,rules);
            newSolution = hcfi.search(automata);
            System.out.println("Iteration " + i + " best: " + bestSolution.getFitness() + " found: " + newSolution.getFitness());
            if(bestSolution.getFitness() <= newSolution.getFitness()){
                bestSolution = newSolution;
            }
        }
        return bestSolution;
    }
}
