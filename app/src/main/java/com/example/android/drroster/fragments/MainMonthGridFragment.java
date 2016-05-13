package com.example.android.drroster.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.android.drroster.MainActivity;
import com.example.android.drroster.R;
import com.example.android.drroster.adapters.MainViewGridAdapterLand;
import com.example.android.drroster.databases.ItemMainViewHelper;
import com.example.android.drroster.models.ItemMainView;
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
public class MainMonthGridFragment extends Fragment {

    public static ArrayList<ItemMainView> listData;
    int numColumns;
    public static int CURRENT_ORIENTATION;
    public static final int ORIENTATION_MODE_LAND = 0;
    public static final int ORIENTATION_MODE_PORT = 1;
    private int sumTextViews;

    public MainMonthGridFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity) getActivity()).setMonthNavVisibility(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_month_grid, container, false);

        // Construct the data source
        listData = ItemMainViewHelper.buildMainItemMonthList(MainActivity.CURRENT_MONTH_DATE);
        ArrayList<String> fullDataArray = new ArrayList<>();
        fullDataArray = buildTitlesArray(fullDataArray);

        numColumns = fullDataArray.size();
        fullDataArray = buildFullDataArray(fullDataArray,listData);

        GridView gridview = (GridView) view.findViewById(R.id.main_grid_layout);

        gridview.setNumColumns(numColumns);
        gridview.setAdapter(new MainViewGridAdapterLand(getContext(),fullDataArray,numColumns));

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

//    private void setPersonDetailsFragment(int position) {
//
//        ItemMainView itemMainView = (ItemMainView) getListView().getAdapter().getItem(position);
//
//        SinglePersonDetailsFragment nextFrag = SinglePersonDetailsFragment.newInstance(itemMainView);
//        this.getFragmentManager().beginTransaction()
//                .replace(R.id.main_activity_place_holder, nextFrag)
//                .addToBackStack(null)
//                .commit();
//    }

    private ArrayList<String> buildFullDataArray(ArrayList<String> dataList,ArrayList<ItemMainView> dataTable){

        for (ItemMainView item : dataTable ){
            dataList = buildDataArrayForItem(dataList,item);
        }

        return dataList;
    }
    private ArrayList<String> buildDataArrayForItem(ArrayList<String> mData,ItemMainView item) {

        mData.add(UIUtils.getDayNumber(item.getDay()) + " " + UIUtils.getDayName(item.getDay(), Calendar.SHORT));
        mData.add(item.getFirstCallName());
        mData.add(item.getSecondCallName());
        mData = handleThirdCall(mData,item);
        mData = handleDutyTypes(mData,item);
        mData.add(UIUtils.getStringFromArray(item.getLeaveDatePeople()));
        return mData;
    }
    private ArrayList<String> handleDutyTypes(ArrayList<String> mData,ItemMainView item) {
        //Loop on current item day to check duty doer
        for (Pair pair : item.getDutyTypeDoerPairArray()){
            if (pair.second == null || (pair.second).equals("")){
                mData.add("-");
            }else {
                mData.add((String) pair.second);
            }
        }
        return mData;
    }
    private ArrayList<String> handleThirdCall(ArrayList<String> mTitles,ItemMainView item) {
        if (MainActivity.IS_THERE_THIRD_CALL) {
            mTitles.add(item.getThirdCallName());
        } //add 3rd call title only if needed
        return mTitles;
    }


    private ArrayList<String> buildTitlesArray(ArrayList<String> mTitles) {

        mTitles.add("Date");
        mTitles.add("1st");
        mTitles.add("2nd");
        mTitles = handleThirdCallTitle(mTitles);
        mTitles = handleDutyTypesTitle(mTitles);
        mTitles.add("Leave");
        return mTitles;
    }
    private ArrayList<String> handleDutyTypesTitle(ArrayList<String> mTitles) {
        //Loop on first day to check duty type
        for (Pair pair : listData.get(0).getDutyTypeDoerPairArray()) {
            if (pair.first != null && !(pair.first).equals("")) {
                mTitles.add((String) pair.first);
            }
        }
        return mTitles;
    }
    private ArrayList<String> handleThirdCallTitle(ArrayList<String> mTitles) {
        if (MainActivity.IS_THERE_THIRD_CALL) {
            mTitles.add("3rd");
        } //add 3rd call title only if needed
        return mTitles;
    }

//    private void setOrientation() {
//        if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            CURRENT_ORIENTATION = ORIENTATION_MODE_LAND;
//        } else {
//            CURRENT_ORIENTATION = ORIENTATION_MODE_PORT;
//        }
//    }

}
