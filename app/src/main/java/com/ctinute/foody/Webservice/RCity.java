package com.ctinute.foody.Webservice;

import com.ctinute.foody.Object.City;
import com.loopj.android.http.*;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class RCity {
    public static ArrayList<City> getCityList() {
        final ArrayList<City> cityArrayList = new ArrayList<>();
        RestClient.get("city", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        cityArrayList.add(new City(response.getJSONObject(i)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return cityArrayList;
    }
}
