package com.example.whatsthewait.models;

import com.example.whatsthewait.RestaurantItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel(analyze = {Region.class})
public class Region {

    @SerializedName("center")
    @Expose
    private Center center;

    public Center getCenter() {
        return center;
    }

    public void setCenter(Center center) {
        this.center = center;
    }
}