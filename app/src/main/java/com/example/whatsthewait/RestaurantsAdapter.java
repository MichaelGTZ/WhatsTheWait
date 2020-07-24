package com.example.whatsthewait;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.bumptech.glide.request.RequestOptions;
import com.example.whatsthewait.models.Business;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.parceler.Parcels;

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
        private TextView tvHours;
        private RatingBar rbPrice;
        private ToggleButton tbFavorite;
        private TextView tvCuisine;

        public ViewHolder(@NonNull View itemview) {
            super(itemview);

            ivRestaurantPic = itemview.findViewById(R.id.ivRestaurantPic);
            tvRestaurantName = itemview.findViewById(R.id.tvRestaurantName);
            rbRating = itemview.findViewById(R.id.rbRating);
            tvRatingCount = itemview.findViewById(R.id.tvRatingCount);
            tvArea = itemview.findViewById(R.id.tvArea);
            tvDistance = itemview.findViewById(R.id.tvDistance);
            tvHours = itemview.findViewById(R.id.tvHours);
            rbPrice = itemview.findViewById(R.id.rbPrice);

            tbFavorite = itemview.findViewById(R.id.tbFavorite);
            tbFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ParseUser currentuser = ParseUser.getCurrentUser();
                    int position = getAdapterPosition();
                    Business favoritedRestaurant = null;
                    if (position != RecyclerView.NO_POSITION) {
                        // Won't work if class is static
                        favoritedRestaurant = restaurants.get(position);
                    }
                    if (tbFavorite.isChecked()) {
                        // Set the Parse Restaurant items info using a method in the Business class

                        // Add item to favorites array
                        favoritedRestaurant.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    Log.i(TAG, "Restaurant item saved");
                                } else {
                                    Log.e(TAG, "Restaurant item failed to create", e);
                                }
                            }
                        });
                        currentuser.addUnique("favorites", favoritedRestaurant);
                    } else {
                        // Remove from favorites array
//                        List<Business> currentFavs = currentuser.getList("favorites");
//                        currentFavs.remove(favoritedRestaurant.getId());
//                        currentuser.put("favorites", currentFavs);
                    }
                    // Save in background
                    currentuser.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Log.i(TAG, "Favorites list updated successfully");
                            } else {
                                Log.e(TAG, "Favorites list failed to save", e);
                            }
                        }
                    });
                }
            });

            tvCuisine = itemview.findViewById(R.id.tvCuisine);

            itemview.setOnClickListener(this);
        }

        public void bind(Business restaurantItem) {
            Glide.with(context)
                    .load(restaurantItem.getImageUrl())
                    .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(10)))
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(ivRestaurantPic);
            tvRestaurantName.setText(restaurantItem.getName());
            rbRating.setRating(restaurantItem.getRating());
            tvRatingCount.setText(String.format("%d",restaurantItem.getReviewCount()));
            tvArea.setText(restaurantItem.getLocation().getAddress1());
            tvDistance.setText(restaurantItem.displayDistance());
            tvHours.setText("Open until 9:00 PM");
            if (restaurantItem.getPrice() != null) {
                rbPrice.setRating(restaurantItem.getPrice().split("").length);
            } else {
                rbPrice.setRating(0);
            }
            tvCuisine.setText(restaurantItem.getCategories().get(0).getTitle());
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Log.i(TAG, "onClick: restaurant item clicked");
            // Ensure the position is valid (exists in the view)
            if (position != RecyclerView.NO_POSITION) {
                // Won't work if class is static
                Business restaurantItem = restaurants.get(position);
                // Create intent for the new activity
                Intent intent = new Intent(context, RestaurantDetail.class);
                // Serialize the movie using parceler
                intent.putExtra(RestaurantItem.class.getSimpleName(), Parcels.wrap(restaurantItem));
                // Show activity
                context.startActivity(intent);
            }
        }
    }
}
