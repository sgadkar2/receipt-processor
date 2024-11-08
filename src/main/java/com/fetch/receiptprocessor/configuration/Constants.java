package com.fetch.receiptprocessor.configuration;

public class Constants {
    
    // Constants for Points Calculation
    public static final int POINTS_TOTAL_IS_WHOLE_DOLLAR = 50;
    public static final int POINTS_TOTAL_IS_MULTIPLE_OF_QUARTERS = 25;
    public static final int POINTS_EVERY_PAIR_OF_ITEMS = 5;
    public static final int POINTS_PURCHASE_DATE_IS_ODD = 6;
    public static final int POINTS_PURCHASE_TIME_IS_WITHIN_INTERVAL = 10;
    public static final double POINTS_MULTIPLIER_ITEM_LENGTH_IS_MULTIPLE_OF_THREE = 0.2;
    
    // Constants for Time Interval
    public static final String PURCHASE_TIME_INTERVAL_START = "14:00";
    public static final String PURCHASE_TIME_INTERVAL_END = "16:00";
}
