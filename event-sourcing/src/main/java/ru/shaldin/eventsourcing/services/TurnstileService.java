package ru.shaldin.eventsourcing.services;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shaldin.eventsourcing.aggregates.ManagerAggregate;
import ru.shaldin.eventsourcing.aggregates.TurnstileAggregate;
import ru.shaldin.eventsourcing.commands.BindSubscriptionCommand;
import ru.shaldin.eventsourcing.commands.CreateClientCommand;
import ru.shaldin.eventsourcing.commands.ExtendSubscriptionCommand;
import ru.shaldin.eventsourcing.commands.VisitClientCommand;
import ru.shaldin.eventsourcing.events.Event;
import ru.shaldin.eventsourcing.projectors.ReportProjector;

import java.util.List;

@RestController
@RequestMapping("/turnstile")
public class TurnstileService {
    private final TurnstileAggregate turnstileAggregate;
    private final ReportProjector reportProjector;

    public TurnstileService(TurnstileAggregate turnstileAggregate, ReportProjector reportProjector) {
        this.turnstileAggregate = turnstileAggregate;
        this.reportProjector = reportProjector;
    }

    @PostMapping("/visit")
    public void visit(@RequestBody VisitClientCommand command) {
        List<Event> events = turnstileAggregate.handleSaveVisitCommand(command);
        reportProjector.project(events);
    }
}