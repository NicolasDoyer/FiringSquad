package searchers.utils;

public class Solution {

    private int[] rules;
    private int fitness;

    public Solution(int[] rules){
        this.rules = rules;
    }

    public int[] getRules() {
        return rules;
    }

    public int getFitness() {
        return fitness;
    }

    public void setRules(int[] rules) {
        this.rules = rules;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }
}
