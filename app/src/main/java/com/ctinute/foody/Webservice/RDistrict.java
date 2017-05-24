package com.ctinute.foody.Webservice;

import com.ctinute.foody.Object.District;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class RDistrict {
    public static ArrayList<District> getDistrictList() {
        final ArrayList<District> districtArrayList = new ArrayList<>();
        RestClient.get("category", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        districtArrayList.add(new District(response.getJSONObject(i)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return districtArrayList;
    }

    public static ArrayList<District> getDistrictList(int cityId) {
        final ArrayList<District> districtArrayList = new ArrayList<>();
        RestClient.get("category?cityId="+cityId, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        districtArrayList.add(new District(response.getJSONObject(i)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return districtArrayList;
    }
}
