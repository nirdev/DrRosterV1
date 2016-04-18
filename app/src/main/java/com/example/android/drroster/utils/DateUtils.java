package com.example.android.drroster.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Nir on 4/10/2016.
 */
public class DateUtils {

    public static String[] getDatesUI (List<Date> rawDateList){

        //First - day String, Second - month + year String
        String[] mDateUIList = new String[2];

        //Sets formatting types for raw dates
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
        SimpleDateFormat mothFormat = new SimpleDateFormat("MMM yyyy");
        mDateUIList[0] = dayFormat.format(rawDateList.get(0));

        //Check for more then one day
        if (rawDateList.size() > 1) {
            for (int i3 = 1; i3 < rawDateList.size(); i3++) {
                mDateUIList[0] =  mDateUIList[0] + " ," + dayFormat.format(rawDateList.get(i3));
            }
        }

        //Set month and year - check date are same month or not
        if (isSameMonth(rawDateList.get(0),rawDateList.get(rawDateList.size()-1))) {
            mDateUIList[1] = "   " + mothFormat.format(rawDateList.get(rawDateList.size() - 1));
        }

        else{
            mDateUIList[1] = "   " + mothFormat.format(rawDateList.get(0))  +
                    " - " + mothFormat.format(rawDateList.get(rawDateList.size() - 1)) ;
        }
        return mDateUIList;
    }

    //check if dates are at the same month
    public static boolean isSameMonth(Date startDate, Date endDate){
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(startDate);
        cal2.setTime(endDate);

        return  cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
    }

    public static Date getDateFromInt(int month, int year){

        String input =  year + "-" + month; //build string format - "yyyy-MM"
        Date myDate = null;
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM");
        try {

            myDate = inputFormat.parse(input);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return myDate;
    }

    public static int getNumberOfDayInMonth(int month,int year){
        int iYear = year;
        int iMonth = month; // 1 (months begin with 0)
        int iDay = 1; //always form first day

        // Create a calendar object and set year and month
        Calendar mycal = new GregorianCalendar(iYear, iMonth, iDay);

        // Get the number of days in that month
        int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH); // 28

        return daysInMonth;
    }
    public static ArrayList<Date> buildMonthOfDatesArray(int month,int year){
        int numberOfDays = getNumberOfDayInMonth(month,year);
        Date firstDayDate = getDateFromInt(month,year);

        ArrayList<Date> monthOfDatesArray = new ArrayList<>();

        return monthOfDatesArray;
    }
}
