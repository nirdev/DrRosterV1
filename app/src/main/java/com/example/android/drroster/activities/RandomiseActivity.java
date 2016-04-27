package com.example.android.drroster.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.drroster.MainActivity;
import com.example.android.drroster.R;
import com.example.android.drroster.databases.PersonDBHelper;
import com.example.android.drroster.databases.ShiftHelper;
import com.example.android.drroster.fragments.RandomListFragment;
import com.example.android.drroster.models.ShiftFull;
import com.example.android.drroster.utils.DateUtils;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RandomiseActivity extends AppCompatActivity {

    //Constants
    public static final int RANDOM_FRAGMENT_NUMBER = 3;
    public static final int RANDOM_FRAGMENT_FIRST_CALL = 0;
    public static final int RANDOM_FRAGMENT_SECOND_CALL = 1;
    public static final int RANDOM_FRAGMENT_THIRD_CALL = 2;
    public static ArrayList<ArrayList<String>> shuffledTable;
    public static final String[] typesList = new String[] {
            "Type 1",
            "Type 2",
            "Type 3",
            "Type 4",
            "Type 5"
    };

    private int fragmentIndex = 0;
    Button nextButton;
    Button previousButton;
    public static int currentFragment;
    public static ArrayList<String> nameList;
    public static ArrayList<ShiftFull> mPeopleArray;
    public static ArrayList<String> mUnShuffledNames;
    public static Integer[] selectionOption = new Integer[3];
    public static String[] ADArray;
    public static int[] monthYearNumbers;

    ArrayList<ArrayList<String>> leaveDatesArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_randomis);

        //Initialize array with 3 items - ine for each fragment
        shuffledTable = new ArrayList<>(3);
        for (int x = 0;x < 3 ; x++) {
           shuffledTable.add(new ArrayList<String>());
        }
        //get unShuffled name list to show numbers and not items in fragment week days
        mUnShuffledNames = PersonDBHelper.getNameList();

        //List of organized names
        nameList = PersonDBHelper.getNameList();

        //Unparcelize the ShiftFull array from
        mPeopleArray = Parcels.unwrap(getIntent().getExtras().getParcelable(GenerateRosterActivity.INTENT_EXTRA_PEOPLE_ARRAY));
        monthYearNumbers = getIntent().getExtras().getIntArray(GenerateRosterActivity.INTENT_EXTRA_MONTH_YEAR_ARRAY);
        ADArray = getIntent().getExtras().getStringArray(GenerateRosterActivity.INTENT_EXTRA_AD_ARRAY);
        //Build leave date array for DB
        leaveDatesArray  = leaveDatesBuilder(mPeopleArray,monthYearNumbers);

        //Sets UI
        nextButton = (Button) findViewById(R.id.next_button_random_activity);
        previousButton = (Button) findViewById(R.id.previous_button_random_activity);
        setUI(fragmentIndex);

    }

    //Build list of pairs with only leave date people and their date arrays
    private ArrayList<ArrayList<String>> leaveDatesBuilder(ArrayList<ShiftFull> peopleArray, int[] monthYearNumbers) {
        ArrayList<ArrayList<String>> namesOnDatesArray = new ArrayList<>();
        int numOfDayInMonth = DateUtils.getNumberOfDayInMonth(monthYearNumbers[0],monthYearNumbers[1]);

        //initialize the array list with new null arrays
        for (int init = 0; init < numOfDayInMonth;init++){
            namesOnDatesArray.add(new ArrayList<String>());
        }

        //Iterate all FullShift Array
        for (ShiftFull shiftFullPerson : peopleArray){
            //if current checked person isOnLeaveDate
            if (shiftFullPerson.getIsLeavDate()){
                //Get the dates array
                List<Date> tempDates = shiftFullPerson.getLeaveDates();
                //Iterate the dates array
                for (Date date : tempDates){
                    //Get index of day (if it's the 28 day index = 27)
                    int dayIndex = DateUtils.getDayIndex(date);
                    //Array of string for that particular  index (index  = day of month)
                    ArrayList<String> oneDayNames;
                    //If on that day this loop already put names - add new name to previous array
                    if (namesOnDatesArray.get(dayIndex) != null){
                        //Take old names array for current day index
                        oneDayNames = namesOnDatesArray.get(dayIndex);
                        //Add new name for that particular name
                        oneDayNames.add(shiftFullPerson.getName());
                        //Set the new array back in the parent array ("namesOnDatesArray")
                    }
                    //If this is first name for this date index
                    else
                    {
                        //Initialize new array for that day index
                        oneDayNames = new ArrayList<>();
                        //Set person name
                        oneDayNames.add(shiftFullPerson.getName());
                    }
                }
            }
        }

        return namesOnDatesArray;
    }
    private void setUI(int index){
        setFragmentUI(index);
        setButtonUI(index);
    }

    private void setFragmentUI(int index) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        switch (index){
            case RANDOM_FRAGMENT_FIRST_CALL:
                currentFragment = RANDOM_FRAGMENT_FIRST_CALL;
                ft.replace(R.id.fragment_place_holder_random_activity,
                        new RandomListFragment(),RANDOM_FRAGMENT_FIRST_CALL+"");
                break;

            case RANDOM_FRAGMENT_SECOND_CALL:
                currentFragment = RANDOM_FRAGMENT_SECOND_CALL;
                ft.replace(R.id.fragment_place_holder_random_activity,
                        new RandomListFragment(),RANDOM_FRAGMENT_SECOND_CALL + "");
                break;

            case RANDOM_FRAGMENT_THIRD_CALL:
                currentFragment = RANDOM_FRAGMENT_THIRD_CALL;
                ft.replace(R.id.fragment_place_holder_random_activity,
                        new RandomListFragment(),RANDOM_FRAGMENT_THIRD_CALL + "");
        }
        ft.commit();
    }

    private void setButtonUI(int index) {
        //if first button
        if (index == 0){
            previousButton.setVisibility(View.INVISIBLE);
        }
        //if one before last button
        else if (index == RANDOM_FRAGMENT_NUMBER - 1){
            nextButton.setText(R.string.generate_random_acitvity_button);
        }
        //if last button
        else if (index == RANDOM_FRAGMENT_NUMBER){
            ShiftHelper.buildShiftTable(monthYearNumbers[0], monthYearNumbers[1], shuffledTable, ADArray, leaveDatesArray);

            Intent i1 = new Intent(this, MainActivity.class);
            startActivity(i1);
        }
        //normal status
        else {
            previousButton.setVisibility(View.VISIBLE);
            nextButton.setText(R.string.next_button_text);
        }
    }


    public void onNextButton(View view) {
        saveCurrentNames();
        fragmentIndex++;
        setUI(fragmentIndex);
    }

    public void onPreviousButton(View view) {
        saveCurrentNames();
        fragmentIndex--;
        setUI(fragmentIndex);
    }

    //Set in the index of current visible fragment the chosen list from the fragment instance
    private void saveCurrentNames() {
        shuffledTable.set(fragmentIndex, RandomListFragment.chosenRandomNameList);
    }


    public void setActionBarTitle(String title){
        TextView menuTitle = (TextView) findViewById(R.id.toolbar_title_rostergen);
        menuTitle.setText(title);
    }
}
