package com.example.android.drroster.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.android.drroster.R;
import com.example.android.drroster.models.ADBean;

import java.util.ArrayList;

/**
 * Created by Nir on 4/13/2016.
 */
public class ADItemListAdapter extends ArrayAdapter<ADBean> {

    Context context;
    ArrayList <ADBean> modelItems;

    public ADItemListAdapter(Context context, ArrayList<ADBean> resource) {
        super(context, R.layout.item_addition_dutie_list,resource);

        this.context = context;
        this.modelItems = resource;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        //inflate the layout
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.item_addition_dutie_list,parent,false);

        //Find the views
        TextView name = (TextView) convertView.findViewById(R.id.text_AD);
        final CheckBox cb = (CheckBox) convertView.findViewById(R.id.checkbox_draggable_list_item_AD);

        //Set the views
        name.setText(modelItems.get(position).getName());
        cb.setActivated(modelItems.get(position).getIsChecked());

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ADBean temp = new ADBean();
                modelItems.set(position,temp);
                notifyDataSetChanged();
            }
        });

        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyDataSetChanged();
            }
        });

        return convertView;
    }
}
