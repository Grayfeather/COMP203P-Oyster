package com.tfl.billing;

import org.junit.Test;

import java.util.Date;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class JourneyTest {


    private final UUID testCardIdStart = UUID.randomUUID();
    private final UUID testReaderIdStart = UUID.randomUUID();
    private final UUID testCardIdEnd = UUID.randomUUID();
    private final UUID testReaderIdEnd = UUID.randomUUID();
    private final JourneyEvent testStart = new JourneyStart(testCardIdStart, testReaderIdStart);
    private final JourneyEvent testEnd = new JourneyEnd(testCardIdEnd, testReaderIdEnd);
    private Journey test = new Journey(testStart, testEnd);

    @Test
    public void returnsOriginId() {
        assertThat(test.originId(), is(testStart.readerId()));
    }

    @Test
    public void returnsDestinationId() {
        assertThat(test.destinationId(), is(testEnd.readerId()));
    }

    @Test
    //public Date startTime() { return new Date(start.time()); }
    public void returnsStartTime() { assertThat(test.startTime(), is(new Date(testStart.time())));}

    @Test
    //public Date endTime() { return new Date(end.time()); }
    public void returnsEndTime() { assertThat(test.endTime(), is(new Date(testEnd.time())));}

}
