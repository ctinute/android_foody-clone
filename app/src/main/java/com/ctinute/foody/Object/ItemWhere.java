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

public class ItemWhere implements Serializable {
    private int id, categoryId, cityID, districtId, streetId, typeId;
    private Double avgRating;
    private String address, name, totalPictures, totalReviews;
    private Bitmap image;

    public ItemWhere(){}
    public ItemWhere(JSONObject jsonObject, final Context context, final ImageLoaderListener imageLoaderListener){
        try {
            this.id = jsonObject.getInt("id");
            this.name = jsonObject.getString("name");
            this.address = jsonObject.getString("address");
            final ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.loadImage(jsonObject.getString("image"), new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    image = loadedImage;
                    imageLoaderListener.onSuccess();
                }
                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    image = BitmapFactory.decodeResource(context.getResources(), R.drawable.fdi_null);
                    imageLoaderListener.onFailed();
                }
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    image = BitmapFactory.decodeResource(context.getResources(), R.drawable.fdi_loading);
                    //imageLoaderListener.onLoading();
                }
            });
            this.avgRating = jsonObject.getDouble("avgRating");
            this.totalReviews = jsonObject.getString("noReview");
            this.totalPictures = jsonObject.getString("noPhoto");
            this.cityID = jsonObject.getInt("cityID");
            this.districtId = jsonObject.getInt("districtId");
            this.streetId = jsonObject.getInt("streetId");
            this.categoryId = jsonObject.getInt("categoryId");
            this.typeId = jsonObject.getInt("typeId");
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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getCityID() {
        return cityID;
    }

    public void setCityID(int cityID) {
        this.cityID = cityID;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public int getStreetId() {
        return streetId;
    }

    public void setStreetId(int streetId) {
        this.streetId = streetId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public Double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(Double avgRating) {
        this.avgRating = avgRating;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getTotalPictures() {
        return totalPictures;
    }

    public void setTotalPictures(String totalPictures) {
        this.totalPictures = totalPictures;
    }

    public String getTotalReviews() {
        return totalReviews;
    }

    public void setTotalReviews(String totalReviews) {
        this.totalReviews = totalReviews;
    }
}
