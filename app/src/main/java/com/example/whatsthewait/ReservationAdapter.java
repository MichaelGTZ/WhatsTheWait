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

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ViewHolder> {

    private Context context;
    private List<ReservationItem> reservations;

    public ReservationAdapter(Context context, List<ReservationItem> reservations) {
        this.context = context;
        this.reservations = reservations;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_reservation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservationAdapter.ViewHolder holder, int position) {
        ReservationItem reservationItem = reservations.get(position);
        holder.bind(reservationItem);
    }

    @Override
    public int getItemCount() {
        return reservations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ViewHolder(@NonNull View itemview) {
            super(itemview);

            itemview.setOnClickListener(this);
        }

        public void bind(ReservationItem reservationItem) {

        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Log.i("PostsAdapter", "onClick: post clicked");
            // Ensure the position is valid (exists in the view)
            if (position != RecyclerView.NO_POSITION) {
                // Won't work if class is static
                ReservationItem reservationItem = reservations.get(position);
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
