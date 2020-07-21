package com.example.whatsthewait;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class RestaurantDetail extends AppCompatActivity {

    private ImageView ivFavorite; // Best way to handle this going back and forth between detailed view and RV? Maybe constantly change the user's fav list or wait till they navigate away to update.
    protected boolean isFavoriteClicked; // Can this be tied to the data passed in via the change in activities?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);

        isFavoriteClicked = getIntent().getBooleanExtra("isFavorited", true);
        ivFavorite = findViewById(R.id.ivFavorite);
        if (isFavoriteClicked == true) {
            Glide.with(getApplicationContext()).load(R.drawable.favorite_selected).into(ivFavorite);
        }
        ivFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFavoriteClicked == false) {
                    Glide.with(getApplicationContext()).load(R.drawable.favorite_unselected).into(ivFavorite);
                    isFavoriteClicked = true;
                } else {
                    Glide.with(getApplicationContext()).load(R.drawable.favorite_selected).into(ivFavorite);
                    isFavoriteClicked = false;
                }
            }
        });
    }
}