package Server.server;

import Server.searcher_interface.Heuristic;
import Server.searcher_interface.State;

public class PgManhattanDistance implements Heuristic<PgLevel> {
    @Override
    public double indication() {
        return Double.POSITIVE_INFINITY;
    }

    @Override
    public boolean isLeftBetter(double left, double right) {
        return left < right;
    }

    @Override
    public double calcHeuristic(State<PgLevel> s) {
        int positionX = s.getState().getPosition().x;
        int positionY = s.getState().getPosition().y;
        int endX = s.getState().getEnd().x;
        int endY = s.getState().getEnd().y;
        return Math.abs(positionX - endX) + Math.abs(positionY - endY); // in Pipe Game the movement cost is always 1.
    }
}