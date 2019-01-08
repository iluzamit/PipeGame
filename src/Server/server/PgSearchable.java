package Server.server;

import Server.searcher_interface.Searchable;
import Server.searcher_interface.State;
import java.awt.*;
import java.util.*;
import java.util.List;
import static Server.server.PgSearchable.Direction.*;

public class PgSearchable implements Searchable<PgLevel> {
    private State<PgLevel> initialState;
    PgSearchable(PgLevel level) {
        this.initialState = new State<>(level);
    }

    @Override
    public State<PgLevel> getInitialState() {
        return initialState;
    }

    private boolean isOutOfBound(int i, int j) {
        return (i < 0 || i >= initialState.getState().getNumOfRows() ||
                j < 0 || j >= initialState.getState().getNumOfCols());
    }

    private Boolean hasNoPipe(PgLevel level, Point position) {
        return level.getObject(position.x, position.y) == ' ';
    }

    private Boolean isCurvedPipe(PgLevel level, Point position) {
        char c = level.getObject(position.x, position.y);
        return c == 'L' || c == 'J' || c == 'F' || c == '7';
    }

    private Boolean isGoal(PgLevel level, Point position) {
        char c = level.getObject(position.x, position.y);
        return c == 'g';
    }

    private Point applyMoveToDirection(int row, int col, Direction dir) {
        switch (dir) {
            case UP:
                return new Point(row - 1, col);
            case DOWN:
                return new Point(row + 1, col);
            case LEFT:
                return new Point(row, col - 1);
            case RIGHT:
                return new Point(row, col + 1);
        }
        return null;
    }

    private void AnalyzePossibleState(List<State<PgLevel>> list, State<PgLevel> state, Direction cameFromDirection) {
        int row = state.getState().getRow();
        int col = state.getState().getCol();
        Point position = applyMoveToDirection(row, col, cameFromDirection);
        if (isOutOfBound(position.x, position.y)) return;
        if (state.getCameFrom() != null) {
            int cameFromRow = state.getCameFrom().getState().getRow();
            int cameFromCol = state.getCameFrom().getState().getCol();
            if (position.x == cameFromRow && position.y == cameFromCol)
                return;
        }

        PgLevel level = state.getState();
        PgLevel tmp = new PgLevel(level);

        if (hasNoPipe(level, position)) return;
        if (isGoal(level, position)) {
            tmp.setRow(position.x);
            tmp.setCol(position.y);
            list.add(new State<>(new PgLevel(tmp)));
            return;
        }

        tmp.setRow(position.x);
        tmp.setCol(position.y);
        switch (cameFromDirection) {
            case UP:
                if (isCurvedPipe(level, position)) {
                    tmp.setObject(position.x, position.y, 'F');
                    list.add(new State<>(new PgLevel(tmp)));
                    tmp.setObject(position.x, position.y, '7');
                    list.add(new State<>(new PgLevel(tmp)));
                } else {
                    tmp.setObject(position.x, position.y, '|');
                    list.add(new State<>(new PgLevel(tmp)));
                }
                break;
            case DOWN:
                if (isCurvedPipe(level, position)) {
                    tmp.setObject(position.x, position.y, 'J');
                    list.add(new State<>(new PgLevel(tmp)));
                    tmp.setObject(position.x, position.y, 'L');
                    list.add(new State<>(new PgLevel(tmp)));
                } else {
                    tmp.setObject(position.x, position.y, '|');
                    list.add(new State<>(new PgLevel(tmp)));
                }
                break;
            case RIGHT:
                if (isCurvedPipe(level, position)) {
                    tmp.setObject(position.x, position.y, 'J');
                    list.add(new State<>(new PgLevel(tmp)));
                    tmp.setObject(position.x, position.y, '7');
                    list.add(new State<>(new PgLevel(tmp)));
                } else {
                    tmp.setObject(position.x, position.y, '-');
                    list.add(new State<>(new PgLevel(tmp)));
                }
                break;
            case LEFT:
                if (isCurvedPipe(level, position)) {
                    tmp.setObject(position.x, position.y, 'F');
                    list.add(new State<>(new PgLevel(tmp)));
                    tmp.setObject(position.x, position.y, 'L');
                    list.add(new State<>(new PgLevel(tmp)));
                } else {
                    tmp.setObject(position.x, position.y, '-');
                    list.add(new State<>(new PgLevel(tmp)));
                }
                break;
        }
    }

    @Override
    public ArrayList<State<PgLevel>> getPossibleStates(State<PgLevel> state) {
        ArrayList<State<PgLevel>> possibleStates = new ArrayList<>();
        int row = state.getState().getRow();
        int col = state.getState().getCol();
        List<Direction> nextSteps = nextSteps(state.getState());
        for (Direction dir : nextSteps) {
            AnalyzePossibleState(possibleStates, state, dir);
        }
        for (State<PgLevel> s : possibleStates) {
            s.setCameFrom(state);
            s.setCost(state.getCost() + 1);
        }
        return possibleStates;
    }
    public enum Direction {UP, DOWN, LEFT, RIGHT}
    private List<Direction> nextSteps(PgLevel level) {
        ArrayList<Direction> result = new ArrayList<>();
        int i = level.getRow();
        int j = level.getCol();
        char c = level.getObject(i, j);
        switch (c) {
            case 's':
                result.add(UP);
                result.add(RIGHT);
                result.add(DOWN);
                result.add(LEFT);
                break;
            case 'L':
                result.add(UP);
                result.add(RIGHT);
                break;
            case 'F':
                result.add(DOWN);
                result.add(RIGHT);
                break;
            case '7':
                result.add(DOWN);
                result.add(LEFT);
                break;
            case 'J':
                result.add(UP);
                result.add(LEFT);
                break;
            case '|':
                result.add(DOWN);
                result.add(UP);
                break;
            case '-':
                result.add(LEFT);
                result.add(RIGHT);
                break;
        }

        return result;
    }

    public boolean isGoalState(State<PgLevel> state) {
        return state.getState().getObjectOnPosition() == 'g';
    }
}