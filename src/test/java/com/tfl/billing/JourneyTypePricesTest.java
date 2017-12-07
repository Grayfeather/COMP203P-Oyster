package com.tfl.billing;

import org.junit.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JourneyTypePricesTest {

    private Instant startTime = Instant.parse("2017-12-03T08:30:00.00Z");
    private Instant endTime = Instant.parse("2017-12-03T10:45:00.00Z");

    private Journey myJourney = mock(Journey.class);

    @Test
    public void testIfJourneyIsPeak() {
        when(myJourney.startTime()).thenReturn(new Date(startTime.toEpochMilli()));
        when(myJourney.endTime()).thenReturn(new Date(endTime.toEpochMilli()));
        assertThat(JourneyTypePrices.getInstance().isPeakJourney(myJourney), is(true));
    }

    @Test
    public void testIfJourneyIsLong() {
        when(myJourney.startTime()).thenReturn(new Date(startTime.toEpochMilli()));
        when(myJourney.endTime()).thenReturn(new Date(endTime.toEpochMilli()));
        int testDuration = (int) ((endTime.toEpochMilli() - startTime.toEpochMilli()) / 1000);
        when(myJourney.durationSeconds()).thenReturn(testDuration);
        assertThat(JourneyTypePrices.getInstance().isLongJourney(myJourney), is(true));
    }

    @Test
    public void testJourneyCalculator() {
        when(myJourney.startTime()).thenReturn(new Date(startTime.toEpochMilli()));
        when(myJourney.endTime()).thenReturn(new Date(endTime.toEpochMilli()));
        int testDuration = (int) ((endTime.toEpochMilli() - startTime.toEpochMilli()) / 1000);
        when(myJourney.durationSeconds()).thenReturn(testDuration);
        List<Journey> myJourneys = new ArrayList<>();
        myJourneys.add(myJourney);
        assertThat(JourneyTypePrices.getInstance().calculateJourneyPrice(myJourneys), is(TravelTracker.getInstance().roundToNearestPenny(BigDecimal.valueOf(3.80))));
    }

    @Test
    public void testDailyCaps () {
        assertThat(JourneyTypePrices.getInstance().calculateDailyCaps(true), is(BigDecimal.valueOf(9)));
    }
}
