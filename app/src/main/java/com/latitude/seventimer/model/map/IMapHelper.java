package com.latitude.seventimer.model.map;

import com.latitude.seventimer.model.WeatherLocation;

import io.reactivex.Flowable;

/**
 * Created by cloud on 2019/4/27.
 */
public interface IMapHelper {

    Flowable<WeatherLocation> reverseGeoCode(WeatherLocation location);
}
