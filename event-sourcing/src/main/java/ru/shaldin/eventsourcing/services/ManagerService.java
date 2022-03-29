package ru.shaldin.eventsourcing.services;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shaldin.eventsourcing.aggregates.ManagerAggregate;
import ru.shaldin.eventsourcing.commands.BindSubscriptionCommand;
import ru.shaldin.eventsourcing.commands.CreateClientCommand;
import ru.shaldin.eventsourcing.commands.CreateSubscriptionCommand;
import ru.shaldin.eventsourcing.commands.ExtendSubscriptionCommand;
import ru.shaldin.eventsourcing.events.Event;
import ru.shaldin.eventsourcing.projectors.ClientProjector;

import java.util.List;

@RestController
@RequestMapping("/manager")
public class ManagerService {
    private final ManagerAggregate managerAggregate;
    private final ClientProjector clientProjector;

    public ManagerService(ManagerAggregate managerAggregate, ClientProjector clientProjector) {
        this.managerAggregate = managerAggregate;
        this.clientProjector = clientProjector;
    }

    @PostMapping("/createClient")
    public void createClient(@RequestBody CreateClientCommand command) {
        List<Event> events = managerAggregate.handleCreateClientCommand(command);
        clientProjector.project(command.getClientId(), events);
    }

    @PostMapping("/createSubscription")
    public void createSubscription(@RequestBody CreateSubscriptionCommand command) {
        List<Event> events = managerAggregate.handleCreateSubscriptionCommand(command);
        clientProjector.project(command.getClientId(), events);
    }

    @PostMapping("/bindSubscription")
    public void bindSubscription(@RequestBody BindSubscriptionCommand command) {
        List<Event> events = managerAggregate.handleBindSubscriptionCommand(command);
        clientProjector.project(command.getClientId(), events);
    }

    @PostMapping("/extendSubscription")
    public void extendSubscription(@RequestBody ExtendSubscriptionCommand command) {
        List<Event> events = managerAggregate.handleExtendSubscription(command);
        clientProjector.project(command.getSubscriptionId(), events);
    }
}
