package com.home.zubrin.zbodytemp.Utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by SlavaZu on 5/11/15.
 */
public class DateUtils {

    private static Calendar cal = Calendar.getInstance();

    public static Date startOfDay(Date date) {
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date endOfDay(Date date) {
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 24);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}
