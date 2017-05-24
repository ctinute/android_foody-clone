package com.ctinute.foody.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ctinute.foody.Object.Category;
import com.ctinute.foody.Object.District;
import com.ctinute.foody.Object.Type;
import com.ctinute.foody.R;
import com.ctinute.foody.Webservice.RestClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SplashActivity extends AppCompatActivity {

    private ArrayList<Type> types = new ArrayList<>();
    private ArrayList<Category> categories = new ArrayList<>();
    private ArrayList<District> districts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread myThread = new Thread(){
            @Override
            public void run() {
                try {
                    //preloadData();
                    sleep(2000);
                    toMainActivity();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();
    }

    // preload mot so du lieu
    private void preloadData(){
        final boolean[] allGood = {true,true,true};
        RestClient.get("type", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        types.add(new Type(response.getJSONObject(i),getBaseContext()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                allGood[0] = false;
            }
        });
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
                allGood[1] = false;
            }
        });
        RequestParams params = new RequestParams();
        params.add("cityid",String.valueOf(1));
        RestClient.get("district", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        districts.add(new District(response.getJSONObject(i)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                allGood[2] = false;
            }
        });
        while (allGood[0] && allGood[1] && allGood[2]){}
        toMainActivity();
    }

    // chuyen den main activity
    private void toMainActivity(){
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }
}
