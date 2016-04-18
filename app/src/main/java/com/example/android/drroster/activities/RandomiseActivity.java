package com.example.android.drroster.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.android.drroster.R;
import com.example.android.drroster.fragments.RandomListFragment;
import com.example.android.drroster.models.Person;

import org.parceler.Parcels;

import java.util.ArrayList;

public class RandomiseActivity extends AppCompatActivity {

    //Constants
    public static final int RANDOM_FRAGMENT_NUMBER = 3;
    public static final int RANDOM_FRAGMENT_FIRST_CALL = 0;
    public static final int RANDOM_FRAGMENT_SECOND_CALL = 1;
    public static final int RANDOM_FRAGMENT_THIRD_CALL = 2;
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
    public static ArrayList<Person> mPeopleArray;
    public static Integer[] selectionOption = new Integer[3];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_randomis);
        mPeopleArray = Parcels.unwrap(getIntent().getExtras().getParcelable(GenerateRosterActivity.INTENT_EXTRA_PEOPLE_ARRAY));
        //Sets UI
        nextButton = (Button) findViewById(R.id.next_button_random_activity);
        previousButton = (Button) findViewById(R.id.previous_button_random_activity);
        setUI(fragmentIndex);

    }
    private void setUI(int index){
        setMenuUI(index);
        setFragmentUI(index);
        setButtonUI(index);
    }

    private void setFragmentUI(int index) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        switch (index){
            case RANDOM_FRAGMENT_FIRST_CALL:
                ft.replace(R.id.fragment_place_holder_random_activity,
                        new RandomListFragment(),RANDOM_FRAGMENT_FIRST_CALL+"");
                break;

            case RANDOM_FRAGMENT_SECOND_CALL:
                ft.replace(R.id.fragment_place_holder_random_activity,
                        new RandomListFragment(),RANDOM_FRAGMENT_SECOND_CALL + "");
                break;

            case RANDOM_FRAGMENT_THIRD_CALL:
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
        //if last button
        else if (index == RANDOM_FRAGMENT_NUMBER){
            nextButton.setText(R.string.generate_random_acitvity_button);
        }
        //normal status
        else {
            previousButton.setVisibility(View.VISIBLE);
            nextButton.setText(R.string.next_button_text);
        }
    }

    private void setMenuUI(int index) {
    }

    public void onNextButton(View view) {
        fragmentIndex++;
        setUI(fragmentIndex);
    }

    public void onPreviousButton(View view) {
        fragmentIndex--;
        setUI(fragmentIndex);
    }
}
