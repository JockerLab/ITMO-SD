package ru.shaldin.eventsourcing.aggregates;

import org.springframework.stereotype.Service;
import ru.shaldin.eventsourcing.commands.VisitClientCommand;
import ru.shaldin.eventsourcing.events.Event;
import ru.shaldin.eventsourcing.events.VisitClientEvent;
import ru.shaldin.eventsourcing.repository.ReportEventStore;

import java.util.Arrays;
import java.util.List;

@Service
public class TurnstileAggregate {
    private final ReportEventStore writeRepository;
    public TurnstileAggregate(ReportEventStore reportRepository) {
        this.writeRepository = reportRepository;
    }

    public List<Event> handleSaveVisitCommand(VisitClientCommand command) {
        VisitClientEvent event = new VisitClientEvent(command.getEntry(), command.getLeave());
        writeRepository.addEvent(command.getEntry(), event);
        return Arrays.asList(event);
    }
}
