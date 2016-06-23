package com.taller2.matchapp.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by federicofarina on 6/23/16.
 */
public class DateUtils {

    public static String convertDate(String dateString) {
        String formattedDate = null;
        DateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy", Locale.getDefault());
        try {
            Date date = formatter.parse(dateString);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            final int hour = cal.get(Calendar.HOUR);
            String hourString = getFieldString(hour);

            final int minute = cal.get(Calendar.MINUTE);
            String minuteString = getFieldString(minute);

            formattedDate = hourString + ":" + minuteString;

        } catch (ParseException e) {
            //Nothing to do if server do not send a valid date
        }
        return formattedDate;
    }

    private static String getFieldString(int value) {
        String formattedValue = "";
        if (value == 0) {
            formattedValue = "00";
        } else if (value < 10) {
            formattedValue += "0" + value;
        } else {
            formattedValue += value;
        }
        return formattedValue;
    }
}
