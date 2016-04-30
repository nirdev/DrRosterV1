package com.example.android.drroster.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Nir on 4/28/2016.
 */
public class UIUtils {

    public static String getDayNumber(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        String day = cal.get(Calendar.DAY_OF_MONTH) + "";
        return day;
    }
    public static String getDayName(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        String day = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.US);
        return day;
    }
    public static String getStringFromArray(ArrayList<String> names){
        String namesUI = "";
        Boolean first = true;

        for (String name : names){
            if (!first) {
                namesUI = namesUI + ", " + name;
            }
            else
            {
                namesUI = name;
                first = false;
            }
        }
        if (namesUI.equals("")){
            return "-";
        }
        return  namesUI;
    }
}
