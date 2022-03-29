package statistics;

import clock.Clock;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class EventsStatisticImpl implements EventsStatistic {
    private final Map<String, List<Instant>> statistic;
    private final Map<String, Integer> pointers;
    private final Clock clock;

    public EventsStatisticImpl(Clock clock) {
        statistic = new HashMap<>();
        pointers = new HashMap<>();
        this.clock = clock;
    }


    @Override
    public void incEvent(String name) {
        List<Instant> events = statistic.getOrDefault(name, new ArrayList<>());
        events.add(clock.now());
        statistic.put(name, events);
    }

    @Override
    public double getEventStatisticByName(String name) {
        Instant now = clock.now();
        List<Instant> events = statistic.getOrDefault(name, new ArrayList<>());
        int pointer = pointers.getOrDefault(name, 0);
        for (int i = pointer; i < events.size(); i++, pointer++) {
            Instant event = events.get(pointer);
            if (event.plus(1, ChronoUnit.HOURS).isAfter(now)) {
                break;
            }
        }
        pointers.put(name, pointer);
        return (events.size() - pointer) / 60.0;
    }

    @Override
    public Map<String, Double> getAllEventStatistic() {
        Map<String, Double> result = new HashMap<>();
        for (String name : statistic.keySet()) {
            result.put(name, getEventStatisticByName(name));
        }
        return result;
    }

    @Override
    public void printStatistic() {
        Instant begin = clock.now(), end = clock.now();
        for (String name : statistic.keySet()) {
            List<Instant> events = statistic.get(name);
            if (events.get(0).isBefore(begin)) {
                begin = events.get(0);
            }
            if (events.get(events.size() - 1).isAfter(end)) {
                end = events.get(events.size() - 1);
            }
        }
        long minutes = Math.max(1, ChronoUnit.MINUTES.between(begin, end));
        for (String name : statistic.keySet()) {
            List<Instant> events = statistic.get(name);
            System.out.println("Statistic for '" + name + "': " + (double) events.size() / minutes);
        }
    }
}
