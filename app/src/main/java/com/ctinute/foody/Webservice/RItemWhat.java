package com.ctinute.foody.Webservice;

import com.ctinute.foody.Object.ItemWhat;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class RItemWhat {
    /*
    public static ArrayList<ItemWhat> getItemWhatList() {
        final ArrayList<ItemWhat> itemWhatArrayList = new ArrayList<>();
        RestClient.get("type", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        itemWhatArrayList.add(new ItemWhat(response.getJSONObject(i)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return itemWhatArrayList;
    }

    public static ArrayList<ItemWhat> getItemWhatList(int cityId, int districtId, int streetId, int categoryId, int beginIndex) {
        final ArrayList<ItemWhat> itemWhatArrayList = new ArrayList<>();
        RestClient.get("type", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        itemWhatArrayList.add(new ItemWhat(response.getJSONObject(i)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return itemWhatArrayList;
    }
    */
}
