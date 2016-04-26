package com.example.android.drroster.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.drroster.R;
import com.example.android.drroster.Signin.SigninConstants;
import com.example.android.drroster.activities.GenerateRosterActivity;
import com.example.android.drroster.utils.DateUtils;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FinalReviewFragment extends Fragment {

    View view;

    //Data models to set in UI
    String userName;
    String month;
    ArrayList<String> firstCallNames;
    ArrayList<String> secondCallNames;
    ArrayList<String> thirdCallNames;
    ArrayList<String> leaveCallNames;
    ArrayList<String> additionalDutiesNames;

    //Views
    TextView depratmentTextView;
    TextView monthTextView;
    LinearLayout firstCallListView;
    LinearLayout secondtCallListView;
    LinearLayout thirdCallListView;
    LinearLayout leaveCallListView;
    LinearLayout additionalDutiesListView;

    public FinalReviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_final_review, container, false);

        //Set menu title
        ((GenerateRosterActivity) getActivity()).setActionBarTitle(getString(R.string.genros_final_review_menu_title));

        inflateViews();

        if(GenerateRosterActivity.mPeopleArray != null){
            buildLocalData();
            setUI();
        }

        return view;
    }

    private void inflateViews() {
        depratmentTextView = (TextView) view.findViewById(R.id.title_final_review);
        monthTextView = (TextView) view.findViewById(R.id.month_chosen_one_final_review);
        firstCallListView = (LinearLayout) view.findViewById(R.id.first_call_listView_final_review);
        secondtCallListView = (LinearLayout) view.findViewById(R.id.second_call_listView_final_review);
        thirdCallListView = (LinearLayout) view.findViewById(R.id.third_call_listView_final_review);
        leaveCallListView = (LinearLayout) view.findViewById(R.id.date_call_listView_final_review);
        additionalDutiesListView = (LinearLayout) view.findViewById(R.id.additional_duties_listView_final_review);
    }

    private void buildLocalData() {
        userName = userNameBuilder();
        month = GenerateRosterActivity.chosedMonth;
        firstCallNames = firstCallNamesBuilder();
        secondCallNames = secondCallNamesBuilder();
        thirdCallNames = thirdCallNamesBuilder();
        leaveCallNames = leaveDatesNamesAndDatesBuilder();
        additionalDutiesNames = additionalDutiesNamesBuilder();

    }

    private void setUI() {
        depratmentTextView.setText(userName);
        monthTextView.setText(month);
        buildLinearLayoutList(firstCallListView, firstCallNames);
        buildLinearLayoutList(secondtCallListView, secondCallNames);
        buildLinearLayoutList(thirdCallListView, thirdCallNames);
        buildLinearLayoutList(leaveCallListView, leaveCallNames);
        buildLinearLayoutList(additionalDutiesListView, additionalDutiesNames);

    }
    private void buildLinearLayoutList(LinearLayout layout, ArrayList<String> data){

        for (int i = 0 ; i < data.size() ; i++) {

            LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            TextView tv = new TextView(getActivity());
            tv.setLayoutParams(lparams);
            tv.setText(data.get(i));
            tv.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
            layout.addView(tv);
        }

    }

    private String userNameBuilder() {
        //Retrieve string from sharedPreference
        SharedPreferences mSettings = getActivity().getSharedPreferences(SigninConstants.SHAREDPREF_FILE_KEY, SigninConstants.SHAREDPREF_MODE_KEY);
        String departmentName = mSettings.getString(SigninConstants.SHAREDPREF_DEPARTMENT_KEY, null);
        return departmentName;
    }

    private ArrayList<String> firstCallNamesBuilder() {
        ArrayList<String> newArray = new ArrayList<>();

        for (int i = 0; i < GenerateRosterActivity.mPeopleArray.size() ; i++ ) {
            if (GenerateRosterActivity.mPeopleArray.get(i).getIsFirstCall() != null
                    && GenerateRosterActivity.mPeopleArray.get(i).getIsFirstCall()) {

                newArray.add(GenerateRosterActivity.mPeopleArray.get(i).getName());
            }
        }
        return newArray;
    }

    private ArrayList<String> secondCallNamesBuilder() {
        ArrayList<String> newArray = new ArrayList<>();

        for (int i = 0; i < GenerateRosterActivity.mPeopleArray.size() ; i++ ) {
            if (GenerateRosterActivity.mPeopleArray.get(i).getIsSecondCall() != null
                    && GenerateRosterActivity.mPeopleArray.get(i).getIsSecondCall()) {

                newArray.add(GenerateRosterActivity.mPeopleArray.get(i).getName());
            }
        }
        return newArray;
    }

    private ArrayList<String> thirdCallNamesBuilder() {
        ArrayList<String> newArray = new ArrayList<>();

        for (int i = 0; i < GenerateRosterActivity.mPeopleArray.size() ; i++ ) {
            if (GenerateRosterActivity.mPeopleArray.get(i).getIsThirdCall() != null
                    && GenerateRosterActivity.mPeopleArray.get(i).getIsThirdCall()) {

                newArray.add(GenerateRosterActivity.mPeopleArray.get(i).getName());
            }
        }
        return newArray;
    }

    private ArrayList<String> leaveDatesNamesAndDatesBuilder() {
        ArrayList<String> newArray = new ArrayList<>();

        for (int i = 0; i < GenerateRosterActivity.mPeopleArray.size() ; i++ ) {
            if ((GenerateRosterActivity.mPeopleArray.get(i).getLeaveDates() != null)
                    && GenerateRosterActivity.mPeopleArray.get(i).getIsLeavDate()) {

                String dateUI;
                String dateUIBuilder[] = DateUtils.getDatesUI(
                        GenerateRosterActivity.mPeopleArray.get(i).getLeaveDates());

                dateUI = GenerateRosterActivity.mPeopleArray.get(i).getName() + " " //person name
                        + dateUIBuilder[0]  //days
                        + dateUIBuilder[1] ; //months

                newArray.add(dateUI);
            }
        }
        return newArray;
    }

    private ArrayList<String> additionalDutiesNamesBuilder() {
        ArrayList<String> newArray = new ArrayList<>();

        for (int i = 0; i < GenerateRosterActivity.mADArray.size() ; i++ ) {
            if (GenerateRosterActivity.mADArray.get(i).getIsChecked() != null
                    && GenerateRosterActivity.mADArray.get(i).getIsChecked()) {

                newArray.add(GenerateRosterActivity.mADArray.get(i).getName());
            }
        }
        return newArray;
    }



}
