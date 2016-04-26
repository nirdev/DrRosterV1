package com.example.android.drroster.databases;

import com.activeandroid.query.Select;
import com.example.android.drroster.models.PersonDB;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nir on 4/24/2016.
 */
public class PersonDBHelper {

    public static ArrayList<String> getNameList(){
        ArrayList<String> mNames = new ArrayList<>();
        List<PersonDB> mPerson = new ArrayList<>();

        mPerson = new Select()
                .from(PersonDB.class)
                .orderBy("Number ASC")
                .execute();
        for (PersonDB person : mPerson){
            mNames.add(person.name);
        }

        return mNames;
    }

    public static int nameToNumberConverter(String shuffledName,ArrayList<String> unshuffledNameList){
        return unshuffledNameList.indexOf(shuffledName);
    }
    public static int getIdFromString(String name){
        PersonDB mPerson = new Select()
                .from(PersonDB.class)
                .executeSingle();

        return  safeLongToInt(mPerson.getId());
    }

    public static PersonDB getPersonFromString(String name){
        PersonDB mPerson = new Select()
                .from(PersonDB.class)
                .executeSingle();

        return  mPerson;
    }

    public static int safeLongToInt(long l) {
        if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
            throw new IllegalArgumentException
                    (l + " cannot be cast to int without changing its value.");
        }
        return (int) l;
    }
}