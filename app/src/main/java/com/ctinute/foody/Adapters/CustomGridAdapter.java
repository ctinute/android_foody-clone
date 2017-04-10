package com.ctinute.foody.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ctinute.foody.R;

public class CustomGridAdapter extends BaseAdapter{
    private Context mContext;
    private final String[] labelList;
    private final int[] imageList;

    public CustomGridAdapter(Context c, String[] labelList, int[] imageList ) {
        mContext = c;
        this.labelList = labelList;
        this.imageList = imageList;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return labelList.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridItem;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            gridItem = new View(mContext);
            gridItem = inflater.inflate(R.layout.grid_item, null);

            TextView textView = (TextView) gridItem.findViewById(R.id.gridView_category_item_text);
            ImageView imageView = (ImageView)gridItem.findViewById(R.id.gridView_category_item_img);
            textView.setText(labelList[position]);
            imageView.setImageResource(imageList[position]);

        } else {
            gridItem = (View) convertView;
        }

        return gridItem;
    }
}