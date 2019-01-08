package Server.server;
import Server.server_interface.ClientHandler;
import Server.server_interface.Server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class PgServer implements Server {
    @Override
    public void start(ClientHandler clientHandler) {
        try {
            this.server = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stop = false;
        System.out.println("Server is on (port: " + server.getLocalPort()+ (")"));
        try {
            server.setSoTimeout(1000);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        new Thread(()-> {
            try {
                run(clientHandler);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void stop()  {
        this.stop = true;
        System.out.println("Server closed.");
    }

    private ServerSocket server;
    public boolean stop;
    private int port;


    public PgServer(int port) {
        this.port = port;
        this.server = null;
        this.stop = true;
    }

    private void run(ClientHandler clientHandler) throws IOException {
        while (!stop){
            try {
                Socket socket = this.server.accept();
                new Thread(() -> {
                    try {
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        PrintWriter out = new PrintWriter(socket.getOutputStream());
                        String firstLine = in.readLine();

                        if(!firstLine.equals("test")) {
                            System.out.println("\nNew client on port " + socket.getPort() + " Weight: " + firstLine);
                            clientHandler.handler(in, out);
                        }

                        in.close();
                        out.close();
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }catch (SocketTimeoutException ignored) { }
        }
        server.close();
    }

    public static void main(String[] args)  {
        PgServer myServer = new PgServer(6400);
        myServer.start(new PgClientHandler(new PgSolver(),new PgCacheManager()));
    }
}