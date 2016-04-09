package com.example.android.drroster.activities;


import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.android.drroster.R;
import com.example.android.drroster.UI.NavigationView;
import com.example.android.drroster.fragments.ChooseMonthFragment;
import com.example.android.drroster.fragments.DraggableListFragment;
import com.example.android.drroster.models.Person;

import java.util.ArrayList;

public class GenerateRosterActivity extends AppCompatActivity {

    public static ArrayList<Person> mPeopleArray;

    //Constants
    public static final int GENERATOR_FRAGMENTS_NUMBER = 7;
    public static final int FRAGMENT_CHOOSE_MONTH_INDEX = 0;
    public static final int FRAGMENT_PEOPLE_LIST_FIRST_CALL_INDEX = 1;
    public static final int FRAGMENT_PEOPLE_LIST_SECOND_CALL_INDEX = 2;
    public static final int FRAGMENT_PEOPLE_LIST_THIRD_CALL_INDEX = 3;
    public static final int FRAGMENT_DATEABLE_LIST_INDEX = 4;
    public static final int FRAGMENT_ADDITION_DUTIES_INDEX = 5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_roster);

        //TODO: take from database
        mPeopleArray = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            mPeopleArray.add(new Person(Long.valueOf(i),"item" +i,false,false,false,false,null));
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
                    }
                    // replace
                    ft.commit();
                }
            });
        }

    }

    //Automagically lose focus on any click outside editText - this way app don't crush
    // http://stackoverflow.com/questions/4828636/edittext-clear-focus-on-touch-outside
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }

}
