package com.ctinute.foody.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ctinute.foody.Objects.City;
import com.ctinute.foody.R;

import java.util.ArrayList;

public class ListViewCityAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<City> cityList;
    private int selectedIndex = -1;

    public ListViewCityAdapter(Context mContext, ArrayList<City> cityList) {
        this.mContext = mContext;
        this.cityList = cityList;
    }

    public ListViewCityAdapter(Context mContext, ArrayList<City> cityList, int selectedCityId) {
        this.mContext = mContext;
        this.cityList = cityList;
        for (int i=0;i<cityList.size();i++)
            if (selectedCityId == cityList.get(i).getId())
                selectedIndex = i;
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return cityList.size();
    }

    @Override
    public Object getItem(int position) {
        return cityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return cityList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView;

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = inflater.inflate(R.layout.layout_item_text_tick, parent, false);
        }
        else {
            itemView = convertView;
        }

        TextView textView = (TextView) itemView.findViewById(R.id.item_text);
        ImageView check = (ImageView) itemView.findViewById(R.id.item_check);

        textView.setText(cityList.get(position).getName());

        // selected/not selected style
        if (position == selectedIndex) {
            textView.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            check.setVisibility(View.VISIBLE);
        }
        else {
            textView.setTextColor(mContext.getResources().getColor(R.color.textColorMain));
            check.setVisibility(View.GONE);
        }

        return itemView;
    }
}
