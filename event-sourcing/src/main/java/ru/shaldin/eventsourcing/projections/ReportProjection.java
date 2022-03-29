package ru.shaldin.eventsourcing.projections;

import org.springframework.stereotype.Service;
import ru.shaldin.eventsourcing.queries.AverageVisitCountQuery;
import ru.shaldin.eventsourcing.queries.AverageVisitDurationQuery;
import ru.shaldin.eventsourcing.queries.VisitCountQuery;
import ru.shaldin.eventsourcing.repository.ReportReadRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class ReportProjection {
    private final ReportReadRepository repository;

    public ReportProjection(ReportReadRepository repository) {
        this.repository = repository;
    }

    public int handle(VisitCountQuery query) {
        return repository.getVisits(query.getDate()).size();
    }

    public double handle(AverageVisitCountQuery query) {
        Map<LocalDate, List<Long>> visits = repository.getAllVisits();
        int daysCount = 0;
        int visitsCount = 0;
        for (List<Long> dayVisits : visits.values()) {
            daysCount++;
            visitsCount += dayVisits.size();
        }
        return (double) visitsCount / daysCount;
    }

    public double handle(AverageVisitDurationQuery query) {
        Map<LocalDate, List<Long>> visits = repository.getAllVisits();
        int visitsDuration = 0;
        int visitsCount = 0;
        for (List<Long> dayVisits : visits.values()) {
            for (Long visit : dayVisits) {
                visitsDuration += visit;
            }
            visitsCount += dayVisits.size();
        }
        return (double) visitsDuration / visitsCount;
    }
}
