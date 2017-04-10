package com.ctinute.foody.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.ctinute.foody.Objects.WhereItem;
import com.ctinute.foody.R;

import java.util.ArrayList;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private int layout;
    private ArrayList<WhereItem> itemList;

    public RecyclerViewAdapter(Context mContext, int layout, ArrayList<WhereItem> itemList) {
        this.mContext = mContext;
        this.layout = layout;
        this.itemList = itemList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        WhereItem whereItem = itemList.get(i);

        viewHolder.textViewAvgRating.setText(String.valueOf((double) Math.round(whereItem.getAvgRating() * 10) / 10));
        viewHolder.textViewLabel.setText(whereItem.getName());
        viewHolder.textViewAddress.setText(whereItem.getAddress());
        // TODO: xu li video
        if (!whereItem.getImg().equals("")){
            int imageResource = mContext.getResources().getIdentifier("fdi"+whereItem.getImg(), "drawable", mContext.getPackageName());
            viewHolder.imageView.setImageResource(imageResource);
        }
        else {
            viewHolder.imageView.setImageResource(R.drawable.fdi_null);
        }
        viewHolder.buttonOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"Tính năng chưa hoàn thiện !",Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.textViewComment.setText(String.valueOf(whereItem.getTotalReviews()));
        viewHolder.textViewPhoto.setText(String.valueOf(whereItem.getTotalPictures()));
        // TODO: xu li trang thai dua vao gio dong mo cua trong database (can sua lai database)
        viewHolder.textViewStatus.setText(String.valueOf("Đang mở cửa"));
        viewHolder.imageViewStatus.setColorFilter(mContext.getResources().getColor(R.color.colorSuccess));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
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

        ViewHolder(View v) {
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
}
