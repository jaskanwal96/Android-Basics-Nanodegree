package com.example.jaska.citybeautiful;

/**
 * Created by jaska on 19-Dec-17.
 */

public class Places {
    private String placeName;
    private String placeDetails;
    private String location;
    private int imageResourceId;
    Places(String name, String details, int imageId, String loc){
        placeName = name;
        placeDetails = details;
        imageResourceId = imageId;
        location = loc;
    }
    public String getPlaceName() {
        return placeName;
    }

    public String getLocation() {
        return location;
    }

    public String getPlaceDetails() {
        return placeDetails;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

}
