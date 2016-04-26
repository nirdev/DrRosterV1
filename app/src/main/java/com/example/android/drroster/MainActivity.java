package com.example.android.drroster;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.activeandroid.query.Select;
import com.example.android.drroster.Signin.SigninActivity;
import com.example.android.drroster.Signin.SigninConstants;
import com.example.android.drroster.activities.GenerateRosterActivity;
import com.example.android.drroster.databases.ShiftHelper;
import com.example.android.drroster.models.AdditionalDutyDB;
import com.example.android.drroster.models.PersonDB;
import com.example.android.drroster.utils.DateUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Go to choose month activity //TODO:Delete after finish build sign in
        Intent i1 = new Intent(this,GenerateRosterActivity.class );
        startActivity(i1);

        //Retrieve string from sharedPreference
        SharedPreferences mSettings = getSharedPreferences(SigninConstants.SHAREDPREF_FILE_KEY, SigninConstants.SHAREDPREF_MODE_KEY);
        String departmentName = mSettings.getString(SigninConstants.SHAREDPREF_DEPARTMENT_KEY, null);

        //check if this is first signin
        if ( departmentName == null || departmentName.isEmpty() ){

            //if true Got to signinActivity
            Intent i = new Intent(this,SigninActivity.class);
            startActivity(i);
        }
        // if false start app

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        //get current date time with Date()
        Date date = new Date();
        System.out.println(dateFormat.format(date));
//
//        date = addOneDay(date);
//        System.out.println(dateFormat.format(date));
//
//        date = addOneDay(date);
//        System.out.println(dateFormat.format(date));
//
        date = DateUtils.getStartOfDay(date);
//
//        PersonDB personDB = new PersonDB("nir2");
//        personDB.save();
//
        List<PersonDB> people = getAllPeople();

        for (PersonDB personDB1 : people){
            System.out.print("peopleList:" + personDB1.name);
            System.out.println(" id: " + personDB1.getId());
        }

//        int temp = PersonDBHelper.getIdFromString("nir2");
//        Log.wtf("here", " " + temp);

//        AdditionalDutyDB dutyDB = new AdditionalDutyDB(date,"outreach","or");
//        dutyDB.save();

        List<AdditionalDutyDB> dutyDBList = ShiftHelper.getAdditionalDutiesForDateList(date);

        for (AdditionalDutyDB dutyDBModel : dutyDBList){
            System.out.print("additional duty list:" + dutyDBModel.date);
            System.out.print(" " + dutyDBModel.type);
            System.out.println(" id: " + dutyDBModel.getId());
        }



        //ShiftDB currentDayShift  = new ShiftDB(date,personDB,personDB,personDB);


    }
    public static List<AdditionalDutyDB> getAllADforDate(PersonDB personDB) {
        // This is how you execute a query
        return new Select()
                .from(AdditionalDutyDB.class)
                .where("person = ?", personDB.getId())
                .execute();
    }

    public static List<PersonDB> getAllPeople() {
        // This is how you execute a query
        return new Select()
                .from(PersonDB.class)
                .orderBy("Name ASC")
                .execute();
    }

    public static Date addOneDay(Date date){
        long timeadj = 24*60*60*1000; // one day in millisecond
        return new Date(date.getTime ()+ timeadj);
    }
}

