package com.pms.utility;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class YearChecker {

    private static final List<Integer> preMonths = Arrays.asList(12, 11, 10, 9, 8);
    private static final List<Integer> postMonths = Arrays.asList(1, 2, 3, 4);
    private static final SimpleDateFormat yearFormat = new SimpleDateFormat("YYYY");
    private static final SimpleDateFormat monthFormat = new SimpleDateFormat("MM");

    public static Integer getYear(Date date) {

        Integer curMonth = Integer.valueOf(monthFormat.format(date));
        Integer curYear = Integer.valueOf(yearFormat.format(date));

        if (preMonths.contains(curMonth)) {
            return curYear + 1;
        } else if (postMonths.contains(curMonth)) {
            return curYear;
        }

        return curYear;
    }

    public static String getAyProject(Date date) {
        Integer curMonth = Integer.valueOf(monthFormat.format(date));
        Integer curYear = Integer.valueOf(yearFormat.format(date));

        if (preMonths.contains(curMonth)) {
            curYear = curYear + 1;
        }

        String old = String.valueOf(curYear - 1);
        String cur = String.valueOf(curYear);
        return old + "-" + cur.substring(cur.length() - 2);

    }


    public static String generateProjectId(List<String> projectIds) {
        String prefix = "PRJ" + getYear(new java.util.Date()) + "CE";
        if (projectIds == null || projectIds.size() == 0) {
            return prefix + "001";
        }

        int i = 1;
        for (String projectId : projectIds) {
            if (i != getSuffixNum(projectId)) {
                return prefix + String.format("%03d", i);
            }
            i++;
        }

        return prefix + String.format("%03d", i);
    }

    private static Integer getSuffixNum(String projectId) {
        return Integer.valueOf(projectId.substring(projectId.length() - 3));
    }
}