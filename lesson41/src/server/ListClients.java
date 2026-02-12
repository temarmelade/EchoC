package server;

import java.util.ArrayList;
import java.util.List;

public class ListClients {
    private final List<Client> clients;
    public ListClients() {
        this.clients = new ArrayList<>();
    }
    public void addClient(Client client) {
        this.clients.add(client);
    }
    public void removeClient(Client client) {
        this.clients.remove(client);
    }
    public List<Client> getClients() {
        return this.clients;
    }
}
