package com.example.whatsthewait;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ToggleButton;

import com.google.android.material.tabs.TabLayout;

public class RestaurantDetail extends AppCompatActivity {

    private ToggleButton tbFavorite;
    private ImageView ivBackButton;
    private RestaurantItem restaurantItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);

        tbFavorite = findViewById(R.id.tbFavorite);
        ivBackButton = findViewById(R.id.ivBackButton);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new RestaurantDetailFragmentPagerAdapter(getSupportFragmentManager(),
                RestaurantDetail.this));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = findViewById(R.id.slidingTabs);
        tabLayout.setupWithViewPager(viewPager);
    }
}