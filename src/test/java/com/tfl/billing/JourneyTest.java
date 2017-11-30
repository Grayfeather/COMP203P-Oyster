package com.tfl.billing;

import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import sun.rmi.runtime.Log;

import java.util.Date;
import java.util.UUID;
import java.util.logging.Logger;

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
    //public Date startTime() { return new Date(start.time()); }
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
    public void returnsDurationMinutes () { // needs improvement
        String minutes = test.durationMinutes();
        int sep = minutes.indexOf(':');
        int mins = 0;
        if(sep == 1) mins = minutes.charAt(0) - '0';
        int secs = 0;
        if(sep == 1) secs = minutes.charAt(2) - '0';
        assertThat(mins * 60 + secs, is(test.durationSeconds()));
    }

}
