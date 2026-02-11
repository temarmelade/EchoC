package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EchoServer {
    private final int port;
    private final ExecutorService pool = Executors.newCachedThreadPool();
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
                pool.submit(() -> handle(clientSocket));
            }
        }
        catch (IOException e) {
            System.out.printf("К сожалению порт %s уже занят.%n", port);
        }
    }
    private void handle(Socket socket) {
        System.out.printf("Hello, %s%n%n%n", socket);
        try(socket; Scanner reader = getReader(socket); PrintWriter writer = getWriter(socket)) {
            sendResponse("Привет " + socket, writer);
            while (true) {
                String line = reader.nextLine().strip();
                if (isQuit(line) || isEmpty(line)) {
                    break;
                }
                sendResponse(line, writer);
            }
        } catch (NoSuchElementException e) {
            System.out.println("Client dropped connection");
        } catch (IOException e) {
            e.printStackTrace();
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
//    private String reverseMessage(String message) {
//        return new StringBuilder(message).reverse().toString();
//    }
}
