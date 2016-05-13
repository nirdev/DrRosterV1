package com.example.android.drroster.UI;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.example.android.drroster.MainActivity;
import com.example.android.drroster.R;
import com.example.android.drroster.utils.DateUtils;

import java.util.Date;

/**
 * Created by Nir on 4/1/2016.bububu
 */
public class MonthNavView extends RelativeLayout {

    //Navigation buttons
    private ImageButton mPreviousButton;
    private ImageButton mNextButton;

    //Index of stepBar & fragment
    private Date mCurrentDate;

    //Constructor @pram attrs is android default attrs
    public MonthNavView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);
    }

    //Interface to set call back to the activity on fragment change
    public interface IfMonthChangeListener {
        public void onMonthChange();
    }

    private IfMonthChangeListener monthChangeListener;

    public void setMonthChangeListener(IfMonthChangeListener listener){
        this.monthChangeListener = listener;
    }

    private void initializeViews(Context context) {
        //inflate the view layout
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_month_nav, this);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();


        mPreviousButton = (ImageButton) this.findViewById(R.id.view_month_nav_previous_button);
        mPreviousButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                lastMonth();
            }
        });

        // When the next button is pressed, select the next item in the list.
        mNextButton = (ImageButton) this.findViewById(R.id.view_month_nav_next_button);
        mNextButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                nextMonth();
            }
        });

    }

    private void lastMonth() {
        MainActivity.CURRENT_MONTH_DATE = DateUtils.removeOneMonth(MainActivity.CURRENT_MONTH_DATE);
        setMonthChanged();
    }

    private void nextMonth() {
        MainActivity.CURRENT_MONTH_DATE = DateUtils.addMonth(MainActivity.CURRENT_MONTH_DATE);
        setMonthChanged();
    }
    /**
     * Sets the selected month.
     */
    public void setMonthChanged() {

        if (monthChangeListener != null){
            monthChangeListener.onMonthChange();
        }

    }
}

