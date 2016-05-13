package com.example.android.drroster.utils;

import android.util.Pair;

import com.example.android.drroster.models.ItemMainView;
import com.example.android.drroster.models.ShiftFull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Nir on 4/18/2016.
 */
public class RandomManager {

    public static final String RANDOM_TYPE_1 = "1";
    public static final String RANDOM_TYPE_2 = "2";
    public static final String RANDOM_TYPE_3 = "3";
    public static final String RANDOM_TYPE_4 = "4";
    public static final String RANDOM_TYPE_5 = "5";


    private String CALL_TAG;
    RandomTypeBuilder randomTypeBuilder;
    private ArrayList<ShiftFull> mRosterArray;
    private ArrayList<ArrayList<String>> mCurrentCallRandomizeTable;
    private ArrayList<String> mCurrentNames;
    private int[] monthAndYear = new int[2];
    private ArrayList<String> mLastMonthNames;
//    private ArrayList<ItemMainView> mLastMonthFullShift;
    private int numOfDays;
    private Date currentMonth;
    private Date lastMonth;

    public RandomManager() {
    }


    public void initiateRandomManager(ArrayList<ShiftFull> data, int[] monthAndYear, String call_tag) {

        CALL_TAG = call_tag;
        this.mRosterArray = data;
        this.monthAndYear = monthAndYear;
        currentMonth = DateUtils.getDateFromInt(monthAndYear[0], monthAndYear[1]);
        lastMonth = DateUtils.removeOneMonth(currentMonth);
        numOfDays = DateUtils.getNumberOfDayInMonth(monthAndYear[0], monthAndYear[1]);
//        mLastMonthFullShift = ItemMainViewHelper.buildMainItemMonthList(lastMonth);

        //Build table of name with their leave date
        ArrayList<Pair<String, List<Date>>> namesDatePairArray = leaveDatesBuilderOld();
        ArrayList<ArrayList<String>> mLeaveDates = leaveDateBuilder(mRosterArray);

        randomTypeBuilder = new RandomTypeBuilder(
                mLeaveDates,
                this.monthAndYear);


        //Decide which names array to build base on the tag
        switch (CALL_TAG) {
            case "0":
                mCurrentNames = firstCallNamesBuilder(this.mRosterArray);
//                mLastMonthNames = getLastMonthFirstCallNames(mLastMonthFullShift);
                break;
            case "1":
                mCurrentNames = secondCallNamesBuilder(this.mRosterArray);
//                mLastMonthNames = getLastMonthSecondCallNames(mLastMonthFullShift);
                break;
            case "2":
                mCurrentNames = thirdCallNamesBuilder(this.mRosterArray);
//                mLastMonthNames = getLastMonthThirdCallNames(mLastMonthFullShift);
                break;
        }
        if (!isCallEmpty()) {
            //Build the table
            mCurrentCallRandomizeTable = getRandomMonth();
        }
    }

    public ArrayList<ArrayList<String>> getRandomizeTable() {
        return mCurrentCallRandomizeTable;
    }

    public ArrayList<String> getRandomizedType(int typeNumber) {
        return mCurrentCallRandomizeTable.get(typeNumber - 1);
    }

    //Return if call is with people or not
    public Boolean isCallEmpty() {
        return mCurrentNames.isEmpty();
    }

    public Boolean isCallToShort() {
        return (mCurrentNames.size() < 3);
    }

    private ArrayList<ArrayList<String>> getRandomMonth() {
        ArrayList<ArrayList<String>> fullRandomMonthName = new ArrayList<>();
        fullRandomMonthName.add(randomTypeBuilder.buildTypeOne(mCurrentNames, mLastMonthNames));
        fullRandomMonthName.add(randomTypeBuilder.buildTypeTwo(mCurrentNames, mLastMonthNames));
        fullRandomMonthName.add(randomTypeBuilder.buildTypeThree(mCurrentNames, mLastMonthNames));
        fullRandomMonthName.add(randomTypeBuilder.buildTypeFour(mCurrentNames, mLastMonthNames));
        fullRandomMonthName.add(randomTypeBuilder.buildTypeFive(mCurrentNames, mLastMonthNames));
        return fullRandomMonthName;
    }


    //Build list of pairs with only leave date people and their date arrays
    private ArrayList<Pair<String, List<Date>>> leaveDatesBuilderOld() {
        ArrayList<Pair<String, List<Date>>> namesDatePairArray = new ArrayList<>();

        for (ShiftFull person : mRosterArray) {
            if (person.getIsLeavDate()) {
                namesDatePairArray.add(new Pair<>(person.getName(), person.getLeaveDates()));
            }
        }
        return namesDatePairArray;
    }

    private ArrayList<ArrayList<String>> leaveDateBuilder(ArrayList<ShiftFull> rosterArray) {
        ArrayList<ArrayList<String>> mLeaveDate = new ArrayList<>();

        //initialize array of numOfDays in particular month with null array strings
        for (int z =0;z < numOfDays;z++){
            mLeaveDate.add(new ArrayList<String>());
        }

        for (int i = 0; i < rosterArray.size(); i++) {
            List<Date> onePersonLeaveDates = rosterArray.get(i).getLeaveDates();
            String name = rosterArray.get(i).getName();
            //Loop on array of one person dates
            if (onePersonLeaveDates != null && onePersonLeaveDates.size() > 0) {
                for (int x = 0; x < onePersonLeaveDates.size(); x++) {
                    //Check for old leave dates
                    if (DateUtils.isSameMonth(currentMonth,onePersonLeaveDates.get(x))) {
                        //put names arrays in month length array respect to there leave data arrays
                        int indexInMonth = DateUtils.getDayIndex(onePersonLeaveDates.get(x));
                        //get people already on current day index and add new person
                        ArrayList<String> oldNamesInDayIndex = mLeaveDate.get(indexInMonth);
                        //add new name to array
                        oldNamesInDayIndex.add(name);
                        //Add modified array back to full leave dates array
                        mLeaveDate.set(indexInMonth, oldNamesInDayIndex);
                    }
                }
            }
        }

        return mLeaveDate;
    }

    private ArrayList<String> firstCallNamesBuilder(ArrayList<ShiftFull> data) {
        ArrayList<String> newArray = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getIsFirstCall() != null
                    && data.get(i).getIsFirstCall()) {

                newArray.add(data.get(i).getName());
            }
        }
        return newArray;
    }

    private ArrayList<String> secondCallNamesBuilder(ArrayList<ShiftFull> data) {
        ArrayList<String> newArray = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getIsSecondCall() != null
                    && data.get(i).getIsSecondCall()) {

                newArray.add(data.get(i).getName());
            }
        }
        return newArray;
    }

    private ArrayList<String> thirdCallNamesBuilder(ArrayList<ShiftFull> data) {
        ArrayList<String> newArray = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getIsThirdCall() != null
                    && data.get(i).getIsThirdCall()) {

                newArray.add(data.get(i).getName());
            }
        }
        return newArray;
    }

    private ArrayList<String> getLastMonthFirstCallNames(ArrayList<ItemMainView> data) {
        ArrayList<String> newArray = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getFirstCallName() != null) {
                newArray.add(data.get(i).getFirstCallName());
            }
        }
        return newArray;
    }
    private ArrayList<String> getLastMonthSecondCallNames(ArrayList<ItemMainView> data) {
        ArrayList<String> newArray = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getSecondCallName() != null) {
                newArray.add(data.get(i).getSecondCallName());
            }
        }
        return newArray;
    }
    private ArrayList<String> getLastMonthThirdCallNames(ArrayList<ItemMainView> data) {
        ArrayList<String> newArray = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getThirdCallName() != null &&
                    !data.get(i).getThirdCallName().isEmpty() &&
                    !data.get(i).getThirdCallName().equals("")) {
                newArray.add(data.get(i).getThirdCallName());
            }
        }
        return newArray;
    }

}
