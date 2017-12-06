package com.tfl.billing;


import java.math.BigDecimal;
import java.util.Date;

public class JourneyTypePrices { //first phase of TDD

    static final BigDecimal OFF_PEAK_LONG_JOURNEY_PRICE = new BigDecimal(2.70);
    static final BigDecimal OFF_PEAK_SHORT_JOURNEY_PRICE = new BigDecimal(1.60);
    static final BigDecimal PEAK_LONG_JOURNEY_PRICE = new BigDecimal(3.80);
    static final BigDecimal PEAK_SHORT_JOURNEY_PRICE = new BigDecimal(2.90);
    private static JourneyTypePrices instance = new JourneyTypePrices();

    public static JourneyTypePrices getInstance() {
        return instance;
    }

    public boolean isPeakJourney(Journey myJourney) {
        return true;
    }

    public boolean peak(Date time) {
        return false;
    }

    public boolean isLongJourney(Journey myJourney) {
        return true;
    }

    public BigDecimal calculateJourneyPrice(Journey myJourney) {
        return BigDecimal.valueOf(3.80);
    }

    public BigDecimal calculateDailyCaps(BigDecimal total, boolean x) {
        return BigDecimal.valueOf(7);
    }

}
