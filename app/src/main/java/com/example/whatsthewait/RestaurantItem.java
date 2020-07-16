package com.example.whatsthewait;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("RestaurantItem")
public class RestaurantItem extends ParseObject {

    String restaurantName;
    double rating;
    int numRatings;
    String streetAddress;
    double distance;
    String hours;
    int price;

    public RestaurantItem() {}
}
