package ru.shaldin.eventsourcing.events;

public class CreatedClientEvent extends Event {
    private int clientId;
    private String name;

    public CreatedClientEvent(int clientId, String name) {
        this.clientId = clientId;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getClientId() {
        return clientId;
    }
}
