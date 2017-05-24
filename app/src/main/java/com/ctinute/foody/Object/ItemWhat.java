package com.ctinute.foody.Object;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.ctinute.foody.R;
import com.ctinute.foody.View.Listener.ImageLoaderListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class ItemWhat implements Serializable {
    private int id;
    private Bitmap image;
    private String foodName;
    private String locationName;
    private String address;
    private String userName;
    private Bitmap userImg;
    private String date;

    public ItemWhat(){}
    public ItemWhat(JSONObject jsonObject, final Context context, final ImageLoaderListener imageLoaderListener){
        try {
            this.id = jsonObject.getInt("id");
            final ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.loadImage(jsonObject.getString("image"), new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    image = loadedImage;
                    imageLoaderListener.onSuccess();
                }
                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    image = BitmapFactory.decodeResource(context.getResources(), R.drawable.fdi_loading);
                    imageLoaderListener.onFailed();
                }

                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    image = BitmapFactory.decodeResource(context.getResources(), R.drawable.fdi_null);
                    imageLoaderListener.onLoading();
                }
            });
            this.foodName = jsonObject.getString("foodName");
            this.locationName = jsonObject.getString("locationName");
            this.address = jsonObject.getString("address");
            this.userName = jsonObject.getString("userName");
            imageLoader.loadImage(jsonObject.getString("userImg"), new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    userImg = loadedImage;
                    imageLoaderListener.onSuccess();
                }
                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    userImg = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_user_circle);
                    imageLoaderListener.onFailed();
                }

                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    userImg = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_user_circle);
                    imageLoaderListener.onLoading();
                }
            });
            this.date = jsonObject.getString("date");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public void setUserImg(Bitmap userImg) {
        this.userImg = userImg;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Bitmap getUserImg() {
        return userImg;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
