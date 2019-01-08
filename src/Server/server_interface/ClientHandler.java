package Server.server_interface;

import java.io.*;

public interface ClientHandler {
    void handler(BufferedReader in, PrintWriter out) throws IOException;
}