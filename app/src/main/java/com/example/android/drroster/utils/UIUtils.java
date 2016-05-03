package com.example.android.drroster.utils;

import android.content.Context;
import android.widget.Toast;

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
    public static String getDayName(Date date,int length){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        String day = cal.getDisplayName(Calendar.DAY_OF_WEEK, length, Locale.US);
        return day;
    }
    public static String getmonthName(Date date,int length){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        String day = cal.getDisplayName(Calendar.MONTH, length, Locale.US);
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
    public static void toast(String content,Context context){
        Toast.makeText(context,content,Toast.LENGTH_LONG).show();
    }
}
