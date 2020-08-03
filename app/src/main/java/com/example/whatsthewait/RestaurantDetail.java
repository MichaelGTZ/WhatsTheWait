package com.example.whatsthewait;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import android.widget.ImageView;
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
    private Business restaurantItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);

        ivBackButton = findViewById(R.id.ivBackButton);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new RestaurantDetailFragmentPagerAdapter(getSupportFragmentManager(),
                RestaurantDetail.this));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = findViewById(R.id.slidingTabs);
        tabLayout.setupWithViewPager(viewPager);

        tbFavorite = findViewById(R.id.tbFavorite);



        restaurantItem = (Business) getIntent().getParcelableExtra(Business.class.getSimpleName());
        Log.i(TAG, "onCreate Detail: " + restaurantItem.parseToString());





        // Check if the restaurant is a favorite and fill in the favorite icon if needed
        ParseUser currentuser = ParseUser.getCurrentUser();
        ParseRelation<ParseObject> relation = currentuser.getRelation("favoritesRelation");
        ParseQuery<ParseObject> query = relation.getQuery();
        query.whereEqualTo("restaurantId", restaurantItem.getRestaurantId());
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
    }
}