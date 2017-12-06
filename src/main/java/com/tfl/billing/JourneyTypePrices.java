package com.tfl.billing;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class JourneyTypePrices {

    static final BigDecimal OFF_PEAK_LONG_JOURNEY_PRICE = new BigDecimal(2.70);
    static final BigDecimal OFF_PEAK_SHORT_JOURNEY_PRICE = new BigDecimal(1.60);
    static final BigDecimal PEAK_LONG_JOURNEY_PRICE = new BigDecimal(3.80);
    static final BigDecimal PEAK_SHORT_JOURNEY_PRICE = new BigDecimal(2.90);
    private static JourneyTypePrices instance = new JourneyTypePrices();
    List<Journey> journeys = new ArrayList<Journey>();

    public static JourneyTypePrices getInstance() {
        return instance;
    }

    public boolean isPeakJourney(Journey myJourney){
        return peak(myJourney.startTime()) || peak(myJourney.endTime());
    }

    public boolean peak(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        return (hour >= 6 && hour < 9) || (hour >= 17 && hour < 19);

    }

    public boolean isLongJourney(Journey myJourney) {
        int durationInMinutes = Integer.valueOf(myJourney.durationMinutes());
        if (durationInMinutes >= 25)
            return true;
        else
            return false;
    }

    public BigDecimal calculateJourneyPrice() {
        BigDecimal journeyPrice = new BigDecimal(0);
        for (Journey journey : journeys) {
            if(isPeakJourney(journey)){
                if(isLongJourney(journey))
                    journeyPrice = PEAK_LONG_JOURNEY_PRICE;
                else
                    journeyPrice = PEAK_SHORT_JOURNEY_PRICE;
            }
            else if(!isPeakJourney(journey)){
                if(isLongJourney(journey))
                    journeyPrice = OFF_PEAK_LONG_JOURNEY_PRICE;
                else
                    journeyPrice = OFF_PEAK_SHORT_JOURNEY_PRICE;
            }
        }
        return journeyPrice;
    }
}