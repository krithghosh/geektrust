package com.example.geektrust.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {
    private static final List<String> monthList = new ArrayList<>(Arrays.asList("JANUARY", "FEBRUARY", "MARCH", "APRIL",
            "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"));

    private static final List<String> fundType = new ArrayList<>(Arrays.asList("EQUITY", "DEBT", "GOLD"));

    public static int getMonth(String month) {
        return monthList.indexOf(month);
    }

    public static List<String> getFundType() {
        return fundType;
    }

    public static int getPreviousMonth(String month) {
        return monthList.indexOf(month) - 1;
    }
}
