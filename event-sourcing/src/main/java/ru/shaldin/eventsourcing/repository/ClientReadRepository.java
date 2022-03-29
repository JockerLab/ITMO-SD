package ru.shaldin.eventsourcing.repository;

import org.springframework.stereotype.Repository;
import ru.shaldin.eventsourcing.domain.Client;
import ru.shaldin.eventsourcing.domain.Subscription;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ClientReadRepository {
    private Map<Integer, Client> clientStore = new HashMap<>();
    private Map<Integer, Subscription> subscriptionStore = new HashMap<>();

    public void addClient(int clientId, Client client) {
        clientStore.put(clientId, client);
    }

    public Client getClient(int clientId) {
        return clientStore.get(clientId);
    }

    public void addSubscription(int subscriptionId, Subscription subscription) {
        subscriptionStore.put(subscriptionId, subscription);
    }

    public Subscription getSubscription(int subscriptionId) {
        return subscriptionStore.get(subscriptionId);
    }
}
