package com.example.whatsthewait;

import com.example.whatsthewait.models.Category;
import com.example.whatsthewait.models.Coordinates;
import com.example.whatsthewait.models.Hour;
import com.example.whatsthewait.models.Location;
import com.example.whatsthewait.models.SpecialHour;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.parse.ParseClassName;
import com.parse.ParseObject;

import org.parceler.Parcel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Parcel(analyze = {RestaurantItem.class})
@ParseClassName("RestaurantItem")
public class RestaurantItem extends ParseObject {

//    String restaurantName;
//    double rating;
//    int numRatings;
//    String streetAddress;
//    double distance;
//    String hours;
//    int price;

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("alias")
    @Expose
    private String alias;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("is_claimed")
    @Expose
    private boolean isClaimed;
    @SerializedName("is_closed")
    @Expose
    private boolean isClosed;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("display_phone")
    @Expose
    private String displayPhone;
    @SerializedName("review_count")
    @Expose
    private int reviewCount;
    @SerializedName("categories")
    @Expose
    private List<Category> categories = null;
    @SerializedName("rating")
    @Expose
    private float rating;
    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("coordinates")
    @Expose
    private Coordinates coordinates;
    @SerializedName("photos")
    @Expose
    private List<String> photos = null;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("hours")
    @Expose
    private List<Hour> hours = null;
    @SerializedName("transactions")
    @Expose
    private List<String> transactions = null;
    @SerializedName("special_hours")
    @Expose
    private List<SpecialHour> specialHours = null;

    public RestaurantItem() {}


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isIsClaimed() {
        return isClaimed;
    }

    public void setIsClaimed(boolean isClaimed) {
        this.isClaimed = isClaimed;
    }

    public boolean isIsClosed() {
        return isClosed;
    }

    public void setIsClosed(boolean isClosed) {
        this.isClosed = isClosed;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDisplayPhone() {
        return displayPhone;
    }

    public void setDisplayPhone(String displayPhone) {
        this.displayPhone = displayPhone;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<Hour> getHours() {
        return hours;
    }

    public void setHours(List<Hour> hours) {
        this.hours = hours;
    }

    public List<String> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<String> transactions) {
        this.transactions = transactions;
    }

    public List<SpecialHour> getSpecialHours() {
        return specialHours;
    }

    public void setSpecialHours(List<SpecialHour> specialHours) {
        this.specialHours = specialHours;
    }
}
