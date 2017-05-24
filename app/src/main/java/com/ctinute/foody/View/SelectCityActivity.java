package com.ctinute.foody.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ctinute.foody.Object.City;
import com.ctinute.foody.R;
import com.ctinute.foody.View.Adapters.ListViewCategoryAdapter;
import com.ctinute.foody.View.Adapters.ListViewCityAdapter;
import com.ctinute.foody.Webservice.RestClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SelectCityActivity extends AppCompatActivity {

    //private int selectedCountryId;
    private City selectedCity;
    private int selectedCityId;

    ArrayList<City> cityList = new ArrayList<>();
    ListView listViewCity;
    ListViewCityAdapter listViewCityAdapter;

    //GoogleApiClient mGoogleApiClient;
    //Location mLastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);

        selectedCityId = getIntent().getIntExtra("selectedCityId", 0);

        listViewCity = (ListView) findViewById(R.id.listView_city);

        // lay danh sach tinh/thanh pho
        RestClient.get("city", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        cityList.add(new City(response.getJSONObject(i)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                listViewCityAdapter = new ListViewCityAdapter(getApplicationContext(), cityList, selectedCityId);
                listViewCity.setAdapter(listViewCityAdapter);
            }
        });

        // xu li chon tinh/thanh pho
        listViewCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                changeCity(position);
            }
        });

        // xu li tim kiem
        EditText editTextSearch = (EditText) findViewById(R.id.editText_search);
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                RequestParams params = new RequestParams();
                params.put("keyword", String.valueOf(s));
                RestClient.get("city", params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        cityList.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                cityList.add(new City(response.getJSONObject(i)));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        ((ListViewCityAdapter) listViewCity.getAdapter()).changeDataSet(cityList);
                    }
                });
            }
        });

        // xu li tu dong lay vi tri
        /* su dung google service
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {
                //mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
                //mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
            }
            LocationManager locationManager = (LocationManager)
                        getSystemService(Context.LOCATION_SERVICE);
        }*/
        RelativeLayout buttonAutoLocation = (RelativeLayout) findViewById(R.id.button_autoLocation);
        buttonAutoLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SelectCityActivity.this, "Tính năng chưa hoàn thiện", Toast.LENGTH_SHORT).show();
            }
        });

        // xu li doi quoc gia
        RelativeLayout buttonChangeCountry = (RelativeLayout) findViewById(R.id.button_changeCountry);
        buttonChangeCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SelectCityActivity.this, "Tính năng chưa hoàn thiện", Toast.LENGTH_SHORT).show();
            }
        });

        // xong:
        Button buttonDone = (Button) findViewById(R.id.button_done);
        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("isCityChanged", true);
                intent.putExtra("selectedCityId", selectedCity.getId());
                intent.putExtra("selectedCityName", selectedCity.getName());
                setResult(11, intent);   // 11: // request code tu HomeWhereFragment
                finish();
            }
        });

        // huy
        ImageButton buttonBack = (ImageButton) findViewById(R.id.button_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("isCityChanged", false);
                setResult(11, intent);   // 11: // request code tu HomeWhereFragment
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent();
        intent.putExtra("isCityChanged", false);
        setResult(11, intent);   // 11: // request code tu HomeWhereFragment
        finish();
    }

    // thay doi tinh/thanh pho
    private void changeCity(int index){
        listViewCityAdapter.setSelectedIndex(index);
        selectedCity = (City) listViewCityAdapter.getItem(index);
    }
}
