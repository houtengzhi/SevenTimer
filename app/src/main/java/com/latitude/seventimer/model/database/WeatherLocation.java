package com.latitude.seventimer.model.database;

import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Created by yechy on 2015/9/6.
 */
@Entity(tableName = "location")
public class WeatherLocation implements Serializable {

    @Ignore
    private static final long serialVersionUID = 1L;

    @PrimaryKey(autoGenerate = true)
    public int id;

    private String city;

    @ColumnInfo(name = "address")
    private String address;

    private int imageId;

    @ColumnInfo(name = "latitude")
    private float latitude;

    @ColumnInfo(name = "longitude")
    private float longitude;

    public WeatherLocation() {

    }

    @Ignore
    public WeatherLocation(float latitude, float longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Ignore
    public WeatherLocation(String address, int imageId, float latitude, float longitude) {
        this.address = address;
        this.imageId = imageId;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    @NonNull
    @Override
    public String toString() {
        return city + " " + address;
    }
}
