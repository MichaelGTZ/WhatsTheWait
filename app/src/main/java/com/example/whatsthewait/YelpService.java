package com.example.whatsthewait;

import com.example.whatsthewait.models.BusinessDetailed;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface YelpService {

    // Request method and URL specified in the annotation

    @GET("businesses/search")
    Call<RestaurantSearchResult> searchRestaurants(@Header("Authorization") String authHeader, @Query("term") String searchTerm, @Query("location") String location);

    @GET("businesses/search")
    Call<RestaurantSearchResult> searchRestaurantsCurrentLocation(@Header("Authorization") String authHeader, @Query("term") String searchTerm, @Query("latitude") double latitude, @Query("longitude") double longitude);

    @GET("businesses/{id}")
    Call<BusinessDetailed> searchRestaurantDetail(@Header("Authorization") String authHeader, @Path("id") String path);

    @GET("businesses/{id}/reviews")
    Call<ReviewsSearchResult> searchRestaurantReviews(@Header("Authorization") String authHeader, @Path("id") String path);
}
