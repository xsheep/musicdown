package com.xiaor.archetype;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Test {

    public static void main(String[] args) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startDate = sdf.parse("2020-02-01 00:00:00");
            String[] dates = new String[]{"2020-02-20 16:31:34", "2020-02-20 16:31:33", "2020-02-20 16:31:32"};
            for (String date : dates) {
                Date curDate = sdf.parse(date);
                double diffValue = curDate.getTime() - startDate.getTime();
                double days = diffValue / 1000 / 60 / 60 / 24;
                System.out.println(String.format("date:%s, diffValue:%s", date, days));
            }
            Set<String> set = new HashSet<>();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
