package com.tfl.billing;

import com.oyster.OysterCard;
import com.oyster.OysterCardReader;
import com.tfl.external.Customer;
import com.tfl.external.CustomerDatabase;
import com.tfl.underground.OysterReaderLocator;
import com.tfl.underground.Station;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TravelTrackerTest {

    private void generateJourneyTime(int seconds) throws InterruptedException {
        Thread.sleep(seconds * 1000);
    }

    private CustomerDatabase testCustomerDatabase = mock(CustomerDatabase.class);
    private List<Customer> testCustomers = new ArrayList<>();

    @Test
    public void chargesAccounts() throws InterruptedException {
        OysterCard mockNewCard = new OysterCard("42ad6e1a-1908-4ff5-a689-3388df83802b");
        Customer newKid = new Customer("Noah Fence", mockNewCard);
        testCustomers.add(newKid);
        when(testCustomerDatabase.getCustomers()).thenReturn(testCustomers);
        when(testCustomerDatabase.isRegisteredId(mockNewCard.id())).thenReturn(true);
        /*OysterCard testCard = new OysterCard("38400000-8cf0-11bd-b23e-10b96e4ef00d");*/
        OysterCardReader testPaddingtonReader = OysterReaderLocator.atStation(Station.PADDINGTON);
        OysterCardReader testBakerStreetReader = OysterReaderLocator.atStation(Station.BAKER_STREET);
        OysterCardReader testEustonSquareReader = OysterReaderLocator.atStation(Station.EUSTON_SQUARE);
        TravelTracker testTravelTracker = new TravelTracker();
        testTravelTracker.importMockDatabase(testCustomerDatabase);
        testTravelTracker.connect(testPaddingtonReader, testBakerStreetReader, testEustonSquareReader);
        testPaddingtonReader.touch(mockNewCard);
        generateJourneyTime(5);
        testBakerStreetReader.touch(mockNewCard);
        generateJourneyTime(5);
        testBakerStreetReader.touch(mockNewCard);
        generateJourneyTime(5);
        testEustonSquareReader.touch(mockNewCard);
        testTravelTracker.chargeAccounts(false);
    }
}
