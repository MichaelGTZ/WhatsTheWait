package com.example.whatsthewait;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import org.parceler.Parcel;

@Parcel(analyze = {RestaurantItem.class})
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
