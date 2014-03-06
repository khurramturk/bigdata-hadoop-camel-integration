package com.ews.enterprise.datacollection.mongo;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;

public class MongoDateFormatter {

    private static final ThreadLocal<SimpleDateFormat> dateFormatHolder = new ThreadLocal<SimpleDateFormat>() {

        @Override
        protected SimpleDateFormat initialValue() {
            SimpleDateFormat sdf = new SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            sdf.setCalendar(new GregorianCalendar(new SimpleTimeZone(0, "GMT")));
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        }
    };

    /*
     * Every time there is a call for DateFormat, ThreadLocal will return
     * calling Thread's copy of SimpleDateFormat
     */
    public static SimpleDateFormat getDateFormatter() {
        return dateFormatHolder.get();
    }
}
