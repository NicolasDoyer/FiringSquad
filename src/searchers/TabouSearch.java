package searchers;

import helpers.Initializer;
import helpers.NeighboursHelper;
import problem.Automata;
import problem.Neighbour;
import searchers.utils.Solution;

import java.util.HashMap;
import java.util.Random;
import java.util.Vector;

public class TabouSearch extends AbstractSearcher{

    private int iteration;
    private int[] initialRules;
    private static final int DEFAULT_ITERATIONS = 1000000;

    public TabouSearch(int iteration){
        this.iteration = iteration;
        this.initialRules = new int[216];
        new Initializer().init(this.initialRules);
    }

    public TabouSearch(int iteration, int[] initialRules){
        this(iteration);
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
        HashMap<Integer,Integer> M = new HashMap<>();

        // Initialize first solution
        int bestFitness = automata.f(rules,30);
        Solution toReturn = new Solution(rules); toReturn.setFitness(bestFitness);


        for(int i = 0; i < this.iteration; i++){

            System.out.println("Current fitness : " + bestFitness + " Tabou: " + M.size());

            // Choose best neighbour
            Neighbour bestNeighbour = neighbours.get(generator.nextInt(neighbours.size()));
            int[] temp = rules.clone();
            init.setRule(temp,bestNeighbour.getIndex(),bestNeighbour.getState());
            int bestNeighbourFitness = automata.f(temp,30);
            for(Neighbour neighbour: neighbours){
                int[] tempRules = rules.clone();
                int tempFitness;
                init.setRule(tempRules,neighbour.getIndex(),neighbour.getState());
                tempFitness = automata.f(tempRules,30);
                if(tempFitness > bestNeighbourFitness){
                    // Check if the solution is better than the current best fitness even if it is in tabou (default aspiration)
                    if(tempFitness > bestFitness || !M.containsKey(neighbour.getIndex())){
                        bestNeighbour = neighbour;
                        bestNeighbourFitness = tempFitness;
                    }
                }
            }

            // Do not put duplicate index (aspiration)
            if(!M.containsKey(bestNeighbour.getIndex())){
                M.put(bestNeighbour.getIndex(),20);
            }

            System.out.println("Modified : " + bestNeighbour.getIndex());
            init.setRule(rules,bestNeighbour.getIndex(),bestNeighbour.getState());
            bestFitness = automata.f(rules,30);
            neighbours = NeighboursHelper.getNeighbours(rules);

            // Decrement Tabou
            Vector<Integer> toDelete = new Vector<>();
            for(Integer key: M.keySet()){
                if(M.get(key) > 0){
                    M.replace(key,M.get(key) - 1);
                }
                else{
                    toDelete.add(key);
                }
            }
            for(Integer key : toDelete){
                M.remove(key);
            }
        }

        // Returning solution
        toReturn.setRules(rules);
        toReturn.setFitness(bestFitness);
        return toReturn;
    }
}
