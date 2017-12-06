package com.tfl.billing;

import org.junit.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class JourneyTypePricesTest {

    private UUID testCardIdStart = UUID.randomUUID();
    private UUID testReaderIdStart = UUID.randomUUID();
    private UUID testCardIdEnd = UUID.randomUUID();
    private UUID testReaderIdEnd = UUID.randomUUID();

    Instant startTime = Instant.parse("2017-12-03T08:30:00.00Z");
    Instant endTime = Instant.parse("2017-12-03T10:45:00.00Z");

    private JourneyStart myJourneyStart = new JourneyStart(testCardIdStart, testReaderIdStart);
    private JourneyEnd myJourneyEnd = new JourneyEnd(testCardIdEnd, testReaderIdEnd);
    private Journey myJourney = new Journey(myJourneyStart, myJourneyEnd);

    @Test
    public void testIfJourneyIsPeak() {
        myJourneyStart.time = startTime.toEpochMilli();
        myJourneyEnd.time = endTime.toEpochMilli();
        myJourney = new Journey(myJourneyStart, myJourneyEnd);
        assertThat(JourneyTypePrices.getInstance().isPeakJourney(myJourney), is(true));
    }

    @Test
    public void testIfJourneyIsLong() {
        myJourneyStart.time = startTime.toEpochMilli();
        myJourneyEnd.time = endTime.toEpochMilli();
        myJourney = new Journey(myJourneyStart, myJourneyEnd);
        assertThat(JourneyTypePrices.getInstance().isLongJourney(myJourney), is(true));
    }

    @Test
    public void testJourneyCalculator() {
        myJourneyStart.time = startTime.toEpochMilli();
        myJourneyEnd.time = endTime.toEpochMilli();
        myJourney = new Journey(myJourneyStart, myJourneyEnd);
        List<Journey> myJourneys = new ArrayList<>();
        myJourneys.add(myJourney);
        assertThat(JourneyTypePrices.getInstance().calculateJourneyPrice(myJourneys), is(TravelTracker.getInstance().roundToNearestPenny(BigDecimal.valueOf(3.80))));
    }

    @Test
    public void testDailyCaps () {
        BigDecimal totalPrice = BigDecimal.valueOf(12);
        assertThat(JourneyTypePrices.getInstance().calculateDailyCaps(false), is(BigDecimal.valueOf(7)));
    }
}
