package com.example.android.drroster.databases;

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
        int numberOfDays = monthDates.size() -1;


        for (int dayIndex = 0;dayIndex < numberOfDays ; dayIndex++){
            Date mCurrentDate = monthDates.get(dayIndex);

            //get name of people from shuffled array
            String mFirstCallName = shuffledTable.get(0).get(dayIndex);
            String mSecondCallName = shuffledTable.get(1).get(dayIndex);
            String mThirdCallName = shuffledTable.get(2).get(dayIndex);

            //get person model from DB
            PersonDB firstCall = PersonDBHelper.getPersonFromString(mFirstCallName);
            PersonDB secondCall = PersonDBHelper.getPersonFromString(mSecondCallName);
            PersonDB thirdCall = PersonDBHelper.getPersonFromString(mThirdCallName);

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
            //Run on month sized array with string arrays for each day index with respect to leave dates
            for (int dayIndex2= 0;dayIndex2 < namesOnDatesArray.size();dayIndex2++){
                //Get current day leave names array
                ArrayList<String> mCurrentDayLeaveNames = namesOnDatesArray.get(dayIndex2);
                //Loop on the array if not null and add each on to the array
                if (mCurrentDayLeaveNames != null){
                    for (String name : mCurrentDayLeaveNames){
                        //Add each name of specific day string array as new additional
                        //duty column with date and person DB relations
                        PersonDB mPersonDB = PersonDBHelper.getPersonFromString(name);
                        LeaveDateDB mLeaveDateDB = new LeaveDateDB(mCurrentDate,mPersonDB);
                        mLeaveDateDB.save();
                    }
                }
            }

        }
        //Save table of first dates in moth to make ready roster index
        RosterDB rosterDB = new RosterDB(monthDates.get(0));
        rosterDB.save();

    }
}
