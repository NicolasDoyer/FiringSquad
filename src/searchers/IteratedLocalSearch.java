package searchers;

        import helpers.Initializer;
        import helpers.NeighboursHelper;
        import problem.Automata;
        import problem.Neighbour;
        import searchers.utils.Solution;

        import java.util.Random;
        import java.util.Vector;

public class IteratedLocalSearch extends AbstractSearcher{

    public static final int DEFAULT_ITERATIONS = 2000;
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

        // Helpers
        Initializer initializer = new Initializer();
        HillClimberFI hillClimberFI = new HillClimberFI(HillClimberFI.DEFAULT_ITERATIONS,rules);

        // Initialize first solution
        Solution solution = hillClimberFI.search(automata);

        // Beginning iterated local search
        for(int i = 0; i < iteration; i++){
            int previousFitness = solution.getFitness();
            int[] previousRules   = solution.getRules().clone();

            runPerturbation(solution.getRules(),initializer);
            hillClimberFI.setInitialRules(solution.getRules());
            solution = hillClimberFI.search(automata);

            System.out.println("Iteration n°" + i + " |  Best: " + previousFitness + " New: " + solution.getFitness());

            if(solution.getFitness() <= previousFitness){
                solution.setFitness(previousFitness);
                solution.setRules(previousRules);
            }
        }
        return solution;
    }

    private void runPerturbation(int[] rules, Initializer initializer){
        Random generator = new Random();
        Vector<Neighbour> neighbours = NeighboursHelper.getNeighbours(rules);
        Vector<Neighbour> toDelete = new Vector<>();
        for(int i = 0; i < this.pertubationNumber; i++){
            // Choose a random rule
            int randomIndex = generator.nextInt(neighbours.size());
            Neighbour chosenNeighbour = neighbours.get(randomIndex);
            initializer.setRule(rules,chosenNeighbour.getIndex(),chosenNeighbour.getState());

            // Delete all similar neighbours (with the same index)
            for(Neighbour neighbour: neighbours){
                if(neighbour.getIndex() == chosenNeighbour.getIndex())
                    toDelete.add(neighbour);
            }
            neighbours.removeAll(toDelete);
        }
    }
}
