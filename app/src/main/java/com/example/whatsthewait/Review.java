package com.example.whatsthewait;

import android.text.format.DateUtils;
import android.util.Log;

import com.example.whatsthewait.models.ReviewUser;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

//public class Review {
//
//    private int rating;
//    private ReviewUser user;
//    private String reviewText;
//    private String timeCreated;
//
//    public Review(int rating, ReviewUser user, String reviewText, String timeCreated) {
//        this.rating = rating;
//        this.user = user;
//        this.reviewText = reviewText;
//        this.timeCreated = timeCreated;
//    }
//
//    public String getRelativeTimeAgo(String createdAt) {
//        String format = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
//        SimpleDateFormat sf = new SimpleDateFormat(format, Locale.ENGLISH);
//        sf.setLenient(true);
//
//        String relativeDate = "";
//        try {
//            long dateMillis = sf.parse(createdAt).getTime();
//            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
//                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
//        } catch (ParseException e) {
//            Log.e("RelativeTimeAgo", "getRelativeTimeAgo: failed", e);
//        }
//        return relativeDate;
//    }
//    public int getRating() {
//        return rating;
//    }
//
//    public void setRating(int rating) {
//        this.rating = rating;
//    }
//
//    public ReviewUser getUser() {
//        return user;
//    }
//
//    public void setUser(ReviewUser user) {
//        this.user = user;
//    }
//
//    public String getReviewText() {
//        return reviewText;
//    }
//
//    public void setReviewText(String reviewText) {
//        this.reviewText = reviewText;
//    }
//
//    public String getTimeCreated() {
//        return getRelativeTimeAgo(timeCreated);
//    }
//
//    public void setTimeCreated(String timeCreated) {
//        this.timeCreated = timeCreated;
//    }
//}

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Review {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("rating")
    @Expose
    private float rating;
    @SerializedName("user")
    @Expose
    private ReviewUser user;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("time_created")
    @Expose
    private String timeCreated;
    @SerializedName("url")
    @Expose
    private String url;

    public String getRelativeTimeAgo() {
        String format = "yyyy-mm-dd HH:mm:ss";
        SimpleDateFormat sf = new SimpleDateFormat(format, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(timeCreated).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            Log.e("RelativeTimeAgo", "getRelativeTimeAgo: failed", e);
        }
        return relativeDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public ReviewUser getUser() {
        return user;
    }

    public void setUser(ReviewUser user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(String timeCreated) {
        this.timeCreated = timeCreated;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
