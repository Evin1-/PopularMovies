package com.loopcupcakes.udacity.popularmovies.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by evin on 10/15/16.
 */

public class DatesMagic {
    public static String generateDate(int number) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, number * -30);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        return simpleDateFormat.format(calendar.getTime());
    }
}
