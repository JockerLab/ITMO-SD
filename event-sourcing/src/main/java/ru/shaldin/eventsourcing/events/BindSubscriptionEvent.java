package ru.shaldin.eventsourcing.events;

public class BindSubscriptionEvent extends Event {
    private final int subscriptionId;
    private final int clientId;

    public BindSubscriptionEvent(int clientId, int subscriptionId) {
        this.subscriptionId = subscriptionId;
        this.clientId = clientId;
    }

    public int getSubscriptionId() {
        return subscriptionId;
    }

    public int getClientId() {
        return clientId;
    }
}
