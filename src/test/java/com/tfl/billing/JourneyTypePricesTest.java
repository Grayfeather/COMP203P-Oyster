package com.tfl.billing;

import org.junit.Test;

import java.util.UUID;


public class JourneyTypePricesTest {

    private UUID testCardIdStart = UUID.randomUUID();
    private UUID testReaderIdStart = UUID.randomUUID();
    private UUID testCardIdEnd = UUID.randomUUID();
    private UUID testReaderIdEnd = UUID.randomUUID();

    private JourneyStart myJourneyStart = new JourneyStart(testCardIdStart, testReaderIdStart);
    private JourneyEnd myJourneyEnd = new JourneyEnd(testCardIdEnd, testReaderIdEnd);
    private Journey myJourney = new Journey(myJourneyStart, myJourneyEnd);

    @Test
    public void testIfJourneyIsPeak() {

    }
}
