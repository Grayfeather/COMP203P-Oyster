package com.tfl.billing;

import com.oyster.OysterCardReader;
import com.oyster.ScanListener;
import com.tfl.external.Customer;
import com.tfl.external.CustomerDatabase;
import com.tfl.external.PaymentsSystem;

import java.math.BigDecimal;
import java.util.*;


public class TravelTracker implements ScanListener {

    private final List<JourneyEvent> eventLog = new ArrayList<JourneyEvent>();
    private final Set<UUID> currentlyTravelling = new HashSet<UUID>(); //data structure much like RecentlyUsedList

    private CustomerDatabase customerDatabase;
    private List<Customer> customers;
    private static TravelTracker instance = new TravelTracker();

    public static TravelTracker getInstance() {
        return instance;
    }


    private void importDatabase() {
        customerDatabase = CustomerDatabase.getInstance();
        customers = customerDatabase.getCustomers();
    }

    /*add journeyEvent in list customerJourneyEvents for each customer and calculate the total price (for each customer)
    of the journey in customerTotal*/
    public void chargeAccounts() {
        importDatabase();
        for (Customer customer : customers) {
            totalJourneysFor(customer);
        }
    }


    private void totalJourneysFor(Customer customer) {
        //add journeyEvent in list customerJourneyEvents for a customer
        List<JourneyEvent> customerJourneyEvents = new ArrayList<JourneyEvent>();
        for (JourneyEvent journeyEvent : eventLog) {
            if (journeyEvent.cardId().equals(customer.cardId())) {
                customerJourneyEvents.add(journeyEvent);
            }
        }

        //creates a journey for each successful pair of events
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

        boolean peak = false;
        BigDecimal customerTotal = new BigDecimal(0);
        customerTotal = customerTotal.add(JourneyTypePrices.getInstance().calculateJourneyPrice(journeys));

        //if one of the journeys is true, peak becomes true.
        for (Journey i : journeys) {
            if(JourneyTypePrices.getInstance().isPeakJourney(i)){
                peak = true;
            }
        }
        if((customerTotal.compareTo(BigDecimal.valueOf(7)) > 0 && !peak)
                ||
                (customerTotal.compareTo(BigDecimal.valueOf(9)) > 0 && peak)
                ) {
            PaymentsSystem.getInstance().charge(customer, journeys, roundToNearestPenny(JourneyTypePrices.getInstance().calculateDailyCaps(peak)));
        } else {
            PaymentsSystem.getInstance().charge(customer, journeys, roundToNearestPenny(customerTotal));
        }

}

    public BigDecimal roundToNearestPenny(BigDecimal poundsAndPence) {
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