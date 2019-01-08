package Server.common_searchers;

import Server.searcher_interface.*;
import java.util.ArrayList;
import java.util.Random;

public class HillClimbing<T> extends CommonSearcher<T> implements Searcher<T> {
    private long timeToRun;
    private Heuristic<T> heuristic;

    public HillClimbing(long timeToRun, Heuristic<T> heuristic) {
        this.timeToRun = timeToRun;
        this.heuristic = heuristic;
    }

    @Override
    public Solution search(Searchable<T> searchable) {
        State<T> next = searchable.getInitialState();

        long time0 = System.currentTimeMillis();

        while (System.currentTimeMillis() - time0 < timeToRun) {
            ArrayList<State<T>> neighbors = searchable.getPossibleStates(next);
            if (searchable.isGoalState(next)) {
                return this.BackTrace(next);
            }

            if (neighbors.size() > 0) {
                if (Math.random() < 0.7) {
                    double grade = heuristic.indication();
                    for (State<T> step : neighbors) {
                        double g = heuristic.calcHeuristic(step);
                        if (heuristic.isLeftBetter(g, grade)) {
                            grade = g;
                            next = step;
                        }
                    }
                } else {
                    next = neighbors.get(new Random().nextInt(neighbors.size()));
                }
            }
        }
        System.out.println("HillClimbing: TimeOut / NoPath");

        return null;
    }
}