package com.example.android.drroster.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.drroster.R;
import com.example.android.drroster.activities.GenerateRosterActivity;
import com.example.android.drroster.models.ADBean;


/**
 * Created by Nir on 4/15/2016.
 */
public class AdditionalDutiesAdapter extends BaseAdapter {

    private Activity mParentActivity;
    private Context context;
    private ListView mListView;

    public AdditionalDutiesAdapter(Activity mParentActivity, ListView listView,Context context) {
        this.mParentActivity = mParentActivity;
        this.mListView = listView;
        this.context = context;
    }

    private static class AccessoriesViewHolder {
        public CheckBox checkBox;
        public TextView textView;
    }

    @Override
    public int getCount() {
        return GenerateRosterActivity.mADArray.size();
    }

    @Override
    public ADBean getItem(int position) {
        return GenerateRosterActivity.mADArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        AccessoriesViewHolder holder = null;

        if (convertView == null) {
            convertView = mParentActivity.getLayoutInflater().inflate(R.layout.item_addition_dutie_list, parent, false);

            holder = new AccessoriesViewHolder();
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox_draggable_list_item_AD);
            holder.checkBox.setOnCheckedChangeListener(mStarCheckedChanceChangeListener);
            holder.textView = (TextView) convertView.findViewById(R.id.text_AD);
            holder.textView.setOnClickListener(mOnTextClickListener);

            convertView.setTag(holder);
        } else {
            holder = (AccessoriesViewHolder) convertView.getTag();
        }

        //Dispatch an Retch click listener so scroll want crush app
        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setChecked(GenerateRosterActivity.mADArray.get(position).getIsChecked());
        holder.checkBox.setOnCheckedChangeListener(mStarCheckedChanceChangeListener);

        //Set textview
        holder.textView.setText(GenerateRosterActivity.mADArray.get(position).getName());

        return convertView;
    }

    //Checkbox listener
    private CompoundButton.OnCheckedChangeListener mStarCheckedChanceChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            final int position = mListView.getPositionForView(buttonView);
            if (position != ListView.INVALID_POSITION) {

                ADBean temp = GenerateRosterActivity.mADArray.get(position);
                temp.setIsChecked(isChecked);
                GenerateRosterActivity.mADArray.set(position, temp);
            }
        }
    };

    //Text Listener
    private View.OnClickListener mOnTextClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final int position = mListView.getPositionForView(v);
            if (position != ListView.INVALID_POSITION) {
               showEditNameDialog(position);
            }
        }
    };

    private void showEditNameDialog(int position) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);
        final EditText input = new EditText(context);
        input.setText(getItemName(position), TextView.BufferType.EDITABLE);
        alertDialogBuilder.setView(input);
        alertDialogBuilder.setCancelable(false);
        final int mSavedPosition = position;//Save position because no reference on edit text
        alertDialogBuilder.setTitle("Edit name"); //Set the title of the box
        alertDialogBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Shut Keyboard after edit text so app want crash
                View view = mParentActivity.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) mParentActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

                String name = input.getText().toString();
                changePersonName(mSavedPosition, name);
                dialog.cancel(); //when they click dismiss we will dismiss the box
            }
        });
        alertDialogBuilder.setNegativeButton("delete person", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                deletePerson(mSavedPosition);
                dialog.cancel(); //when they click dismiss we will dismiss the box
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create(); //create the box
        alertDialog.show(); //actually show the box
    }


    private void deletePerson(int position) {
        if(position < GenerateRosterActivity.mADArray.size() && position >= 0) {
            GenerateRosterActivity.mADArray.remove(position);
            notifyDataSetChanged();
        }
    }

    private void changePersonName(int position, String newName) {
        if (position < GenerateRosterActivity.mADArray.size() && position >= 0) {
            ADBean newDuty = GenerateRosterActivity.mADArray.get(position);
            newDuty.setName(newName);
            GenerateRosterActivity.mADArray.set(position, newDuty);
            notifyDataSetChanged();
        }
    }

    private String getItemName(int position) {
        return GenerateRosterActivity.mADArray.get(position).getName();
    }

}
