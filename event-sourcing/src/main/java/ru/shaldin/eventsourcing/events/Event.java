package ru.shaldin.eventsourcing.events;

import java.util.UUID;

public abstract class Event {
    public final UUID id = UUID.randomUUID();
}
