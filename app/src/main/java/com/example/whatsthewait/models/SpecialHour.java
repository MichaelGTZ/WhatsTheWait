package com.example.whatsthewait.models;

import com.example.whatsthewait.RestaurantItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel(analyze = {SpecialHour.class})
public class SpecialHour {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("is_closed")
    @Expose
    private boolean isClosed;
    @SerializedName("start")
    @Expose
    private String start;
    @SerializedName("end")
    @Expose
    private String end;
    @SerializedName("is_overnight")
    @Expose
    private boolean isOvernight;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Object getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(boolean isClosed) {
        this.isClosed = isClosed;
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

    public boolean isIsOvernight() {
        return isOvernight;
    }

    public void setIsOvernight(boolean isOvernight) {
        this.isOvernight = isOvernight;
    }
}