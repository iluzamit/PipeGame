package Server.common_searchers;

import Server.searcher_interface.Searchable;
import Server.searcher_interface.Searcher;
import Server.searcher_interface.Solution;
import Server.searcher_interface.State;
import java.util.Stack;

public class DFS<T> extends CommonSearcher implements Searcher<T> {
    private Stack<State<T>> stack;

    public DFS() {
        stack = new Stack<>();
    }

    @Override
    public Solution search(Searchable<T> searchable) {
        State<T> initialState = searchable.getInitialState();
        stack.add(initialState);

        while (!stack.isEmpty()) {
            State<T> current = stack.pop();
            if (!closeList.contains(current)) {
                if (searchable.isGoalState(current)) {
                    System.out.println("DFS: GOAL!");
                    return BackTrace(current);
                } else {
                    stack.addAll(searchable.getPossibleStates(current));
                    closeList.add(current);
                }
            }
        }
        System.out.println("DFS: Can't find path.");

        return null;
    }
}