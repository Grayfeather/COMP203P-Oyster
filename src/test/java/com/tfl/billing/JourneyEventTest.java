package com.tfl.billing;

import org.junit.Test;
import java.util.Date;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class JourneyEventTest {

    private JourneyEvent test;

    @Test
    public void longToDateTime() {
        long testTime = test.time();
        Date date = new Date(testTime); //from long to date
        assertThat(test.time(), is(date.getTime()));
    }

    /*
    String testTimeString = String.valueOf(testTime); //from long to string
    String pattern = "yyyy-MM-dd";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern); //from string to date format
    String date = simpleDateFormat.format(new Date(testTime));
    */
}