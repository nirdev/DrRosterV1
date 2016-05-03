package com.example.android.drroster.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.drroster.MainActivity;
import com.example.android.drroster.R;
import com.example.android.drroster.databases.DutiesHelper;
import com.example.android.drroster.models.ItemMainView;
import com.example.android.drroster.utils.DateUtils;
import com.example.android.drroster.utils.UIUtils;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SinglePersonDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SinglePersonDetailsFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_ITEM = "param1";
    //Data item
    private ItemMainView mItem;
    private Context mContext;
    //Views objects
    private ImageButton actionbarPreviousBtn;
    private TextView actionbarYearTitle;
    private TextView aactionbarDayName;
    private TextView firstCallTitle;
    private TextView firstCallName;
    private TextView secondtCallTitle;
    private TextView secondCallName;
    private TextView thirdCallTitle;
    private TextView thirdCallName;
    private TextView leaveDateTitle;
    private TextView leaveDateName;
    private LinearLayout dutiesLayout;
    //empty constructor
    public SinglePersonDetailsFragment() {
    }

    public static SinglePersonDetailsFragment newInstance(ItemMainView item) {
        SinglePersonDetailsFragment fragment = new SinglePersonDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_ITEM, Parcels.wrap(item));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity) getActivity()).setMonthNavVisibility(false);
        if (getArguments() != null) {
            mItem = Parcels.unwrap(getArguments().getParcelable(ARG_ITEM));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        ((MainActivity) getActivity()).setMonthNavVisibility(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_single_person_details, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getContext();
        inflateViews(view);
        attachDataToUI(view);
        attachListeners();
    }

    private void attachDataToUI(View view) {
        actionbarYearTitle.setText(getTitleDateUI(mItem.getDay()));
        aactionbarDayName.setText(getDayNameUI(mItem.getDay()));

        firstCallTitle.setText(mContext.getResources().getString(R.string.main_list_first_call_title));
        firstCallName.setText(mItem.getFirstCallName());

        secondtCallTitle.setText(mContext.getResources().getString(R.string.main_list_second_call_title));
        secondCallName.setText(mItem.getSecondCallName());

        thirdCallTitle.setText(mContext.getResources().getString(R.string.main_list_third_call_title));
        thirdCallName.setText(mItem.getThirdCallName());

        buildDutiesViews();

        leaveDateTitle.setText(mContext.getResources().getString(R.string.main_list_leave_title));
        leaveDateName.setText(UIUtils.getStringFromArray(mItem.getLeaveDatePeople()));

    }

    private void buildDutiesViews() {

        for (int i = 0;i < mItem.getDutyTypeDoerPairArray().size();i++){
            String dutyType = mItem.getDutyTypeDoerPairArray().get(i).first ;
            String dutyDoer = mItem.getDutyTypeDoerPairArray().get(i).second;

            //inflate the layout
            View inflated = getActivity().getLayoutInflater().inflate(R.layout.duty_pair_layout,null);

            //Set duty type text
            TextView text1 = (TextView) inflated.findViewById(R.id.text1);
            text1.setId(i);
            text1.setText(dutyType);

            //Set duty doer edt text with related hint
            EditText editText = (EditText) inflated.findViewById(R.id.edit_text1);
            String editTextHint = (dutyDoer == null || dutyDoer.isEmpty() || dutyDoer.equals("")) ? "" : dutyDoer;
            editText.setId(i);
            editText.setText(editTextHint);
            //Attach new inflated layout to view
            dutiesLayout.addView(inflated);

        }
    }

    private void goBackToMain(View view) {
        saveDataFromEditText(view);
        changeToMainFrag();
    }

    private void saveDataFromEditText(View view) {
        //inflate the layout
        dutiesLayout = (LinearLayout) getView().findViewById(R.id.person_details_duties_layout);
        ArrayList<Pair<String,String>> newDutiesList = findAllEditTexts(dutiesLayout);

        if (newDutiesList != null && newDutiesList.size() > 0){
            //Loop on duty and update
            for (int i = 0;i < newDutiesList.size() ; i++){
                Date currentDay = mItem.getDay();
                String dutyType = newDutiesList.get(i).first;
                String dutyDoer = newDutiesList.get(i).second;

                //Update db
                DutiesHelper.updateDutyDateDoer(currentDay,dutyType,dutyDoer);
            }
        }
    }

    private ArrayList<Pair<String, String>> findAllEditTexts(ViewGroup viewGroup) {

        ArrayList<Pair<String,String>> newDutiesList = new ArrayList<>();
        int count = viewGroup.getChildCount();
        for (int i = 0; i < count; i++) {
            ViewGroup linearLayoutPair = (ViewGroup) viewGroup.getChildAt(i);
            TextView dutyType = (TextView) linearLayoutPair.getChildAt(0);
            EditText dutyDoer = (EditText) linearLayoutPair.getChildAt(1);
            if (dutyDoer != null && dutyDoer.getText() != null && dutyDoer.getText().length() > 0){
                String dutyDoerString = dutyDoer.getText().toString(); //Cast string from edit text
                Pair newDuty = new Pair(dutyType.getText(),dutyDoerString);
                newDutiesList.add(newDuty);
            }
        }
        return newDutiesList;
    }

    private void changeToMainFrag() {

        MainMonthListFragment mainFrag= new MainMonthListFragment();

        //Clear back stack
        FragmentManager fm = getActivity().getSupportFragmentManager();
        for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }

        //Replace back to main list view
        this.getFragmentManager().beginTransaction()
                .replace(R.id.main_activity_place_holder, mainFrag)
                .commit();
    }
    private void attachListeners() {
        actionbarPreviousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackToMain(v);
            }
        });
    }
    private void inflateViews(View view) {
        actionbarPreviousBtn = (ImageButton) view.findViewById(R.id.detailed_person_frg_previous_button);
        actionbarYearTitle = (TextView) view.findViewById(R.id.detailed_person_frg_title_month_year_day_num);
        aactionbarDayName = (TextView) view.findViewById(R.id.detailed_person_frg_day_name);
        firstCallTitle = (TextView) view.findViewById(R.id.person_details_first_call_title);
        firstCallName = (TextView) view.findViewById(R.id.person_details_first_call_name);
        secondtCallTitle = (TextView) view.findViewById(R.id.person_details_second_call_title);
        secondCallName = (TextView) view.findViewById(R.id.person_details_second_call_name);
        thirdCallTitle = (TextView) view.findViewById(R.id.person_details_third_call_title);
        thirdCallName = (TextView) view.findViewById(R.id.person_details_third_call_name);
        leaveDateTitle = (TextView) view.findViewById(R.id.person_details_leave_title);
        leaveDateName = (TextView) view.findViewById(R.id.person_details_leave_name);
        dutiesLayout = (LinearLayout) view.findViewById(R.id.person_details_duties_layout);
    }

    private String getDayNameUI(Date day) {
        String dayName = UIUtils.getDayName(day, Calendar.LONG);
        return dayName;
    }
    private String getTitleDateUI(Date day) {
        String titleDateString =
                DateUtils.getDayFromDate(day) + " " +
                UIUtils.getmonthName(day,Calendar.SHORT) + " " +
                DateUtils.getYearFromDate(day);

        return titleDateString;
    }
}
