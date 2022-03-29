package ru.shaldin.eventsourcing.commands;

public class CreateClientCommand {
    private int clientId;
    private String name;

    public CreateClientCommand(int clientId, String name) {
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
