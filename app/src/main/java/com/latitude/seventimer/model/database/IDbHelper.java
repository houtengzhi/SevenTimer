package com.latitude.seventimer.model.database;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * Created by cloud on 2019/7/7.
 */
public interface IDbHelper {

    Single<List<WeatherLocation>> queryAllLocations();

    Single<Long> insertLocation(WeatherLocation location);

    Single<Integer> deleteLocation(WeatherLocation location);
}
