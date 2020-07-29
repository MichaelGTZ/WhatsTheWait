package com.example.whatsthewait.fragments;

import android.os.Bundle;
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
import com.example.whatsthewait.Review;
import com.example.whatsthewait.ReviewsAdapter;
import com.example.whatsthewait.models.ReviewUser;

import java.util.ArrayList;
import java.util.List;

public class ReviewsFragment extends Fragment {

    public static final String TAG = "Review Fragment";
    public static final String ARG_PAGE = "ARG_PAGE";

    private RecyclerView rvReviews;
    private ReviewsAdapter adapter;
    private List<Review> allReviews;

    private int mPage;
    private TextView textviewsample;

    public ReviewsFragment() {
    }

    public static ReviewsFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        ReviewsFragment fragment = new ReviewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
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

        // Testing functionality with sample reviews
        ReviewUser user = new ReviewUser("Michael G.", "https://s3-media3.fl.yelpcdn.com/photo/iwoAD12zkONZxJ94ChAaMg/o.jpg");
        Review review1 = new Review(1, user, "Very good many food", "2016-08-29 00:41:13");
        Review review2 = new Review(2, user, "Very good many food", "2016-08-29 00:41:13");
        Review review3 = new Review(3, user, "Very good many food", "2016-08-29 00:41:13");
        Review review4 = new Review(4, user, "Very good many food", "2016-08-29 00:41:13");

        allReviews.add(review1);
        allReviews.add(review2);
        allReviews.add(review3);
        allReviews.add(review4);
        adapter.notifyDataSetChanged();
    }
}