package com.example.android.drroster.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.android.drroster.R;
import com.example.android.drroster.activities.GenerateRosterActivity;
import com.example.android.drroster.models.Person;
import com.example.android.drroster.utils.DateUtils;
import com.squareup.timessquare.CalendarPickerView;
import com.woxthebox.draglistview.DragItemAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Created by Nir on 4/3/2016.bugalbugala
 */
public class ItemDragListAdapter extends DragItemAdapter<Person, ItemDragListAdapter.ViewHolderNormal> {

    //.super adapter vas
    private int mLayoutId;
    private int mGrabHandleId;

    //Constants
    private static final int NORMAL_VIEWTYPE = 0;
    private static final int FOOTER_VIEWTYPE = 1;

    String FRAGMENT_TAG;

    //Calnedar vars
    private CalendarPickerView mCalendarPickerView;
    private AlertDialog theDialog;
    Context mContext;
    Activity mActivity;
    final Calendar nextMonth;
    final Calendar lastMonth;

    //Constructor
    public ItemDragListAdapter(ArrayList<Person> list, int layoutId,
                               int grabHandleId,
                               boolean dragOnLongPress,
                               String TAG,
                               Context context,
                               Activity myActivity) {
        super(dragOnLongPress);
        mLayoutId = layoutId;
        mGrabHandleId = grabHandleId;
        FRAGMENT_TAG = TAG;
        mContext = context;
        mActivity = myActivity;
        setHasStableIds(true);
        setItemList(list);

        nextMonth = Calendar.getInstance();
        nextMonth.add(Calendar.MONTH, 1);
        lastMonth = Calendar.getInstance();
        lastMonth.add(Calendar.MONTH, -1);
    }

    @Override
    public ViewHolderNormal onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mLayoutId, parent, false);
        return new ViewHolderNormal(view);

    }

    @Override
    public void onBindViewHolder(ViewHolderNormal holder, int position) {
        super.onBindViewHolder(holder, position);
        //If not footer
        if (position != (mItemList.size() - 1)) {
            //Set keyboard down automatically
            mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

            Boolean isCheckedBoolean = null; // default if not checked
            String text = mItemList.get(position).getName();
            holder.mNameTextView.setText(text);
            holder.itemView.setTag(text);

            //Set checkbox viewholders
            switch (FRAGMENT_TAG) {
                case GenerateRosterActivity.FRAGMENT_PEOPLE_LIST_FIRST_CALL_INDEX + "":
                    isCheckedBoolean = mItemList.get(position).getIsFirstCall();
                    break;
                case GenerateRosterActivity.FRAGMENT_PEOPLE_LIST_SECOND_CALL_INDEX + "":
                    isCheckedBoolean = mItemList.get(position).getIsSecondCall();
                    break;
                case GenerateRosterActivity.FRAGMENT_PEOPLE_LIST_THIRD_CALL_INDEX + "":
                    isCheckedBoolean = mItemList.get(position).getIsThirdCall();
                    break;
                case GenerateRosterActivity.FRAGMENT_DATEABLE_LIST_INDEX + "":
                    isCheckedBoolean = mItemList.get(position).getIsLeavDate();
                    break;
            }
            if (isCheckedBoolean == null) {
                isCheckedBoolean = false;
            }
            holder.mCheckBox.setChecked(isCheckedBoolean);
            holder.itemView.setActivated(isCheckedBoolean);


            //Set dates view holders if not empty
            if ((FRAGMENT_TAG.equals(String.valueOf(GenerateRosterActivity.FRAGMENT_DATEABLE_LIST_INDEX)))
                    && (mItemList.get(position).getLeaveDates() != null)
                    && mItemList.get(position).getIsLeavDate()) {
                String[] UIStringList = DateUtils.getDatesUI(mItemList.get(position).getLeaveDates());

                //Set days in item view
                holder.mChoseDays.setText(UIStringList[0]);
                holder.mChoseMonthandYear.setText(UIStringList[1]);
            } else {
                //Set dummy clean string in recycle view
                holder.mChoseDays.setText("");
                holder.mChoseMonthandYear.setText("");
            }

        }
        //Footer viewholder
        else {
            holder.mNameTextView.setText(R.string.add_new_friend_footer_view_text);
            holder.mNameTextView.setTextColor(Color.GRAY);
            holder.mCheckBox.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (position < mItemList.size() - 1) {
            return NORMAL_VIEWTYPE;
        }
        return FOOTER_VIEWTYPE;
    }

    @Override
    public long getItemId(int position) {
        return mItemList.get(position).getId();
    }

    public class ViewHolderNormal extends DragItemAdapter<ArrayList<Person>, ViewHolderNormal>.ViewHolder {
        public ImageButton mDeleteImageButton;
        public CheckBox mCheckBox;

        //chosen dates vars
        public TextView mChoseDays;
        public TextView mChoseMonthandYear;

        //People name vars
        public TextView mNameTextView;


        public ViewHolderNormal(final View itemView) {
            super(itemView, mGrabHandleId);

            //If not footer - normal view
            if(getItemPosition() < (mItemList.size() - 1) ) {
                mNameTextView = (TextView) itemView.findViewById(R.id.text);
                mDeleteImageButton = (ImageButton) itemView.findViewById(R.id.delete_draggable_item);
                mCheckBox = (CheckBox) itemView.findViewById(R.id.checkbox_draggable_list_item);
                mChoseDays = (TextView) itemView.findViewById(R.id.days_item_draglist);
                mChoseMonthandYear = (TextView) itemView.findViewById(R.id.month_item_draglist);

                mCheckBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Boolean isChecked = mCheckBox.isChecked();
                        //Sets checked boxes on chose item and save data depends on current fragment tag
                        switch (FRAGMENT_TAG) {
                            case GenerateRosterActivity.FRAGMENT_PEOPLE_LIST_FIRST_CALL_INDEX + "":
                                itemView.setActivated(isChecked);
                                GenerateRosterActivity.mPeopleArray.get(getItemPosition()).setIsFirstCall(isChecked);
                                break;
                            case GenerateRosterActivity.FRAGMENT_PEOPLE_LIST_SECOND_CALL_INDEX + "":
                                itemView.setActivated(isChecked);
                                GenerateRosterActivity.mPeopleArray.get(getItemPosition()).setIsSecondCall(isChecked);
                                break;
                            case GenerateRosterActivity.FRAGMENT_PEOPLE_LIST_THIRD_CALL_INDEX + "":
                                itemView.setActivated(isChecked);
                                GenerateRosterActivity.mPeopleArray.get(getItemPosition()).setIsThirdCall(isChecked);
                                break;
                            case GenerateRosterActivity.FRAGMENT_DATEABLE_LIST_INDEX + "":
                                itemView.setActivated(isChecked);
                                GenerateRosterActivity.mPeopleArray.get(getItemPosition()).setIsLeavDate(isChecked);
                                break;
                        }

                        //if date option is available && item is marked as checked
                        if ((FRAGMENT_TAG.equals(String.valueOf(GenerateRosterActivity.FRAGMENT_DATEABLE_LIST_INDEX))) && isChecked) {

                            showCalendarInDialog(mContext.getString(R.string.dialog_calerdar_title), R.layout.dialog_date_picker);
                            mCalendarPickerView.init(lastMonth.getTime(), nextMonth.getTime())
                                    .inMode(CalendarPickerView.SelectionMode.MULTIPLE)
                                    .withSelectedDate(new Date());

                        }
                        notifyDataSetChanged();
                    }
                });
            }
            //Footer options -
            else {
                Log.wtf("here", "--------------------------------------------");
            }
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

                                //Sets the currently being added dates in final array
                                GenerateRosterActivity.mPeopleArray.get(getItemPosition()).setLeaveDates(datesRange);

                                //Build UI Strings

                                String[] mDatesUIList = DateUtils.getDatesUI(datesRange);

                                //Sets the UI in view hierarchy
                                mChoseDays = (TextView) itemView.findViewById(R.id.days_item_draglist);
                                mChoseMonthandYear = (TextView) itemView.findViewById(R.id.month_item_draglist);
                                mChoseDays.setText(mDatesUIList[0]);
                                mChoseMonthandYear.setText(mDatesUIList[1]);
                            }

                        }
                    })
                    .create();

            theDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialogInterface) {
                    mCalendarPickerView.fixDialogDimens();
                }
            });


            theDialog.show();
        }

        private void showEditNameDialog() {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    mContext);
            final EditText input = new EditText(mContext);
            input.setText(getItemName(), TextView.BufferType.EDITABLE);
            alertDialogBuilder.setView(input);
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setTitle("Edit name"); //Set the title of the box
            //alertDialogBuilder.setMessage(""); //Set the message for the box
            alertDialogBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    String name = input.getText().toString();

                    changePersonName(getItemPosition(), name);
                    dialog.cancel(); //when they click dismiss we will dismiss the box
                }
            });
            alertDialogBuilder.setNegativeButton("delete person", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    deletePerson(getItemPosition());
                    dialog.cancel(); //when they click dismiss we will dismiss the box
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create(); //create the box
            alertDialog.show(); //actually show the box
        }

        private void showAddNewNameDialog() {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    mContext);
            final EditText input = new EditText(mContext);
            input.setText("", TextView.BufferType.EDITABLE);
            input.setHint("New name");
            alertDialogBuilder.setView(input);
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setTitle("Add new name"); //Set the title of the box
            //alertDialogBuilder.setMessage(""); //Set the message for the box
            alertDialogBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    String name = input.getText().toString();

                    AddPersonName(name);
                    dialog.cancel(); //when they click dismiss we will dismiss the box
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create(); //create the box
            alertDialog.show(); //actually show the box
        }

        private void deletePerson(int position) {
            GenerateRosterActivity.mPeopleArray.remove(position);
            notifyDataSetChanged();
        }

        private void changePersonName(int position, String newName) {
            Person newPerson = GenerateRosterActivity.mPeopleArray.get(position);
            newPerson.setName(newName);
            GenerateRosterActivity.mPeopleArray.set(position, newPerson);
            notifyDataSetChanged();
        }
        private void AddPersonName( String newName) {
            Person newPerson = new Person((long) (mItemList.size()),newName, false,false,false,false,null);
            GenerateRosterActivity.mPeopleArray.add(newPerson);
            notifyDataSetChanged();
        }

        private int getItemPosition() {
            return (getPosition());
        }

        private String getItemName() {
            return GenerateRosterActivity.mPeopleArray.get(getItemPosition()).getName();
        }

        //List item is clicked listener
        @Override
        public void onItemClicked(View view) {
            // if not footer
            if (getItemPosition() < mItemList.size() - 1 ) {

                showEditNameDialog();
            }
            else {
                showAddNewNameDialog();
            }
        }

        @Override
        public boolean onItemLongClicked(View view) {
            return true;
        }
    }

}