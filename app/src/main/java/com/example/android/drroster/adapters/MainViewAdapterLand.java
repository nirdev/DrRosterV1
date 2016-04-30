package com.example.android.drroster.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Pair;
import android.util.TypedValue;
import android.view.Gravity;
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
public class MainViewAdapterLand extends ArrayAdapter<ItemMainView> {

    private static final int HEADER_TYPE = 0;

    ItemMainView item;
    ArrayList<Integer> positions;
    private int sumTextViews;

    public MainViewAdapterLand(Context context, ArrayList<ItemMainView> items,ArrayList<Integer> positions) {
        super(context, 0, items);
        this.positions = positions;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position

        item = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null ) {
            //Check from for different layouts - 3rd call landscape, 3d portrait, no 3rd landscape , no 3rd portrait
            convertView = buildItemView(parent);
        }

        return convertView;
    }

//        if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            LinearLayout linearLayout = (LinearLayout) convertView.findViewById(R.id.main_list_date_parent_layout);
//            linearLayout.setWeightSum(5f + item.getDutyTypeDoerPairArray().size());
//        }

//        inflateViews(convertView);
//        attachDataToUI(item);
//        // Lookup view for data population
//        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
//        TextView tvHome = (TextView) convertView.findViewById(R.id.tvHome);
//        // Populate the data into the template view using the data object
//        tvName.setText(item.name);
//        tvHome.setText(item.hometown);
    // Return the completed view to render on screen
//    private void attachDataToUI(ItemMainView item) {
//
//        //Set number and day name
//        dateDayName_tv.setText(UIUtils.getDayName(item.getDay()));
//        dateNumber_tv.setText(UIUtils.getDayNumber(item.getDay()));
//
//        //if weekend make bold
//        if (DateUtils.isWeekend(item.getDay())) {
//            dateDayName_tv.setTextColor(ContextCompat.getColor(getContext(), R.color.drag_list_view_choosen_dates));
//            dateNumber_tv.setTextColor(ContextCompat.getColor(getContext(), R.color.drag_list_view_choosen_dates));
//        } else {
//            dateDayName_tv.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
//            dateNumber_tv.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
//        }
//
//        //Set people names
//        firstCallPerson_tv.setText(item.getFirstCallName());
//        secondCallPerson_tv.setText(item.getSecondCallName());
//        if (MainActivity.IS_THERE_THIRD_CALL) {
//            thirdCallPerson_tv.setText(item.getThirdCallName());
//        }
//
//        //Set duties
//        if (item.getDutyTypeDoerPairArray() != null && item.getDutyTypeDoerPairArray().size() > 0) {
//            buildLinearLayoutLists(item);
//        }
//
//        //Set leave date people
//        leavePeople_tv.setText(UIUtils.getStringFromArray(item.getLeaveDatePeople()));
//
//
//    }

//    //build Linear Layout Lists for duties types and people
//    private void buildLinearLayoutLists(ItemMainView item) {
//
//        if (getContext().getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
//            //Remove all layouts if exist
//            if (dutyTypes_ll.getChildCount() > 0)
//                dutyTypes_ll.removeAllViews();
//
//            if (dutyPeople_ll.getChildCount() > 0)
//                dutyPeople_ll.removeAllViews();
//
//            for (int i = 0; i < item.getDutyTypeDoerPairArray().size(); i++) {
//                Pair<String, String> mPair = item.getDutyTypeDoerPairArray().get(i);
//
//                String dutyType = mPair.first;
//                String dutyDoer = mPair.second;
//
//                if (dutyDoer == null || dutyDoer.equals("")) {
//                    dutyDoer = "adsadasd";
//                }
//
//                LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
//                        LinearLayout.LayoutParams.MATCH_PARENT,
//                        LinearLayout.LayoutParams.MATCH_PARENT);
//
//                //make dp in pixels
//                int paddinValueInPixels = (int) getContext().getResources().getDimension(R.dimen.main_list_item_padding_bottom_title);
//
//                    //Attach duty type with attr
//                    TextView dutyType_tv = new TextView(getContext());
//                    dutyType_tv.setLayoutParams(lparams);
//                    dutyType_tv.setText(dutyType);
//                    dutyType_tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,
//                            getContext().getResources().getDimension(R.dimen.main_list_text_size));
//                    dutyType_tv.setPadding(0, 0, 0, paddinValueInPixels);
//                    dutyType_tv.setTextColor(ContextCompat.getColor(getContext(), R.color.list_person_name_color));
//
//                    dutyTypes_ll.addView(dutyType_tv);
//
//                //Attach duty name with attr
//                TextView dutyName_tv = new TextView(getContext());
//                dutyName_tv.setLayoutParams(lparams);
//                dutyName_tv.setText(dutyDoer);
//                dutyName_tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,
//                        getContext().getResources().getDimension(R.dimen.main_list_text_size));
//                dutyName_tv.setPadding(0, 0, 0, paddinValueInPixels);
//                dutyPeople_ll.addView(dutyName_tv);
//            }
//        }
//        //landscape mode
//        else {
//
//            if (dutyPeople_ll.getChildCount() > 0) {
//                dutyPeople_ll.removeAllViews();
//            }
//
//            LinearLayout.LayoutParams lparams2 = new LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.WRAP_CONTENT,
//                    LinearLayout.LayoutParams.WRAP_CONTENT,item.getDutyTypeDoerPairArray().size());
//            dutyPeople_ll.setLayoutParams(lparams2);
//
//
//            for (int i = 0; i < item.getDutyTypeDoerPairArray().size(); i++) {
//                Pair<String, String> mPair = item.getDutyTypeDoerPairArray().get(i);
//
//                String dutyDoer = mPair.second;
//
//                if (dutyDoer == null || dutyDoer.equals("")) {
//                    dutyDoer = "adsadasd";
//                }
//
//                LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
//                        LinearLayout.LayoutParams.WRAP_CONTENT,
//                        LinearLayout.LayoutParams.WRAP_CONTENT,1f);
//
//                //make dp in pixels
//                int paddinValueInPixels = (int) getContext().getResources().getDimension(R.dimen.main_list_item_padding_bottom_title);
//
//
//                //Attach duty name with attr
//                TextView dutyName_tv = new TextView(getContext());
//                dutyName_tv.setLayoutParams(lparams);
//                dutyName_tv.setText(dutyDoer);
//                dutyName_tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,
//                        getContext().getResources().getDimension(R.dimen.main_list_text_size));
//                dutyName_tv.setPadding(0, 0, 0, paddinValueInPixels);
//                dutyPeople_ll.addView(dutyName_tv);
//            }
//
//
//
//        }
//    }

    private View buildItemView(ViewGroup parent) {

        //private vars
        Context context = getContext();

        //inflate view layout and parent layout inside
        View mItemView = LayoutInflater.from(getContext()).inflate(R.layout.item_list_main, parent, false);
        LinearLayout parentLayout = (LinearLayout) mItemView.findViewById(R.id.main_list_parent_layout_land);
        //Build itemData array
        ArrayList<String> itemData = buildDataArray();
        sumTextViews = itemData.size();

        //build array of textViews with correct style and weight
        ArrayList<TextView> textViews = buildTextViewArray(itemData,context);

        //Add textViews to parent
        for (TextView textView : textViews){
            parentLayout.addView(textView);
        }

        //parentLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//        parentLayout.addView(tv1);
//        parentLayout.addView(tv2);
//        parentLayout.addView(tv3);
//        parentLayout.addView(tv4);

        return mItemView;
    }

    private ArrayList<TextView> buildTextViewArray(ArrayList<String> dataItems , Context context) {
        ArrayList<TextView> mTextViews = new ArrayList<>();
        for (int i = 0; i < dataItems.size() ; i++){
            //Get the title
            String data = dataItems.get(i);
            TextView textView = new TextView(context);
            //Set general attr (text,size,color)
//            textView.setTextColor(ContextCompat.getColor(getContext(), R.color.list_person_name_color));
            textView.setText(data);
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    getContext().getResources().getDimension(R.dimen.main_list_text_size_land));

            //Check if date or not to customize attr
            if (i < 2){
                //Set dates UI
                textView = setDatesUI(textView,i);
            }
            //if no date add weight
            else {
                textView.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
            }


            mTextViews.add(textView);
        }

        return mTextViews;
    }

    private TextView setDatesUI(TextView textView, int i) {

        //Set padding from start
        int paddingValueInPixels = (int) getContext().getResources().getDimension(R.dimen.main_list_item_padding_first_left_land_adapter);
//        textView.set(paddingValueInPixels, 0, 0, 0);

        //if weekend make darker if not make bright green color
        if (DateUtils.isWeekend(item.getDay())) {
            textView.setTextColor(ContextCompat.getColor(getContext(), R.color.drag_list_view_choosen_dates));
        } else {
            textView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        }

        //0.5 layout_weight to dates
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,0.5f);

        //Handle gravity
        if(i == 0){
            textView.setGravity(Gravity.RIGHT);
            params.setMarginEnd(paddingValueInPixels);
        }else {
            textView.setGravity(Gravity.LEFT);
            params.setMarginStart(paddingValueInPixels);
        }
        textView.setLayoutParams(params);
        return textView;
    }

    private ArrayList<String> buildDataArray() {
        ArrayList<String> mData = new ArrayList<>();

        mData.add(UIUtils.getDayNumber(item.getDay()));
        mData.add(UIUtils.getDayName(item.getDay()));
        mData.add(item.getFirstCallName());
        mData.add(item.getSecondCallName());
        mData = handleThirdCall(mData);
        mData = handleDutyTypes(mData);
        mData.add(UIUtils.getStringFromArray(item.getLeaveDatePeople()));
        return mData;
    }

    private ArrayList<String> handleDutyTypes(ArrayList<String> mData) {
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

    private ArrayList<String> handleThirdCall(ArrayList<String> mTitles) {
        if (MainActivity.IS_THERE_THIRD_CALL) {
            mTitles.add(item.getThirdCallName());
        } //add 3rd call title only if needed
        return mTitles;
    }

}