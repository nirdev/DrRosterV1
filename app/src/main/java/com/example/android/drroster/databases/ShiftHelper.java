package com.example.android.drroster.databases;

import com.activeandroid.query.Select;
import com.example.android.drroster.models.DutyDateDB;
import com.example.android.drroster.models.DutyTypeDB;
import com.example.android.drroster.models.LeaveDateDB;
import com.example.android.drroster.models.PersonDB;
import com.example.android.drroster.models.RosterDB;
import com.example.android.drroster.models.ShiftDB;
import com.example.android.drroster.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Nir on 4/26/2016.
 */
public class ShiftHelper {

    public static void buildShiftTable(int month,int year,
                                ArrayList<ArrayList<String>> data,
                                String[] ADArray,
                                ArrayList<ArrayList<String>> namesOnDatesArray){

        //Array of all the month full with date for each day
        ArrayList<Date> monthDates = DateUtils.buildMonthOfDatesArray(month,year);

        //Table of 3 strings array full with shuffled names
        ArrayList<ArrayList<String>> shuffledTable = data;

        //number of days in current month
        int numberOfDays = monthDates.size();


        for (int dayIndex = 0;dayIndex < numberOfDays ; dayIndex++){
            Date mCurrentDate = monthDates.get(dayIndex);

            //get name of people from shuffled array
            String mFirstCallName = shuffledTable.get(0).get(dayIndex);
            String mSecondCallName = shuffledTable.get(1).get(dayIndex);

            String mThirdCallName = null;//Check because maybe there is no third call
            if (shuffledTable.get(2) != null && shuffledTable.get(2).size() > 0){
                mThirdCallName = shuffledTable.get(2).get(dayIndex);
            }


            //get person model from DB
            PersonDB firstCall = PersonDBHelper.getPersonFromString(mFirstCallName);
            PersonDB secondCall = PersonDBHelper.getPersonFromString(mSecondCallName);

            PersonDB thirdCall = null; //Check because maybe there is no third call
            if (shuffledTable.get(2) != null && shuffledTable.get(2).size() > 0){
                thirdCall = PersonDBHelper.getPersonFromString(mThirdCallName);
            }

            //Build Shift model
            ShiftDB shift =  new ShiftDB(mCurrentDate,firstCall,secondCall,thirdCall);
            shift.save();

            // Build all additional duties on this current date with type only
            // Example - "Outreach" - person name will be added later
            for (int i = 0; i < ADArray.length; i++){
                DutyTypeDB mDutyType = DutiesHelper.getDutyTypeFromString(ADArray[i]);

                //Build new duties (amount of check duties types from ADArray) and set current date for each duty.
                DutyDateDB mDutyDate = new DutyDateDB(mCurrentDate,mDutyType);
                mDutyDate.save();
            }

            //Build all leave dates
            saveLeaveDates(dayIndex,namesOnDatesArray,mCurrentDate);


        }
        //Save table of first dates in moth to make ready roster index
        RosterDB rosterDB = new RosterDB(monthDates.get(0));
        rosterDB.save();

    }
    private static void saveLeaveDates(int dayIndex,ArrayList<ArrayList<String>> namesOnDatesArray,Date mCurrentDate){
        //Get current day leave names array
        ArrayList<String> mCurrentDayLeaveNames = namesOnDatesArray.get(dayIndex);
        //Loop on the array if not null and add each on to the array
        if (mCurrentDayLeaveNames != null && (mCurrentDayLeaveNames.size() > 0)){
            for (String name : mCurrentDayLeaveNames){
                //Add each name of specific day string array as new additional
                //duty column with date and person DB relations
                PersonDB mPersonDB = PersonDBHelper.getPersonFromString(name);
                LeaveDateDB mLeaveDateDB = new LeaveDateDB(mCurrentDate,mPersonDB);
                mLeaveDateDB.save();
            }
        }
    }
    public static Boolean isThereThirdCall(Date date){
        ShiftDB shiftDB = getShiftForDate(date);
        if (shiftDB != null && shiftDB.thirdCall != null && !shiftDB.thirdCall.equals("")){
            return true;
        }
        return false;
    }
    public static ShiftDB getShiftForDate(Date date){
        ShiftDB shiftDB  = new Select()
                .from(ShiftDB.class)
                .where("Day = ?",date.getTime())
                .executeSingle();

        return shiftDB;
    }
}
