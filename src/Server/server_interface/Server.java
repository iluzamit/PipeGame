package Server.server_interface;

public interface Server {
    void start(ClientHandler clientHandler);
    void stop() ;
}