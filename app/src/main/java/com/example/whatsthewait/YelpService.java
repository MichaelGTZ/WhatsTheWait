package com.example.whatsthewait;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface YelpService {

    // Request method and URL specified in the annotation

    @GET("businesses/search")
    Call<RestaurantSearchResult> searchRestaurants(@Header("Authorization") String authHeader, @Query("term") String searchTerm, @Query("location") String location);

}
