package ru.shaldin.eventsourcing.repository;

import org.springframework.stereotype.Repository;
import ru.shaldin.eventsourcing.events.Event;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Repository
public class ReportEventStore {
    private Map<LocalDate, List<Event>> store = new HashMap<>();

    private LocalDate getLocalDateFromDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public void addEvent(Date date, Event event) {
        LocalDate localDate = getLocalDateFromDate(date);
        List<Event> events = store.get(localDate);
        if (events == null) {
            events = new ArrayList<Event>();
            events.add(event);
            store.put(localDate, events);
        } else {
            events.add(event);
        }
    }

    public List<Event> getEvents(Date date) {
        return store.get(getLocalDateFromDate(date));
    }
}