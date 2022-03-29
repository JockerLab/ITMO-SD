package ru.shaldin.eventsourcing.projections;

import org.springframework.stereotype.Service;
import ru.shaldin.eventsourcing.domain.Subscription;
import ru.shaldin.eventsourcing.events.BindSubscriptionEvent;
import ru.shaldin.eventsourcing.queries.CreateSubscriptionQuery;
import ru.shaldin.eventsourcing.repository.ClientReadRepository;

@Service
public class ManagerProjection {
    private ClientReadRepository readRepository;
    public ManagerProjection(ClientReadRepository readRepository) {
        this.readRepository = readRepository;
    }

    public Subscription handle(CreateSubscriptionQuery query) {
        return readRepository.getSubscription(query.getSubscriptionId());
    }
}
