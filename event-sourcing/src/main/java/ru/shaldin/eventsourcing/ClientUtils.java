package ru.shaldin.eventsourcing;

import ru.shaldin.eventsourcing.domain.Subscription;
import ru.shaldin.eventsourcing.events.CreatedSubscriptionEvent;
import ru.shaldin.eventsourcing.events.Event;
import ru.shaldin.eventsourcing.events.ExtendedSubscriptionEvent;
import ru.shaldin.eventsourcing.repository.EventStore;

import java.util.List;

public class ClientUtils {
    public static Subscription recreateSubscriptionState(EventStore store, int subscriptionId) {
        Subscription subscription = null;
        List<Event> events = store.getSubscriptionEvents(subscriptionId);
        for (Event event : events) {
            if (event instanceof CreatedSubscriptionEvent) {
                CreatedSubscriptionEvent e = (CreatedSubscriptionEvent) event;
                subscription = new Subscription(e.getSubscriptionId(), e.getCreateDate(), e.getExpiryDate());
            }
            if (event instanceof ExtendedSubscriptionEvent) {
                ExtendedSubscriptionEvent e = (ExtendedSubscriptionEvent) event;
                if (subscription != null) {
                    subscription.setExpiryDate(e.getExpiryDate());
                }
            }
        }
        return subscription;
    }
}
