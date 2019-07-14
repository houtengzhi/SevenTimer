package com.latitude.seventimer.model;

import com.latitude.seventimer.model.database.WeatherLocation;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by cloud on 2019/4/20.
 */
public interface IDataHelper {
    public Flowable<WeatherLocation> fetchLocationInfo(float latitude, float longitude);

    public Flowable<AstroWeatherCluster> fetchAstroWeather(float latitude, float longitude);


    //--------------------------------------- Map -----------------------------------------
    public Observable<WeatherLocation> reverseGeoCode(WeatherLocation location);

    public Observable<List<WeatherLocation>> fetchSuggestionLocation(String city, String keyword);


    //------------------------------------- Database --------------------------------------
    public Single<List<WeatherLocation>> queryAllLocations();

    public Single<Long> insertLocation(WeatherLocation location);

    public Single<Long> insertOrUpdateLocation(WeatherLocation location);

    public Single<Integer> updateLocation(WeatherLocation location);

    public Single<Integer> deleteLocation(WeatherLocation location);

    public void release();
}
