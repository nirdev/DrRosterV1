package com.example.android.drroster.utils;

import android.util.Pair;

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
    public static String safeName;

    public RandomManager() {
    }


    public void initiateRandomManager(ArrayList<ShiftFull> data, int[] monthAndYear, String call_tag, String safeName) {

        CALL_TAG = call_tag;
        this.mRosterArray = data;
        this.monthAndYear = monthAndYear;
        this.safeName = safeName;


        //Build table of name with their leave date
        ArrayList<Pair<String, List<Date>>> namesDatePairArray = leaveDatesBuilder();

        randomTypeBuilder = new RandomTypeBuilder(
                namesDatePairArray,
                this.monthAndYear);


        //Decide which names array to build base on the tag
        switch (CALL_TAG) {
            case "0":
                mCurrentNames = firstCallNamesBuilder(this.mRosterArray);
                break;
            case "1":
                mCurrentNames = secondCallNamesBuilder(this.mRosterArray);
                break;
            case "2":
                mCurrentNames = thirdCallNamesBuilder(this.mRosterArray);
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
        //Todo: change to 3 after startup
        return (mCurrentNames.size() < 1);
    }

    private ArrayList<ArrayList<String>> getRandomMonth() {
        ArrayList<ArrayList<String>> fullRandomMonthName = new ArrayList<>();
        fullRandomMonthName.add(randomTypeBuilder.buildTypeOne(mCurrentNames, safeName));
        fullRandomMonthName.add(randomTypeBuilder.buildTypeTwo(mCurrentNames, safeName));
        fullRandomMonthName.add(randomTypeBuilder.buildTypeThree(mCurrentNames, safeName));
        fullRandomMonthName.add(randomTypeBuilder.buildTypeFour(mCurrentNames, safeName));
        fullRandomMonthName.add(randomTypeBuilder.buildTypeFive(mCurrentNames, safeName));
        return fullRandomMonthName;
    }

    //Build list of pairs with only leave date people and their date arrays
    private ArrayList<Pair<String, List<Date>>> leaveDatesBuilder() {
        ArrayList<Pair<String, List<Date>>> namesDatePairArray = new ArrayList<>();

        for (ShiftFull person : mRosterArray) {
            if (person.getIsLeavDate()) {
                namesDatePairArray.add(new Pair<>(person.getName(), person.getLeaveDates()));
            }
        }
        return namesDatePairArray;
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

}
