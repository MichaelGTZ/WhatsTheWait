package com.example.whatsthewait.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsthewait.R;
import com.example.whatsthewait.RestaurantDetail;
import com.example.whatsthewait.RestaurantSearchResult;
import com.example.whatsthewait.Review;
import com.example.whatsthewait.ReviewsAdapter;
import com.example.whatsthewait.ReviewsSearchResult;
import com.example.whatsthewait.YelpService;
import com.example.whatsthewait.models.BusinessDetailed;
import com.example.whatsthewait.models.ReviewUser;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReviewsFragment extends Fragment {

    public static final String TAG = "Review Fragment";
    public static final String ARG_PAGE = "ARG_PAGE";

    private RecyclerView rvReviews;
    private ReviewsAdapter adapter;
    private List<Review> allReviews;
    private BusinessDetailed businessDetailed;

    private int mPage;

    public ReviewsFragment() {
    }

    public static ReviewsFragment newInstance(int page, BusinessDetailed businessDetailed) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        args.putParcelable("restaurant", Parcels.wrap(businessDetailed));
        ReviewsFragment fragment = new ReviewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
        businessDetailed = Parcels.unwrap(getArguments().getParcelable("restaurant"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reviews, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvReviews = view.findViewById(R.id.rvReviews);

        allReviews = new ArrayList<>();
        adapter = new ReviewsAdapter(getContext(), allReviews);
        rvReviews.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvReviews.setLayoutManager(linearLayoutManager);

        // Make a search for the detailed item
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(this.getString(R.string.yelp_base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        YelpService yelpService = retrofit.create(YelpService.class);

        Log.i(TAG, "onViewCreated: " + businessDetailed.toString());
        businessDetailed = ((RestaurantDetail) this.getActivity()).businessDetailedItem;
        yelpService.searchRestaurantReviews("Bearer " + getContext().getString(R.string.yelp_api_key), businessDetailed.getRestaurantId()).enqueue(new Callback<ReviewsSearchResult>() {
            @Override
            public void onResponse(Call<ReviewsSearchResult> call, Response<ReviewsSearchResult> response) {
                Log.i(TAG, "onResponse " + response.toString());
                ReviewsSearchResult body = response.body();
                if (body == null) {
                    Log.i(TAG, "Did not receive a valid response body from Yelp API... exiting");
                    return;
                }
                allReviews.clear();
                allReviews.addAll(body.getReviews());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ReviewsSearchResult> call, Throwable t) {
                Log.i(TAG, "onFailure " + t.toString());
            }
        }); //asynchronous call
    }
}