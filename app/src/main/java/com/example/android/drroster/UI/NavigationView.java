package com.example.android.drroster.UI;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.android.drroster.R;
import com.example.android.drroster.activities.GenerateRosterActivity;
import com.example.android.drroster.models.ShiftFull;
import com.example.android.drroster.utils.UIUtils;

/**
 * Created by Nir on 4/1/2016.bububu
 */
public class NavigationView extends RelativeLayout {

    Context context;

    //Navigation buttons
    private Button mPreviousButton;
    private Button mNextButton;

    //stepBar
    private StepBarView mStepBarView;
    //Index of stepBar & fragment
    private int mSelectedIndex = -1;

    //Constructor @pram attrs is android default attrs
    public NavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);
    }

    //Interface to set call back to the activity on fragment change
    public interface IFragmentChangeListener {
        public void onFragmentChange(int index);
    }

    private IFragmentChangeListener fragmentChangeListener;

    public void setFragmentChangeListener(IFragmentChangeListener listener){
        this.fragmentChangeListener = listener;
    }

    private void initializeViews(Context context) {
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_navigation, this);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();


        //find step bar in view
        mStepBarView  = (StepBarView) findViewById(R.id.step_bar_view);


        // When the next button is pressed, select the next item in the list.
        mNextButton = (Button) this.findViewById(R.id.navigationView_next_button);
        mNextButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if ( mSelectedIndex < GenerateRosterActivity.GENERATOR_FRAGMENTS_NUMBER &&
                        moveNextChecker(mSelectedIndex)) { //Check if user choose right things
                    int newSelectedIndex = mSelectedIndex + 1;
                    setSelectedIndex(newSelectedIndex);
                }
            }
        });

        mPreviousButton = (Button) this.findViewById(R.id.navigationView_previous_button);
        mPreviousButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (mSelectedIndex > 0) {
                    int newSelectedIndex = mSelectedIndex - 1;
                    setSelectedIndex(newSelectedIndex);
                }
            }
        });



        // Select the first value by default.
        setSelectedIndex(0);
    }

    /**
     * Sets the selected index of the spinner.
     * @param index the index of the value to select.
     */
    public void setSelectedIndex(int index) {

        //if month haven't been chosen don't do anything beside toast
        if ((index == 1) && (GenerateRosterActivity.monthYearNumbers[1] == 0)){
            Toast.makeText(getContext(), "Please choose month", Toast.LENGTH_LONG).show();
        }
        else {
            if (fragmentChangeListener != null) {
                fragmentChangeListener.onFragmentChange(index);
            }

            // Set the current index and display the value.
            mSelectedIndex = index;

            //Customize stepBar
            mStepBarView.setBoldDot(index);

            // If the first value is shown, hide the previous button.
            if (mSelectedIndex == 0) {
                mPreviousButton.setVisibility(INVISIBLE);
            } else {
                mPreviousButton.setVisibility(VISIBLE);
            }
            // If the last value is shown,show "generate" button.
            if (mSelectedIndex == GenerateRosterActivity.GENERATOR_FRAGMENTS_NUMBER - 1) {
                mNextButton.setText(R.string.generate_button_navigation_view);
            } else {
                mNextButton.setText(R.string.next_button_text);
                mNextButton.setVisibility(VISIBLE);
            }
        }
    }

    private Boolean moveNextChecker(int index){
        Boolean moveNext = false;
        String toastReason = null;
        switch (index ){ //index -1 because is go from current to next fragment
            case GenerateRosterActivity.FRAGMENT_CHOOSE_MONTH_INDEX :
                moveNext = (GenerateRosterActivity.monthYearNumbers[0] != 0);
                toastReason = "Please choose month";
                break;

            case GenerateRosterActivity.FRAGMENT_PEOPLE_LIST_FIRST_CALL_INDEX:
                moveNext = (checkedPeople(index) > 2);
                toastReason = context.getString(R.string.toast_choose_more_people);
                break;

            case GenerateRosterActivity.FRAGMENT_PEOPLE_LIST_SECOND_CALL_INDEX:
                moveNext = (checkedPeople(index) > 2);
                toastReason = context.getString(R.string.toast_choose_more_people);
                break;
            case GenerateRosterActivity.FRAGMENT_PEOPLE_LIST_THIRD_CALL_INDEX:
                moveNext = (checkedPeople(index) > 2);
                toastReason = context.getString(R.string.toast_choose_more_people);
                break;
            case GenerateRosterActivity.FRAGMENT_DATEABLE_LIST_INDEX:
                moveNext = true;
            case GenerateRosterActivity.FRAGMENT_ADDITION_DUTIES_INDEX:
                moveNext = true;
                break;
            case GenerateRosterActivity.FRAGMENT_FINAL_REVIEW_INDEX:
                moveNext = true;
                break;
        }
        if (!moveNext){
            UIUtils.toast(toastReason, context);
        }
        return moveNext;
    }
    private int checkedPeople(int index){
        int count = 0;
        if (index == GenerateRosterActivity.FRAGMENT_PEOPLE_LIST_FIRST_CALL_INDEX){
            for (ShiftFull shift : GenerateRosterActivity.mPeopleArray){
                if (shift.getIsFirstCall()){
                    count++;
                }
            }
        }
        else if (index == GenerateRosterActivity.FRAGMENT_PEOPLE_LIST_SECOND_CALL_INDEX){
            for (ShiftFull shift : GenerateRosterActivity.mPeopleArray){
                if (shift.getIsSecondCall()){
                    count++;
                }
            }
        }
        else if (index == GenerateRosterActivity.FRAGMENT_PEOPLE_LIST_THIRD_CALL_INDEX){
            for (ShiftFull shift : GenerateRosterActivity.mPeopleArray){
                if (shift.getIsThirdCall()){
                    count++;
                }
            }
        }
        return count;
    }
}

