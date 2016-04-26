package com.example.android.drroster.databases;

import com.activeandroid.query.Select;
import com.example.android.drroster.models.AdditionalDutyDB;
import com.example.android.drroster.models.LeaveDateDB;
import com.example.android.drroster.models.PersonDB;
import com.example.android.drroster.models.ShiftDB;
import com.example.android.drroster.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
            String mThirdCallName = shuffledTable.get(2).get(dayIndex);

            //get person model from DB
            PersonDB firstCall = PersonDBHelper.getPersonFromString(mFirstCallName);
            PersonDB secondCall = PersonDBHelper.getPersonFromString(mSecondCallName);
            PersonDB thirdCall = PersonDBHelper.getPersonFromString(mThirdCallName);

            //Build Shift model
            ShiftDB shift =  new ShiftDB(mCurrentDate,firstCall,secondCall,thirdCall);
            shift.save();

            //Build all additional duties on this current date with type only
            // Example - "Outreach" - person name will be added later
            for (int i = 0; i < ADArray.length; i++){
                AdditionalDutyDB currentAD = new AdditionalDutyDB(mCurrentDate,ADArray[i]);
                currentAD.save();
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

    }

    public static List<AdditionalDutyDB> getAdditionalDutiesForDateList(Date currentDate){

        List<AdditionalDutyDB> mADList;

        mADList = new Select()
                .from(AdditionalDutyDB.class)
                .where("Date = ?",currentDate.getTime())
                .execute();

        return mADList;
    }


}
