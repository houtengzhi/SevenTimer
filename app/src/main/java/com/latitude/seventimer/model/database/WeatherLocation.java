package com.latitude.seventimer.model.database;

import android.os.Parcel;
import android.os.Parcelable;

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
public class WeatherLocation implements Parcelable {

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
    public WeatherLocation(int id, float latitude, float longitude) {
        this.id = id;
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

    @Ignore
    public WeatherLocation(Parcel source) {
        id = source.readInt();
        city = source.readString();
        address = source.readString();
        imageId = source.readInt();
        latitude = source.readFloat();
        longitude = source.readFloat();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(city);
        dest.writeString(address);
        dest.writeInt(imageId);
        dest.writeFloat(latitude);
        dest.writeFloat(longitude);
    }

    @Ignore
    public static final Creator<WeatherLocation> CREATOR = new Creator<WeatherLocation>() {
        @Override
        public WeatherLocation createFromParcel(Parcel source) {
            return new WeatherLocation(source);
        }

        @Override
        public WeatherLocation[] newArray(int size) {
            return new WeatherLocation[0];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Ignore
    @Override
    public String toString() {
        return "WeatherLocation{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", imageId=" + imageId +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    @Ignore
    public String getFormatAddress() {
        return city + " " + address;
    }
}
