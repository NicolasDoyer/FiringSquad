import helpers.Logger;
import problem.Automata;
import runnables.HillClimberFIRunner;
import runnables.IteratedLocalSearchRunner;
import runnables.RandomSearchRunner;
import searchers.HillClimberFI;
import searchers.IteratedLocalSearch;
import searchers.RandomSearch;
import searchers.TabouSearch;
import searchers.utils.Solution;

import java.io.File;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final int MAX_FIRING_MEN = 30;
    private List<Option> optsList;
    private List<String> doubleOptsList;

    private class Option {
        String flag, opt;
        public Option(String flag, String opt) { this.flag = flag; this.opt = opt; }
    }

    public Main(){
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
                    executable.launchBenchmark(automata);
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
                    IteratedLocalSearch ils = new IteratedLocalSearch( (iteration == -1) ? IteratedLocalSearch.DEFAULT_ITERATIONS : iteration, 3);
                    s = ils.search(automata);
                    Logger.printAndSaveSolution(s,automata);
                    break;
                case "tabou":
                    TabouSearch ts = new TabouSearch( (iteration == -1) ? IteratedLocalSearch.DEFAULT_ITERATIONS : iteration);
                    s = ts.search(automata);
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
        System.out.println("         --tabou : run Tabou Search method (use '-iteration X' to perform X iterations on the algorithm) ");
        System.out.println("         -iteration : specify the number of iterations to run (if its not specified, then the algorithm will use its default value available in class)");
    }

    private void launchBenchmark(Automata automata){
        // Clean old benchmarks
        String[] paths = {System.getProperty("user.dir")+"/benchmark/", System.getProperty("user.dir")+"/benchmark/old/"};
        for(String path: paths){
            File directory = new File(path);
            if (! directory.exists()){
                directory.mkdir();
            }
        }
        moveOldfile(RandomSearchRunner.DEFAULT_FILENAME);
        moveOldfile(HillClimberFIRunner.DEFAULT_FILENAME);
        moveOldfile(IteratedLocalSearchRunner.DEFAULT_FILENAME);


        // Creating Threads in order to speed up the benchmark
        System.out.println("Loading Benchmark ...");
        Thread randomSearchThread = new Thread(new RandomSearchRunner(RandomSearch.DEFAULT_ITERAITONS,automata));
        Thread hillClimberFIThread = new Thread(new HillClimberFIRunner(100,automata));
        Thread iteratedLocalSearchThread = new Thread(new IteratedLocalSearchRunner(10,automata));

        // Launching Threads
        System.out.println("Running Benchmark (May take a while) ...");
        randomSearchThread.start();
        hillClimberFIThread.start();

        try{
            randomSearchThread.join();
            hillClimberFIThread.join();
        }catch (Exception e){
            System.out.println("Thread interrupted");
        }

        iteratedLocalSearchThread.start();
        try{
            iteratedLocalSearchThread.join();
        }catch (Exception e){
            System.out.println("Thread interrupted");
        }

        System.out.println("Benchmark finished !");
    }

    private void moveOldfile(String filename){
        String path = System.getProperty("user.dir")+"/benchmark/";
        try {
            Files.move(Paths.get(path + filename), Paths.get(path + "old/" +filename), StandardCopyOption.REPLACE_EXISTING);
        }catch (NoSuchFileException e) {
            System.out.println("No old " + filename + " to move");
        }catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}




