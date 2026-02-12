package server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EchoServer {
    private final int port;
    private final ExecutorService pool = Executors.newCachedThreadPool();
    private final ListClients listClients = new ListClients();
    public EchoServer(int port) {
        this.port = port;
    }
    public static EchoServer bindToPort(int port) {
        return new EchoServer(port);
    }
    public void run() {
        try(ServerSocket server = new ServerSocket(port)) {
            while(!server.isClosed()) {
                Socket clientSocket = server.accept();
                pool.submit(() -> {
                    listClients.addClient(new Client(clientSocket));
                    System.out.println(clientSocket.getPort());
                    handle(clientSocket);
                });
            }
        }
        catch (IOException e) {
            System.out.printf("К сожалению порт %s уже занят.%n", port);
        }
    }
    public void handle(Socket socket) {
        System.out.printf("Hello, %s%n%n%n", socket);
        Client client = new Client(socket);
        try(socket; Scanner reader = getReader(socket); PrintWriter writer = getWriter(socket)) {
            while (true) {
                String line = reader.nextLine().strip();
                if (isQuit(line) || isEmpty(line)) {
                    break;
                };
                for (Client c : listClients.getClients()) {
                    if (!c.getSocket().equals(socket)) {
                        sendResponse(client.getUsername() + ": " + line, new PrintWriter(c.getSocket().getOutputStream()));
                    }
                }
            }
        } catch (NoSuchElementException e) {
            System.out.println("Client dropped connection");
            listClients.removeClient(new Client(socket));
        } catch (IOException e) {
            System.out.printf("Sorry, there is a mistake %n%n%s", e.getMessage());
        }
        System.out.printf("Client [%s] disconnected.%n", socket);
    }
    private Scanner getReader(Socket socket) throws IOException {
        InputStreamReader isr = new InputStreamReader(socket.getInputStream());
        return new Scanner(isr);
    }
    private PrintWriter getWriter(Socket socket) throws IOException {
        return new PrintWriter(socket.getOutputStream());
    }
    private void sendResponse(String response, PrintWriter writer) throws IOException {
        writer.println(response);
        writer.flush();
    }
    private boolean isQuit(String msg) {
        return "bye".equalsIgnoreCase(msg);
    }
    private boolean isEmpty(String msg) {
        return msg == null || msg.isBlank();
    }
}
