package com.ctinute.foody.Object;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Street implements Serializable {
    private int id;
    private String name;
    private int districtID;

    public Street (){}
    public Street (int id, String name) {
        this.id = id;
        this.name = name;
    }
    public Street (int id, String name, int districtID) {
        this.id = id;
        this.name = name;
        this.districtID = districtID;
    }
    public Street (JSONObject jsonObject){
        try {
            this.id = jsonObject.getInt("id");
            this.name = jsonObject.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    public int getDistrictID() {
        return districtID;
    }

    public void setDistrictID(int districtID) {
        this.districtID = districtID;
    }
}
