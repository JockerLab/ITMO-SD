package ru.shaldin.eventsourcing.queries;

public class IsSubscriptionAvailableQuery {
    public IsSubscriptionAvailableQuery(int subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    private int subscriptionId;

    public int getSubscriptionId() {
        return subscriptionId;
    }
}
