package com.example.android.drroster.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.drroster.R;
import com.example.android.drroster.activities.GenerateRosterActivity;
import com.example.android.drroster.dialogs.ChooseMonthDialog;
import com.example.android.drroster.utils.DateUtils;
import com.example.android.drroster.utils.UIUtils;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;

public class ChooseMonthFragment extends Fragment {

    ChooseMonthDialog dialog;
    Date currentDate = GenerateRosterActivity.CURRENT_DATE;
    Button chooseMonthBtn;
    View view;
    TextView choosedMonthTextview;
    //empty constructor
    public ChooseMonthFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_choos_month,
                container, false);

        //Set menu title
        ((GenerateRosterActivity) getActivity()).setActionBarTitle(getActivity().getString(R.string.genros_month_menu_title));

        //initialize dialog
        dialog = new ChooseMonthDialog(getActivity());
        // Listener for dialog result
        dialog.setDialogResult(new ChooseMonthDialog.OnMyDialogResult() {
            public void finish(int month,int year) {


                choosedMonthTextview = (TextView) view.findViewById(R.id.choosed_month_textview);
                if (choosedMonthTextview != null) {
                    //Set chosen month text view
                    choosedMonthTextview.setText(
                            //convert month number to name
                            new DateFormatSymbols().getMonths()[month-1] + " " + year);
                    GenerateRosterActivity.chosedMonth = new DateFormatSymbols().getMonths()[month-1] + " " + year;
                    GenerateRosterActivity.monthYearNumbers[0] = month;
                    GenerateRosterActivity.monthYearNumbers[1] = year;
                }
            }
        });

        //Sets onClick listener for choose month button
        chooseMonthBtn = (Button) view.findViewById(R.id.pick_month_dialog_button_fg);
        chooseMonthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setCurrentDate(currentDate);
    }

    private void saveDate(Date date) {
        GenerateRosterActivity.chosedMonth = UIUtils.getMonthName(date, Calendar.LONG);
        GenerateRosterActivity.monthYearNumbers[0] = DateUtils.getMonthFromDate(date);
        GenerateRosterActivity.monthYearNumbers[1] = DateUtils.getYearFromDate(date);
    }

    private void setCurrentDate(Date date) {
        if (date != null){
            saveDate(date);
            //Set UI
            choosedMonthTextview = (TextView) view.findViewById(R.id.choosed_month_textview);
            String[] dateUI = DateUtils.getDateUIMonthYear(date);
            choosedMonthTextview.setText(dateUI[0] + " " + dateUI[1]);
        }
    }

}
