package com.tfl.billing;


import com.tfl.external.CustomerDatabase;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class JourneyTypePrices {

    private static final BigDecimal OFF_PEAK_LONG_JOURNEY_PRICE = new BigDecimal(2.70);
    private static final BigDecimal OFF_PEAK_SHORT_JOURNEY_PRICE = new BigDecimal(1.60);
    private static final BigDecimal PEAK_LONG_JOURNEY_PRICE = new BigDecimal(3.80);
    private static final BigDecimal PEAK_SHORT_JOURNEY_PRICE = new BigDecimal(2.90);
    private static JourneyTypePrices instance = new JourneyTypePrices();
    //List<Journey> journeys = new ArrayList<Journey>();

    public static JourneyTypePrices getInstance() {
        return instance;
    }

    public boolean isPeakJourney(Journey myJourney){
        return peak(myJourney.startTime()) || peak(myJourney.endTime());
    }

    private boolean peak(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.setTimeZone(TimeZone.getTimeZone("Europe/London"));
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        return (hour >= 6 && hour < 10) || (hour >= 17 && hour < 20);

    }

    public boolean isLongJourney(Journey myJourney) {
        int durationInMinutes = myJourney.durationSeconds()/60;
        return durationInMinutes >= 25;
    }

    public BigDecimal calculateJourneyPrice(List<Journey> journeys) {
        TravelTracker travelTracker = new TravelTracker(CustomerDatabase.getInstance());
        BigDecimal journeyPrice = new BigDecimal(0);
        for (Journey journey : journeys) {
            if(isPeakJourney(journey) && isLongJourney(journey)) {
                journeyPrice = PEAK_LONG_JOURNEY_PRICE;
            } else if(isPeakJourney(journey)) {
                journeyPrice = PEAK_SHORT_JOURNEY_PRICE;
            } else if(isLongJourney(journey)) {
                journeyPrice = OFF_PEAK_LONG_JOURNEY_PRICE;
            } else {
                journeyPrice = OFF_PEAK_SHORT_JOURNEY_PRICE;
            }
        }
        return travelTracker.roundToNearestPenny(journeyPrice);
    }

    public BigDecimal calculateDailyCaps(boolean peak) {
        if (peak)
            return BigDecimal.valueOf(9);

        return BigDecimal.valueOf(7);
    }

}