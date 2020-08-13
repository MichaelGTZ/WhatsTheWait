package com.example.whatsthewait.models;

import com.example.whatsthewait.RestaurantItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel(analyze = {Open.class})
public class Open {

    @SerializedName("is_overnight")
    @Expose
    private boolean isOvernight;
    @SerializedName("start")
    @Expose
    private String start;
    @SerializedName("end")
    @Expose
    private String end;
    @SerializedName("day")
    @Expose
    private int day;

    @Override
    public String toString() {
        return "Open{" +
                "isOvernight=" + isOvernight +
                ", start='" + start + '\'' +
                ", end='" + end + '\'' +
                ", day=" + day +
                '}';
    }

    public boolean isIsOvernight() {
        return isOvernight;
    }

    public void setIsOvernight(boolean isOvernight) {
        this.isOvernight = isOvernight;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}