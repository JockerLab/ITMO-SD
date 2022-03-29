package ru.shaldin.eventsourcing.aggregates;

import org.springframework.stereotype.Service;
import ru.shaldin.eventsourcing.ClientUtils;
import ru.shaldin.eventsourcing.commands.BindSubscriptionCommand;
import ru.shaldin.eventsourcing.commands.CreateClientCommand;
import ru.shaldin.eventsourcing.commands.CreateSubscriptionCommand;
import ru.shaldin.eventsourcing.commands.ExtendSubscriptionCommand;
import ru.shaldin.eventsourcing.domain.Subscription;
import ru.shaldin.eventsourcing.events.*;
import ru.shaldin.eventsourcing.repository.EventStore;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class ManagerAggregate {
    private EventStore writeRepository;
    public ManagerAggregate(EventStore repository) {
        this.writeRepository = repository;
    }

    public List<Event> handleCreateClientCommand(CreateClientCommand command) {
        CreatedClientEvent event = new CreatedClientEvent(command.getClientId(), command.getName());
        writeRepository.addClientEvent(command.getClientId(), event);
        return Arrays.asList(event);
    }

    public List<Event> handleCreateSubscriptionCommand(CreateSubscriptionCommand command) {
        Date currentDate = new Date();
        Date expiryDate = new Date(currentDate.getTime() + (30L * 24L * 60L * 60L * 1000L));
        CreatedSubscriptionEvent event = new CreatedSubscriptionEvent(command.getSubscriptionId(), currentDate, expiryDate);
        writeRepository.addSubscriptionEvent(command.getSubscriptionId(), event);
        return Arrays.asList(event);
    }

    public List<Event> handleBindSubscriptionCommand(BindSubscriptionCommand command) {
        BindSubscriptionEvent event = new BindSubscriptionEvent(command.getClientId(), command.getSubscriptionId());
        writeRepository.addClientEvent(command.getClientId(), event);
        return Arrays.asList(event);
    }

    public List<Event> handleExtendSubscription(ExtendSubscriptionCommand command) {
        Subscription subscription = ClientUtils.recreateSubscriptionState(writeRepository, command.getSubscriptionId());
        Date expiryDate = subscription.getExpiryDate();
        Date newExpiryDate = new Date(expiryDate.getTime() + (30L * 24L * 60L * 60L * 1000L));
        ExtendedSubscriptionEvent event = new ExtendedSubscriptionEvent(command.getSubscriptionId(), newExpiryDate);
        writeRepository.addSubscriptionEvent(command.getSubscriptionId(), event);
        return Arrays.asList(event);
    }
}
