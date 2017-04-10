package com.ctinute.foody.Objects;

public class Street {
    private int id;
    private String name;
    private int cityID;

    public Street (){}
    public Street (int id, String name) {
        this.id = id;
        this.name = name;
    }
    public Street (int id, String name, int cityID) {
        this.id = id;
        this.name = name;
        this.cityID = cityID;
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

    public int getCityID() {
        return cityID;
    }

    public void setCityID(int cityID) {
        this.cityID = cityID;
    }
}
