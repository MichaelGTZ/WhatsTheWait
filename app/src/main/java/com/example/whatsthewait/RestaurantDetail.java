package com.example.whatsthewait;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.whatsthewait.models.Business;
import com.google.android.material.tabs.TabLayout;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.util.List;

public class RestaurantDetail extends AppCompatActivity {

    private static final String TAG = "Restaurant Detail";
    private ToggleButton tbFavorite;
    private ImageView ivBackButton;
    private TextView tvRestaurantName;
    private TextView tvRatingCount;
    private RatingBar rbPrice;
    private TextView tvAddress;
    private TextView tvDistance;
    private TextView tvHours;
    private TextView tvCuisine;
    private RatingBar rbRating;

    private Business restaurantItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);

        // Unwrap restaurant item
        restaurantItem = (Business) getIntent().getParcelableExtra(Business.class.getSimpleName());
        Log.i(TAG, "onCreate Detail: " + restaurantItem.parseToString());

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new RestaurantDetailFragmentPagerAdapter(getSupportFragmentManager(),
                RestaurantDetail.this));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = findViewById(R.id.slidingTabs);
        tabLayout.setupWithViewPager(viewPager);


        // Check if the restaurant is a favorite and fill in the favorite icon if needed
        tbFavorite = findViewById(R.id.tbFavorite);
        ParseUser currentuser = ParseUser.getCurrentUser();
        ParseRelation<ParseObject> relation = currentuser.getRelation("favoritesRelation");
        ParseQuery<ParseObject> query = relation.getQuery();
        query.whereEqualTo("restaurantId", restaurantItem.getParseRestaurantId());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "On Bind: Error finding restaurant item", e);
                } else {
                    Log.i(TAG, "On Bind: Successfully found matching restaurant:  " + objects.toString());
                    if (objects.isEmpty()) {
                        tbFavorite.setChecked(false);
                    } else {
                        tbFavorite.setChecked(true);
                    }
                }
            }
        });

        // Find items
        ivBackButton = findViewById(R.id.ivBackButton);
        tvRestaurantName = findViewById(R.id.tvRestaurantName);
        rbRating = findViewById(R.id.rbRating);
        tvRatingCount = findViewById(R.id.tvRatingCount);
        rbPrice = findViewById(R.id.rbPrice);
        tvAddress = findViewById(R.id.tvAddress);
        tvDistance = findViewById(R.id.tvDistance);
        tvHours = findViewById(R.id.tvHours);
        tvCuisine = findViewById(R.id.tvCuisine);

        // Populate fields
        tvRestaurantName.setText(restaurantItem.getParseName());
        rbRating.setRating((float) restaurantItem.getParseRating());
        tvRatingCount.setText(String.format("%d", (int) restaurantItem.getParseReviewCount()));
        if (restaurantItem.getParsePrice().equals("null")) {
            rbPrice.setRating(0);
        } else {
            rbPrice.setRating(restaurantItem.getParsePrice().split("").length);
        }
        tvAddress.setText(restaurantItem.getParseAddress());
        tvDistance.setText(restaurantItem.displayParseDistance((float) restaurantItem.getParseDistance()));
        tvHours.setText("Open until 9:00 PM");
        tvCuisine.setText(restaurantItem.getParseCuisine());
    }
}