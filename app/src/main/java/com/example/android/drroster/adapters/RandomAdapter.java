package com.example.android.drroster.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;

import com.example.android.drroster.R;
import com.example.android.drroster.activities.RandomiseActivity;

/**
 * Created by Nir on 4/17/2016.
 */
public class RandomAdapter extends BaseAdapter {

    int selectedIndex = -1;
    String[] typeStringArray = new String[5];
    Activity mParentActivity;
    public final String FRAGMENT_TAG;

    public RandomAdapter(String[] typeStringArray, Activity mParentActivity, String fragment_tag) {
        this.typeStringArray = typeStringArray;
        this.mParentActivity = mParentActivity;
        FRAGMENT_TAG = fragment_tag;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mParentActivity.getLayoutInflater().inflate(R.layout.item_list_random, parent, false);

        //Set Radio Button
        RadioButton rbSelect = (RadioButton) convertView.findViewById(R.id.radio1);
        rbSelect.setText(typeStringArray[position]);
        if(selectedIndex == position){
            rbSelect.setChecked(true);
        }
        else{rbSelect.setChecked(false);}

        return convertView;
    }

    public void setSelectedIndex(int index){
        selectedIndex = index;
        putSelectedIndex();
    }

    //insert selected index in selectionOption array depends on fragment TAG
    private void putSelectedIndex() {
        switch (FRAGMENT_TAG) {
            case RandomiseActivity.RANDOM_FRAGMENT_FIRST_CALL + "":
                 RandomiseActivity.selectionOption[RandomiseActivity.RANDOM_FRAGMENT_FIRST_CALL] =selectedIndex ;
                break;

            case RandomiseActivity.RANDOM_FRAGMENT_SECOND_CALL + "":
                RandomiseActivity.selectionOption[RandomiseActivity.RANDOM_FRAGMENT_SECOND_CALL] =selectedIndex ;
                break;

            case RandomiseActivity.RANDOM_FRAGMENT_THIRD_CALL + "":
                RandomiseActivity.selectionOption[RandomiseActivity.RANDOM_FRAGMENT_THIRD_CALL] =selectedIndex ;
                break;
        }
    }

    @Override
    public int getCount() {
        return typeStringArray.length - 1;
    }

    @Override
    public Object getItem(int position) {
        return typeStringArray[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
