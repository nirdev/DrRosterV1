package com.example.android.drroster.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.example.android.drroster.R;
import com.example.android.drroster.fragments.MainMonthGridFragment;
import com.example.android.drroster.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;

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
public class MainViewGridAdapterLand extends BaseAdapter {

    private Context context;
    private ArrayList<String> text;
    private int numColumns;

    public MainViewGridAdapterLand(Context context, ArrayList<String> text, int numColumns) {
        this.context = context;
        this.text = text;
        this.numColumns = numColumns;
    }

    @Override
    public int getCount() {
        return text.size();
    }

    @Override
    public Object getItem(int position) {
        return text.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView tv;
        int paddingValueInPixels = (int) context.getResources().getDimension(R.dimen.main_list_item_padding_first_left_land_adapter);

        tv = new TextView(context);
        tv.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        tv = setStyle(tv, position);

        tv.setText(text.get(position));
        tv.setBackgroundColor(ContextCompat.getColor(context, R.color.colorTitleWhite));
        tv.setPadding(0,  paddingValueInPixels / 2 , 0, paddingValueInPixels / 2);
        tv.setTextSize((int) context.getResources().getDimension(R.dimen.main_list_text_size_land));

        return tv;
    }

    private TextView setStyle(TextView tv, int position) {
       //check for titles
        if ( position >= numColumns) {
            //check for column number 1 (days)
            if (isDayString(position)) {
                //check if day is weekend or weekday
                if (isWeekend(position)) {
                    tv.setTextColor(ContextCompat.getColor(context, R.color.drag_list_view_choosen_dates));
                } else {
                    tv.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                }
            } else {
                //if text view is blue set default style
            }
        }
        else {
            //titles
            tv.setTextColor(ContextCompat.getColor(context, R.color.list_person_name_color));
            tv.setTypeface(null, Typeface.BOLD);
        }
        return tv;
    }
    private boolean isDayString(int position) {
        //Check for first item in the row by check math remainder 0
        if (position % numColumns == 0) {
            return true;
        }
        return false;
    }
    private boolean isWeekend(int position) {
        //get date from item list by divide position in item per column in grid view
        Date date = MainMonthGridFragment.listData.get((position - numColumns ) / numColumns).getDay(); // -numColumns because first row is header
        if (DateUtils.isWeekend(date)){
            return true;
        }
        return false;
    }
}