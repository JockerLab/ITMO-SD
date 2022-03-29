package ru.shaldin.eventsourcing.projectors;

import org.springframework.stereotype.Service;
import ru.shaldin.eventsourcing.events.*;
import ru.shaldin.eventsourcing.repository.ReportReadRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class ReportProjector {
    ReportReadRepository readRepository = new ReportReadRepository();
    public ReportProjector(ReportReadRepository readRepository) {
        this.readRepository = readRepository;
    }

    public void project(List<Event> events) {
        for (Event event : events) {
            if (event instanceof VisitClientEvent)
                apply((VisitClientEvent) event);
        }
    }

    public void apply(VisitClientEvent event) {
        readRepository.saveVisit(event.getEntry(), event.getLeave());
    }
}
