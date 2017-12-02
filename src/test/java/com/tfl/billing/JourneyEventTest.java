package com.tfl.billing;

import org.junit.Test;
import java.util.Date;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class JourneyEventTest {
    private JourneyEvent test = new JourneyEvent(UUID.randomUUID(), UUID.randomUUID()) {};

    @Test
    public void longToDateTime() {
        long testTime = test.time();
        Date date = new Date(testTime);
        assertThat(test.time() <= System.currentTimeMillis(), is(true));
    }

    /*
    String testTimeString = String.valueOf(testTime); //from long to string
    String pattern = "yyyy-MM-dd";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern); //from string to date format
    String date = simpleDateFormat.format(new Date(testTime));
    */
}