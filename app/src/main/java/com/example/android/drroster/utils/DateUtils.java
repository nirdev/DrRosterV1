package com.example.android.drroster.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
}
