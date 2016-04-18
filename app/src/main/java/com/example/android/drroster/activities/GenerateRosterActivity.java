package com.example.android.drroster.activities;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.activeandroid.query.Select;
import com.example.android.drroster.R;
import com.example.android.drroster.UI.NavigationView;
import com.example.android.drroster.database.AdditionalDutyModel;
import com.example.android.drroster.database.PersonModel;
import com.example.android.drroster.fragments.AdditionalDutiesListFragment;
import com.example.android.drroster.fragments.ChooseMonthFragment;
import com.example.android.drroster.fragments.DraggableListFragment;
import com.example.android.drroster.fragments.FinalReviewFragment;
import com.example.android.drroster.models.ADBean;
import com.example.android.drroster.models.Person;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class GenerateRosterActivity extends AppCompatActivity {

    private List<PersonModel> mPeopleOnlyArray;
    private List<AdditionalDutyModel> dutiesOnlyArray;

    public static ArrayList<Person> mPeopleArray;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_roster);
        mContext = this;
        //TODO: take from database - last item is dummy for + add new
//
//        PersonModel tempPerson = new PersonModel();
//        tempPerson.name = "nir";
//        tempPerson.number = 1;
//        tempPerson.save();
//
//        tempPerson = new PersonModel();
//        tempPerson.name = "nir1";
//        tempPerson.number = 2;
//        tempPerson.save();
//
//        tempPerson = new PersonModel();
//        tempPerson.name = "nir3";
//        tempPerson.number = 3;
//        tempPerson.save();


//
//        SimpleDateFormat mothFormat = new SimpleDateFormat("MMM yyyy dd");
//        String temp = mothFormat.format(myDate);
//        Log.wtf("here", "--------------------------------------------" + temp);

        //int check = DateUtils.getNumberOfDayInMonth()


        int nir;
        //Build peopleOnlyArray from DB data
        mPeopleOnlyArray = new Select()
                .from(PersonModel.class)
                .orderBy("Number ASC")
                .execute();

        //Build dutiesOnlyArray
        dutiesOnlyArray = new Select()
                .from(AdditionalDutyModel.class)
                .orderBy("Number ASC")
                .execute();

        //Set local array
        mPeopleArray = new ArrayList<>();
        for (PersonModel personModel : mPeopleOnlyArray){
            mPeopleArray.add(
                    new Person( //Long id, String name, Boolean isFirstCall, Boolean isSecondCall, Boolean isThirdCall,Boolean isLeavDate, List<Date> leaveDates
                            Long.valueOf(personModel.number), //Long id
                            personModel.name, // String name
                            false, // Boolean isFirstCall
                            false, // Boolean isSecondCall
                            false, // Boolean isThirdCall
                            false, // Boolean isLeaveDate
                            null));// List<Date> leaveDates
        }
        //Add Last person as - listView footer
        mPeopleArray.add(
                new Person(Long.valueOf(mPeopleOnlyArray.size()),"Add new friend",
                        false,false,false,false,null));


        mADArray = new ArrayList<>();
        for (AdditionalDutyModel additionalDutyModel : dutiesOnlyArray) {
            mADArray.add(new ADBean(additionalDutyModel.name));
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
                    // get fragment manager
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    switch (index){

                        case FRAGMENT_CHOOSE_MONTH_INDEX:
                            ft.replace(R.id.fragment_place_holder_generate_roster,
                                    new ChooseMonthFragment(),FRAGMENT_CHOOSE_MONTH_INDEX+"");
                            break;

                        case FRAGMENT_PEOPLE_LIST_FIRST_CALL_INDEX:
                            ft.replace(R.id.fragment_place_holder_generate_roster,
                                    new DraggableListFragment(),FRAGMENT_PEOPLE_LIST_FIRST_CALL_INDEX + "");
                            break;

                        case FRAGMENT_PEOPLE_LIST_SECOND_CALL_INDEX:
                            ft.replace(R.id.fragment_place_holder_generate_roster,
                                    new DraggableListFragment(),FRAGMENT_PEOPLE_LIST_SECOND_CALL_INDEX + "");
                            break;
                        case FRAGMENT_PEOPLE_LIST_THIRD_CALL_INDEX:
                            ft.replace(R.id.fragment_place_holder_generate_roster,
                                    new DraggableListFragment(),FRAGMENT_PEOPLE_LIST_THIRD_CALL_INDEX + "");
                            break;
                        case FRAGMENT_DATEABLE_LIST_INDEX:
                            ft.replace(R.id.fragment_place_holder_generate_roster,
                                    new DraggableListFragment(),FRAGMENT_DATEABLE_LIST_INDEX + "");
                            break;
                        case FRAGMENT_ADDITION_DUTIES_INDEX:
                            ft.replace(R.id.fragment_place_holder_generate_roster,
                                    new AdditionalDutiesListFragment(),FRAGMENT_DATEABLE_LIST_INDEX + "");
                            break;
                        case FRAGMENT_FINAL_REVIEW_INDEX:
                            ft.replace(R.id.fragment_place_holder_generate_roster,
                                    new FinalReviewFragment(),FRAGMENT_DATEABLE_LIST_INDEX + "");
                            break;
                    }
                    // replace
                    ft.commit();

                    //If last button
                    if (index == RANDOM_ACTIVITY){
                        Intent i = new Intent(mContext,RandomiseActivity.class);
                        i.putExtra(INTENT_EXTRA_PEOPLE_ARRAY, Parcels.wrap(mPeopleArray));
                        startActivity(i);
                    }
                }
            });
        }

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
