package com.ctinute.foody.Webservice;

import android.content.Context;

import com.ctinute.foody.Object.Type;
import com.ctinute.foody.View.Listener.AsyncHttpListener;
import com.ctinute.foody.View.Listener.ImageLoaderListener;
import com.loopj.android.http.*;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class RType {
    public static void getTypeList(final Context context, final AsyncHttpListener asyncHttpListener, final ImageLoaderListener imageLoaderListener) {
        RestClient.get("type", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                asyncHttpListener.onSend();
                ArrayList<Type> typeArrayList = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        typeArrayList.add(new Type(response.getJSONObject(i), context, imageLoaderListener));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                asyncHttpListener.onFinish(typeArrayList);
            }
        });
    }
}
