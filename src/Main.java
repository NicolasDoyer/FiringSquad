import helpers.Logger;
import problem.Automata;
import searchers.HillClimberFI;
import searchers.IteratedLocalSearch;
import searchers.RandomSearch;
import searchers.utils.Solution;

public class Main {

    private static final int MAX_FIRING_MEN = 30;

    public static void main(String[] args){
        Automata automata = new Automata(MAX_FIRING_MEN);
        //RandomSearch rs = new RandomSearch(10000);
        //Solution s = rs.search(automata);
//        HillClimberFI hcfi = new HillClimberFI(10000000);
//        Solution s = hcfi.search(automata);
        IteratedLocalSearch its = new IteratedLocalSearch(50,50);
        Solution s = its.search(automata);
        Logger.printAndSaveSolution(s,automata);
    }
}


