package ru.shaldin.eventsourcing.repository;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Repository
public class ReportReadRepository {
    private Map<LocalDate, List<Long>> visitStore = new HashMap<>(); // Visit in minutes

    private LocalDate getLocalDateFromDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public void saveVisit(Date entry, Date leave) {
        LocalDate date = getLocalDateFromDate(entry);
        if (!visitStore.containsKey(date)) {
            visitStore.put(date, new ArrayList<>());
        }
        visitStore.get(date).add((entry.getTime() - leave.getTime()) / (1000L * 60L));
    }

    public List<Long> getVisits(Date date) {
        LocalDate localDate = getLocalDateFromDate(date);
        return visitStore.get(localDate);
    }

    public Map<LocalDate, List<Long>> getAllVisits() {
        return visitStore;
    }
}
