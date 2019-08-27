package com.pms.utility;

import java.text.SimpleDateFormat;

public class sampleYearChecker {
    public static void main(String[] args) {
        SimpleDateFormat format = new SimpleDateFormat("YYYY");
        System.out.println(Integer.parseInt(format.format(new java.util.Date())) + 1);
    }
}
