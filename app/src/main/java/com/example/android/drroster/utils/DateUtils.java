package com.example.android.drroster.utils;

import java.text.DateFormat;
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


    public static String[] getDatesUI(List<Date> rawDateList) {

        //First - day String, Second - month + year String
        String[] mDateUIList = new String[2];

        //Sets formatting types for raw dates
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
        SimpleDateFormat mothFormat = new SimpleDateFormat("MMM yyyy");
        mDateUIList[0] = dayFormat.format(rawDateList.get(0));

        //Check for more then one day
        if (rawDateList.size() > 1) {
            for (int i3 = 1; i3 < rawDateList.size(); i3++) {
                mDateUIList[0] = mDateUIList[0] + " ," + dayFormat.format(rawDateList.get(i3));
            }
        }

        //Set month and year - check date are same month or not
        if (isSameMonth(rawDateList.get(0), rawDateList.get(rawDateList.size() - 1))) {
            mDateUIList[1] = "   " + mothFormat.format(rawDateList.get(rawDateList.size() - 1));
        } else {
            mDateUIList[1] = "   " + mothFormat.format(rawDateList.get(0)) +
                    " - " + mothFormat.format(rawDateList.get(rawDateList.size() - 1));
        }
        return mDateUIList;
    }
    public static String[] getDateUIMonthYear(Date date){
        //@Params: First - month String, Second - year String
        String[] mDateUIList = new String[2];

        //Set formatting types for raw dates
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM");
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");

        //Set month and year
        mDateUIList[0] = monthFormat.format(date);
        mDateUIList[1] = yearFormat.format(date);
        return mDateUIList;
    }
    public static Date addMonth(Date date) {
        int amount = 1;
        return add(date, Calendar.MONTH, amount);
    }
    public static Date removeOneMonth(Date date) {
        int amount = -1;
        return add(date, Calendar.MONTH, amount);
    }
    public static Calendar getCalendarFromInt(int month, int year){
        Date date = getDateFromInt(month,year);
        return getCalendarFromDate(date);
    }
    public static Boolean isWeekend(Date date){
       return isDayInWeek(date, Calendar.SUNDAY) || isDayInWeek(date, Calendar.SATURDAY);
    }
    public static Boolean isDayInWeek(Date date, int dayConstant){

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK) == dayConstant;
    }
    public static Calendar getCalendarFromDate(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;

    }
    public static Date add(Date date, int calendarField, int amount) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(calendarField, amount);
        return c.getTime();
    }
    public static boolean isSameMonth(Date startDate, Date endDate) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(startDate);
        cal2.setTime(endDate);

        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
    }
    public static Date getDateFromInt(int month, int year) {

        String input = year + "-" + month; //build string format - "yyyy-MM"
        Date myDate = null;
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM");
        try {

            myDate = inputFormat.parse(input);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return myDate;
    }
    public static int getNumberOfDayInMonth(int month, int year) {
        int iYear = year;
        int iMonth = month - 1; // 1 (months begin with 0)
        int iDay = 1; //always form first day

        // Create a calendar object and set year and month
        Calendar mycal = new GregorianCalendar(iYear, iMonth, iDay);

        // Get the number of days in that month
        int daysInMonth = (mycal.getActualMaximum(Calendar.DAY_OF_MONTH))  ;

        return daysInMonth;
    }
    public static ArrayList<Date> buildMonthOfDatesArray(int month, int year) {

        Date monthDate = getDateFromInt(month,year);
        monthDate = getFirstDayOfMonthDate(monthDate);

        ArrayList<Date> monthOfDatesArray = buildMonthOfDatesArray2(monthDate);
        return monthOfDatesArray;
    }
    public static ArrayList<Date> buildMonthOfDatesArray2(Date date) {

        Date currentDayDate = getStartOfDay(date);
        Date endDate = addMonth(getStartOfDay(date));

        ArrayList<Date> monthOfDatesArray = new ArrayList<>();

        while (currentDayDate.getTime() < endDate.getTime()){
            monthOfDatesArray.add(currentDayDate);
            currentDayDate = addOneDay(currentDayDate);
        }


        return monthOfDatesArray;
    }
    public static Date getFirstDayOfThisMonthDate(){
        return getFirstDayOfMonthDate(new Date());
    }
    public static Date getFirstDayOfMonthDate(Date date){

        Date mDate = date;
        mDate = getStartOfDay(mDate);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);

        calendar.set(Calendar.DAY_OF_MONTH, 1);

        Date firstDayOfMonth = calendar.getTime();

        return firstDayOfMonth;
    }
    public static Date getCurrentDate(){
        Date date = new Date();
        date = getStartOfDay(date);
        return date;
    }
    public static Date addOneDay(Date date) {
        long timeadj = 24 * 60 * 60 * 1000; // one day in millisecond
        return new Date(date.getTime() + timeadj);
    }
    public static int getDayIndex(Date date){
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date);

        return (cal1.get(Calendar.DAY_OF_MONTH) - 1);
    }
    public static Date getStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
    public static int getYearFromDate(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }
    public static int getMonthFromDate(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH) + 1;
    }
    public static int getDayFromDate(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }
    public static Date getDateFromString(String dayString){

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date startDate = null;
        try {
            startDate = df.parse(dayString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return startDate;
    }

}
