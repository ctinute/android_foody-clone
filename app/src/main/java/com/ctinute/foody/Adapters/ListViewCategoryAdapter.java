package com.ctinute.foody.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ctinute.foody.Objects.Category;
import com.ctinute.foody.R;

import java.util.ArrayList;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class ListViewCategoryAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Category> categories;
    private int selectedIndex;

    public ListViewCategoryAdapter(Context mContext, ArrayList<Category> categories) {
        this.mContext = mContext;
        this.categories = categories;
    }

    public void setSelectedIndex(int index){
        this.selectedIndex = index;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int position) {
        return categories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return categories.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView;
        Category currCategory = categories.get(position);

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = inflater.inflate(R.layout.layout_item_image_text_tick, parent, false);
        }
        else
            itemView = convertView;

        ImageView imageView = (ImageView) itemView.findViewById(R.id.item_image);
        TextView textView = (TextView) itemView.findViewById(R.id.item_text);
        ImageView check = (ImageView) itemView.findViewById(R.id.item_check);

        if (currCategory.getImage() == null)
            imageView.setVisibility(GONE);
        else {
            int imageResource = mContext.getResources().getIdentifier(currCategory.getImage(), "drawable", mContext.getPackageName());
            imageView.setImageResource(imageResource);
            imageView.setVisibility(VISIBLE);
        }
        textView.setText(currCategory.getName());

        // selected/not seleted style
        if (position == selectedIndex) {
            textView.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            check.setVisibility(VISIBLE);
        }
        else {
            textView.setTextColor(mContext.getResources().getColor(R.color.textColorMain));
            check.setVisibility(GONE);
        }

        return itemView;
    }
}
