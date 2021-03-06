package com.ctinute.foody.View.Adapters;

import android.content.Context;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.ctinute.foody.View.CustomView.ExpandableHeightGridView;
import com.ctinute.foody.Object.ItemWhere;
import com.ctinute.foody.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class RecyclerViewWhereAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_SLIDE = 0;
    private static final int TYPE_GRID = 1;
    private static final int TYPE_NULL = 2;
    private static final int TYPE_ITEM = 3;

    private Context mContext;
    private ArrayList<ItemWhere> itemList;

    private BaseAdapter gridAdapter;    // adapter cho GridView
    private PagerAdapter slideAdapter;  // adapter cho ViewPager (Image Slide)

    public RecyclerViewWhereAdapter(Context mContext, ArrayList<ItemWhere> itemList, BaseAdapter gridAdapter, PagerAdapter slideAdapter) {
        this.mContext = mContext;
        this.itemList = itemList;
        this.slideAdapter = slideAdapter;
        this.gridAdapter = gridAdapter;
    }

    public void appendData(List<ItemWhere> items){
        int index = this.itemList.size();
        this.itemList.addAll(items);
        notifyItemRangeInserted(index+4,items.size());
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
                viewHolder = new ViewHolderSlide(view);
                break;
            case TYPE_GRID:
                view = inflater.inflate(R.layout.recycler_grid, parent, false);
                viewHolder = new ViewHolderGrid(view);
                break;
            case TYPE_NULL:
                view = inflater.inflate(R.layout.layout_item_error, parent, false);
                viewHolder = new ViewHolderNull(view);
                break;
            case TYPE_ITEM:
                view = inflater.inflate(R.layout.recycler_item_where, parent, false);
                viewHolder = new ViewHolderItem(view);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        // Gan du lieu cho cac ViewHolder
        switch (viewHolder.getItemViewType()){
            case TYPE_SLIDE:
                final ViewHolderSlide viewHolderSlide = (ViewHolderSlide) viewHolder;
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
                ViewHolderGrid viewHolderGrid = (ViewHolderGrid) viewHolder;
                viewHolderGrid.gridView.setExpanded(true);
                viewHolderGrid.gridView.setAdapter(gridAdapter);
                break;

            case TYPE_NULL:
                ViewHolderNull viewHolderNull = (ViewHolderNull) viewHolder;
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
                ViewHolderItem viewHolderItem = (ViewHolderItem) viewHolder;
                ItemWhere itemWhere = itemList.get(i-3);

                viewHolderItem.textViewAvgRating.setText(String.valueOf((double) Math.round(itemWhere.getAvgRating() * 10) / 10));
                viewHolderItem.textViewLabel.setText(itemWhere.getName());
                viewHolderItem.textViewAddress.setText(itemWhere.getAddress());
                viewHolderItem.imageView.setImageBitmap(itemWhere.getImage());
                viewHolderItem.imageView.setDrawingCacheEnabled(true);
                viewHolderItem.buttonOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext,"Tính năng chưa hoàn thiện !",Toast.LENGTH_SHORT).show();
                    }
                });
                viewHolderItem.textViewComment.setText(String.valueOf(itemWhere.getTotalReviews()));
                viewHolderItem.textViewPhoto.setText(String.valueOf(itemWhere.getTotalPictures()));
                // TODO: xu li trang thai dua vao gio dong mo cua trong database (can sua lai database)
                viewHolderItem.textViewStatus.setText(String.valueOf("Đang mở cửa"));
                viewHolderItem.imageViewStatus.setColorFilter(ContextCompat.getColor(mContext,R.color.colorSuccess));
                //Log.w("log","created item");
                break;
        }
    }

    // Slide
    private static class ViewHolderSlide extends RecyclerView.ViewHolder{
        ViewPager viewPager;
        ViewHolderSlide(View itemView) {
            super(itemView);
            viewPager = (ViewPager) itemView.findViewById(R.id.viewPager_slide);
        }
    }

    // Grid menu
    private static class ViewHolderGrid extends RecyclerView.ViewHolder {
        ExpandableHeightGridView gridView;
        ViewHolderGrid(View itemView) {
            super(itemView);
            gridView = (ExpandableHeightGridView) itemView.findViewById(R.id.gridView_category);
        }
    }

    // Item
    private static class ViewHolderItem extends RecyclerView.ViewHolder {
        TextView textViewAvgRating;
        TextView textViewLabel;
        TextView textViewAddress;
        VideoView videoView ;
        ImageView imageView;
        LinearLayout buttonOrder;
        TextView textViewComment;
        TextView textViewPhoto;
        TextView textViewStatus;
        ImageView imageViewStatus;

        ViewHolderItem(View v) {
            super(v);
            textViewAvgRating = (TextView) v.findViewById(R.id.item_text_avgRating);
            textViewLabel = (TextView) v.findViewById(R.id.item_text_label);
            textViewAddress = (TextView) v.findViewById(R.id.item_text_address);
            videoView = (VideoView) v.findViewById(R.id.item_video);
            imageView = (ImageView) v.findViewById(R.id.item_image);
            buttonOrder = (LinearLayout) v.findViewById(R.id.item_button_oderNNow);
            textViewComment = (TextView) v.findViewById(R.id.item_text_comment);
            textViewPhoto = (TextView) v.findViewById(R.id.item_text_photo);
            textViewStatus = (TextView) v.findViewById(R.id.item_text_status);
            imageViewStatus = (ImageView) v.findViewById(R.id.item_image_status);
        }
    }

    // Null Item
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
