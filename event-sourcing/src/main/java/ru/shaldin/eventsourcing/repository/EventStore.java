package ru.shaldin.eventsourcing.repository;

import org.springframework.stereotype.Repository;
import ru.shaldin.eventsourcing.events.Event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class EventStore {
    private Map<Integer, List<Event>> clientStore = new HashMap<>();
    private Map<Integer, List<Event>> subscriptionStore = new HashMap<>();

    public void addClientEvent(int id, Event event) {
        List<Event> events = clientStore.get(id);
        if (events == null) {
            events = new ArrayList<Event>();
            events.add(event);
            clientStore.put(id, events);
        } else {
            events.add(event);
        }
    }

    public List<Event> getClientEvents(int id) {
        return clientStore.get(id);
    }

    public void addSubscriptionEvent(int id, Event event) {
        List<Event> events = subscriptionStore.get(id);
        if (events == null) {
            events = new ArrayList<Event>();
            events.add(event);
            subscriptionStore.put(id, events);
        } else {
            events.add(event);
        }
    }

    public List<Event> getSubscriptionEvents(int id) {
        return subscriptionStore.get(id);
    }
}
