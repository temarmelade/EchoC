package server;

import java.net.Socket;

public class Client {
    private final String username;
    private final int port;
    private  final Socket socket;
    public Client(Socket socket) {
        this.username = String.format("User%s", socket.getPort());
        this.port = socket.getPort();
        this.socket = socket;
    }
    public String getUsername() {
        return username;
    }
    public Socket getSocket() {
        return socket;
    }
    @Override
    public String toString() {
        return username;
    }
}

