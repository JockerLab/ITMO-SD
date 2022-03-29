package ru.shaldin.eventsourcing.commands;

import java.util.Date;

public class VisitClientCommand {
    private Date entry;
    private Date leave;

    public VisitClientCommand(Date entry, Date leave) {
        if (entry == null) {
            this.entry = new Date();
        } else {
            this.entry = entry;
        }
        if (leave == null) {
            this.leave = new Date();
        } else {
            this.leave = leave;
        }
    }

    public Date getEntry() {
        return entry;
    }

    public Date getLeave() {
        return leave;
    }

}
