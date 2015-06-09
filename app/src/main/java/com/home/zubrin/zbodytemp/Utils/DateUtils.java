package com.home.zubrin.zbodytemp.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by SlavaZu on 5/11/15.
 */
public class DateUtils {

    private static Calendar cal = Calendar.getInstance();
    private static SimpleDateFormat xmlDateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    public static
    Date startOfDay(Date date) {
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static
    Date endOfDay(Date date) {
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 24);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    // De/serialization operations

    static public
    String date2xml(Date date) {
        return xmlDateFormatter.format(date);
    }

    static public
    Date xml2date(String xml) {
        try {
            return xmlDateFormatter.parse(xml);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

}
