package searchers;

        import helpers.Initializer;
        import helpers.NeighboursHelper;
        import problem.Automata;
        import problem.Neighbour;
        import searchers.utils.Solution;

        import java.util.Random;
        import java.util.Vector;

public class IteratedLocalSearch extends AbstractSearcher{

    public static final int DEFAULT_ITERATIONS = 100;
    private int iteration;

    public IteratedLocalSearch(int iteration){
        this.iteration = iteration;
    }

    @Override
    public Solution search(Automata automata) {

        // Rules & Neighbours
        int[] rules = new int[216]; new Initializer().init(rules);

        // Helpers
        Initializer initializer = new Initializer();
        HillClimberFI hillClimberFI = new HillClimberFI(HillClimberFI.DEFAULT_ITERATIONS,rules);

        // Initialize first solution
        Solution solution = hillClimberFI.search(automata);

        // Beginning iterated local search
        for(int i = 0; i < iteration; i++){
            // Backup Data
            int[] previousRules = solution.getRules().clone();
            int previousFitness = solution.getFitness();

            // New solution (perturbation)
            initializer.init(rules);
            hillClimberFI.setInitialRules(rules);
            solution = hillClimberFI.search(automata);

            System.out.println("Iteration n°" + i + " |  Best: " + previousFitness + " New: " + solution.getFitness());

            if(previousFitness > solution.getFitness()){
                solution.setFitness(previousFitness);
                solution.setRules(previousRules);
            }
        }
        return solution;
    }
}
