package com.ctinute.foody.Adapters;

import android.content.Context;
import android.net.LinkAddress;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.ctinute.foody.Objects.WhereItem;
import com.ctinute.foody.R;

import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;
    int layout;
    List<WhereItem> items;

    // ViewHolder class <=> item trong recycler view
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewAvgRating, textViewLabel, textViewAddress, textViewComments, textViewPhotos, textViewStatus;
        ImageView imageView;
        VideoView videoView;
        LinearLayout buttonDeliverNow;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewAvgRating = (TextView) itemView.findViewById(R.id.item_text_avgRating);
            textViewLabel = (TextView) itemView.findViewById(R.id.item_text_label);
            textViewAddress = (TextView) itemView.findViewById(R.id.item_text_address);
            textViewComments = (TextView) itemView.findViewById(R.id.item_text_comment);
            textViewPhotos = (TextView) itemView.findViewById(R.id.item_text_photo);
            textViewStatus = (TextView) itemView.findViewById(R.id.item_text_status);
            imageView = (ImageView) itemView.findViewById(R.id.item_image);
            videoView = (VideoView) itemView.findViewById(R.id.item_video);
            buttonDeliverNow = (LinearLayout) itemView.findViewById(R.id.item_button_oderNNow);
        }
    }

    public RecyclerViewAdapter(Context context, int layout, List<WhereItem> items) {
        this.context = context;
        this.layout = layout;
        this.items = items;
    }

    // tao view (item) moi
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(layout,parent,false);
        return new ViewHolder(view);
    }

    // thay doi noi dung view
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        /*
        WhereItem item = items.get(position);
        if(!item.getImg().equals("")){
            // String uri = "drawable/fdi"+item.getImg();
            int imageResource = context.getResources().getIdentifier("fdi"+item.getImg(), "drawable", context.getPackageName());
            //Drawable image = context.getResources().getDrawable(imageResource);
            if(imageResource != 0){
                Picasso.with(context).load(imageResource).fit().centerInside().into(holder.imgItem);
            }else{
                Picasso.with(context).load(R.drawable.fdi1).fit().centerInside().into(holder.imgItem);
            }

        }else{
            holder.imgItem.setImageDrawable(null);
        }
        holder.txtName.setText(item.getName().toString());
        holder.txtAvgRating.setText(String.format("%.1f",item.getAvgRating())+"");
        holder.txtAddress.setText(item.getAddress().toString());
        holder.btnCountComment.setText(String.valueOf(item.getTotalReviews()));
        holder.btnCountPicture.setText(String.valueOf(item.getTotalPictures()));

        ReviewAdapter reviewAdapter = new ReviewAdapter(context,R.layout.custom_one_row_reivew,item.getReviews());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        holder.recyclerReview.setLayoutManager(layoutManager);
        holder.recyclerReview.setAdapter(reviewAdapter);
        reviewAdapter.notifyDataSetChanged();
        */
    }

    // Return the size of dataset
    @Override
    public int getItemCount() {
        return items.size();
    }
}
