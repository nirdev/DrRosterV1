package com.example.android.drroster.databases;

import android.util.Log;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.example.android.drroster.models.RosterDB;
import com.example.android.drroster.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Nir on 4/27/2016.
 */
public class RosterHelper {

    public static List<RosterDB> getAllReadyRosters(){

        List<RosterDB> mRosterDB;
        mRosterDB = new Select()
                .from(RosterDB.class)
                .orderBy("Date")
                .execute();

        return mRosterDB;
    }
    public static Boolean isAvailableRoster(Date currentMonth){
        RosterDB mRosterDB  = new Select()
                .from(RosterDB.class)
                .where("Date = ?",currentMonth.getTime())
                .executeSingle();
        if (mRosterDB == null){
            return false;
        }
        //if roster was found
        return true;
    }
    public static ArrayList<Date> getAllRosterDates(){
        ArrayList<Date> rosterDates = new ArrayList<>();
        //Get all ready rosters from above function
        List<RosterDB> mRosterDBs = getAllReadyRosters();

        for (RosterDB rosterDB : mRosterDBs){
            rosterDates.add(rosterDB.date);
        }

        return rosterDates;
    }
    public static int getCurrentDayRosterIndex(){
        //if no roster have been built to this month show oldest roster
        int index = 0;

        //get first day of this month
        Date firstDayOfThisMonth = DateUtils.getFirstDayOfThisMonthDate();
        ArrayList<Date> allRosterDates = getAllRosterDates();
        //check in ready roster date array which on is equal
        if (allRosterDates.indexOf(firstDayOfThisMonth) != -1){
            index = allRosterDates.indexOf(firstDayOfThisMonth);
        }
        else {
            Log.wtf("here", "-------------------------------------------- dude get a life and check " +
                    "getCurrentDayRosterIndex method in RosterHelper class, fucker");
        }

        return index;
    }
    public static Date getRosterDateFromIndex(int index){
        ArrayList<Date> allRosterDates = getAllRosterDates();
        return allRosterDates.get(index);
    }

    public static void removeRoster (Date date){
        new Delete()
                .from(RosterDB.class)
                .where("Date = ?",date.getTime())
                .execute();
    }
    public static void removeAllRosterForMonth(Date currentDate){
        ArrayList<Date> monthDates = DateUtils.buildMonthOfDatesArray2(currentDate);

        for (Date day: monthDates){
            DutiesHelper.removeDutyForDate(day);
            LeaveDatesHelper.removeAllLeaveDatePersonForDate(day);
            ShiftHelper.RemoveShiftForDate(day);
            removeRoster(day);
        }
    }

}
