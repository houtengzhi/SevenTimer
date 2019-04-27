package com.latitude.seventimer.model;

import io.reactivex.Flowable;

/**
 * Created by cloud on 2019/4/20.
 */
public interface IDataHelper {
    Flowable<WeatherLocation> fetchLocationInfo(float latitude, float longitude);

    Flowable<AstroWeatherCluster> fetchAstroWeather(float latitude, float longitude);
}
