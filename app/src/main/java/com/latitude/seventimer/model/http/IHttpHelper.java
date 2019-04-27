package com.latitude.seventimer.model.http;

import com.latitude.seventimer.model.WeatherLocation;
import com.latitude.seventimer.model.AstroWeatherCluster;

import io.reactivex.Flowable;

/**
 * Created by yechy on 2017/9/15.
 */

public interface IHttpHelper {

    Flowable<WeatherLocation> fetchLocationInfo(float latitude, float longitude);

    Flowable<AstroWeatherCluster> fetchAstroWeather(float latitude, float longitude);
}
