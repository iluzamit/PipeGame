package Server.common_searchers;

import Server.searcher_interface.Searchable.*;
import Server.searcher_interface.Searchable;
import Server.searcher_interface.Solution;
import Server.searcher_interface.State;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class CommonSearcher<T> {
    List<State<T>> closeList;
    CommonSearcher() {
        closeList = new ArrayList<>();
    }

    Solution<T> BackTrace(State<T> state) {
        Solution<T> solution = new Solution<>();
        State<T> currentState = state;

        while (currentState.hasCameFrom()) {
            solution.add(currentState.getState());
            currentState = currentState.getCameFrom();
        }

        solution.add(currentState.getState());
        Collections.reverse(solution);

        return solution;
    }
}