package com.example.whatsthewait;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.whatsthewait.models.Business;
import com.example.whatsthewait.models.BusinessDetailed;
import com.google.android.material.tabs.TabLayout;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
    private TextView tvBusy;

    private Business restaurantItem;
    public BusinessDetailed businessDetailedItem;

    private Map<String, Integer> daysOfWeekToNumber = new HashMap<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);

        daysOfWeekToNumber.put("MONDAY", 0);
        daysOfWeekToNumber.put("TUESDAY", 1);
        daysOfWeekToNumber.put("WEDNESDAY", 2);
        daysOfWeekToNumber.put("THURSDAY", 3);
        daysOfWeekToNumber.put("FRIDAY", 4);
        daysOfWeekToNumber.put("SATURDAY", 5);
        daysOfWeekToNumber.put("SUNDAY", 6);

        // Unwrap restaurant item
        restaurantItem = (Business) getIntent().getParcelableExtra(Business.class.getSimpleName());
        Log.i(TAG, "onCreate Detail: " + restaurantItem.parseToString());

        // Get current day of the week and hour for Google Popular Times Call
        LocalDate date = LocalDate.now();
        DayOfWeek dow = date.getDayOfWeek();
        int hourOfDay = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        Log.i(TAG, "onResponse: " + dow.toString());
        Log.i(TAG, "onResponse: " + hourOfDay);

        // HTTP Async client
        AsyncHttpClient client = new AsyncHttpClient();

        // Make a search for the detailed item
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(this.getString(R.string.yelp_base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        YelpService yelpService = retrofit.create(YelpService.class);
        yelpService.searchRestaurantDetail("Bearer " + getString(R.string.yelp_api_key), restaurantItem.getParseRestaurantId()).enqueue(new Callback<BusinessDetailed>() {
            @Override
            public void onResponse(Call<BusinessDetailed> call, Response<BusinessDetailed> response) {
                Log.i(TAG, "onResponse " + response.toString());
                BusinessDetailed body = response.body();
                if (body == null) {
                    Log.i(TAG, "Did not receive a valid response body from Yelp API... exiting");
                    return;
                }
                businessDetailedItem = body;
                Log.i(TAG, "onResponse: " + businessDetailedItem.toString());

                // Get the ViewPager and set it's PagerAdapter so that it can display items
                ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
                RestaurantDetailFragmentPagerAdapter restaurantDetailFragmentPagerAdapter = new RestaurantDetailFragmentPagerAdapter(getSupportFragmentManager(),
                        RestaurantDetail.this, businessDetailedItem);
                viewPager.setAdapter(restaurantDetailFragmentPagerAdapter);

                // Give the TabLayout the ViewPager
                TabLayout tabLayout = findViewById(R.id.slidingTabs);
                tabLayout.setupWithViewPager(viewPager);

                tvHours = findViewById(R.id.tvHours);
                if (businessDetailedItem.getIsClosed()) {
                    tvHours.setText("Permanently Closed");
                    tvHours.setTextColor(Color.parseColor("#ff1616"));
                }
                else if (businessDetailedItem.getHours() == null || businessDetailedItem.getHours().get(0) == null) {
                    tvHours.setText("No business hours provided");
                }
                else if (businessDetailedItem.getHours().get(0).IsOpenNow() == true) {
                    tvHours.setText("Open Now");
                    tvHours.setTextColor(Color.parseColor("#008037"));
                } else {
                    tvHours.setText("Closed Now");
                    tvHours.setTextColor(Color.parseColor("#ff1616"));
                }



                // Address call must be in format: "(name) location.address1, location.city, location.country"
                String popularTimesAddress = String.format("(%s) %s, %s, %s", businessDetailedItem.getName(), businessDetailedItem.getLocation().getAddress1(), businessDetailedItem.getLocation().getCity(), businessDetailedItem.getLocation().getCountry());
                Log.i(TAG, "onCreate: address for popular times call: " + popularTimesAddress);
                String encodedPopularTimesAddress = "";
                try {
                    encodedPopularTimesAddress = URLEncoder.encode(popularTimesAddress, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                client.get(String.format("%s?address=%s", getString(R.string.functions_trigger_url), encodedPopularTimesAddress), new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        // Called when response HTTP status is 200 OK
                        JSONObject jsonObject = json.jsonObject;
                        try {
                            JSONArray results = jsonObject.getJSONArray("populartimes");
                            Log.i(TAG, "Results: " + results.get(daysOfWeekToNumber.get(dow.toString())).toString());
                            JSONArray hourOfDayJSON = ((JSONObject) results.get(daysOfWeekToNumber.get(dow.toString()))).getJSONArray("data");
                            Log.i(TAG, "onSuccess: " + hourOfDayJSON.get(hourOfDay));
                            tvBusy = findViewById(R.id.tvBusy);
                            tvBusy.setText(String.format("Currently %d%% full", hourOfDayJSON.get(hourOfDay)));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                        // Called when response HTTP status is 4XX, usually indicating a client error
                        Log.d(TAG, "onFailure" + throwable);
                    }
                });
            }

            @Override
            public void onFailure(Call<BusinessDetailed> call, Throwable t) {
                Log.i(TAG, "onFailure " + t.toString());
            }
        }); //asynchronous call

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
        tvCuisine = findViewById(R.id.tvCuisine);

        // Populate fields
        ivBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Invoke the back button to prevent reloading of previous activity
                onBackPressed();
            }
        });
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
        tvCuisine.setText(restaurantItem.getParseCuisine());


    }
}