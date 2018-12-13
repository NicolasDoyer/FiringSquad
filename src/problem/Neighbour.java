package problem;

public class Neighbour {

    private int state; // State in the automata
    private int index; // Index of the rule in the rules array (g * 36 + c * 6 + d)

    public Neighbour(int state, int index){
        this.state = state;
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public int getState() {
        return state;
    }
}
