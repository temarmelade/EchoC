import server.EchoClient;

public class Main {
    public static void main(String[] args) {
        EchoClient.toConnect(8089).run();
    }
}