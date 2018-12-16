package searchers;
import helpers.Initializer;
import problem.Automata;
import searchers.utils.Solution;

public class RandomSearch extends AbstractSearcher{

    private int iteration;
    public static final int DEFAULT_ITERAITONS = 10000000;

    public RandomSearch(int iteration){
        this.iteration = iteration;
    }

    @Override
    public Solution search(Automata automata) {
        int[] bestRules = new int[216];
        int[] newRules = new int [216];
        int newFitness, bestFitness = 0;
        Initializer init = new Initializer();

        try {
            for(int i = 0; i < iteration; i++) {
                init.init(newRules);
                newFitness = automata.f(newRules, 30);
                if(bestFitness < newFitness){
                    bestFitness = newFitness;
                    bestRules = newRules.clone();
                }
            }
            Solution toReturn = new Solution(bestRules);
            toReturn.setFitness(bestFitness);
            return toReturn;
        }
        catch (Exception e){
            System.out.println(e.toString());
            return new Solution(new int[] {});
        }
    }
}
