package com.ctinute.foody;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ctinute.foody.Adapters.ListViewCityAdapter;
import com.ctinute.foody.Database.CityDB;
import com.ctinute.foody.Objects.City;

import java.util.ArrayList;

public class SelectCityActivity extends AppCompatActivity {

    //private int selectedCountryId;
    private City selectedCity;
    private int selectedCityId;

    ArrayList<City> cityList;
    CityDB cityDB;
    ListView listViewCity;
    ListViewCityAdapter listViewCityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);

        cityDB = new CityDB(MainActivity.database);

        selectedCityId = getIntent().getIntExtra("selectedCityId",0);

        // lay danh sach tinh/thanh pho
        cityList = cityDB.getCityList();
        listViewCity = (ListView) findViewById(R.id.listView_city);
        listViewCityAdapter = new ListViewCityAdapter(getApplicationContext(), cityList, selectedCityId);
        listViewCity.setAdapter(listViewCityAdapter);

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
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                cityList = cityDB.getCityList(String.valueOf(s));
                listViewCityAdapter = new ListViewCityAdapter(getApplicationContext(), cityList, selectedCityId);
                listViewCity.setAdapter(listViewCityAdapter);
            }
        });

        // xu li tu dong lay vi tri
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
                intent.putExtra("isCityChanged",true);
                intent.putExtra("selectedCityId",selectedCity.getId());
                intent.putExtra("selectedCityName",selectedCity.getName());
                setResult(11,intent);   // 11: // request code tu HomeWhereFragment
                finish();
            }
        });

        // huy
        ImageButton buttonBack = (ImageButton) findViewById(R.id.button_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("isCityChanged",false);
                setResult(11,intent);   // 11: // request code tu HomeWhereFragment
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent();
        intent.putExtra("isCityChanged",false);
        setResult(11,intent);   // 11: // request code tu HomeWhereFragment
        finish();
    }

    // thay doi tinh/thanh pho
    private void changeCity(int index){
        listViewCityAdapter.setSelectedIndex(index);
        selectedCity = (City) listViewCityAdapter.getItem(index);
    }
}
