package Server.server;

import Server.searcher_interface.Heuristic;
import Server.searcher_interface.State;
import java.util.Random;

public class PgRandomHeuristic implements Heuristic<PgLevel> {
    private Random random = new Random();

    @Override
    public double indication() {
        return 0;
    }

    @Override
    public boolean isLeftBetter(double left, double right) {
        return left > right;
    }

    @Override
    public double calcHeuristic(State<PgLevel> s) {
        return (s.getCost() *random.nextInt());
    }
}