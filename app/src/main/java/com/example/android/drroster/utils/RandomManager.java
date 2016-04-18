package com.example.android.drroster.utils;

import android.util.Pair;

import com.example.android.drroster.models.Person;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Nir on 4/18/2016.
 */
public class RandomManager {

    public static final int RANDOM_TYPE_1 = 1;
    public static final int RANDOM_TYPE_2 = 2;
    public static final int RANDOM_TYPE_3 = 3;
    public static final int RANDOM_TYPE_4 = 4;
    public static final int RANDOM_TYPE_5 = 5;

    public static final int FIRST_CALL = 11;
    public static final int SECOND_CALL = 12;
    public static final int THIRD_CALL = 13;

    private int CALL_TAG;
    private ArrayList<Person> mRosterArray;
    private  int[] monthAndYear = new int[2];



    public RandomManager(ArrayList<Person> data,int[] monthAndYear,int call_tag) {
        CALL_TAG = call_tag;
        this.mRosterArray = data;
        this.monthAndYear = monthAndYear;
    }

    public ArrayList<ArrayList<String>> getFirstCallRandomMonth (){
        ArrayList<String> firstCallNames = firstCallNamesBuilder(mRosterArray);
        Date firstDayDate = DateUtils.getDateFromInt(monthAndYear[0], monthAndYear[1]);
        int NumberOfDays = DateUtils.getNumberOfDayInMonth(monthAndYear[0], monthAndYear[1]);




        return null;
    }

    //Build list of pairs with only leave date people and their date arrays
    public ArrayList<Pair<String,List<Date>>> LeaveDatesBuilder(){
        ArrayList<Pair<String,List<Date>>> namesDatePairArray = new ArrayList<>();

        for (Person person : mRosterArray){
            if (person.getIsLeavDate()){
                namesDatePairArray.add(new Pair<>(person.getName(),person.getLeaveDates()));
            }
        }

        return namesDatePairArray;
    }



    private ArrayList<String> firstCallNamesBuilder(ArrayList<Person> data) {
        ArrayList<String> newArray = new ArrayList<>();

        for (int i = 0; i < data.size() ; i++ ) {
            if (data.get(i).getIsFirstCall() != null
                    && data.get(i).getIsFirstCall()) {

                newArray.add(data.get(i).getName());
            }
        }
        return newArray;
    }

    private ArrayList<String> secondCallNamesBuilder(ArrayList<Person> data) {
        ArrayList<String> newArray = new ArrayList<>();

        for (int i = 0; i < data.size() ; i++ ) {
            if (data.get(i).getIsSecondCall() != null
                    && data.get(i).getIsSecondCall()) {

                newArray.add(data.get(i).getName());
            }
        }
        return newArray;
    }

    private ArrayList<String> thirdCallNamesBuilder(ArrayList<Person> data) {
        ArrayList<String> newArray = new ArrayList<>();

        for (int i = 0; i < data.size() ; i++ ) {
            if (data.get(i).getIsThirdCall() != null
                    && data.get(i).getIsThirdCall()) {

                newArray.add(data.get(i).getName());
            }
        }
        return newArray;
    }
}
