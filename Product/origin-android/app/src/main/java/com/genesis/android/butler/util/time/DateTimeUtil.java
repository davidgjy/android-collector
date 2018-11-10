package com.genesis.android.butler.util.time;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by KG on 16/11/4.
 */
public class DateTimeUtil {
    public static String getDateTimeByFormat(Date date, String format) {
        if (date == null) {
            return "";
        }

        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String dateString = formatter.format(date);

        return dateString;
    }

    public static Date convertDateFromStringFormat(String dateString, String format) {
        if (dateString == null || dateString == "") {
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            Date date = sdf.parse(dateString);
            return date;
        } catch (Exception e) {
            return null;
        }
    }

    public static String convertDateStringFromStringFormat(String dateString, String format) {
        Date dt = convertDateFromStringFormat(dateString, format);

        return getDateTimeByFormat(dt, format);
    }
}

