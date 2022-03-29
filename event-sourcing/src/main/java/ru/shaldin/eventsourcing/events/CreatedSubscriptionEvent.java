package ru.shaldin.eventsourcing.events;

import java.util.Date;

public class CreatedSubscriptionEvent extends Event {
    private final int subscriptionId;
    private final Date createDate;
    private final Date expiryDate;

    public CreatedSubscriptionEvent(int subscriptionId, Date createDate, Date expiryDate) {
        this.subscriptionId = subscriptionId;
        this.createDate = createDate;
        this.expiryDate = expiryDate;
    }

    public int getSubscriptionId() {
        return subscriptionId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }
}
