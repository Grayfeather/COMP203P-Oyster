package com.tfl.billing;

import org.junit.Test;

import java.util.Date;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class JourneyTest {


    private UUID testCardIdStart = UUID.randomUUID();
    private UUID testReaderIdStart = UUID.randomUUID();
    private UUID testCardIdEnd = UUID.randomUUID();
    private UUID testReaderIdEnd = UUID.randomUUID();
    private JourneyStart testStart;
    private JourneyEnd testEnd;
    private Journey test;

    private void createJourneyTest(int seconds) throws InterruptedException{
        testStart  = new JourneyStart(testCardIdStart, testReaderIdStart);
        Thread.sleep(seconds*1000);//wait x seconds after testStart ends and before testEnd starts
        testEnd = new JourneyEnd(testCardIdEnd, testReaderIdEnd);
        test = new Journey(testStart, testEnd);
    }

    @Test
    public void returnsOriginId() throws InterruptedException{
        createJourneyTest(1);
        assertThat(test.originId(), is(testReaderIdStart));
    }

    @Test
    public void returnsDestinationId() throws InterruptedException{
        createJourneyTest(1);
        assertThat(test.destinationId(), is(testReaderIdEnd));
    }

    @Test
    public void returnsStringStart() throws InterruptedException{
        createJourneyTest(1);
        assertThat(test.formattedStartTime() != null, is(true));
    }

    @Test
    public void returnStringEnd() throws InterruptedException {
        createJourneyTest(1);
        assertThat(test.formattedEndTime() != null, is(true));
    }

    @Test
    public void returnsDateStart() throws InterruptedException {
        createJourneyTest(1);
        assertThat(test.startTime() instanceof Date, is(true));
    }

    @Test
    public void returnsDateEnd() throws InterruptedException {
        createJourneyTest(1);
        assertThat(test.endTime() instanceof Date, is(true));
    }

    @Test
    public void returnsDurationSeconds () throws InterruptedException {
        createJourneyTest(1);
        int duration = (int) (testEnd.time() - testStart.time()) / 1000;
        assertThat(test.durationSeconds(), is(duration));
    }

    @Test
    public void returnsDurationMinutes () throws InterruptedException {
        createJourneyTest(1);
        String minutes = test.durationMinutes();
        int separator = minutes.indexOf(':');
        int mins = 0;
        for(int i = 0; i < separator; i ++) {
            mins = mins * 10 + (minutes.charAt(i) - '0');
        }
        int secs = 0;
        for(int i = separator + 1; i < test.durationMinutes().length(); i ++) {
            secs = secs * 10 + (minutes.charAt(i) - '0');
        }
        assertThat(mins * 60 + secs, is(test.durationSeconds()));
    }

    @Test
    public void testThatStartTimeIsBeforeEndTime() throws InterruptedException {
        createJourneyTest(1);
        Date startTime = test.startTime();
        Date endTime = test.endTime();
        assertThat(startTime.before(endTime), is(true));
    }
}
