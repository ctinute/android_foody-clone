package com.ctinute.foody.View.Adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ctinute.foody.Object.Type;
import com.ctinute.foody.R;

import java.util.ArrayList;


public class ListViewTypeAdapter extends BaseAdapter {

    private Context mContext;
    private int selectedIndex;
    private ArrayList<Type> types;

    public ListViewTypeAdapter(Context mContext, ArrayList<Type> types) {
        super();
        this.mContext = mContext;
        this.types = types;
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return types.size();
    }

    @Override
    public Object getItem(int position) {
        return types.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem;
        Type currentType = types.get(position);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            listItem = inflater.inflate(R.layout.layout_item_image_text_tag, parent, false);
        } else {
            listItem = convertView;
        }

        ImageView icon = (ImageView) listItem.findViewById(R.id.listView_filter_item_icon);
        TextView label = (TextView) listItem.findViewById(R.id.listView_filter_item_text);
        TextView tag = (TextView) listItem.findViewById(R.id.listView_filter_item_tag);

        icon.setImageBitmap(currentType.getImage());
        label.setText(currentType.getName());
        if (currentType.isNew())
            tag.setVisibility(View.VISIBLE);
        else
            tag.setVisibility(View.GONE);

        if (position == selectedIndex){
            label.setTextColor(ContextCompat.getColor(mContext,R.color.colorPrimary));
            icon.setColorFilter(ContextCompat.getColor(mContext,R.color.colorAccent));
        }
        else {
            label.setTextColor(ContextCompat.getColor(mContext,R.color.textColorMain));
            icon.setColorFilter(ContextCompat.getColor(mContext,R.color.textColorMain));
        }
        return listItem;
    }
}
