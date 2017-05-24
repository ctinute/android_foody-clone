package com.ctinute.foody.Object;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class District implements Serializable{

    private int id;
    private String name;
    private List<Street> streetList;
    private int cityId;

    public District(){}

    public District(JSONObject jsonObject){
        try {
            this.id = jsonObject.getInt("id");
            this.name = jsonObject.getString("name");
            JSONArray jsonStreets  = jsonObject.getJSONArray("streetList");
            this.streetList = new ArrayList<>();
            for (int i=0; i<jsonStreets.length(); i++){
                Street street = new Street(jsonStreets.getJSONObject(i));
                street.setDistrictID(this.id);
                this.streetList.add(street);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public District(int id, String name) {
        this.id = id;
        this.name = name;
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

    public List<Street> getStreetList() {
        return streetList;
    }

    public void setStreetList(List<Street> streetList) {
        this.streetList = streetList;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
}
