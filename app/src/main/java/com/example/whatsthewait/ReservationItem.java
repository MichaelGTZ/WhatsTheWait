package com.example.whatsthewait;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("ReservationItem")
public class ReservationItem extends ParseObject {

    String restaurantName;

    public ReservationItem() {}
}
