package com.example.whatsthewait.models;

import java.util.List;

import com.example.whatsthewait.RestaurantItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel(analyze = {Hour.class})
public class Hour {

    @SerializedName("open")
    @Expose
    private List<Open> open = null;
    @SerializedName("hours_type")
    @Expose
    private String hoursType;
    @SerializedName("is_open_now")
    @Expose
    private boolean isOpenNow;

    public List<Open> getOpen() {
        return open;
    }

    public void setOpen(List<Open> open) {
        this.open = open;
    }

    public String getHoursType() {
        return hoursType;
    }

    public void setHoursType(String hoursType) {
        this.hoursType = hoursType;
    }

    public boolean isIsOpenNow() {
        return isOpenNow;
    }

    public void setIsOpenNow(boolean isOpenNow) {
        this.isOpenNow = isOpenNow;
    }
}
