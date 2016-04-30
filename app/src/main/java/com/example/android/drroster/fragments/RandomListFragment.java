package com.example.android.drroster.fragments;


import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.android.drroster.R;
import com.example.android.drroster.activities.RandomiseActivity;
import com.example.android.drroster.adapters.RandomAdapter;
import com.example.android.drroster.utils.RandomManager;

import java.util.ArrayList;


public class RandomListFragment extends ListFragment {

    private RandomAdapter adapter;
    private RandomManager randomManager;
    private Boolean mGoNext = false;
    ArrayList<String> nameList;
    public static ArrayList<String> chosenRandomNameList;

    public RandomListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Build and initiate the randomManager class
        randomManager = new RandomManager();
        randomManager.initiateRandomManager(
                RandomiseActivity.mPeopleArray,
                RandomiseActivity.monthYearNumbers
                , getTag(), "gogo");

        //List of organized names
        nameList = RandomiseActivity.nameList;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Setup actionbar
        setUpMenu();

        //set go next bool
        setGoNext(false);

        //Not initialize anything if call empty
        if (!randomManager.isCallToShort()) {
            //Let the data setter know if data was changes
            RandomiseActivity.isThisFragmentNull = false;

            final ListView listView = getListView();

            //Add day names header to the list
            View headerView = View.inflate(getContext(), R.layout.item_list_random_header, null);
            listView.addHeaderView(headerView);

            //Set choice mode so lst behave like radio group
            listView.setItemsCanFocus(false);
            listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

            //Set new Check List adapter
            adapter = new RandomAdapter(RandomiseActivity.typesList, getActivity(), getTag(), randomManager, nameList);
            setListAdapter(adapter);
        }
        //call is empty
        else {
            //Let the data seter know if data was changes
            RandomiseActivity.isThisFragmentNull = true;
            ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.null_data, android.R.layout.simple_list_item_1);
            setListAdapter(adapter);
        }
    }

    private void setGoNext(Boolean bool) {
        mGoNext = bool;
        if ((getTag().equals(RandomiseActivity.RANDOM_FRAGMENT_THIRD_CALL + "")) &&
                randomManager.isCallToShort()) {
            mGoNext = true;

        }
    }

    public Boolean getGoNext() {
        return mGoNext;
    }

    private void setUpMenu() {
        setMenuTitle();
        setMenuGoBackButton();
    }

    private void setMenuGoBackButton() {
        Boolean bool = false;
        switch (getTag()) {
            case RandomiseActivity.RANDOM_FRAGMENT_FIRST_CALL + "":
                bool = true;
                break;
            case RandomiseActivity.RANDOM_FRAGMENT_SECOND_CALL + "":
                bool = false;
                break;
            case RandomiseActivity.RANDOM_FRAGMENT_THIRD_CALL + "":
                bool = false;
                break;
        }
        //Set menu go back button in the activity
        ((RandomiseActivity) getActivity()).setActionBarGoBackButton(bool);
    }

    private void setMenuTitle() {
        String title = getString(R.string.default_menu_title);

        switch (getTag()) {
            case RandomiseActivity.RANDOM_FRAGMENT_FIRST_CALL + "":
                title = getString(R.string.random_first_call_menu_title);
                break;
            case RandomiseActivity.RANDOM_FRAGMENT_SECOND_CALL + "":
                title = getString(R.string.random_second_call_menu_title);
                break;
            case RandomiseActivity.RANDOM_FRAGMENT_THIRD_CALL + "":
                title = getString(R.string.random_third_call_menu_title);
                break;
        }

        //Set menu title in the activity
        ((RandomiseActivity) getActivity()).setActionBarTitle(title);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if (position > 0) {
            chosenRandomNameList = randomManager.getRandomizedType(position);
            adapter.setSelectedIndex(position);
            adapter.notifyDataSetChanged();

            //set go next true
            setGoNext(true);
        }
    }
}
