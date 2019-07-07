package com.latitude.seventimer.model.database;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by cloud on 2019/7/7.
 */
public interface IDbHelper {

    Flowable<List<WeatherLocation>> queryAllLocations();
}
