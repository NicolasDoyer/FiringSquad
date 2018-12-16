import helpers.Logger;
import problem.Automata;
import searchers.HillClimberFI;
import searchers.IteratedLocalSearch;
import searchers.RandomSearch;
import searchers.utils.Solution;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final int MAX_FIRING_MEN = 30;
    private static final String DEFAULT_FILE_FORMAT = ".csv";
    List<String> argsList;
    List<Option> optsList;
    List<String> doubleOptsList;

    private class Option {
        String flag, opt;
        public Option(String flag, String opt) { this.flag = flag; this.opt = opt; }
    }

    public Main(){
        this.argsList = new ArrayList<>();
        this.optsList = new ArrayList<>();
        this.doubleOptsList = new ArrayList<>();
    }


    public static void main(String[] args){
        // Init options
        Main executable = new Main();
        executable.parseOptions(args);

        //Automata & Final Solution
        Automata automata = new Automata(MAX_FIRING_MEN);
        Solution s;


        int iteration = -1;
        for(Option option: executable.optsList){
            if(option.flag.equals("-iteration")){
                iteration = Integer.valueOf(option.opt);
            }
        }

        for (String doubleOption: executable.doubleOptsList) {
            switch (doubleOption){
                case "help":
                    executable.printHelp();
                    break;
                case "benchmark":
                    executable.launchBenchmark();
                    break;
                case "randomsearch":
                    RandomSearch randomSearch = new RandomSearch( (iteration == -1) ? RandomSearch.DEFAULT_ITERAITONS : iteration);
                    s = randomSearch.search(automata);
                    Logger.printAndSaveSolution(s,automata);
                    break;
                case "hcfi":
                    HillClimberFI hcfi = new HillClimberFI( (iteration == -1) ? HillClimberFI.DEFAULT_ITERATIONS : iteration);
                    s = hcfi.search(automata);
                    Logger.printAndSaveSolution(s,automata);
                    break;
                case "ils":
                    IteratedLocalSearch ils = new IteratedLocalSearch( (iteration == -1) ? IteratedLocalSearch.DEFAULT_ITERATIONS : iteration);
                    s = ils.search(automata);
                    Logger.printAndSaveSolution(s,automata);
                    break;
                default:
                    break;
            }
        }
    }

    private void parseOptions(String[] args){
        for (int i = 0; i < args.length; i++) {
            switch (args[i].charAt(0)) {
                case '-':
                    if (args[i].length() < 2)
                        throw new IllegalArgumentException("Not a valid argument: "+args[i]);
                    if (args[i].charAt(1) == '-') {
                        if (args[i].length() < 3)
                            throw new IllegalArgumentException("Not a valid argument: "+args[i]);
                        doubleOptsList.add(args[i].substring(2, args[i].length()));
                    } else {
                        if (args.length-1 == i)
                            throw new IllegalArgumentException("Expected arg after: "+args[i]);
                        optsList.add(new Option(args[i], args[i+1]));
                        i++;
                    }
                    break;
                default:
                    // arg
                    argsList.add(args[i]);
                    break;
            }
        }
    }

    private void printHelp(){
        System.out.println();
        System.out.println("Usage: java Main [option(s)]");
        System.out.println("Options:");
        System.out.println("         --help : print help");
        System.out.println("         --benchmark : launch benchmark with multiple search methods (results are saved in different files) ( |!!!| It may take a while)");
        System.out.println("         --randomsearch : run Random Search method (use '-iteration X' to perform X iterations on the algorithm) ");
        System.out.println("         --hcfi : run Hill Climber First Improvement method (use '-iteration X' to perform X iterations on the algorithm) ");
        System.out.println("         --ils : run Iterated Local Search method (using hcfi as local search) (use '-iteration X' to perform X iterations on the algorithm) ");
        System.out.println("         -iteration : specify the number of iterations to run (if its not specified, then the algorithm will use its default value available in class)");
    }

    private void launchBenchmark(){

    }
}




