package com.ctinute.foody.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ctinute.foody.Objects.District;
import com.ctinute.foody.Objects.Street;
import com.ctinute.foody.R;

import java.util.List;



public class ListViewDistrictAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<District> districtList;

    public ListViewDistrictAdapter(Context c, List<District> districtList){
        mContext =c;
        this.districtList = districtList;
    }

    @Override
    public int getGroupCount() {
        return districtList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return districtList.get(groupPosition).getStreetList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return districtList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return districtList.get(groupPosition).getStreetList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        District districtToDisplay = districtList.get(groupPosition);

        View listItem;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            listItem = inflater.inflate(R.layout.listview_district_item, parent, false);
        } else {
            listItem = convertView;
        }

        TextView textViewDistrictName = (TextView) listItem.findViewById(R.id.item_text_cname);
        TextView textViewDistrictId = (TextView) listItem.findViewById(R.id.item_text_did);
        TextView textViewNumberOfStreet = (TextView) listItem.findViewById(R.id.item_text_numberOfStreet);
        LinearLayout buttonStreet = (LinearLayout) listItem.findViewById(R.id.item_button_street);

        textViewDistrictName.setText(districtToDisplay.getName());
        textViewDistrictId.setText(String.valueOf(districtToDisplay.getId()));
        String strToDisplay = String.valueOf(districtToDisplay.getStreetList().size())+" đường";
        textViewNumberOfStreet.setText(strToDisplay);

        // mo rong khi nhan nut ben phai
        final boolean fisExpanded = isExpanded;
        final ViewGroup fparent = parent;
        final int fgroupPosition = groupPosition;
        buttonStreet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fisExpanded)
                    ((ExpandableListView) fparent).collapseGroup(fgroupPosition);
                else
                    ((ExpandableListView) fparent).expandGroup(fgroupPosition, true);
            }
        });
        return listItem;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Street streetToDisplay = districtList.get(groupPosition).getStreetList().get(childPosition);

        View listItem;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            listItem = inflater.inflate(R.layout.listview_item_text_and_id,  parent, false);
        } else {
            listItem = convertView;
        }

        TextView textView = (TextView) listItem.findViewById(R.id.item_text_streetname);
        TextView textViewId = (TextView) listItem.findViewById(R.id.item_text_streetid);
        textView.setText(streetToDisplay.getName());
        textViewId.setText(String.valueOf(streetToDisplay.getId()));

        return listItem;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
