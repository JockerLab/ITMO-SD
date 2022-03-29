package ru.shaldin.eventsourcing.projections;

import org.springframework.stereotype.Service;
import ru.shaldin.eventsourcing.domain.Subscription;
import ru.shaldin.eventsourcing.queries.IsSubscriptionAvailableQuery;
import ru.shaldin.eventsourcing.repository.ClientReadRepository;

import java.util.Date;

@Service
public class TurnstileProjection {
    private ClientReadRepository clientRepository;
    public TurnstileProjection(ClientReadRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public boolean handle(IsSubscriptionAvailableQuery query) {
        Subscription subscription = clientRepository.getSubscription(query.getSubscriptionId());
        Date currentDate = new Date();
        return subscription.getExpiryDate().after(currentDate);
    }
}
