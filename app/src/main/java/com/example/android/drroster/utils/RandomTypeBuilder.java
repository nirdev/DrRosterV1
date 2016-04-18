package com.example.android.drroster.utils;

import android.util.Pair;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Nir on 4/18/2016.
 */
public class RandomTypeBuilder {

    private ArrayList<Pair<String,List<Date>>> leaveDatesList;
    private int[] monthAndYear;
    private int numberOfDaysInMonth;
    private ArrayList<Date> dateInMonthList;

    public RandomTypeBuilder(ArrayList<Pair<String, List<Date>>> leaveDatesList, int[] monthAndYear) {
        this.leaveDatesList = leaveDatesList;
        this.monthAndYear = monthAndYear;
        numberOfDaysInMonth = DateUtils.getNumberOfDayInMonth(monthAndYear[0],monthAndYear[1]);


    }

    //Last person is good
    public ArrayList<String> buildTypeOne(ArrayList<String> names){
        ArrayList<String> randomizedArray = new ArrayList<>();

        //No leave dates



        return randomizedArray;
    }

    private boolean isOnLeaveDate (Date currentDate ,String name){

        //Irritate over two dimensional array of pairs of string and date lists and check
        // if current name is on leave date on current day
        for (Pair pair : leaveDatesList){
            if (pair.first.equals(name)){
                for (Date date : (List<Date>) pair.second){
                    if (date.equals(currentDate)){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
