package com.latitude.seventimer.model.http;

import com.latitude.seventimer.model.database.WeatherLocation;
import com.latitude.seventimer.model.AstroWeatherBinder;

import io.reactivex.Flowable;

/**
 * Created by yechy on 2017/9/15.
 */

public interface IHttpHelper {

    Flowable<WeatherLocation> fetchLocationInfo(float latitude, float longitude);

    Flowable<AstroWeatherBinder> fetchAstroWeather(float latitude, float longitude);
}
