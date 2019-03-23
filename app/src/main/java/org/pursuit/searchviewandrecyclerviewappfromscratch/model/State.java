package org.pursuit.searchviewandrecyclerviewappfromscratch.model;

//use below annotation for serializable

import com.google.gson.annotations.SerializedName;

public class State {

    private String name;
    private String capital;
    //use below when you want to change the word in the pojo
    @SerializedName("long")
    private String longitude;
    @SerializedName("lat")
    private String latitude;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
