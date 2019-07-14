package com.latitude.seventimer.model.database;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * Created by cloud on 2019/7/7.
 */
public interface IDbHelper {

    Single<List<WeatherLocation>> queryAllLocations();

    Single<WeatherLocation> queryLocationById(int id);

    Single<Long> insertLocation(WeatherLocation location);

    Single<Integer> updateLocation(WeatherLocation location);

    Single<Integer> deleteLocation(WeatherLocation location);

    Single<Long> insertOrUpdateLocation(WeatherLocation location);
}
