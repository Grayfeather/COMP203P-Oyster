package com.tfl.billing;

import com.oyster.OysterCardReader;
import com.oyster.ScanListener;
import com.tfl.external.Customer;
import com.tfl.external.CustomerDatabase;
import com.tfl.external.PaymentsSystem;

import java.math.BigDecimal;
import java.util.*;

import static com.tfl.billing.JourneyTypePrices.*;


public class TravelTracker implements ScanListener {

    private final List<JourneyEvent> eventLog = new ArrayList<JourneyEvent>();
    private final Set<UUID> currentlyTravelling = new HashSet<UUID>(); //data structure much like RecentlyUsedList

    private CustomerDatabase customerDatabase;
    private List<Customer> customers;


    private void importDatabase() {
        customerDatabase = CustomerDatabase.getInstance();
        customers = customerDatabase.getCustomers();
    }

    public void chargeAccounts() {
        importDatabase();
        for (Customer customer : customers) {
            totalJourneysFor(customer);
        }
    }

    private void totalJourneysFor(Customer customer) {
        List<JourneyEvent> customerJourneyEvents = new ArrayList<JourneyEvent>();
        for (JourneyEvent journeyEvent : eventLog) {
            if (journeyEvent.cardId().equals(customer.cardId())) {
                customerJourneyEvents.add(journeyEvent);
            }
        }

        List<Journey> journeys = new ArrayList<Journey>();

        JourneyEvent start = null;
        for (JourneyEvent event : customerJourneyEvents) {
            if (event instanceof JourneyStart) {
                start = event;
            }
            if (event instanceof JourneyEnd && start != null) {
                journeys.add(new Journey(start, event));
                start = null;
            }
        }

        BigDecimal customerTotal = new BigDecimal(0);
        BigDecimal journeyPrice = new BigDecimal(0);
        JourneyTypePrices myJourney = new JourneyTypePrices();
        for (Journey journey : journeys) {
            if(myJourney.isPeakJourney(journey)){
                if(myJourney.isLongJourney(journey))
                    journeyPrice = PEAK_LONG_JOURNEY_PRICE;
                    journeyPrice = PEAK_SHORT_JOURNEY_PRICE;
            }
            else if(!myJourney.isPeakJourney(journey)){
                if(myJourney.isLongJourney(journey))
                    journeyPrice = OFF_PEAK_LONG_JOURNEY_PRICE;
                    journeyPrice = OFF_PEAK_SHORT_JOURNEY_PRICE;
            }
            customerTotal = customerTotal.add(journeyPrice);
        }

        if(customerTotal.compareTo(BigDecimal.valueOf(0)) == 1) {
            PaymentsSystem.getInstance().charge(customer, journeys, roundToNearestPenny(customerTotal));
        }
    }

    private BigDecimal roundToNearestPenny(BigDecimal poundsAndPence) {
        return poundsAndPence.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public void connect(OysterCardReader... cardReaders) {
        for (OysterCardReader cardReader : cardReaders) {
            cardReader.register(this);
        }
    }

    @Override
    public void cardScanned(UUID cardId, UUID readerId) {
        if (currentlyTravelling.contains(cardId)) {
            eventLog.add(new JourneyEnd(cardId, readerId));
            currentlyTravelling.remove(cardId);
        } else {
            if (CustomerDatabase.getInstance().isRegisteredId(cardId)) {
                currentlyTravelling.add(cardId);
                eventLog.add(new JourneyStart(cardId, readerId));
            } else {
                throw new UnknownOysterCardException(cardId);
            }
        }
    }

}
