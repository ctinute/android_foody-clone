package com.ctinute.foody.View.Adapters;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ctinute.foody.View.CustomView.ExpandableHeightGridView;
import com.ctinute.foody.Object.ItemWhat;
import com.ctinute.foody.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class RecyclerViewWhatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_SLIDE = 0;
    private static final int TYPE_GRID = 1;
    private static final int TYPE_NULL = 2;
    public static final int TYPE_ITEM = 3;

    private Context mContext;
    private ArrayList<ItemWhat> itemList;

    private BaseAdapter gridAdapter;    // adapter cho GridView
    private PagerAdapter slideAdapter;  // adapter cho ViewPager (Image Slide)

    public RecyclerViewWhatAdapter(Context mContext, ArrayList<ItemWhat> itemList, BaseAdapter gridAdapter, PagerAdapter slideAdapter) {
        this.mContext = mContext;
        this.itemList = itemList;
        this.slideAdapter = slideAdapter;
        this.gridAdapter = gridAdapter;
    }

    // them du lieu vao list
    public void appendData(List<ItemWhat> items){
        int index = this.itemList.size();
        this.itemList.addAll(items);
        notifyItemRangeInserted(index,items.size());
    }

    @Override
    public int getItemViewType(int position)
    {
        switch (position) {
            case 0:
                return TYPE_SLIDE;
            case 1:
                return TYPE_GRID;
            case 2:
                return TYPE_NULL;
            default:
                return TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size()+3;
    }

    @Override
    public long getItemId(int position) {
        if (position == getItemCount())
            return 0;
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        //Log.w("log","onCreateViewHolder "+i);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;
        RecyclerView.ViewHolder viewHolder = null;
        switch (i) {
            case TYPE_SLIDE:
                view = inflater.inflate(R.layout.recycler_slide, parent, false);
                viewHolder = new RecyclerViewWhatAdapter.ViewHolderSlide(view);
                break;
            case TYPE_GRID:
                view = inflater.inflate(R.layout.recycler_grid, parent, false);
                viewHolder = new RecyclerViewWhatAdapter.ViewHolderGrid(view);
                break;
            case TYPE_NULL:
                view = inflater.inflate(R.layout.layout_item_error, parent, false);
                viewHolder = new RecyclerViewWhatAdapter.ViewHolderNull(view);
                break;
            case TYPE_ITEM:
                view = inflater.inflate(R.layout.recycler_item_what, parent, false);
                viewHolder = new RecyclerViewWhatAdapter.ViewHolderItem(view);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        //Log.w("log","onBindViewHolder "+i + " - type"+viewHolder.getItemViewType());
        switch (viewHolder.getItemViewType()){
            case TYPE_SLIDE:
                final RecyclerViewWhatAdapter.ViewHolderSlide viewHolderSlide = (RecyclerViewWhatAdapter.ViewHolderSlide) viewHolder;
                viewHolderSlide.viewPager.setAdapter(slideAdapter);

                // thiet lap tu dong slide
                final Handler handler = new Handler();
                final Runnable Update = new Runnable() {
                    int currentPage = 0;
                    public void run() {
                        if (currentPage == (viewHolderSlide.viewPager.getChildCount()-1))
                            currentPage = 0;
                        else
                            currentPage++;
                        viewHolderSlide.viewPager.setCurrentItem(currentPage, true);
                    }
                };
                Timer swipeTimer = new Timer();
                swipeTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.post(Update);
                    }
                }, 5000, 5000);
                break;

            case TYPE_GRID:
                RecyclerViewWhatAdapter.ViewHolderGrid viewHolderGrid = (RecyclerViewWhatAdapter.ViewHolderGrid) viewHolder;
                viewHolderGrid.gridView.setExpanded(true);
                viewHolderGrid.gridView.setAdapter(gridAdapter);
                break;

            case TYPE_NULL:
                RecyclerViewWhatAdapter.ViewHolderNull viewHolderNull = (RecyclerViewWhatAdapter.ViewHolderNull) viewHolder;
                if (itemList == null || itemList.size() == 0){
                    viewHolderNull.layout.setVisibility(View.VISIBLE);
                    viewHolderNull.imageView.setVisibility(View.VISIBLE);
                    viewHolderNull.textView.setVisibility(View.VISIBLE);
                }
                else {
                    viewHolderNull.layout.setVisibility(View.GONE);
                    viewHolderNull.imageView.setVisibility(View.GONE);
                    viewHolderNull.textView.setVisibility(View.GONE);
                }
                break;

            case TYPE_ITEM:
                RecyclerViewWhatAdapter.ViewHolderItem viewHolderItem = (RecyclerViewWhatAdapter.ViewHolderItem) viewHolder;
                ItemWhat itemWhat = itemList.get(i-3);
                viewHolderItem.imageView.setImageBitmap(itemWhat.getImage());
                viewHolderItem.textViewFoodName.setText(itemWhat.getFoodName());
                viewHolderItem.textViewLocationName.setText(itemWhat.getLocationName());
                viewHolderItem.textViewAddress.setText(itemWhat.getAddress());
                viewHolderItem.userImage.setImageBitmap(itemWhat.getUserImg());
                viewHolderItem.userName.setText(itemWhat.getUserName());
                viewHolderItem.date.setText(itemWhat.getDate());
                break;
        }
    }

    private static class ViewHolderSlide extends RecyclerView.ViewHolder{
        ViewPager viewPager;
        ViewHolderSlide(View itemView) {
            super(itemView);
            viewPager = (ViewPager) itemView.findViewById(R.id.viewPager_slide);
        }
    }

    private static class ViewHolderGrid extends RecyclerView.ViewHolder {
        ExpandableHeightGridView gridView;
        ViewHolderGrid(View itemView) {
            super(itemView);
            gridView = (ExpandableHeightGridView) itemView.findViewById(R.id.gridView_category);
        }
    }

    private static class ViewHolderItem extends RecyclerView.ViewHolder {
        TextView textViewFoodName;
        TextView textViewLocationName;
        TextView textViewAddress;
        ImageView imageView;
        ImageView userImage;
        TextView userName;
        TextView date;

        ViewHolderItem(View v) {
            super(v);
            textViewFoodName = (TextView) v.findViewById(R.id.item_food_name);
            textViewLocationName = (TextView) v.findViewById(R.id.item_location_name);
            textViewAddress = (TextView) v.findViewById(R.id.item_address);
            imageView = (ImageView) v.findViewById(R.id.item_image);
            userImage = (ImageView) v.findViewById(R.id.item_user_img);
            userName = (TextView) v.findViewById(R.id.item_user_name);
            date = (TextView) v.findViewById(R.id.item_date);
        }
    }

    private static class ViewHolderNull extends RecyclerView.ViewHolder {
        LinearLayout layout;
        ImageView imageView;
        TextView textView;

        ViewHolderNull(View v) {
            super(v);
            layout = (LinearLayout) v.findViewById(R.id.list_item_null);
            imageView = (ImageView) v.findViewById(R.id.list_item_image_null);
            textView = (TextView) v.findViewById(R.id.list_item_text_null);
        }
    }
}
