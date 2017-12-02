package com.tfl.billing;

import com.oyster.OysterCard;
import com.oyster.OysterCardReader;
import com.tfl.external.Customer;
import com.tfl.underground.OysterReaderLocator;
import com.tfl.underground.Station;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TravelTrackerTest {

    public void generateJourneyTime(int seconds) throws InterruptedException {
        Thread.sleep(seconds * 1000);
    }

    @Test
    public void chargesAccounts() throws InterruptedException {
        OysterCard testCard = new OysterCard("38400000-8cf0-11bd-b23e-10b96e4ef00d");
        OysterCardReader testPaddingtonReader = OysterReaderLocator.atStation(Station.PADDINGTON);
        OysterCardReader testBakerStreetReader = OysterReaderLocator.atStation(Station.BAKER_STREET);
        OysterCardReader testEustonSquareReader = OysterReaderLocator.atStation(Station.EUSTON_SQUARE);
        TravelTracker testTravelTracker = new TravelTracker();
        testTravelTracker.connect(testPaddingtonReader, testBakerStreetReader, testEustonSquareReader);
        testPaddingtonReader.touch(testCard);
        generateJourneyTime(5);
        testBakerStreetReader.touch(testCard);
        generateJourneyTime(5);
        testBakerStreetReader.touch(testCard);
        generateJourneyTime(5);
        testEustonSquareReader.touch(testCard);
        testTravelTracker.chargeAccounts();
    }
}
