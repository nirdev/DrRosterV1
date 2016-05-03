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
        view = inflater.inflate(R.layout.fragment_draggable_call, container, false);

        setMenuTitle();

        mDragListView = (DragListView) view.findViewById(R.id.drag_list_view_first_call);
        mDragListView.getRecyclerView().setVerticalScrollBarEnabled(true);


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

    private void setMenuTitle() {
        String title = getString(R.string.default_menu_title);

        switch (getTag()) {
            case GenerateRosterActivity.FRAGMENT_PEOPLE_LIST_FIRST_CALL_INDEX + "":
                title = getString(R.string.genros_first_call_menu_title);
                break;
            case GenerateRosterActivity.FRAGMENT_PEOPLE_LIST_SECOND_CALL_INDEX + "":
                title = getString(R.string.genros_second_call_menu_title);
                break;
            case GenerateRosterActivity.FRAGMENT_PEOPLE_LIST_THIRD_CALL_INDEX + "":
                title = getString(R.string.genros_third_call_menu_title);
                break;
            case GenerateRosterActivity.FRAGMENT_DATEABLE_LIST_INDEX + "":
                title = getString(R.string.genros_leave_menu_title);
                break;
        }

        //Set menu title in the activity
        ((GenerateRosterActivity) getActivity()).setActionBarTitle(title);
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