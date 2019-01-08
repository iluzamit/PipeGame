package Server.server;

import java.io.*;
import Server.server_interface.CacheManager;
import Server.server_interface.ClientHandler;
import Server.server_interface.Instructions;
import Server.server_interface.Solver;

public class PgClientHandler implements ClientHandler {
    @Override
    public void handler(BufferedReader in, PrintWriter out) throws IOException {
        StringBuilder req = new StringBuilder(in.readLine() + "\n");
        while (!req.toString().contains("done")) {
            req.append(in.readLine()).append("\n");
        }

        String tmp = req.toString();
        tmp = req.toString().substring(0,tmp.length()-5);
        PgLevel request = PgLevelBuilder.build(tmp);

        try {
            out.print(this.cacheManager.load(tmp).toString());
        } catch (NullPointerException error) {
            try {
                Instructions pgInstructions = solver.solve(request);
                cacheManager.save(tmp , pgInstructions.toString());
                out.print(pgInstructions.toString());
            } catch (NullPointerException error2) {
                out.print("done");
            }
        }

        System.out.print("Client handled.");
        out.flush();
        out.close();
    }

    private Solver solver;
    private CacheManager cacheManager;

    public PgClientHandler(PgSolver solver, PgCacheManager cacheManager) {
        this.solver = solver;
        this.cacheManager = cacheManager;
    }
}