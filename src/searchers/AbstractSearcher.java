package searchers;

import problem.Automata;
import searchers.utils.Solution;

public abstract class AbstractSearcher {

    public abstract Solution search(Automata automata);
}
