package com.example.android.drroster.utils;

import android.util.Pair;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

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
        dateInMonthList = DateUtils.buildMonthOfDatesArray(monthAndYear[0],monthAndYear[1]);
    }

    //Last person is good
    public ArrayList<String> buildTypeOne(ArrayList<String> names){
        ArrayList<String> randomizedArray = new ArrayList<>();
        ArrayList<String> mNames = names;

        //Sets the last one from the array and set it first because last is for sure not last from last month
        randomizedArray.add(mNames.get(names.size()-1));
        Boolean isFirstTime = true;
        int i2;
        //Building the array (i = 1 because fist one is already settled)
        outerLoop:
        for (int i = 1;i < numberOfDaysInMonth; i++) {
            //build new array each time temp is done filling names of month
            ArrayList<String> mTempNames = mNames;

            //Loop strats from one only first time
            i2 = 0;

            //if first time lose the last because he's already benn used
            if (isFirstTime) {
                isFirstTime = false;
                mTempNames.remove(mTempNames.size() - 1);
                i2 = 1;
            }

            for (int tempi2 = i2; i2 < mNames.size(); i2++) {

                //Break if randomized array is as big as number of days
                if (randomizedArray.size() == numberOfDaysInMonth) {
                    break outerLoop;
                }
                Random rand = null;
                int randomNum = rand.nextInt(mTempNames.size() - i2);
                randomizedArray.add(mTempNames.get(randomNum));
                mTempNames.remove(randomNum);

            }
        }

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
