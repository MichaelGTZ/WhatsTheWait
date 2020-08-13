package com.example.whatsthewait.models;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Restaurant")
public class Business extends ParseObject {

    @SerializedName("rating")
    @Expose
    private float rating;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("alias")
    @Expose
    private String alias;
    @SerializedName("is_closed")
    @Expose
    private boolean isClosed;
    @SerializedName("categories")
    @Expose
    private List<Category> categories = null;
    @SerializedName("review_count")
    @Expose
    private int reviewCount;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("coordinates")
    @Expose
    private Coordinates coordinates;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("distance")
    @Expose
    private float distance;
    @SerializedName("transactions")
    @Expose
    private List<String> transactions = null;

    public static final String KEY_RATING = "rating";
    public static final String KEY_PRICE = "price";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_RESTAURANTID = "restaurantId";
    public static final String KEY_ALIAS = "alias";
    public static final String KEY_ISCLOSED = "isClosed";
    public static final String KEY_REVIEWCOUNT = "reviewCount";
    public static final String KEY_NAME = "name";
    public static final String KEY_URL = "url";
    public static final String KEY_IMAGEURL = "imageUrl";
    public static final String KEY_DISTANCE = "distance";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_CUISINE = "cuisine";
    public static final String KEY_TRANSACTIONS = "transactions";

    public Business() {}

    public String displayDistance() {
        double milesPerMeter = 0.000621371;
        String distanceInMiles = String.format("%.2f", distance * milesPerMeter);
        return String.format("%s mi", distanceInMiles);
    }

    public String displayParseDistance(float parseDistance) {
        double milesPerMeter = 0.000621371;
        String distanceInMiles = String.format("%.2f", parseDistance * milesPerMeter);
        return String.format("%s mi", distanceInMiles);
    }

    // To create the ParseObject before passing it into an intent
    public void setParseFields() {
        setParseRating();
        setParsePrice();
        setParsePhone();
        setParseRestaurantId();
        setParseAlias();
        setParseIsClosed();
        setParseReviewCount();
        setParseName();
        setParseUrl();
        setParseImageUrl();
        setParseDistance();
        setParseAddress();
        setParseCuisine();
        setParseTransactions();
    }

    // To show the fields of the ParseObject (separate from the local object)
    public String parseToString() {
        return "Business{" +
                "rating=" + getParseRating() +
                ", price='" + getParsePrice() + '\'' +
                ", phone='" + getParsePhone() + '\'' +
                ", restaurantId='" + getParseRestaurantId() + '\'' +
                ", alias='" + getParseAlias() + '\'' +
                ", isClosed=" + getParseIsClosed() +
                ", reviewCount=" + getParseReviewCount() +
                ", name='" + getParseName() + '\'' +
                ", url='" + getParseUrl() + '\'' +
                ", imageUrl='" + getParseImageUrl() + '\'' +
                ", distance=" + getParseDistance() +
                '}';
    }

    // For the local object
    @Override
    public String toString() {
        return "Business{" +
                "rating=" + rating +
                ", price='" + price + '\'' +
                ", phone='" + phone + '\'' +
                ", id='" + id + '\'' +
                ", alias='" + alias + '\'' +
                ", isClosed=" + isClosed +
                ", categories=" + categories +
                ", reviewCount=" + reviewCount +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", coordinates=" + coordinates +
                ", imageUrl='" + imageUrl + '\'' +
                ", location=" + location +
                ", distance=" + distance +
                ", transactions=" + transactions +
                '}';
    }

    // Start of Parse Getters and Setters
    public double getParseRating() {
        return getDouble(KEY_RATING);
    }

    public void setParseRating() {
        put(KEY_RATING, rating);
    }

    public String getParsePrice() {
        return getString(KEY_PRICE);
    }

    public void setParsePrice() {
        if (price == null)
            price = "null";
        put(KEY_PRICE, price);
    }

    public String getParsePhone() {
        return getString(KEY_PHONE);
    }

    public void setParsePhone() {
        put(KEY_PHONE, phone);
    }

    public String getParseRestaurantId() {
        return getString(KEY_RESTAURANTID);
    }

    public void setParseRestaurantId() {
        put(KEY_RESTAURANTID, id);
    }

    public String getParseAlias() {
        return getString(KEY_ALIAS);
    }

    public void setParseAlias() {
        put(KEY_ALIAS, alias);
    }

    public boolean getParseIsClosed() {
        return getBoolean(KEY_ISCLOSED);
    }

    public void setParseIsClosed() {
        put(KEY_ISCLOSED, isClosed);
    }

    public Number getParseReviewCount() {
        return getNumber(KEY_REVIEWCOUNT);
    }

    public void setParseReviewCount() {
        put(KEY_REVIEWCOUNT, reviewCount);
    }

    public String getParseName() {
        return getString(KEY_NAME);
    }

    public void setParseName() {
        put(KEY_NAME, name);
    }

    public String getParseUrl() {
        return getString(KEY_URL);
    }

    public void setParseUrl() {
        put(KEY_URL, url);
    }

    public String getParseImageUrl() {
        return getString(KEY_IMAGEURL);
    }

    public void setParseImageUrl() {
        put(KEY_IMAGEURL, imageUrl);
    }

    public double getParseDistance() {
        return getDouble(KEY_DISTANCE);
    }

    public void setParseDistance() {
        put(KEY_DISTANCE, distance);
    }

    public String getParseAddress() {
        return getString(KEY_ADDRESS);
    }

    public void setParseAddress() {
        put(KEY_ADDRESS, location.getAddress1());
    }

    public String getParseCuisine() {
        return getString(KEY_CUISINE);
    }

    public void setParseCuisine() {
        put(KEY_CUISINE, categories.get(0).getTitle());
    }

    public String getParseTransactions() {
        return getString(KEY_TRANSACTIONS);
    }

    public void setParseTransactions() {
        String result;
        if (isClosed == true) {
            result = "Restaurant is Permanently Closed";
        } else {
            String transaction1;
            String transaction2;
            String transaction3;
            switch (transactions.size()) {
                case 0:
                    result = "No dining options provided";
                    break;
                case 1:
                    transaction1 = transactions.get(0);
                    if (transaction1.startsWith("r")) {
                        transaction1 = "reservations";
                    }
                    result = transaction1.substring(0, 1).toUpperCase() + transaction1.substring(1);
                    break;
                case 2:
                    transaction1 = transactions.get(0);
                    transaction2 = transactions.get(1);
                    if (transaction1.startsWith("r")) {
                        transaction1 = "reservations";
                    }
                    if (transaction2.startsWith("r")) {
                        transaction2 = "reservations";
                    }
                    result = transaction1.substring(0, 1).toUpperCase() + transaction1.substring(1)
                            + " and " + transaction2.substring(0, 1).toUpperCase() + transaction2.substring(1);
                    break;
                default: // 3 items
                    transaction1 = transactions.get(0);
                    transaction2 = transactions.get(1);
                    transaction3 = transactions.get(2);
                    if (transaction1.startsWith("r")) {
                        transaction1 = "reservations";
                    }
                    if (transaction2.startsWith("r")) {
                        transaction2 = "reservations";
                    }
                    if (transaction3.startsWith("r")) {
                        transaction3 = "reservations";
                    }
                    result = transaction1.substring(0, 1).toUpperCase() + transaction1.substring(1)
                            + ", " + transaction2.substring(0, 1).toUpperCase() + transaction2.substring(1)
                            + ", and " + transaction3.substring(0, 1).toUpperCase() + transaction3.substring(1);
            }
        }
        put(KEY_TRANSACTIONS, result);
    }
    // End of Parse Getters and Setters


    // Start of object Getters and Setters
    public float getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRestaurantId() {
        return id;
    }

    public void setRestaurantId(String id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public boolean getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(boolean isClosed) {
        this.isClosed = isClosed;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public List<String> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<String> transactions) {
        this.transactions = transactions;
    }
    // End of object Getters and Setters
}