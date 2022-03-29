package ru.shaldin.eventsourcing.commands;

import java.util.Date;

public class CreateSubscriptionCommand {
    private final int subscriptionId;
    private final int clientId;

    public CreateSubscriptionCommand(int clientId, int subscriptionId) {
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
