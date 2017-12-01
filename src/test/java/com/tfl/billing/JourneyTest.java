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
        assertThat(test.originId(), is(testReaderIdStart));
    }

    @Test
    public void returnsDestinationId() {
        assertThat(test.destinationId(), is(testReaderIdEnd));
    }

    @Test
    public void returnsStringStart() {
        assertThat(test.formattedStartTime() != null, is(true));
    }

    @Test
    public void returnStringEnd() {
        assertThat(test.formattedEndTime() != null, is(true));
    }

    @Test
    public void returnsDateStart() {
        assertThat(test.startTime() instanceof Date, is(true));
    }

    @Test
    public void returnsDateEnd() {
        assertThat(test.endTime() instanceof Date, is(true));
    }

    @Test
    public void returnsDurationSeconds () {
        int duration = (int) (testEnd.time() - testStart.time()) / 1000;
        assertThat(test.durationSeconds(), is(duration));
    }

    @Test
    public void returnsDurationMinutes () {
        String minutes = test.durationMinutes();
        int sep = minutes.indexOf(':');
        int mins = 0;
        for(int i = 0; i < sep; i ++) {
            mins = mins * 10 + (minutes.charAt(i) - '0');
        }
        int secs = 0;
        for(int i = sep + 1; i < test.durationMinutes().length(); i ++) {
            secs = secs * 10 + minutes.charAt(i) - '0';
        }
        assertThat(mins * 60 + secs, is(test.durationSeconds()));
    }

}
