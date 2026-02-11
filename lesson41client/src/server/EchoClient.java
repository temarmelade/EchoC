package server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class EchoClient {
    private final int port;
    private final String host;
    private EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }
    public static EchoClient toConnect(int port) {
        return new EchoClient("localhost", port);
    }
    public void run() {
        System.out.printf("Eldana<3%n%n%n");
        try (Socket socket = new Socket(host, port)) {
            Scanner scanner = new Scanner(System.in, "UTF-8");
            OutputStream os = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(os);
            try(scanner; writer) {
                while (true) {
                    String line = scanner.nextLine();
                    writer.write(line);
                    writer.write(System.lineSeparator());
                    writer.flush();
                    if (line.equalsIgnoreCase("bye")) {
                        return;
                    }
                }
            }
        } catch (NoSuchElementException e) {
            System.out.println("Connection dropped");
        } catch (IOException e) {
            System.out.printf("Can not connect to %s: %s%n", host, port);
            e.printStackTrace();
        }
    }
}
