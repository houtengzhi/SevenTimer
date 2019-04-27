package com.latitude.seventimer.model;

import java.io.Serializable;

/**
 * Created by yechy on 2015/9/6.
 */
public class WeatherLocation implements Serializable {

    private static final long serialVersionUID = 1L;
    private String address;
    private int imageId;
    private float latitude;
    private float longitude;

    public WeatherLocation() {

    }

    public WeatherLocation(float latitude, float longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public WeatherLocation(String address, int imageId, float latitude, float longitude) {
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

    public void setAddress(String address) {
        this.address = address;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
}
