package com.latitude.seventimer.model.map;

import com.latitude.seventimer.model.database.WeatherLocation;

import io.reactivex.Observable;

/**
 * Created by cloud on 2019/4/27.
 */
public interface IMapHelper {

    Observable<WeatherLocation> reverseGeoCode(WeatherLocation location);
}
