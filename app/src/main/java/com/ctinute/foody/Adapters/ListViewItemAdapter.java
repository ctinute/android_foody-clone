package com.ctinute.foody.Adapters;

import android.content.Context;
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

import com.ctinute.foody.CustomView.ExpandableHeightGridView;
import com.ctinute.foody.Objects.WhereItem;
import com.ctinute.foody.R;

import java.util.ArrayList;


public class ListViewItemAdapter extends BaseAdapter {

    private static final int TYPE_SPEC = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_MAX_COUNT = 2;

    private Context mContext;
    private ArrayList<WhereItem> itemList;

    public ListViewItemAdapter(Context mContext, ArrayList<WhereItem> itemList) {
        this.mContext = mContext;
        this.itemList = itemList;
    }


    @Override
    public int getItemViewType(int position) {
        return position==0?0:1;
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_MAX_COUNT;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        if (position==0)
            return null;
        return itemList.get(position-1);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            holder = new ViewHolder();
            switch (type) {
                case TYPE_SPEC:
                    convertView = inflater.inflate(R.layout.recycler_where_grid, null);

                    ExpandableHeightGridView gridView = (ExpandableHeightGridView) convertView.findViewById(R.id.gridView_category);
                    String[] gridViewLabelList = {
                            "Gần tôi", "Coupon","Đặt chỗ ưu đãi","Đặt giao hàng","E-card","Game & Fun","Bình luận", "Blogs", "Top thành viên", "Video"};
                    int[] gridViewImageList = {
                            R.drawable.ic_nearby,R.drawable.ic_ecoupon,R.drawable.ic_tablenow,R.drawable.ic_deli,R.drawable.ic_ecard,R.drawable.ic_game,R.drawable.ic_comments,R.drawable.ic_blog,R.drawable.ic_topuser,R.drawable.ic_video};
                    gridView.setExpanded(true);
                    GridViewMenuAdapter gridAdapter = new GridViewMenuAdapter(mContext,gridViewLabelList,gridViewImageList);
                    gridView.setAdapter(gridAdapter);
                    convertView.setTag(holder);
                    break;
                case TYPE_ITEM:
                    convertView = inflater.inflate(R.layout.recycler_where_item, null);

                    holder.textViewAvgRating = (TextView) convertView.findViewById(R.id.item_text_avgRating);
                    holder.textViewLabel = (TextView) convertView.findViewById(R.id.item_text_label);
                    holder.textViewAddress = (TextView) convertView.findViewById(R.id.item_text_address);
                    holder.videoView = (VideoView) convertView.findViewById(R.id.item_video);
                    holder.imageView = (ImageView) convertView.findViewById(R.id.item_image);
                    holder.buttonOrder = (LinearLayout) convertView.findViewById(R.id.item_button_oderNNow);
                    holder.textViewComment = (TextView) convertView.findViewById(R.id.item_text_comment);
                    holder.textViewPhoto = (TextView) convertView.findViewById(R.id.item_text_photo);
                    holder.textViewStatus = (TextView) convertView.findViewById(R.id.item_text_status);
                    holder.imageViewStatus = (ImageView) convertView.findViewById(R.id.item_image_status);
                    convertView.setTag(holder);
                    break;
            }
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        switch (type){
            case TYPE_SPEC:
                break;
            case TYPE_ITEM:
                WhereItem whereItem = itemList.get(position);
                holder.textViewAvgRating.setText(String.valueOf((double) Math.round(whereItem.getAvgRating() * 10) / 10));
                holder.textViewLabel.setText(whereItem.getName());
                holder.textViewAddress.setText(whereItem.getAddress());
                // TODO: xu li video
                if (!whereItem.getImg().equals("")) {
                    int imageResource = mContext.getResources().getIdentifier("fdi" + whereItem.getImg(), "drawable", mContext.getPackageName());
                    holder.imageView.setImageResource(imageResource);
                } else {
                    holder.imageView.setImageResource(R.drawable.fdi_null);
                }
                holder.buttonOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext, "Tính năng chưa hoàn thiện !", Toast.LENGTH_SHORT).show();
                    }
                });
                holder.textViewComment.setText(String.valueOf(whereItem.getTotalReviews()));
                holder.textViewPhoto.setText(String.valueOf(whereItem.getTotalPictures()));
                // TODO: xu li trang thai dua vao gio dong mo cua trong database (can sua lai database)
                holder.textViewStatus.setText(String.valueOf("Đang mở cửa"));
                holder.imageViewStatus.setColorFilter(mContext.getResources().getColor(R.color.colorSuccess));

                Log.w("log", "added: " + whereItem.getName());
                break;
        }

        return convertView;
    }

    private static class ViewHolder {
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
    }
}
