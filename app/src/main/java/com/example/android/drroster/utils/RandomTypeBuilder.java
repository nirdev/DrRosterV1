package com.example.android.drroster.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * Created by Nir on 4/18/2016.
 */
public class RandomTypeBuilder {

    private int[] monthAndYear;
    private ArrayList<Date> dateInMonthList;
    private Date currentMonth;
    private ArrayList<ArrayList<String>> leaveDatesList;
    private int numberOfDaysInMonth;
    private int mTotalLoops = 100000;
    ArrayList<String> dummyData;

    public RandomTypeBuilder(ArrayList<ArrayList<String>> leaveDatesList, int[] monthAndYear) {
        this.leaveDatesList = leaveDatesList;
        this.monthAndYear = monthAndYear;
        currentMonth = DateUtils.getDateFromInt(monthAndYear[0], monthAndYear[1]);
        numberOfDaysInMonth = DateUtils.getNumberOfDayInMonth(monthAndYear[0], monthAndYear[1]);
        dateInMonthList = DateUtils.buildMonthOfDatesArray2(currentMonth);
        dummyData = new ArrayList<>();
        dummyData.add("dummyArray");
        dummyData.add("dummyArray");
        dummyData.add("dummyArray");
        dummyData.add("dummyArray");
    }

    private boolean joinedDates(int dayIndex,
                                String nameToCheck,
                                ArrayList<String> finalTypeNames,
                                ArrayList<String> mLastMonthNames) {
        //if first day
        if (dayIndex == 0) {
            //if name equals to last person from last month
            if (nameToCheck.equals(mLastMonthNames.get(mLastMonthNames.size() - 1))) {
                return true;
            } else {
                //Check if maybe one name was inserted after current day index and if not null check for equality
                if ((finalTypeNames.get(dayIndex + 1) != null) &&
                        nameToCheck.equals(finalTypeNames.get(dayIndex + 1))) {
                    return true;
                }
                return false;
            }
        }

        //if last day check only for day before
        if (dayIndex == numberOfDaysInMonth - 1) {
            return nameToCheck.equals(finalTypeNames.get(dayIndex - 1));
        }

        //if day to check equals the day before OR the day after
        if (nameToCheck.equals(finalTypeNames.get(dayIndex - 1)) ||
                (finalTypeNames.get(dayIndex + 1) != null && nameToCheck.equals(finalTypeNames.get(dayIndex + 1)))) {
            return true;
        }
        return false;
    }

    private boolean onLeaveDate(int dayIndex, String nameToCheck) {
        ArrayList<String> leaveDatePeopleForDay = leaveDatesList.get(dayIndex);
        //If indexOfDay == -1 so person is not found == not on leave dat != false
        Boolean temp = leaveDatePeopleForDay.indexOf(nameToCheck) != -1;
        return temp;
    }

    private String checkName(int dayIndex,
                             String nameToCheck,
                             ArrayList<String> finalTypeNames,
                             ArrayList<String> mLastMonthNames) {

        if (onLeaveDate(dayIndex, nameToCheck) ||
                joinedDates(dayIndex, nameToCheck, finalTypeNames, mLastMonthNames)) {
            return null;
        }
        return nameToCheck;
    }

    public ArrayList<String> buildTypeOne(ArrayList<String> mCurrentNames, ArrayList<String> mLastMonthNames) {
        //initialize array with null objects
        ArrayList<String> finalTypeOneNames = getFinalNamesNullArray(numberOfDaysInMonth);
        RandomNameHolder nameHolder = new RandomNameHolder(mCurrentNames,numberOfDaysInMonth);
        int dayIndex = 0;

        for (int loops = 0; loops < mTotalLoops; loops++) {


            if (dayIndex == numberOfDaysInMonth){
                return finalTypeOneNames;
            }
            else {
                finalTypeOneNames.set(dayIndex, nameHolder.getNewName());
                dayIndex++;
            }

//            String nameToCheck = nameHolder.getNewName();
//            if (nameToCheck == null){
//                //loop from start
//                nameHolder.refreshNames();
////                finalTypeOneNames = getFinalNamesNullArray(numberOfDaysInMonth);
////                dayIndex = 0;
//            }else{
//                String newName = checkName(dayIndex, nameToCheck, finalTypeOneNames, mLastMonthNames);
//                if (newName == null) {
//                    //loop from start
//                    nameHolder.refreshNames();
//                    finalTypeOneNames = getFinalNamesNullArray(numberOfDaysInMonth);
//                    dayIndex = 0;
//                }
//                else {
//                    if (dayIndex == numberOfDaysInMonth - 1){
//                        return finalTypeOneNames;
//                    }
//                    else {
//                        finalTypeOneNames.set(dayIndex, newName);
//                        dayIndex++;
//                    }
//                }
//            }
        }
        return null;
    }

    public ArrayList<String> buildTypeTwo(ArrayList<String> mCurrentNames, ArrayList<String> mLastMonthNames) {
        return buildTypeOne(mCurrentNames, mLastMonthNames);
    }

    public ArrayList<String> buildTypeThree(ArrayList<String> mCurrentNames, ArrayList<String> mLastMonthNames) {
        return buildTypeOne(mCurrentNames, mLastMonthNames);
    }

    public ArrayList<String> buildTypeFive(ArrayList<String> mCurrentNames, ArrayList<String> mLastMonthNames) {
        return buildTypeOne(mCurrentNames, mLastMonthNames);
    }

    public ArrayList<String> buildTypeFour(ArrayList<String> mCurrentNames, ArrayList<String> mLastMonthNames) {
        return buildTypeOne(mCurrentNames, mLastMonthNames);
    }

//    public ArrayList<String> buildTypeOneOld(ArrayList<String> names,ArrayList<String> mLastMonthNames ) {

    private ArrayList<String> getFinalNamesNullArray(int numberOfDaysInMonth) {
        ArrayList<String> temp = new ArrayList<>();
        for (int i = 0; i < numberOfDaysInMonth; i++) {
            temp.add(null);
        }

        return temp;
    }

//        String safeName = "gogo";
//        ArrayList<String> randomizedArray = new ArrayList<>();
//        this.names = names;
//        ArrayList<String> mTempNames = this.names;
//
//
//        //Set safe first one in full array and delete it from temp
//        randomizedArray.add(safeName);
//        mTempNames.remove(safeName);
//
//
//        int dayOfMonthIndex = 1;
//
//        outerLoop:
//        for (int i = 0; i < mTotalLoops; i++) {
//
//            Collections.shuffle(mTempNames);
//
//            // Check Two randomization conditions -
//            // first - last full not equals temp so no same names day after day
//            // no one is on leave date
//            if (firstNotEqualsLastOld(randomizedArray, mTempNames) && !isOnLeaveDateArrayChecker(mTempNames, dayOfMonthIndex)) {
//                //add all to the full array
//                for (int x = 0; x < this.names.size(); x++) {
//                    randomizedArray.add(mTempNames.get(x));
//                    dayOfMonthIndex++;
//                    if (dayOfMonthIndex == (numberOfDaysInMonth + 1)) {
//                        break outerLoop;
//                    }
//                }
//                mTempNames = this.names;
//            }
//        }
//
//
//        return randomizedArray;
//    }
//    private boolean firstNotEqualsLastOld(ArrayList<String> fullArray, ArrayList<String> shuffledArray) {
//        return !fullArray.get(fullArray.size() - 1).equals(shuffledArray.get(0));
//    }
//    private boolean isOnLeaveDateArrayChecker(ArrayList<String> shuffledArray, int dayOfMonthIndex) {
//        for (int i = 0; i < shuffledArray.size(); i++) {
//            //if day on month is on leave date
//            if (dayOfMonthIndex - 1 + i < numberOfDaysInMonth) {
//                if (isOnLeaveDateOld(dateInMonthList.get(dayOfMonthIndex - 1 + i), shuffledArray.get(i))) {
//                    return true;
//                }
//            }
//
//        }
//        return false;
//    }
//    private boolean isOnLeaveDateOld(Date currentDate, String name) {
//        //Irritate over two dimensional array of pairs of string and date lists and check
//        // if current name is on leave date on current day
//        for (Pair pair : leaveDatesList) {
//            if (pair.first.equals(name)) {
//                //Get leave date list for that name
//                List<Date> dateListForName;
//                dateListForName = (List<Date>) pair.second;
//                for (int z = 0;z < dateListForName.size();z++) {
//                    if (dateListForName.get(z).getTime() == currentDate.getTime()) {
//                        int nir;
//                        return true;
//                    }
//                }
//            }
//        }
//        return false;
//    }


}

class RandomNameHolder {

    ArrayList<String> names;
    ArrayList<String> namesOverMonthFull;
    ArrayList<String> namesToSend;

    public RandomNameHolder(ArrayList<String> names,int numOfDaysInMonth) {
        this.names = names;
        namesOverMonthFull = buildMonthNames(this.names, numOfDaysInMonth);
        namesToSend = namesOverMonthFull;
    }

    private ArrayList<String> buildMonthNames(ArrayList<String> names, int numOfDaysInMonth) {

        Collections.shuffle(names);
        ArrayList<String> tempNames = new ArrayList<>(names);
        ArrayList<String> monthNames = new ArrayList<>();
        for (int i = 0; i < numOfDaysInMonth;i++){
            if (tempNames.size() > 0){
                monthNames.add(tempNames.get(0));
                tempNames.remove(0);
            }else {
                tempNames = new ArrayList<>(names);
                monthNames.add(tempNames.get(0));
                tempNames.remove(0);
            }
        }
        return monthNames;
    }

    public String getNewName(){
        //check if there is still people on current list who's haven't been check yet
       if (namesToSend != null && namesToSend.size() >= 0){
           String nameToSend = namesToSend.get(0);
           namesToSend.remove(0);
           return nameToSend;
       }
        return null;
    }

    public void refreshNames(){
        namesToSend = namesOverMonthFull;
//        Collections.shuffle(namesToSend);
    }


}
