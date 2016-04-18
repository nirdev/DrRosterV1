package com.example.android.drroster.fragments;


import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.android.drroster.activities.RandomiseActivity;
import com.example.android.drroster.adapters.RandomAdapter;


public class RandomListFragment extends ListFragment {

    RandomAdapter adapter;

    public RandomListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new RandomAdapter(RandomiseActivity.typesList,getActivity(), getTag());
        setListAdapter(adapter);

        final ListView listView = getListView();
        //listView.addHeaderView();
        listView.setItemsCanFocus(false);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);


    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        adapter.setSelectedIndex(position);
        adapter.notifyDataSetChanged();
    }
}
