package server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class EchoFunction {
    private final Socket socket;
    ListClients listClients;
    public EchoFunction(Socket socket) {
        this.socket = socket;
        Client client = new Client(socket);
        listClients.addClient(client);
    }
    public static EchoFunction turnOn(Socket socket) {
        return new EchoFunction(socket);
    }
//    public void handle() {
//        System.out.printf("Hello, %s%n%n%n", socket);
//        try(socket; Scanner reader = getReader(socket); PrintWriter writer = getWriter(socket)) {
//            while (true) {
//                String line = reader.nextLine().strip();
//                if (isQuit(line) || isEmpty(line)) {
//                    break;
//                }
//                sendResponse(line, writer);
//                sendResponse(listClients.getClients().toString(), writer);
//            }
//        } catch (NoSuchElementException e) {
//            System.out.println("Client dropped connection");
//            listClients.removeClient(new Client(socket));
//        } catch (IOException e) {
//            System.out.printf("Sorry, there is a mistake %n%n%s", e.getMessage());
//        }
//        System.out.printf("Client [%s] disconnected.%n", socket);
//    }
//    private Scanner getReader(Socket socket) throws IOException {
//        InputStreamReader isr = new InputStreamReader(socket.getInputStream());
//        return new Scanner(isr);
//    }
//    private PrintWriter getWriter(Socket socket) throws IOException {
//        return new PrintWriter(socket.getOutputStream());
//    }
//    private void sendResponse(String response, PrintWriter writer) throws IOException {
//        writer.println(response);
//        writer.flush();
//    }
//    private boolean isQuit(String msg) {
//        return "bye".equalsIgnoreCase(msg);
//    }
//    private boolean isEmpty(String msg) {
//        return msg == null || msg.isBlank();
//    }
}