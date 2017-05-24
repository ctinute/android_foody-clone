package com.ctinute.foody.Webservice;

import com.ctinute.foody.Object.Category;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class RCategory {

    ArrayList<Category> categories;

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public RCategory() {
        categories = new ArrayList<>();
        getCategoryList();
    }


    private void getCategoryList() {
        RestClient.get("category", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        categories.add(new Category(response.getJSONObject(i)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
