package com.example.android.drroster.utils;

import android.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Nir on 4/18/2016.
 */
public class RandomTypeBuilder {

    private ArrayList<Pair<String, List<Date>>> leaveDatesList;
    private int[] monthAndYear;
    private int numberOfDaysInMonth;
    Boolean isFirstTime = true;
    private ArrayList<Date> dateInMonthList;
    private int mTotalLoops = 100000;
    private ArrayList<String> names;

    //Last person is good
    public RandomTypeBuilder(ArrayList<Pair<String, List<Date>>> leaveDatesList, int[] monthAndYear) {
        this.leaveDatesList = leaveDatesList;
        this.monthAndYear = monthAndYear;
        numberOfDaysInMonth = DateUtils.getNumberOfDayInMonth(monthAndYear[0], monthAndYear[1]);
        dateInMonthList = DateUtils.buildMonthOfDatesArray(monthAndYear[0], monthAndYear[1]);
    }


    public ArrayList<String> buildTypeOne(ArrayList<String> names, String safeName) {
        ArrayList<String> randomizedArray = new ArrayList<>();
        this.names = names;
        ArrayList<String> mTempNames = this.names;

//        this.names.add(safeName);

        //Set safe first one in full array and delete it from temp
//        randomizedArray.add(safeName);
//        mTempNames.remove(safeName);

//        randomizedArray.add(names.get(0));
//        mTempNames.remove(0);

//        randomizedArray.add("item 1");
        int dayOfMonthIndex = 0;

        outerLoop:
        for (int i = 0; i < mTotalLoops; i++) {

            Collections.shuffle(mTempNames);

            // Check Two randomization conditions -
            // first - last full not equals temp so no same names day after day
            // no one is on leave date
            //TODO: after startup delete
            if (true || firstNotEqualsLast(randomizedArray, mTempNames) && !isOnLeaveDateArrayChecker(mTempNames, dayOfMonthIndex)) {
                //add all to the full array
                for (int x = 0; x < this.names.size(); x++) {
                    randomizedArray.add(mTempNames.get(x));
                    dayOfMonthIndex++;
                    if (dayOfMonthIndex == (numberOfDaysInMonth + 1)) {
                        break outerLoop;
                    }
                }
                mTempNames = this.names;
            }
        }


        return randomizedArray;
    }

    public ArrayList<String> buildTypeTwo(ArrayList<String> mCurrentNames, String safeName) {
        return buildTypeOne(mCurrentNames, safeName);
    }

    public ArrayList<String> buildTypeThree(ArrayList<String> mCurrentNames, String safeName) {
        return buildTypeOne(mCurrentNames, safeName);
    }

    public ArrayList<String> buildTypeFive(ArrayList<String> mCurrentNames, String safeName) {
        return buildTypeOne(mCurrentNames, safeName);
    }

    public ArrayList<String> buildTypeFour(ArrayList<String> mCurrentNames, String safeName) {
        return buildTypeOne(mCurrentNames, safeName);
    }


    private boolean firstNotEqualsLast(ArrayList<String> fullArray, ArrayList<String> shuffledArray) {
        return !fullArray.get(fullArray.size() - 1).equals(shuffledArray.get(0));
    }

    private boolean isOnLeaveDateArrayChecker(ArrayList<String> shuffledArray, int dayOfMonthIndex) {
        for (int i = 0; i < shuffledArray.size(); i++) {
            //if day on month is on leave date
            if (dayOfMonthIndex - 1 + i < numberOfDaysInMonth) {
                if (isOnLeaveDate(dateInMonthList.get(dayOfMonthIndex - 1 + i), shuffledArray.get(i))) {
                    return true;
                }
            }

        }
        return false;
    }

    private boolean isOnLeaveDate(Date currentDate, String name) {
        //Irritate over two dimensional array of pairs of string and date lists and check
        // if current name is on leave date on current day
        for (Pair pair : leaveDatesList) {
            if (pair.first.equals(name)) {
                //Get leave date list for that name
                List<Date> dateListForName;
                dateListForName = (List<Date>) pair.second;
                for (int z = 0;z < dateListForName.size();z++) {
                    if (dateListForName.get(z).getTime() == currentDate.getTime()) {
                        int nir;
                        return true;
                    }
                }
            }
        }
        return false;
    }


}
