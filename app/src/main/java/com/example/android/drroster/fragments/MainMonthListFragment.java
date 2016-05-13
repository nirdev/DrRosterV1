package com.example.android.drroster.fragments;


import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.android.drroster.MainActivity;
import com.example.android.drroster.R;
import com.example.android.drroster.adapters.MainViewAdapter;
import com.example.android.drroster.databases.ItemMainViewHelper;
import com.example.android.drroster.models.ItemMainView;
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
public class MainMonthListFragment extends ListFragment {

    ArrayList<ItemMainView> listData;
    public static int CURRENT_ORIENTATION;
    public static final int ORIENTATION_MODE_LAND = 0;
    public static final int ORIENTATION_MODE_PORT = 1;
    private int sumTextViews;

    public MainMonthListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity) getActivity()).setMonthNavVisibility(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Create the adapter to convert the array to views
        MainViewAdapter adapter = new MainViewAdapter(getActivity(), listData);

        // Attach the adapter to a ListView
        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_month_list, container, false);

        // Construct the data source
        listData = ItemMainViewHelper.buildMainItemMonthList(MainActivity.CURRENT_MONTH_DATE);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListStartPoint(MainActivity.CURRENT_MONTH_DATE);
    }

    private void setListStartPoint(Date currentMonthDate) {
        //check if this is current month roster
        if (DateUtils.isSameMonth(currentMonthDate,DateUtils.getCurrentDate())){
            getListView().clearFocus();
            getListView().post(new Runnable() {
                @Override
                public void run() {
                    getListView().setSelection(DateUtils.getDayIndex(DateUtils.getCurrentDate()));
                }
            });
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        setPersonDetailsFragment(position);
    }

    private void setPersonDetailsFragment(int position) {

        ItemMainView itemMainView = (ItemMainView) getListView().getAdapter().getItem(position);

        SinglePersonDetailsFragment nextFrag = SinglePersonDetailsFragment.newInstance(itemMainView);
        this.getFragmentManager().beginTransaction()
                .replace(R.id.main_activity_place_holder, nextFrag)
                .addToBackStack(null)
                .commit();
    }

//    private ArrayList<Integer> getChildrenPosition() {
//        ArrayList<Integer> childrenPosition = new ArrayList<>();
//        //LinearLayout linearLayout = (LinearLayout) headerView.findViewById(R.id.item_list_main_header_parent_layout);
//        //ArrayList<View> mAllChildrenView = getAllChildren(linearLayout);
//        int height = getLandScreenSize();
//        //Loop on child views to get positions
//        for (int i = 0; i < sumTextViews; i++) {
//            int childY = height / (sumTextViews - i + 1);
//            childrenPosition.add(childY);
//        }
//
//        return childrenPosition;
//    }


//    private int getLandScreenSize() {
//        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
//        Display display = wm.getDefaultDisplay();
//        Point size = new Point();
//        display.getSize(size);
//
//        return size.x;
//    }

//    private int getRelativeLeft(View myView) {
//        return myView.getLeft();
//    }
//
//    private ArrayList<View> getAllChildren(LinearLayout ll) {
//        ArrayList<View> mAllChildrenViews = new ArrayList<>();
//
//        int childrenCount = ll.getChildCount();
//        for (int i = 0; i < childrenCount; i++) {
//            View v = ll.getChildAt(i);
//            mAllChildrenViews.add(v);
//        }
//
//        return mAllChildrenViews;
//    }
//
//    private TextView setDatesUI(TextView textView) {
//        int paddingValueInPixels = (int) getContext().getResources().getDimension(R.dimen.main_list_item_padding_first_left_land_header);
//        textView.setPadding(paddingValueInPixels, 0, 0, 0);
//
//        //No layout_weight to dates
//        textView.setLayoutParams(new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.WRAP_CONTENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
//
//        return textView;
//    }

//    private ArrayList<TextView> buildTextViewArray(ArrayList<String> titles, Context context) {
//        ArrayList<TextView> mTextViews = new ArrayList<>();
//        for (int i = 0; i < titles.size(); i++) {
//            //Get the title
//            String title = titles.get(i);
//            TextView textView = new TextView(context);
//            //Set general attr (text,size,color)
//            textView.setText(title);
//            textView.setGravity(Gravity.CENTER);
//            textView.setTextColor(ContextCompat.getColor(getContext(), R.color.list_person_name_color));
//            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
//                    getContext().getResources().getDimension(R.dimen.main_list_text_size_land));
//
//            //Check if date or not to customize attr
//            if (i < 1) {
//                //Set dates UI
//                textView = setDatesUI(textView);
//            }
//            //if no date add weight
//            else {
//                textView.setLayoutParams(new LinearLayout.LayoutParams(
//                        LinearLayout.LayoutParams.WRAP_CONTENT,
//                        LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
//            }
//
//            mTextViews.add(textView);
//        }
//
//        return mTextViews;
//    }
//    private ArrayList<String> handleDutyTypes(ArrayList<String> mTitles) {
//        //Loop on first day to check duty type
//        for (Pair pair : listData.get(0).getDutyTypeDoerPairArray()) {
//            if (pair.first != null && !(pair.first).equals("")) {
//                mTitles.add((String) pair.first);
//            }
//        }
//        return mTitles;
//    }
//    private ArrayList<String> handleThirdCall(ArrayList<String> mTitles) {
//        if (MainActivity.IS_THERE_THIRD_CALL) {
//            mTitles.add("3rd Call");
//        } //add 3rd call title only if needed
//        return mTitles;
//    }
//    private ArrayList<String> buildTitlesArray() {
//        ArrayList<String> mTitles = new ArrayList<>();
//
//        mTitles.add("Date");
//        mTitles.add("1st Call");
//        mTitles.add("2nd Call");
//        mTitles = handleThirdCall(mTitles);
//        mTitles = handleDutyTypes(mTitles);
//        mTitles.add("Leave");
//        return mTitles;
//    }
    //        if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
//    private View buildHeaderView() {
//
//        //private vars
//        Context context = getContext();
//
//        //inflate view layout and parent layout inside
//        View mHeaderView = View.inflate(getContext(), R.layout.item_main_list_header, null);
//        LinearLayout parentLayout = (LinearLayout) mHeaderView.findViewById(R.id.item_list_main_header_parent_layout);
//        //Build titles array
//        ArrayList<String> titles = buildTitlesArray();
//        sumTextViews = titles.size();
//
//        //build array of textViews with correct stle and wieght
//        ArrayList<TextView> textViews = buildTextViewArray(titles, context);
//
//        //Add textViews to parent
//        for (TextView textView : textViews) {
//            parentLayout.addView(textView);
//        }
//
//        return mHeaderView;
//    }

//    private void setOrientation() {
//            CURRENT_ORIENTATION = ORIENTATION_MODE_LAND;
//        } else {
//            CURRENT_ORIENTATION = ORIENTATION_MODE_PORT;
//        }
//    }
//
//    private void setOrientationAdapter() {
//
//        switch (CURRENT_ORIENTATION) {
//            case ORIENTATION_MODE_PORT: {
//                // Create the adapter to convert the array to views
//                MainViewAdapter adapter = new MainViewAdapter(getActivity(), listData);
//                // Attach the adapter to a ListView
//                setListAdapter(adapter);
//                break;
//            }
//            case ORIENTATION_MODE_LAND: {
////               Add day names header to the list
//                View headerView = buildHeaderView();
//                getListView().addHeaderView(headerView);
//                ArrayList<Integer> childrenPosition = getChildrenPosition();
//
//                // Create the adapter to convert the array to views
//                MainViewAdapterLand adapter = new MainViewAdapterLand(getActivity(), listData, childrenPosition);
//                // Attach the adapter to a ListView
//                setListAdapter(adapter);
//            }
//        }
//    }

}
