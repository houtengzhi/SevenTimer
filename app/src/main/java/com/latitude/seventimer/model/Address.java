package com.latitude.seventimer.model;

import java.io.Serializable;

/**
 * Created by yechy on 2015/9/6.
 */
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;
    private String address;
    private int imageId;
    private float latitude;
    private float longitude;

    public Address() {

    }

    public Address(String address, int imageId, float latitude, float longitude) {
        this.address = address;
        this.imageId = imageId;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public int getImageId() {
        return imageId;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }
}
