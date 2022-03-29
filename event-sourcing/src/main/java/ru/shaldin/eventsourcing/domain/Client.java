package ru.shaldin.eventsourcing.domain;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
public class Client {
    @NonNull
    private int clientId;
    @NonNull
    private String name;
    private int subscription;

    public Client(int clientId, String name) {
        this.clientId = clientId;
        this.name = name;
    }

    public void setSubscription(int subscription) {
        this.subscription = subscription;
    }

    public int getSubscription() {
        return subscription;
    }

    public int getClientid() {
        return clientId;
    }

    public String getName() {
        return name;
    }
}
