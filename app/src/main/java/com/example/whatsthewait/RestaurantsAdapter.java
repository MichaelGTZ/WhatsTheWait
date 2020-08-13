package com.example.whatsthewait;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.whatsthewait.models.Business;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.parceler.Parcels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RestaurantsAdapter extends RecyclerView.Adapter<RestaurantsAdapter.ViewHolder> {

    private static final String TAG = "Restaurant Adapter";

    private Context context;
    private List<Business> restaurants;

    public RestaurantsAdapter(Context context, List<Business> restaurants) {
        this.context = context;
        this.restaurants = restaurants;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_restaurant, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantsAdapter.ViewHolder holder, int position) {
        Business restaurantItem = restaurants.get(position);
        holder.bind(restaurantItem);
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView ivRestaurantPic;
        private TextView tvRestaurantName;
        private RatingBar rbRating;
        private TextView tvRatingCount;
        private TextView tvArea;
        private TextView tvDistance;
        private TextView tvTransactions;
        private RatingBar rbPrice;
        private ToggleButton tbFavorite;
        private TextView tvCuisine;

        public ViewHolder(@NonNull View itemview) {
            super(itemview);

            ivRestaurantPic = itemview.findViewById(R.id.ivRestaurantPic);

            ivRestaurantPic.setOnTouchListener(new OnDoubleTapListener(context) {
                @Override
                public void onDoubleTap(MotionEvent e) {
                    if (tbFavorite.isChecked() == false) {
                        ParseUser currentUser = ParseUser.getCurrentUser();
                        int position = getAdapterPosition();
                        Business favoritedRestaurant = null;
                        if (position != RecyclerView.NO_POSITION) {
                            // Won't work if class is static
                            favoritedRestaurant = restaurants.get(position);
                        }
                        // Set the Parse Restaurant ID
                        favoritedRestaurant.setParseFields();
                        // Save new restaurant item
                        favoritedRestaurant.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    Log.i(TAG, "New restaurant item saved");
                                    currentUser.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(ParseException e) {
                                            if (e == null) {
                                                Log.i(TAG, "Successfully added to favorites relation");
                                            } else {
                                                Log.e(TAG, "Favorites list failed to save", e);
                                            }
                                        }
                                    });
                                } else {
                                    Log.e(TAG, "Restaurant item failed to create", e);
                                }
                            }
                        });
                        // Add item to favorites relation and save
                        ParseRelation<ParseObject> relation = currentUser.getRelation("favoritesRelation");
                        relation.add(favoritedRestaurant);
                        tbFavorite.setChecked(true);
                    }
                }
            });

            tvRestaurantName = itemview.findViewById(R.id.tvRestaurantName);
            rbRating = itemview.findViewById(R.id.rbRating);
            tvRatingCount = itemview.findViewById(R.id.tvRatingCount);
            tvArea = itemview.findViewById(R.id.tvArea);
            tvDistance = itemview.findViewById(R.id.tvDistance);
            tvTransactions = itemview.findViewById(R.id.tvTransactions);
            rbPrice = itemview.findViewById(R.id.rbPrice);

            tbFavorite = itemview.findViewById(R.id.tbFavorite);
            tbFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ParseUser currentUser = ParseUser.getCurrentUser();
                    int position = getAdapterPosition();
                    Business favoritedRestaurant = null;
                    if (position != RecyclerView.NO_POSITION) {
                        // Won't work if class is static
                        favoritedRestaurant = restaurants.get(position);
                    }
                    if (tbFavorite.isChecked()) {
                        // Set the Parse Restaurant ID
                        favoritedRestaurant.setParseFields();
                        // Save new restaurant item
                        favoritedRestaurant.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    Log.i(TAG, "New restaurant item saved");
                                    currentUser.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(ParseException e) {
                                            if (e == null) {
                                                Log.i(TAG, "Successfully added to favorites relation");
                                            } else {
                                                Log.e(TAG, "Favorites list failed to save", e);
                                            }
                                        }
                                    });
                                } else {
                                    Log.e(TAG, "Restaurant item failed to create", e);
                                }
                            }
                        });
                        // Add item to favorites relation and save
                        ParseRelation<ParseObject> relation = currentUser.getRelation("favoritesRelation");
                        relation.add(favoritedRestaurant);
                    } else {
                        // Find the restaurant item that was just unfavorited and remove it from the relation
                        ParseRelation<ParseObject> relation = currentUser.getRelation("favoritesRelation");
                        ParseQuery<ParseObject> query = relation.getQuery();
                        query.whereEqualTo("restaurantId", favoritedRestaurant.getRestaurantId());
                        query.findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(List<ParseObject> objects, ParseException e) {
                                if (e != null) {
                                    Log.e(TAG, "Error finding restaurant item", e);
                                } else {
                                    Log.i(TAG, "Successfully found matching restaurant:  " + objects.toString());
                                    relation.remove(objects.get(0)); // Will always be only one restaurant in the objects list
                                }
                                // Save the relation
                                currentUser.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        if (e == null) {
                                            Log.i(TAG, "Successfully removed from favorites relation");
                                        } else {
                                            Log.e(TAG, "Favorites list failed to save", e);
                                        }
                                    }
                                });
                            }
                        });
                    }
                }
            });

            tvCuisine = itemview.findViewById(R.id.tvCuisine);

            itemview.setOnClickListener(this);
        }

        public void bind(Business restaurantItem) {
            Glide.with(context)
                    .load(restaurantItem.getImageUrl())
                    .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(10)))
                    .placeholder(R.color.colorAccent)
                    .error(R.drawable.ic_launcher_background)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(ivRestaurantPic);
            tvRestaurantName.setText(restaurantItem.getName());
            rbRating.setRating((float) restaurantItem.getRating());
            tvRatingCount.setText(String.format("%d",restaurantItem.getReviewCount()));
            if (restaurantItem.getLocation() == null || restaurantItem.getLocation().getAddress1() == null || restaurantItem.getLocation().getAddress1().isEmpty()) {
                tvArea.setText("No Address Provided");
            } else {
                tvArea.setText(restaurantItem.getLocation().getAddress1());
            }
            tvDistance.setText(restaurantItem.displayDistance());
            if (restaurantItem.getIsClosed() == true) {
                tvTransactions.setText("Restaurant is Permanently Closed");
            } else {
                String transaction1;
                String transaction2;
                String transaction3;
                switch (restaurantItem.getTransactions().size()) {
                    case 0:
                        tvTransactions.setText("No dining options provided");
                        break;
                    case 1:
                        transaction1 = restaurantItem.getTransactions().get(0);
                        if (transaction1.startsWith("r")) {
                            transaction1 = "reservations";
                        }
                        tvTransactions.setText(transaction1.substring(0, 1).toUpperCase() + transaction1.substring(1));
                        break;
                    case 2:
                        transaction1 = restaurantItem.getTransactions().get(0);
                        transaction2 = restaurantItem.getTransactions().get(1);
                        if (transaction1.startsWith("r")) {
                            transaction1 = "reservations";
                        }
                        if (transaction2.startsWith("r")) {
                            transaction2 = "reservations";
                        }
                        String result = transaction1.substring(0, 1).toUpperCase() + transaction1.substring(1)
                                + " and " + transaction2.substring(0, 1).toUpperCase() + transaction2.substring(1);
                        tvTransactions.setText(result);
                        break;
                    default: // 3 items
                        transaction1 = restaurantItem.getTransactions().get(0);
                        transaction2 = restaurantItem.getTransactions().get(1);
                        transaction3 = restaurantItem.getTransactions().get(2);
                        if (transaction1.startsWith("r")) {
                            transaction1 = "reservations";
                        }
                        if (transaction2.startsWith("r")) {
                            transaction2 = "reservations";
                        }
                        if (transaction3.startsWith("r")) {
                            transaction3 = "reservations";
                        }
                        result = transaction1.substring(0, 1).toUpperCase() + transaction1.substring(1)
                                + ", " + transaction2.substring(0, 1).toUpperCase() + transaction2.substring(1)
                                + ", and " + transaction3.substring(0, 1).toUpperCase() + transaction3.substring(1);
                        tvTransactions.setText(result);
                }
            }
            if (restaurantItem.getPrice() != null) {
                rbPrice.setRating(restaurantItem.getPrice().split("").length);
            } else {
                rbPrice.setRating(0);
            }
            tvCuisine.setText(restaurantItem.getCategories().get(0).getTitle());

            // Check if the restaurant is a favorite and fill in the heart if needed
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

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Log.i(TAG, "onClick: restaurant item clicked");
            // Ensure the position is valid (exists in the view)
            if (position != RecyclerView.NO_POSITION) {
                // Won't work if class is static
                Business restaurantItem = restaurants.get(position);
                Log.i(TAG, "onClick RV: " + restaurantItem);
                // Create intent for the new activity
                Intent intent = new Intent(context, RestaurantDetail.class);
                // Serialize the Business using parceler
                //restaurantItem.setParseFields();
                restaurantItem.setParseFields();
                intent.putExtra(Business.class.getSimpleName(), restaurantItem);
                // Show activity
                context.startActivity(intent);
            }
        }
    }
}
