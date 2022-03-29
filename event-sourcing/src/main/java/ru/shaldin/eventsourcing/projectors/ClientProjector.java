package ru.shaldin.eventsourcing.projectors;

import org.springframework.stereotype.Service;
import ru.shaldin.eventsourcing.domain.Client;
import ru.shaldin.eventsourcing.domain.Subscription;
import ru.shaldin.eventsourcing.events.*;
import ru.shaldin.eventsourcing.repository.ClientReadRepository;

import java.util.List;

@Service
public class ClientProjector {
    ClientReadRepository readRepository = new ClientReadRepository();
    public ClientProjector(ClientReadRepository readRepository) {
        this.readRepository = readRepository;
    }

    public void project(int id, List<Event> events) {
        for (Event event : events) {
            if (event instanceof CreatedClientEvent)
                apply(id, (CreatedClientEvent) event);
            if (event instanceof BindSubscriptionEvent)
                apply(id, (BindSubscriptionEvent) event);
            if (event instanceof ExtendedSubscriptionEvent)
                apply(id, (ExtendedSubscriptionEvent) event);
            if (event instanceof CreatedSubscriptionEvent)
                apply(id, (CreatedSubscriptionEvent) event);
        }
    }

    public void apply(int clientId, CreatedClientEvent event) {
        Client client = new Client(event.getClientId(), event.getName());
        readRepository.addClient(clientId, client);
    }

    public void apply(int clientId, BindSubscriptionEvent event) {
        Client client = readRepository.getClient(clientId);
        client.setSubscription(event.getSubscriptionId());
        readRepository.addClient(clientId, client);
    }

    public void apply(int subscriptionId, ExtendedSubscriptionEvent event) {
        Subscription subscription = readRepository.getSubscription(subscriptionId);
        subscription.setExpiryDate(event.getExpiryDate());
        readRepository.addSubscription(subscriptionId, subscription);
    }

    public void apply(int subscriptionId, CreatedSubscriptionEvent event) {
        Subscription subscription = new Subscription(event.getSubscriptionId(), event.getCreateDate(), event.getExpiryDate());
        readRepository.addSubscription(subscriptionId, subscription);
    }
}
