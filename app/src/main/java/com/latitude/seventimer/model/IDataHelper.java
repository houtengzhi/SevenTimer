package com.latitude.seventimer.model;

import com.latitude.seventimer.model.database.WeatherLocation;

import io.reactivex.Flowable;
import io.reactivex.Observable;

/**
 * Created by cloud on 2019/4/20.
 */
public interface IDataHelper {
    Flowable<WeatherLocation> fetchLocationInfo(float latitude, float longitude);

    Flowable<AstroWeatherCluster> fetchAstroWeather(float latitude, float longitude);

    Observable<WeatherLocation> reverseGeoCode(WeatherLocation location);
}
