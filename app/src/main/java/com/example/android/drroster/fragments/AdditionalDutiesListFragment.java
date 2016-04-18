package com.example.android.drroster.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.example.android.drroster.R;
import com.example.android.drroster.activities.GenerateRosterActivity;
import com.example.android.drroster.adapters.AdditionalDutiesAdapter;
import com.example.android.drroster.database.AdditionalDutyModel;
import com.example.android.drroster.models.ADBean;


public class AdditionalDutiesListFragment extends ListFragment {

    View mFooterView;
    AdditionalDutiesAdapter mAdapter;
    public AdditionalDutiesListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_additional_duties, container, false);
        mFooterView = inflater.inflate(R.layout.item_list_additional_duty_footer, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //Set footer
        this.getListView().addFooterView(mFooterView);
        //Set the adapter after view is created

        mAdapter = new AdditionalDutiesAdapter(getActivity(),getListView(),getContext());
        setListAdapter(mAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if (position == GenerateRosterActivity.mADArray.size()){
            showAddNewNameDialog();
        }
    }
    private void showAddNewNameDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getContext());

        //Build View with id in order to inflate it in onClick
        final EditText input = new EditText(getContext());
        final int DIALOD_EDITTEXT_ID = 777;
        input.setId(DIALOD_EDITTEXT_ID);
        input.setHint("New name");

        //Set up the dialog view all programmatically
        alertDialogBuilder.setView(input);
        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.setTitle("Add new name"); //Set the title of the box
        alertDialogBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                EditText edit = (EditText) input.findViewById(DIALOD_EDITTEXT_ID);
                String name = edit.getText().toString();
                AddPersonName(name);
                dialog.cancel(); //when they click dismiss we will dismiss the box
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create(); //create the box
        alertDialog.show(); //actually show the box
    }


    private void AddPersonName( String newName) {
        ADBean newDuty = new ADBean(newName);
        GenerateRosterActivity.mADArray.add(newDuty);
        mAdapter.notifyDataSetChanged();

        //Set new duty in database todo:delte
        AdditionalDutyModel tempDuty = new AdditionalDutyModel();
        tempDuty.name = newName;
        tempDuty.number = GenerateRosterActivity.mPeopleArray.size() - 1;
        tempDuty.save();

    }
}
