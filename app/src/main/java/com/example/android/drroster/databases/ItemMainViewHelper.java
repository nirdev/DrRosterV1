package com.example.android.drroster.databases;

import android.util.Pair;

import com.example.android.drroster.MainActivity;
import com.example.android.drroster.models.DutyDateDB;
import com.example.android.drroster.models.ItemMainView;
import com.example.android.drroster.models.LeaveDateDB;
import com.example.android.drroster.models.ShiftDB;
import com.example.android.drroster.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Model Summary -
 * 4. Date of day - Date (maybe long)
 * 1. First call name - String
 * 2. Second call name - String
 * 3. Third call name - String
 * 5. ArrayList of Duty Task and name Pair<String,String> - F = DutyType, S = DutyDoer
 * 6. ArrayList of leave dates people ArrayList<String>
 */
public class ItemMainViewHelper {

    public static ArrayList<ItemMainView> buildMainItemMonthList(Date month){
        ArrayList<ItemMainView> mainItemArray = new ArrayList<>();
        ArrayList<Date> currentMonthDates = DateUtils.buildMonthOfDatesArray2(month);

        for (Date date : currentMonthDates){
            mainItemArray.add(buildMainItemFromDB(date));
        }

        return  mainItemArray;
    }
    public static ItemMainView buildMainItemFromDB(Date date) {
        //new main view item
        ItemMainView mainItem = new ItemMainView();
        //add date to item
        mainItem.setDay(date);
        //Add people on calls names
        mainItem = addCallsNamesToItem(mainItem, date);
        //Add list of duties pairs
        mainItem = addDutiesList(mainItem,date);
        //Add list of leave date people
        mainItem = addLeaveDatePeopleList(mainItem,date);

        return mainItem;
    }
    public static ItemMainView addLeaveDatePeopleList(ItemMainView mainItem, Date date){
        List<LeaveDateDB>  leaveDateList = LeaveDatesHelper.getAllLeaveDatePersonForDate(date) ;
        ArrayList<String> leaveDatePeople = new ArrayList<>();

        for(LeaveDateDB leaveDateDB : leaveDateList){
            leaveDatePeople.add(leaveDateDB.person.name);
        }
        mainItem.setLeaveDatePeople(leaveDatePeople);
        return mainItem;
    }
    public static ItemMainView addDutiesList(ItemMainView mainItem, Date date){
        List<DutyDateDB> mDutyDate = DutiesHelper.getAllDutiesForDate(date);
        ArrayList<Pair<String,String>> mDutiesListForDay = new ArrayList<>();

        for (DutyDateDB dutyDateDB : mDutyDate){
            Pair<String,String> tempPair = new Pair<>(dutyDateDB.dutyType.type,dutyDateDB.dutyDoer);
            mDutiesListForDay.add(tempPair);
        }
        mainItem.setDutyTypeDoerPairArray(mDutiesListForDay);
        return mainItem;
    }
    //Get shift names of 3 calls and set them in main item
    public static ItemMainView addCallsNamesToItem(ItemMainView mainItem, Date date) {

        ShiftDB shiftDB = ShiftHelper.getShiftForDate(date);
        mainItem.setFirstCallName(shiftDB.firstCall.name);
        mainItem.setSecondCallName(shiftDB.secondCall.name);

        if (MainActivity.IS_THERE_THIRD_CALL) {
            mainItem.setThirdCallName(shiftDB.thirdCall.name);
        }

        return mainItem;
    }
}
