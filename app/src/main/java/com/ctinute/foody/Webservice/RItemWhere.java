package com.ctinute.foody.Webservice;

import android.util.Log;

import com.ctinute.foody.Object.ItemWhere;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class RItemWhere {

    /*
    public static ArrayList<ItemWhere> getItemWhereList() {
        final ArrayList<ItemWhere> itemWhereArrayList = new ArrayList<>();
        RestClient.get("itemw", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.w("http","received: length: "+response.length());
                for (int i = 0; i < response.length(); i++) {
                    try {
                        itemWhereArrayList.add(new ItemWhere(response.getJSONObject(i)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return itemWhereArrayList;
    }

    public static ArrayList<ItemWhere> getItemWhereList(int cityId, int districtId, int streetId, int categoryId, int beginIndex) {
        ArrayList<ItemWhere> itemWhereArrayList = new ArrayList<>();
        RequestParams params = new RequestParams();
        params.put("cityid", cityId);
        params.put("districtid", districtId);
        params.put("streetid", streetId);
        params.put("categoryid", categoryId);
        params.put("beginindex", beginIndex);
        final ArrayList<ItemWhere> items =  itemWhereArrayList;
        RestClient.get("itemw", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.w("http","received: length: "+response.length());
                for (int i = 0; i < response.length(); i++) {
                    try {
                        items.add(new ItemWhere(response.getJSONObject(i)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.w("http","parsed: length: "+items.size());
            }
        });
        Log.w("http","return: length: "+itemWhereArrayList.size());
        return itemWhereArrayList;
    }
    */
}
