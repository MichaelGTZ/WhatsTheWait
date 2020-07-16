package com.example.whatsthewait;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RestaurantsAdapter extends RecyclerView.Adapter<RestaurantsAdapter.ViewHolder> {

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
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ViewHolder(@NonNull View itemview) {
            super(itemview);

            itemview.setOnClickListener(this);
        }

        public void bind(RestaurantItem restaurantItem) {

        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Log.i("PostsAdapter", "onClick: post clicked");
            // Ensure the position is valid (exists in the view)
            if (position != RecyclerView.NO_POSITION) {
                // Won't work if class is static
                RestaurantItem restaurantItem = restaurants.get(position);
                // Create intent for the new activity
                Intent intent = new Intent(context, RestaurantDetail.class);
                // Serialize the movie using parceler
                //intent.putExtra(RestaurantItem.class.getSimpleName(), Parcels.wrap(post));
                // Show activity
                context.startActivity(intent);
            }
        }
    }
}
