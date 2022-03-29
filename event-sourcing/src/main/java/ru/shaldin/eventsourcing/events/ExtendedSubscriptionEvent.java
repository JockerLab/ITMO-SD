package ru.shaldin.eventsourcing.events;

import java.util.Date;

public class ExtendedSubscriptionEvent extends Event {
    private final int subscriptionId;
    private final Date expiryDate;

    public ExtendedSubscriptionEvent(int subscriptionId, Date expiryDate) {
        this.subscriptionId = subscriptionId;
        this.expiryDate = expiryDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }
}
