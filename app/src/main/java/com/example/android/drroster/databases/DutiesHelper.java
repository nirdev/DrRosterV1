package com.example.android.drroster.databases;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.example.android.drroster.models.DutyDateDB;
import com.example.android.drroster.models.DutyTypeDB;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Nir on 4/27/2016.
 */
public class DutiesHelper {

    public static List<DutyDateDB> getAllDutiesForDate(Date date){

        List<DutyDateDB> mDutyDate;
        mDutyDate = new Select()
                .from(DutyDateDB.class)
                .where("Date = ?",date.getTime())
                .execute();

        return mDutyDate;
    }
    public static ArrayList<String> getAllDutiesTypes(){
        ArrayList<String> mNames = new ArrayList<>();
        List<DutyTypeDB> mDutyType;

        mDutyType = new Select()
                .from(DutyTypeDB.class)
                .execute();
        for (DutyTypeDB dutyType : mDutyType){
            mNames.add(dutyType.type);
        }
        return mNames;
    }

    public static DutyTypeDB getDutyTypeFromString(String name){
        DutyTypeDB dutyType = new Select()
                .from(DutyTypeDB.class)
                .where("Type = ?",name)
                .executeSingle();

        return  dutyType;
    }

    public static int getIdFromString(String name){
        DutyTypeDB dutyType = new Select()
                .from(DutyTypeDB.class)
                .where("Type = ?", name)
                .executeSingle();

        return  safeLongToInt(dutyType.getId());
    }

    public static int safeLongToInt(long l) {
        if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
            throw new IllegalArgumentException
                    (l + " cannot be cast to int without changing its value.");
        }
        return (int) l;
    }

    public static void addDutyTypeFromString(String name){
        DutyTypeDB dutyType = new DutyTypeDB(name);
        dutyType.save();
    }
    public static void removeDutyTypeFromString(String name){
        int id = getIdFromString(name);
        DutyTypeDB.delete(DutyTypeDB.class, id);
    }
    public static void updateDutyTypeFromString(String newName,String oldName){
        DutyTypeDB dutyType = getDutyTypeFromString(oldName);
        dutyType.type = newName;
        dutyType.save();
    }

    public static DutyDateDB getDutyDate(DutyTypeDB dutyType,Date date){
        DutyDateDB dutyDateDB = new Select()
                .from(DutyDateDB.class)
                .where("DutyType = ?", dutyType.getId())
                .where("Date = ?", date.getTime())
                .executeSingle();

        return  dutyDateDB;
    }
    public static List<DutyDateDB> getAllDutyForDate(Date date){

        List<DutyDateDB> dutyDateDBList = new Select()
                .from(DutyDateDB.class)
                .where("Date = ?", date.getTime())
                .execute();

        return  dutyDateDBList;
    }
    public static void updateDutyDateDoer(Date date,String dutyType,String dutyDoer){
        DutyTypeDB dutyTypeDB = getDutyTypeFromString(dutyType);

        DutyDateDB dutyDateDB = getDutyDate(dutyTypeDB, date);
        dutyDateDB.dutyDoer = dutyDoer;
        dutyDateDB.save();
    }
    public static void removeDutyForDate(Date date){
         new Delete()
                .from(DutyDateDB.class)
                .where("Date = ?", date.getTime())
                .execute();
    }
}
