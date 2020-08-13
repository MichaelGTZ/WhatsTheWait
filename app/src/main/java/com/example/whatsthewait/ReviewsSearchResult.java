package com.example.whatsthewait;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReviewsSearchResult {

    @SerializedName("reviews")
    @Expose
    private List<Review> reviews = null;
    @SerializedName("total")
    @Expose
    private int total;
    @SerializedName("possible_languages")
    @Expose
    private List<String> possibleLanguages = null;

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<String> getPossibleLanguages() {
        return possibleLanguages;
    }

    public void setPossibleLanguages(List<String> possibleLanguages) {
        this.possibleLanguages = possibleLanguages;
    }

}
