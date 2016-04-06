package com.example.android.drroster.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.drroster.R;
import com.example.android.drroster.activities.GenerateRosterActivity;
import com.example.android.drroster.models.Person;
import com.squareup.timessquare.CalendarPickerView;
import com.woxthebox.draglistview.DragItemAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Nir on 4/3/2016.bugalbugala
 */
public class ItemDragListAdapter extends DragItemAdapter<Person, ItemDragListAdapter.ViewHolder> {

    int classcount = 0;
    static int staticclasscount = 0;

    private boolean onBind;

    private int mLayoutId;
    private int mGrabHandleId;

    String FRAGMENT_TAG;

    private CalendarPickerView mCalendarPickerView;
    private AlertDialog theDialog;
    Context mContext;
    final Calendar nextMonth;
    final Calendar lastMonth;

    //Constructor
    public ItemDragListAdapter(ArrayList<Person> list, int layoutId,
                               int grabHandleId, boolean dragOnLongPress, String TAG, Context context) {
        super(dragOnLongPress);
        mLayoutId = layoutId;
        mGrabHandleId = grabHandleId;
        FRAGMENT_TAG = TAG;
        mContext = context;
        setHasStableIds(true);
        setItemList(list);

        nextMonth = Calendar.getInstance();
        nextMonth.add(Calendar.MONTH, 1);
        lastMonth = Calendar.getInstance();
        lastMonth.add(Calendar.MONTH, -1);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mLayoutId, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        String text = mItemList.get(position).getName();
        Boolean aBoolean = mItemList.get(position).getIsFirstCall();
        holder.mEditText.setText(text);
        holder.itemView.setTag(text);

        onBind = true;
        holder.mCheckBox.setChecked(aBoolean);
        onBind = false;

//        Log.wtf("here", "class count" + classcount + " static: " + staticclasscount + " position " + position);
//        Log.wtf("here", "holder: " + holder.mCheckBox.isChecked());
//
//        holder.itemView.setTag(text);
//
//
//        //Check for boolean and date views to set in view binder
//        int i7000 = 0;
//        for (Pair temp : DraggableListFragment.mPeopleArray) {
//            if (temp.second.equals(text)) {
//                break;
//            }
//            i7000++;
//        }
//
//        holder.mCheckBox.setChecked(DraggableListFragment.mCheckedArray.get(i7000));

    }

    @Override
    public long getItemId(int position) {
        return mItemList.get(position).getId();
    }

    public class ViewHolder extends DragItemAdapter<ArrayList<Person>, ItemDragListAdapter.ViewHolder>.ViewHolder {
        public ImageButton mDeleteImageButton;
        public CheckBox mCheckBox;

        //chosen dates vars
        public TextView chosedDays;
        public TextView chosedMonthandYear;

        //People name vars
        public EditText mEditText;
        public String mText;
        public String mOldText;

        public ViewHolder(final View itemView) {
            super(itemView, mGrabHandleId);

            mEditText = (EditText) itemView.findViewById(R.id.text);
            mText = mEditText.getText().toString();
            mDeleteImageButton = (ImageButton) itemView.findViewById(R.id.delete_draggable_item);
            mCheckBox = (CheckBox) itemView.findViewById(R.id.checkbox_draggable_list_item);

            //Delete Button was clicked listener
            mDeleteImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Check which index was selected by comparing to edit text
                    int i = 0;
                    for (Person temp : GenerateRosterActivity.mPeopleArray) {
                        if (temp.getName().equals("" + mOldText)) {
                            break;
                        }
                        i++;
                    }
                    GenerateRosterActivity.mPeopleArray.remove(i);
                    notifyDataSetChanged();
                }
            });

            //Change Name Listener
            mEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    //User clicked on edit text
                    if (hasFocus) {
                        //Get text before edited
                        mOldText = mEditText.getText().toString();

                        //Set Cancel item visible
                        mDeleteImageButton.setVisibility(View.VISIBLE);
                    }
                    //User leaved edit text
                    else {
                        //Set new text
                        mText = mEditText.getText().toString();

                        //Set delete button invisible
                        mDeleteImageButton.setVisibility(View.GONE);

                        //Set new value in the array
                        int i = 0;
                        Long mTempLong = -1l;
                        //Check which index was selected by comparing to old edit text
                        for (Person temp : GenerateRosterActivity.mPeopleArray) {
                            if (temp.getName().equals("" + mOldText)) {
                                mTempLong = (long) temp.getId();
                                break;
                            }
                            i++;
                        }

                        //If long id was found set the data in the array.
                        if (mTempLong != -1) {
                            GenerateRosterActivity.mPeopleArray.set(i, new Person(mTempLong, mText));
                        }
                    }
                    notifyDataSetChanged();
                }
            });

            //CheckBox change listener
            mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (!onBind) {
                        //Check if no new name was signed to this person
                        if (mOldText == null || mOldText.isEmpty()) {

                            //Check name of currently checked box
                            EditText mCurrentName = (EditText) itemView.findViewById(R.id.text);
                            //set check condition in is checked array
                            int i = 0;
                            //Check which index was selected by comparing to edit text
                            for (Person temp : GenerateRosterActivity.mPeopleArray) {
                                if (temp.getName().equals("" + mCurrentName.getText())) {
                                    break;
                                }
                                i++;
                            }
                            switch (FRAGMENT_TAG) {
                                case GenerateRosterActivity.FRAGMENT_PEOPLE_LIST_FIRST_CALL_INDEX + "":
                                    GenerateRosterActivity.mPeopleArray.get(i).setIsFirstCall(isChecked);
                                    break;
                                case GenerateRosterActivity.FRAGMENT_PEOPLE_LIST_SECOND_CALL_INDEX + "":
                                    GenerateRosterActivity.mPeopleArray.get(i).setIsSecondCall(isChecked);
                                    break;
                                case GenerateRosterActivity.FRAGMENT_PEOPLE_LIST_THIRD_CALL_INDEX + "":
                                    GenerateRosterActivity.mPeopleArray.get(i).setIsThirdCall(isChecked);
                                    break;
                            }

                        }
                        //if name is currently being added check the array by the old name
                        else {
                            //set check condition in is checked array
                            int i = 0;
                            //Check which index was selected by comparing to edit text
                            for (Person temp : GenerateRosterActivity.mPeopleArray) {
                                if (temp.getName().equals("" + mOldText)) {
                                    break;
                                }
                                i++;
                            }
                            switch (FRAGMENT_TAG) {
                                case GenerateRosterActivity.FRAGMENT_PEOPLE_LIST_FIRST_CALL_INDEX + "":
                                    GenerateRosterActivity.mPeopleArray.get(i).setIsFirstCall(isChecked);
                                    break;
                                case GenerateRosterActivity.FRAGMENT_PEOPLE_LIST_SECOND_CALL_INDEX + "":
                                    GenerateRosterActivity.mPeopleArray.get(i).setIsSecondCall(isChecked);
                                    break;
                                case GenerateRosterActivity.FRAGMENT_PEOPLE_LIST_THIRD_CALL_INDEX + "":
                                    GenerateRosterActivity.mPeopleArray.get(i).setIsThirdCall(isChecked);
                                    break;
                            }
                        }

                        //if date option is available && item is marked as checked
                        if ((FRAGMENT_TAG == String.valueOf(GenerateRosterActivity.FRAGMENT_DATEABLE_LIST_INDEX)) && isChecked) {

                            showCalendarInDialog(mContext.getString(R.string.dialog_calerdar_title), R.layout.dialog_date_picker);
                            mCalendarPickerView.init(lastMonth.getTime(), nextMonth.getTime())
                                    .inMode(CalendarPickerView.SelectionMode.MULTIPLE)
                                    .withSelectedDate(new Date());

                        }

                    }
                    notifyDataSetChanged();
                }
            });
        }

        private void showCalendarInDialog(String title, int layoutResId) {

            LayoutInflater inflater = LayoutInflater.from(mContext);
            mCalendarPickerView = (CalendarPickerView) inflater.inflate(layoutResId, null, false);

            theDialog = new AlertDialog.Builder(mContext) //
                    .setTitle(title)
                    .setView(mCalendarPickerView)
                    .setNeutralButton(R.string.dialog_choose_date_button, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();

                            //check if dates were selected
                            if (mCalendarPickerView.getSelectedDates() != null && mCalendarPickerView.getSelectedDates().size() != 0) {
                                //Get first and last Selected dates
                                List<Date> datesRange = mCalendarPickerView.getSelectedDates();

                                //get current text
                                mText = ((EditText) itemView.findViewById(R.id.text)).getText().toString();

                                //set check condition in is checked array
                                int i2 = 0;
                                //Check which index was selected by comparing to edit text
                                for (Person temp : GenerateRosterActivity.mPeopleArray) {
                                    if (temp.getName().equals("" + mText)) {
                                        break;
                                    }
                                    i2++;
                                }
                                //Sets the currently being added dates in final array
                                GenerateRosterActivity.mPeopleArray.get(i).setLeaveDates(datesRange);

                                //Sets UI for chosen dates
                                String monthYearUIString = null;
                                SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
                                SimpleDateFormat mothYearFormat = new SimpleDateFormat("MMM yyyy");
                                String dateUIString = dayFormat.format(datesRange.get(0));

                                //Check for more then one date
                                if (datesRange.size() > 1) {
                                    for (int i3 = 1; i3 < datesRange.size(); i3++) {
                                        dateUIString = dateUIString + " ," + dayFormat.format(datesRange.get(i3));
                                    }
                                }

                                monthYearUIString = "   " + mothYearFormat.format(datesRange.get(datesRange.size() - 1));

                                chosedDays = (TextView) itemView.findViewById(R.id.days_item_draglist);
                                chosedMonthandYear = (TextView) itemView.findViewById(R.id.month_item_draglist);
                                chosedDays.setText(dateUIString);
                                chosedMonthandYear.setText(monthYearUIString);
                            }
                            notifyDataSetChanged();
                        }
                    })
                    .create();
            theDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialogInterface) {
                    mCalendarPickerView.fixDialogDimens();
                    notifyDataSetChanged();
                }
            });


            theDialog.show();
        }

        //List item is clicked listener
        @Override
        public void onItemClicked(View view) {
            Toast.makeText(view.getContext(), "Item clicked", Toast.LENGTH_SHORT).show();
        }

        @Override
        public boolean onItemLongClicked(View view) {
            Toast.makeText(view.getContext(), "Item long clicked", Toast.LENGTH_SHORT).show();
            return true;
        }
    }
}