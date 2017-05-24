package com.ctinute.foody.Object;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.ctinute.foody.R;
import com.ctinute.foody.View.Listener.ImageLoaderListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Type implements Serializable {
    private int id;
    private String name;
    private Bitmap image;
    private boolean isNew;

    public Type(){}
    public Type(JSONObject jsonObject, Context context){
        try {
            this.id = jsonObject.getInt("id");
            this.name = jsonObject.getString("name");
            int imageResource = context.getResources().getIdentifier(jsonObject.getString("image"), "drawable", context.getPackageName());
            this.image = BitmapFactory.decodeResource(context.getResources(), imageResource);
            this.setNew(jsonObject.getBoolean("new"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public Type(JSONObject jsonObject, Context context, ImageLoaderListener imageLoaderListener){
        try {
            this.id = jsonObject.getInt("id");
            this.name = jsonObject.getString("name");
            int imageResource = context.getResources().getIdentifier(jsonObject.getString("image"), "drawable", context.getPackageName());
            this.image = BitmapFactory.decodeResource(context.getResources(), imageResource);
            this.setNew(jsonObject.getBoolean("new"));
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

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }
}
