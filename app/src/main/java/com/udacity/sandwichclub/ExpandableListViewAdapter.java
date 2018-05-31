package com.udacity.sandwichclub;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by akshay on 30/5/18.
 */

public class ExpandableListViewAdapter extends BaseExpandableListAdapter {

    private Context context;

    // List to store the Header titles
    private List<String> expandableListViewDataHeader;

    // The child data (Stored in HashMap) is in format of <header title, child title>
    private HashMap<String, List<String>> expandableListViewChildData;

    //Initializing the passed arguments using a constructor
    public ExpandableListViewAdapter(Context context, List<String> expandableListViewDataHeader,
                                 HashMap<String, List<String>> expandableListViewChildData) {
        this.context = context;
        this.expandableListViewDataHeader = expandableListViewDataHeader;
        this.expandableListViewChildData = expandableListViewChildData;
    }


    @Override
    public int getGroupCount() {
        return expandableListViewDataHeader.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return expandableListViewChildData.get(expandableListViewDataHeader.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
       return expandableListViewDataHeader.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return expandableListViewChildData.get(expandableListViewDataHeader.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    //This method returns the view for the list group header
    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String headerTitle = (String) getGroup(i);
        if (view == null) {
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.expandable_list_group_header, null);
        }

        TextView headerListTV = view.findViewById(R.id.expandableListViewHeader);
        headerListTV.setTypeface(null, Typeface.BOLD);
        headerListTV.setText(headerTitle);

        return view;
    }

    //This method returns the view for the list child items
    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {

         String childText = (String) getChild(i, i1);

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.expandable_list_item, null);
        }

        TextView childListTV =  view.findViewById(R.id.expandableListViewItem);

        childListTV.setText(childText);
        return view;

    }

    //This method tells whether the child view is selectable/clickable or not
    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
