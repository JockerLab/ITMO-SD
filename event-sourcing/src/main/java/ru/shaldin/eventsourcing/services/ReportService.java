package ru.shaldin.eventsourcing.services;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shaldin.eventsourcing.aggregates.ManagerAggregate;
import ru.shaldin.eventsourcing.commands.BindSubscriptionCommand;
import ru.shaldin.eventsourcing.commands.CreateClientCommand;
import ru.shaldin.eventsourcing.commands.ExtendSubscriptionCommand;
import ru.shaldin.eventsourcing.events.Event;
import ru.shaldin.eventsourcing.projections.ReportProjection;
import ru.shaldin.eventsourcing.queries.AverageVisitCountQuery;
import ru.shaldin.eventsourcing.queries.AverageVisitDurationQuery;

import java.util.List;

@RestController
@RequestMapping("/report")
public class ReportService {
    private final ReportProjection reportProjection;

    public ReportService(ReportProjection reportProjection) {
        this.reportProjection = reportProjection;
    }

    @PostMapping("/avgCount")
    public double avgCount(@RequestBody AverageVisitCountQuery query) {
        return reportProjection.handle(query);
    }

    @PostMapping("/avgDuration")
    public double avgDuration(@RequestBody AverageVisitDurationQuery query) {
        return reportProjection.handle(query);
    }
}