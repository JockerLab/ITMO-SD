package ru.shaldin.eventsourcing.commands;

public class BindSubscriptionCommand {
    private final int subscriptionId;
    private final int clientId;

    public BindSubscriptionCommand(int clientId, int subscriptionId) {
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
