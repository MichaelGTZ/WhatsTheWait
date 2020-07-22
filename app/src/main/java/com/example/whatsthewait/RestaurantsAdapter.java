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
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.parceler.Parcels;

import java.util.List;

public class RestaurantsAdapter extends RecyclerView.Adapter<RestaurantsAdapter.ViewHolder> {

    private static final String TAG = "Restaurant Adapter";

    private Context context;
    private List<RestaurantItem> restaurants;

    public RestaurantsAdapter(Context context, List<RestaurantItem> restaurants) {
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
        RestaurantItem restaurantItem = restaurants.get(position);
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

            itemview.setOnClickListener(this);
        }

        public void bind(RestaurantItem restaurantItem) {
            Glide.with(context).load(R.drawable.ic_launcher_background).into(ivRestaurantPic);
            tvRestaurantName.setText("McDonalds");
            rbRating.setRating(2);
            tvRatingCount.setText("773");
            tvArea.setText("Albuquerque");
            tvDistance.setText("900 mi");
            tvHours.setText("Open until 9:00 PM");
            rbPrice.setRating(2);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Log.i(TAG, "onClick: restaurant item clicked");
            // Ensure the position is valid (exists in the view)
            if (position != RecyclerView.NO_POSITION) {
                // Won't work if class is static
                RestaurantItem restaurantItem = restaurants.get(position);
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
