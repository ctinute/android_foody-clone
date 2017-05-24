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

public class User{
    private int id;
    private String email;
    private String name;
    private Bitmap image;
    private String password;

    public User() {}

    public User(JSONObject jsonObject, final Context context) {
        try {
            this.id = jsonObject.getInt("id");
            this.email = jsonObject.getString("email");
            this.name = jsonObject.getString("name");
            this.password = jsonObject.getString("password");
            ImageLoader imageLoader = ImageLoader.getInstance();
            if (!jsonObject.getString("image").equals("")) {
                imageLoader.loadImage(jsonObject.getString("image"), new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        image = loadedImage;
                    }
                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                        image = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_user_circle);
                    }

                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                        image = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_user_circle);
                    }
                });
            }
            else
                image = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_user_circle);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public User(JSONObject jsonObject, final Context context, final ImageLoaderListener imageLoaderListener) {
        try {
            this.id = jsonObject.getInt("id");
            this.email = jsonObject.getString("email");
            this.name = jsonObject.getString("name");
            this.password = jsonObject.getString("password");
            ImageLoader imageLoader = ImageLoader.getInstance();
            if (!jsonObject.getString("image").equals("")) {
                imageLoader.loadImage(jsonObject.getString("image"), new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        image = loadedImage;
                        imageLoaderListener.onSuccess();
                    }
                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                        image = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_user_circle);
                        imageLoaderListener.onFailed();
                    }

                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                        image = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_user_circle);
                        imageLoaderListener.onLoading();
                    }
                });
            }
            else
                image = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_user_circle);
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
