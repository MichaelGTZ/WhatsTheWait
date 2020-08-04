package com.example.whatsthewait.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsthewait.FavoritesAdapter;
import com.example.whatsthewait.R;
import com.example.whatsthewait.RestaurantsAdapter;
import com.example.whatsthewait.models.Business;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends Fragment {

    public static final String TAG = "FavoriteFragment";

    private RecyclerView rvFavorites;
    private FavoritesAdapter adapter;
    private List<Business> favoriteRestaurants;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvFavorites = view.findViewById(R.id.rvFavorites);

        favoriteRestaurants = new ArrayList<>();
        adapter = new FavoritesAdapter(getContext(), favoriteRestaurants);
        rvFavorites.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvFavorites.setLayoutManager(linearLayoutManager);

        // Get current user and their favorites
        ParseUser currentUser = ParseUser.getCurrentUser();
        ParseRelation<Business> relation = currentUser.getRelation("favoritesRelation");
        ParseQuery<Business> query = relation.getQuery();
        query.findInBackground(new FindCallback<Business>() {
            @Override
            public void done(List<Business> objects, ParseException e) {
                favoriteRestaurants.addAll(objects);
                Business favorite = favoriteRestaurants.get(0);

                adapter.notifyDataSetChanged();
            }
        });
    }
}
