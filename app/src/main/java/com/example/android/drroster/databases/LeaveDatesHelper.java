package com.example.android.drroster.databases;

import com.activeandroid.query.Select;
import com.example.android.drroster.models.LeaveDateDB;

import java.util.Date;
import java.util.List;

/**
 * Created by Nir on 4/28/2016.
 */
public class LeaveDatesHelper {

    public static List<LeaveDateDB> getAllLeaveDatePersonForDate(Date date){
        List<LeaveDateDB> leaveDates = new Select()
                .from(LeaveDateDB.class)
                .where("Date = ?",date.getTime())
                .execute();

        return leaveDates;
    }
}
