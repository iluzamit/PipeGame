package Server.server;

import Server.server_interface.CacheManager;
import java.io.*;
import java.util.Scanner;

public class PgCacheManager implements CacheManager {
    @Override
    public PgInstructions load(String request) {
        try {
            Scanner in = new Scanner(new FileInputStream(path + Integer.toString(request.hashCode())));
            PgInstructions directions = new PgInstructions();
            while(in.hasNextLine()){
                directions.add(in.nextLine());
            }
            return directions;
        } catch (FileNotFoundException e) {
        }

        return null;
    }

    @Override
    public void save(String problem, String solution) throws IOException {
        FileOutputStream fileOutputStream=new FileOutputStream(path + Integer.toString(problem.hashCode()));
        fileOutputStream.write(solution.getBytes());
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    private String path;

    public PgCacheManager() {
        path = "./db/";
        new File(path).mkdirs();
    }
}