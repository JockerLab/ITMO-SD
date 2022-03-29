package statistics;

import clock.SetableClock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class EventsStatisticTest {
    private Instant startTime;
    private SetableClock clock;
    private EventsStatistic statistic;

    @BeforeEach
    private void init() {
        startTime = Instant.now();
        clock = new SetableClock(startTime);
        statistic = new EventsStatisticImpl(clock);
    }

    @Test
    public void test_01_getEventStatisticByName() {
        statistic.incEvent("event1");
        clock.setNow(startTime.plus(1, ChronoUnit.MINUTES));
        assertEquals(1.0 / 60.0, statistic.getEventStatisticByName("event1"));
    }

    @Test
    public void test_02_getAllEventStatistic() {
        Map<String, Double> expected = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            statistic.incEvent("event" + i);
            clock.setNow(startTime.plus(i + 1, ChronoUnit.MINUTES));
            expected.put("event" + i, 1.0 / 60.0);
        }
        assertEquals(expected, statistic.getAllEventStatistic());
    }

    @Test
    public void test_03_two_hours_getAllEventStatistic() {
        Map<String, Double> expected = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            clock.setNow(startTime.plus(i + 1, ChronoUnit.MINUTES));
            statistic.incEvent("event" + i);
            clock.setNow(startTime.plus(i + 1, ChronoUnit.MINUTES).plus(1, ChronoUnit.HOURS));
            statistic.incEvent("event" + i);
            expected.put("event" + i, 1.0 / 60.0);
        }
        assertEquals(expected, statistic.getAllEventStatistic());
    }

    @Test
    public void test_04_random_getEventStatisticByName() {
        Random r = new Random();
        int val = (Math.abs(r.nextInt()) % 10) + 1;
        for (int i = 0; i < val; i++) {
            statistic.incEvent("event1");
            clock.setNow(startTime.plus(1, ChronoUnit.MINUTES));
        }
        assertEquals(val / 60.0, statistic.getEventStatisticByName("event1"));
    }

    @Test
    public void test_05_random_printStatistic() {
        Random r = new Random();
        int val = (Math.abs(r.nextInt()) % 10) + 1;
        for (int i = 0; i < val; i++) {
            statistic.incEvent("event" + i);
            clock.setNow(startTime.plus(i + 1, ChronoUnit.MINUTES));
        }
        statistic.printStatistic();
    }
}