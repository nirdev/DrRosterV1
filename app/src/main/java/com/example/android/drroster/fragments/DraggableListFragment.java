package com.example.android.drroster.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.drroster.R;
import com.example.android.drroster.activities.GenerateRosterActivity;
import com.example.android.drroster.adapters.ItemDragListAdapter;
import com.woxthebox.draglistview.DragItem;
import com.woxthebox.draglistview.DragListView;

public class DraggableListFragment extends Fragment {

    private DragListView mDragListView;

    View view;
    public DraggableListFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_first_call, container, false);



        mDragListView = (DragListView) view.findViewById(R.id.drag_list_view_first_call);
        mDragListView.getRecyclerView().setVerticalScrollBarEnabled(true);

        //Todo:Delete when finish list
        mDragListView.setDragListListener(new DragListView.DragListListener() {
            @Override
            public void onItemDragStarted(int position) {
            }

            @Override
            public void onItemDragEnded(int fromPosition, int toPosition) {
                if (fromPosition != toPosition) {
                }
            }
        });

        setupListRecyclerView();

        return view;
    }

    @Override
    public void onDestroyView() {
        //TODo: handel DB here with gettag() - check this is best practice place to handle db in fragment life cycle

        super.onDestroyView();
    }

    public void setupListRecyclerView() {
        mDragListView.setLayoutManager(new LinearLayoutManager(getContext()));
        ItemDragListAdapter listAdapter = new ItemDragListAdapter(
                GenerateRosterActivity.mPeopleArray,
                R.layout.item_list_draggable,
                R.id.image,
                false,
                getTag(),
                getContext(),
                getActivity());
        mDragListView.setAdapter(listAdapter, true);
        mDragListView.setCanDragHorizontally(false);
        mDragListView.setCustomDragItem(new MyDragItem(getContext(), R.layout.item_list_draggable));
    }



    private static class MyDragItem extends DragItem {

        public MyDragItem(Context context, int layoutId) {
            super(context, layoutId);
        }

        @Override
        public void onBindDragView(View clickedView, View dragView) {
            CharSequence text = ((TextView) clickedView.findViewById(R.id.text)).getText();
            ((TextView) dragView.findViewById(R.id.text)).setText(text);
            dragView.setBackgroundColor(dragView.getResources().getColor(R.color.colorTransparent));
        }
    }


}