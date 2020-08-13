package com.example.whatsthewait.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsthewait.R;
import com.example.whatsthewait.RestaurantItem;
import com.example.whatsthewait.RestaurantSearchResult;
import com.example.whatsthewait.RestaurantsAdapter;
import com.example.whatsthewait.YelpService;
import com.example.whatsthewait.models.Business;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    public static final String TAG = "HomeFragment";

    private RecyclerView rvRestaurants;
    private RestaurantsAdapter adapter;
    private List<Business> allRestaurants;
    private SearchView svSearchBar;
    private EditText etLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;
    String location = "";
    private double longitude;
    private double latitude;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvRestaurants = view.findViewById(R.id.rvRestaurants);

        allRestaurants = new ArrayList<>();
        adapter = new RestaurantsAdapter(getContext(), allRestaurants);
        rvRestaurants.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvRestaurants.setLayoutManager(linearLayoutManager);

        fusedLocationProviderClient =  LocationServices.getFusedLocationProviderClient(getContext());
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getContext().getString(R.string.yelp_base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        YelpService yelpService = retrofit.create(YelpService.class);
        yelpService.searchRestaurantsCurrentLocation("Bearer " + getContext().getString(R.string.yelp_api_key), "restaurants", latitude, longitude).enqueue(new Callback<RestaurantSearchResult>() {
            @Override
            public void onResponse(Call<RestaurantSearchResult> call, Response<RestaurantSearchResult> response) {
                Log.i(TAG, "onResponse " + response.toString());
                RestaurantSearchResult body = response.body();
                if (body == null) {
                    Log.i(TAG, "Did not receive a valid response body from Yelp API... exiting");
                    return;
                }
                allRestaurants.clear();
                allRestaurants.addAll(body.getBusinesses());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<RestaurantSearchResult> call, Throwable t) {
                Log.i(TAG, "onFailure " + t.toString());
            }
        }); //asynchronous call

        etLocation = view.findViewById(R.id.etLocation);

        svSearchBar = view.findViewById(R.id.svSearchBar);
        svSearchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                location = etLocation.getText().toString();
                Log.i(TAG, "onViewCreated: " + location.isEmpty());
                if (location.isEmpty()) {
                    yelpService.searchRestaurantsCurrentLocation("Bearer " + getContext().getString(R.string.yelp_api_key), s, latitude, longitude).enqueue(new Callback<RestaurantSearchResult>() {
                        @Override
                        public void onResponse(Call<RestaurantSearchResult> call, Response<RestaurantSearchResult> response) {
                            Log.i(TAG, "onResponse " + response.toString());
                            RestaurantSearchResult body = response.body();
                            if (body == null) {
                                Log.i(TAG, "Did not receive a valid response body from Yelp API... exiting");
                                return;
                            }
                            allRestaurants.clear();
                            allRestaurants.addAll(body.getBusinesses());
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(Call<RestaurantSearchResult> call, Throwable t) {
                            Log.i(TAG, "onFailure " + t.toString());
                        }
                    }); //asynchronous call
                } else {
                    yelpService.searchRestaurants("Bearer " + getContext().getString(R.string.yelp_api_key), s, location).enqueue(new Callback<RestaurantSearchResult>() {
                        @Override
                        public void onResponse(Call<RestaurantSearchResult> call, Response<RestaurantSearchResult> response) {
                            Log.i(TAG, "onResponse " + response.toString());
                            RestaurantSearchResult body = response.body();
                            if (body == null) {
                                Log.i(TAG, "Did not receive a valid response body from Yelp API... exiting");
                                return;
                            }
                            allRestaurants.clear();
                            allRestaurants.addAll(body.getBusinesses());
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(Call<RestaurantSearchResult> call, Throwable t) {
                            Log.i(TAG, "onFailure " + t.toString());
                        }
                    }); //asynchronous call
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                location = etLocation.getText().toString();
                Log.i(TAG, "onViewCreated: " + location.isEmpty());
                if (location.isEmpty()) {
                    yelpService.searchRestaurantsCurrentLocation("Bearer " + getContext().getString(R.string.yelp_api_key), s, latitude, longitude).enqueue(new Callback<RestaurantSearchResult>() {
                        @Override
                        public void onResponse(Call<RestaurantSearchResult> call, Response<RestaurantSearchResult> response) {
                            Log.i(TAG, "onResponse " + response.toString());
                            RestaurantSearchResult body = response.body();
                            if (body == null) {
                                Log.i(TAG, "Did not receive a valid response body from Yelp API... exiting");
                                return;
                            }
                            allRestaurants.clear();
                            allRestaurants.addAll(body.getBusinesses());
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(Call<RestaurantSearchResult> call, Throwable t) {
                            Log.i(TAG, "onFailure " + t.toString());
                        }
                    }); //asynchronous call
                } else {
                    yelpService.searchRestaurants("Bearer " + getContext().getString(R.string.yelp_api_key), s, location).enqueue(new Callback<RestaurantSearchResult>() {
                        @Override
                        public void onResponse(Call<RestaurantSearchResult> call, Response<RestaurantSearchResult> response) {
                            Log.i(TAG, "onResponse " + response.toString());
                            RestaurantSearchResult body = response.body();
                            if (body == null) {
                                Log.i(TAG, "Did not receive a valid response body from Yelp API... exiting");
                                return;
                            }
                            allRestaurants.clear();
                            allRestaurants.addAll(body.getBusinesses());
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(Call<RestaurantSearchResult> call, Throwable t) {
                            Log.i(TAG, "onFailure " + t.toString());
                        }
                    }); //asynchronous call
                }
                return false;
            }
        });
    }
}
