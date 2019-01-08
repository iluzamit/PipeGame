package Server.common_searchers;

import Server.searcher_interface.*;
import java.util.List;

public class BestFirstSearch<T> extends PrioritySearcher<T> implements Searcher<T> {
    public BestFirstSearch(Heuristic heuristic) {
        super(heuristic);
    }

    @Override
    public Solution search(Searchable<T> searchable) {
        openList.add(searchable.getInitialState());
        while (openList.size() > 0) {
            State<T> n = openList.poll();
            if (searchable.isGoalState(n)) {
                System.out.println("Best-first: GOAL!");
                return BackTrace(n);
            }

            List<State<T>> possibleStates = searchable.getPossibleStates(n);
            for (State<T> s : possibleStates) {
                if (!closeList.contains(s)) {
                    if (!openList.contains(s)) {
                        openList.add(s);
                        } else {
                        updateCostInPriorityQueue(s);
                    }
                }
            }

            closeList.add(n);
        }
        System.out.println("Best-first: Can't find path");

        return null;
    }
}