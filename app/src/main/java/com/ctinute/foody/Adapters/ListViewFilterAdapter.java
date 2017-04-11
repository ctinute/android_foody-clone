package com.ctinute.foody.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ctinute.foody.R;


public class ListViewFilterAdapter extends BaseAdapter {

    private Context mContext;
    private String[] listViewNewsestLabelList;
    private int[] listViewNewsestDrawableList;
    private boolean[] listViewNewsestTagList;

    public ListViewFilterAdapter(Context mContext, String[] listViewNewsestLabelList, int[] listViewNewsestDrawableList, boolean[] listViewNewsestTagList) {
        super();
        this.mContext = mContext;
        this.listViewNewsestLabelList = listViewNewsestLabelList;
        this.listViewNewsestDrawableList = listViewNewsestDrawableList;
        this.listViewNewsestTagList = listViewNewsestTagList;
    }

    @Override
    public int getCount() {
        return listViewNewsestLabelList.length;
    }

    @Override
    public Object getItem(int position) {
        return listViewNewsestLabelList[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            listItem = inflater.inflate(R.layout.listview_filter_item, parent, false);
        } else {
            listItem = convertView;
        }

        ImageView icon = (ImageView) listItem.findViewById(R.id.listView_filter_item_icon);
        TextView label = (TextView) listItem.findViewById(R.id.listView_filter_item_text);
        TextView tag = (TextView) listItem.findViewById(R.id.listView_filter_item_tag);

        icon.setImageResource(listViewNewsestDrawableList[position]);
        label.setText(listViewNewsestLabelList[position]);
        if (listViewNewsestTagList[position])
            tag.setVisibility(View.VISIBLE);
        else
            tag.setVisibility(View.GONE);

        /*
        listItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Tính năng chưa hoàn thiện", Toast.LENGTH_SHORT).show();
            }
        });
            */
        return listItem;
    }
}
