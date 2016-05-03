package com.example.android.drroster.activities;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.example.android.drroster.MainActivity;
import com.example.android.drroster.R;
import com.example.android.drroster.UI.NavigationView;
import com.example.android.drroster.UI.SpaceBarColorHelper;
import com.example.android.drroster.databases.DutiesHelper;
import com.example.android.drroster.fragments.ChooseMonthFragment;
import com.example.android.drroster.fragments.DraggableListFragment;
import com.example.android.drroster.fragments.DutiesTypesListFragment;
import com.example.android.drroster.fragments.FinalReviewFragment;
import com.example.android.drroster.models.ADBean;
import com.example.android.drroster.models.PersonDB;
import com.example.android.drroster.models.ShiftFull;
import com.example.android.drroster.utils.DateUtils;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GenerateRosterActivity extends AppCompatActivity {

    private List<PersonDB> mPeopleOnlyArray;
    private ArrayList<String> dutiesTypesList;

    public static ArrayList<ShiftFull> mPeopleArray;
    public static ArrayList<ADBean> mADArray;
    public static String chosedMonth;
    //Firs int is month Second int is Year
    public static int[] monthYearNumbers = new int[2];
    public Context mContext;

    //Constants
    public static final int GENERATOR_FRAGMENTS_NUMBER = 7;
    public static final int FRAGMENT_CHOOSE_MONTH_INDEX = 0;
    public static final int FRAGMENT_PEOPLE_LIST_FIRST_CALL_INDEX = 1;
    public static final int FRAGMENT_PEOPLE_LIST_SECOND_CALL_INDEX = 2;
    public static final int FRAGMENT_PEOPLE_LIST_THIRD_CALL_INDEX = 3;
    public static final int FRAGMENT_DATEABLE_LIST_INDEX = 4;
    public static final int FRAGMENT_ADDITION_DUTIES_INDEX = 5;
    public static final int FRAGMENT_FINAL_REVIEW_INDEX = 6;
    public static final int RANDOM_ACTIVITY = 7;

    public static final String INTENT_EXTRA_PEOPLE_ARRAY = "mPeopleArray";
    public static final String INTENT_EXTRA_MONTH_YEAR_ARRAY = "mMonthAndYear";
    public static final String INTENT_EXTRA_AD_ARRAY = "ADArray";

    public static Date CURRENT_DATE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_roster);
        SpaceBarColorHelper.setDarkColor(this);
        mContext = this;

        CURRENT_DATE = getDateFromIntent();

        //Build peopleOnlyArray from DB data
        mPeopleOnlyArray = new Select()
                .from(PersonDB.class)
                .orderBy("Number ASC")
                .execute();

        //Build duty types list
        dutiesTypesList = DutiesHelper.getAllDutiesTypes();

        //Set local array
        mPeopleArray = new ArrayList<>();
        for (PersonDB personDB : mPeopleOnlyArray) {
            mPeopleArray.add(
                    new ShiftFull( //Long id, String name, Boolean isFirstCall, Boolean isSecondCall, Boolean isThereThirdCall,Boolean isLeavDate, List<Date> leaveDates
                            Long.valueOf(personDB.number), //Long id
                            personDB.name, // String name
                            false, // Boolean isFirstCall
                            false, // Boolean isSecondCall
                            false, // Boolean isThereThirdCall
                            false, // Boolean isLeaveDate
                            null));// List<Date> leaveDates
        }
        //Add Last person as - listView footer
        mPeopleArray.add(
                new ShiftFull(Long.valueOf(mPeopleOnlyArray.size()), "Add new friend",
                        false, false, false, false, null));


        //Build add array
        mADArray = new ArrayList<>();
        for (String dutyName : dutiesTypesList) {
            mADArray.add(new ADBean(dutyName));
        }

        //Set first layout
        // get fragment manager
        FragmentManager fm = getSupportFragmentManager();

        // replace
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_place_holder_generate_roster, new ChooseMonthFragment());
        ft.commit();

        //Set Listener states to change fragments
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        if (navigationView != null) {
            navigationView.setFragmentChangeListener(new NavigationView.IFragmentChangeListener() {
                @Override
                public void onFragmentChange(int index) {

                    changeUI(index);

                }
            });
        }

    }

    private void changeUI(int index) {

        //If last button
        if (index == RANDOM_ACTIVITY) {

            String[] tempADArray = GenerateRosterActivity.getCheckedADArray(GenerateRosterActivity.mADArray);

            Intent i = new Intent(mContext, RandomiseActivity.class);
            i.putExtra(INTENT_EXTRA_PEOPLE_ARRAY, Parcels.wrap(mPeopleArray));
            i.putExtra(INTENT_EXTRA_AD_ARRAY, tempADArray);
            i.putExtra(INTENT_EXTRA_MONTH_YEAR_ARRAY, monthYearNumbers);
            startActivity(i);
        }
        changeFragment(index);

    }

    private void changeFragment(int index) {
        // get fragment manager
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        switch (index) {
            case FRAGMENT_CHOOSE_MONTH_INDEX:
                ft.replace(R.id.fragment_place_holder_generate_roster,
                        new ChooseMonthFragment(), FRAGMENT_CHOOSE_MONTH_INDEX + "");
                break;

            case FRAGMENT_PEOPLE_LIST_FIRST_CALL_INDEX:
                ft.replace(R.id.fragment_place_holder_generate_roster,
                        new DraggableListFragment(), FRAGMENT_PEOPLE_LIST_FIRST_CALL_INDEX + "");
                break;

            case FRAGMENT_PEOPLE_LIST_SECOND_CALL_INDEX:
                ft.replace(R.id.fragment_place_holder_generate_roster,
                        new DraggableListFragment(), FRAGMENT_PEOPLE_LIST_SECOND_CALL_INDEX + "");
                break;
            case FRAGMENT_PEOPLE_LIST_THIRD_CALL_INDEX:
                ft.replace(R.id.fragment_place_holder_generate_roster,
                        new DraggableListFragment(), FRAGMENT_PEOPLE_LIST_THIRD_CALL_INDEX + "");
                break;
            case FRAGMENT_DATEABLE_LIST_INDEX:
                ft.replace(R.id.fragment_place_holder_generate_roster,
                        new DraggableListFragment(), FRAGMENT_DATEABLE_LIST_INDEX + "");
                break;
            case FRAGMENT_ADDITION_DUTIES_INDEX:
                ft.replace(R.id.fragment_place_holder_generate_roster,
                        new DutiesTypesListFragment(), FRAGMENT_DATEABLE_LIST_INDEX + "");
                break;
            case FRAGMENT_FINAL_REVIEW_INDEX:
                ft.replace(R.id.fragment_place_holder_generate_roster,
                        new FinalReviewFragment(), FRAGMENT_DATEABLE_LIST_INDEX + "");
                break;
        }
        // replace
        ft.commit();

    }


    private Date getDateFromIntent() {
        if (getIntent().getExtras() != null) {
            Long dateTime = getIntent().getExtras().getLong(MainActivity.CURRENT_MONTH_KEY);
            if (dateTime > 0) {
                return new Date(dateTime);
            }
        }
        return DateUtils.getFirstDayOfThisMonthDate();
    }

    public static String[] getCheckedADArray(ArrayList<ADBean> data) {

        ArrayList<String> temp = new ArrayList<>();
        for (int i1 = 0; i1 < data.size(); i1++) {
            if (data.get(i1).getIsChecked()) {
                temp.add(data.get(i1).getName());
            }
        }

        String[] ADArray = new String[temp.size()];
        for (int i2 = 0; i2 < temp.size(); i2++) {
            ADArray[i2] = temp.get(i2);
        }

        return ADArray;
    }

    public void setActionBarTitle(String title) {
        TextView menuTitle = (TextView) findViewById(R.id.toolbar_title_rostergen);
        menuTitle.setText(title);
    }

    private void goToMainActivity() {
        Intent i1 = new Intent(this, MainActivity.class);
        startActivity(i1);
    }

    public void exitGenerator(View view) {
        goToMainActivity();
    }
    //Automagically lose focus on any click outside editText - this way app don't crush
    // http://stackoverflow.com/questions/4828636/edittext-clear-focus-on-touch-outside
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent event) {
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            View v = getCurrentFocus();
//            if ( v instanceof EditText) {
//                Rect outRect = new Rect();
//                v.getGlobalVisibleRect(outRect);
//                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
//                    v.clearFocus();
//                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//                }
//            }
//        }
//        return super.dispatchTouchEvent( event );
//    }
}
