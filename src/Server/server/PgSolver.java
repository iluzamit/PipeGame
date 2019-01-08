package Server.server;
import Server.common_searchers.BFS;
import Server.common_searchers.BestFirstSearch;
import Server.common_searchers.DFS;
import Server.common_searchers.HillClimbing;
import Server.searcher_interface.*;
import Server.server_interface.Solver;

public class PgSolver implements Solver<PgLevel> {
    @Override
    public PgInstructions solve(PgLevel level) {
        Searcher<PgLevel> BFSSearcher = new BFS<>();
        Searcher<PgLevel> DFSSearcer = new DFS<>();
        Searcher<PgLevel> BestFirstSearcher = new BestFirstSearch<>(new PgManhattanDistance());
        Searcher<PgLevel> HillClimbingManhattan = new HillClimbing<>(5000, new PgRandomHeuristic());
        Searcher<PgLevel> MyHB = new HillClimbing<>(500, new PgMyHeuristic());

        Solution solution;
        PgSearchable pgSearchable = new PgSearchable(level);
        solution = BestFirstSearcher.search(pgSearchable);
        PgInstructions pgInstructions = new PgInstructions(solution, level);
        System.out.println(pgInstructions);

        return pgInstructions;
    }
}