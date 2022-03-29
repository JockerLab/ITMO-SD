package ru.shaldin.eventsourcing.commands;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@Data
public class ExtendSubscriptionCommand {
    private int subscriptionId;

    public ExtendSubscriptionCommand(int subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public int getSubscriptionId() {
        return subscriptionId;
    }
}
