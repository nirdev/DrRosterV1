package com.example.android.drroster.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Pair;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.drroster.MainActivity;
import com.example.android.drroster.R;
import com.example.android.drroster.models.ItemMainView;
import com.example.android.drroster.utils.DateUtils;
import com.example.android.drroster.utils.UIUtils;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Layouts
 * Portrait -
 * 1.list item
 * 2.list item without third call
 * Landscape -
 * 4.Header
 * 5.List item
 * 6.Header no 3rd call
 * 7.list item no 3rd call
 */
public class MainViewAdapter extends ArrayAdapter<ItemMainView> {


    private static final int PORTRAIT_ITEM = 1;
    private static final int LANDSCAPE_HEADER = 2;
    private static final int LANDSCAPE_ITEM = 3;
    private static final int LANDSCAPE_ITEM_NO_THIRD_CALL = 4;
    private static final int LANDSCAPE_HEADER_NO_THIRD_CALL = 5;
    private static final int PORTRAIT_ITEM_NO_THIRD_CALL = 6;

    private static final int HEADER_TYPE = 0;

    private int CURRENT_ITEM_TYPE = 0;

    private TextView dateNumber_tv;
    private TextView dateDayName_tv;

    //    private TextView firstCallTitle_tv;
//    private TextView secondCallTitle_tv;
//    private TextView leaveTitle_tv;
    private TextView thirdCallTitle_tv;
    private LinearLayout dutyTypes_ll;

    private TextView firstCallPerson_tv;
    private TextView secondCallPerson_tv;
    private TextView thirdCallPerson_tv;
    private TextView leavePeople_tv;
    private LinearLayout dutyPeople_ll;

    public MainViewAdapter(Context context, ArrayList<ItemMainView> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ItemMainView item = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            //Check from for different layouts - 3rd call landscape, 3d portrait, no 3rd landscape , no 3rd portrait
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_list_main, parent, false);
        }

        inflateViews(convertView);
        attachDataToUI(item);
//        // Lookup view for data population
//        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
//        TextView tvHome = (TextView) convertView.findViewById(R.id.tvHome);
//        // Populate the data into the template view using the data object
//        tvName.setText(item.name);
//        tvHome.setText(item.hometown);
        // Return the completed view to render on screen
        return convertView;
    }

    private void attachDataToUI(ItemMainView item) {

        //Set number and day name
        dateDayName_tv.setText(UIUtils.getDayName(item.getDay(), Calendar.SHORT));
        dateNumber_tv.setText(UIUtils.getDayNumber(item.getDay()));

        //if weekend make bold
        if (DateUtils.isWeekend(item.getDay())) {
            dateDayName_tv.setTextColor(ContextCompat.getColor(getContext(), R.color.drag_list_view_choosen_dates));
            dateNumber_tv.setTextColor(ContextCompat.getColor(getContext(), R.color.drag_list_view_choosen_dates));
        } else {
            dateDayName_tv.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
            dateNumber_tv.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        }

        //Set people names
        firstCallPerson_tv.setText(item.getFirstCallName());
        secondCallPerson_tv.setText(item.getSecondCallName());
        //Handle third call
        if (MainActivity.IS_THERE_THIRD_CALL) {
            thirdCallPerson_tv.setText(item.getThirdCallName());
        }else{
            thirdCallPerson_tv.setVisibility(View.GONE);
            thirdCallTitle_tv.setVisibility(View.GONE);
        }

        //Set duties
        if (item.getDutyTypeDoerPairArray() != null && item.getDutyTypeDoerPairArray().size() > 0) {
            buildLinearLayoutLists(item);
        }

        //Set leave date people
        leavePeople_tv.setText(UIUtils.getStringFromArray(item.getLeaveDatePeople()));


    }

    //build Linear Layout Lists for duties types and people
    private void buildLinearLayoutLists(ItemMainView item) {

        //Remove all layouts if exist
        if (dutyTypes_ll.getChildCount() > 0)
            dutyTypes_ll.removeAllViews();

        if (dutyPeople_ll.getChildCount() > 0)
            dutyPeople_ll.removeAllViews();

        for (int i = 0; i < item.getDutyTypeDoerPairArray().size(); i++) {
            Pair<String, String> mPair = item.getDutyTypeDoerPairArray().get(i);

            String dutyType = mPair.first;
            String dutyDoer = mPair.second;

            if (dutyDoer == null || dutyDoer.equals("")) {
                dutyDoer = "-";
            }

            LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);

            //make dp in pixels
            int paddinValueInPixels = (int) getContext().getResources().getDimension(R.dimen.main_list_item_padding_bottom_title);

            //Attach duty type with attr
            TextView dutyType_tv = new TextView(getContext());
            dutyType_tv.setLayoutParams(lparams);
            dutyType_tv.setText(dutyType);
            dutyType_tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    getContext().getResources().getDimension(R.dimen.main_list_text_size));
            dutyType_tv.setPadding(0, 0, 0, paddinValueInPixels);
            dutyType_tv.setTextColor(ContextCompat.getColor(getContext(), R.color.list_person_name_color));

            dutyTypes_ll.addView(dutyType_tv);

            //Attach duty name with attr
            TextView dutyName_tv = new TextView(getContext());
            dutyName_tv.setLayoutParams(lparams);
            dutyName_tv.setText(dutyDoer);
            dutyName_tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    getContext().getResources().getDimension(R.dimen.main_list_text_size));
            dutyName_tv.setPadding(0, 0, 0, paddinValueInPixels);
            dutyPeople_ll.addView(dutyName_tv);
        }
    }


    private void inflateViews(View convertView) {

        //dates
        dateNumber_tv = (TextView) convertView.findViewById(R.id.main_list_date_number);
        dateDayName_tv = (TextView) convertView.findViewById(R.id.main_list_date_name);
//        //Titles
//        firstCallTitle_tv = (TextView) convertView.findViewById(R.id.main_list_shift_firstcall_title);
//        secondCallTitle_tv = (TextView) convertView.findViewById(R.id.main_list_shift_secondcall_title);
//        leaveTitle_tv = (TextView) convertView.findViewById(R.id.main_list_shift_leave_title);

        //People names
        firstCallPerson_tv = (TextView) convertView.findViewById(R.id.main_list_shift_firstcall_person);
        secondCallPerson_tv = (TextView) convertView.findViewById(R.id.main_list_shift_secondcall_person);

        thirdCallPerson_tv = (TextView) convertView.findViewById(R.id.main_list_shift_thirdcall_person);
        thirdCallTitle_tv = (TextView) convertView.findViewById(R.id.main_list_shift_thirdcall_title);

        dutyPeople_ll = (LinearLayout) convertView.findViewById(R.id.main_list_duty_people_linearLayout);
        dutyTypes_ll = (LinearLayout) convertView.findViewById(R.id.main_list_duty_types_linearLayout);

        leavePeople_tv = (TextView) convertView.findViewById(R.id.main_list_shift_leave_people);



        //Handle third call
        //if don't have third call
//        if (!MainActivity.IS_THERE_THIRD_CALL) {
//        }
    }

}