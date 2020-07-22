package com.example.whatsthewait;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;

public class RestaurantDetail extends AppCompatActivity {

    private ToggleButton tbFavorite;
    private ImageView ivBackButton;
    private RestaurantItem restaurantItem;

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