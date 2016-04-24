package com.example.android.drroster.database;

import com.activeandroid.query.Select;
import com.example.android.drroster.models.PersonModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nir on 4/24/2016.
 */
public class DataBaseUtils {

    public static ArrayList<String> getNameList(){
        ArrayList<String> mNames = new ArrayList<>();
        List<PersonModel> mPerson = new ArrayList<>();

        mPerson = new Select()
                .from(PersonModel.class)
                .orderBy("Number ASC")
                .execute();
        for (PersonModel person : mPerson){
            mNames.add(person.name);
        }

        return mNames;
    }
}
