package com.tfl.billing;


import java.math.BigDecimal;
import java.util.Date;

public class JourneyTypePrices {

    static final BigDecimal OFF_PEAK_LONG_JOURNEY_PRICE = new BigDecimal(2.70);
    static final BigDecimal OFF_PEAK_SHORT_JOURNEY_PRICE = new BigDecimal(1.60);
    static final BigDecimal PEAK_LONG_JOURNEY_PRICE = new BigDecimal(3.80);
    static final BigDecimal PEAK_SHORT_JOURNEY_PRICE = new BigDecimal(2.90);
    private static JourneyTypePrices instance = new JourneyTypePrices();

    public static JourneyTypePrices getInstance() {
        return instance;
    }

    public boolean isPeakJourney(Journey myJourney) {
        return false;
    }

    private boolean peak(Date time) {
        return false;
    }

    public boolean isLongJourney(Journey myJourney) {
        return false;
    }

    public BigDecimal calculateJourneyPrice(Journey myJourney) {
        return BigDecimal.valueOf(1);
    }

   /* public BigDecimal calculateDailyCaps(Journey myJourney){

    }*/

}
