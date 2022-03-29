package ru.shaldin.eventsourcing.events;

import java.util.Date;

public class VisitClientEvent extends Event {
    private Date entry;
    private Date leave;

    public VisitClientEvent(Date entry, Date leave) {
        this.entry = entry;
        this.leave = leave;
    }

    public Date getEntry() {
        return entry;
    }

    public Date getLeave() {
        return leave;
    }
}
