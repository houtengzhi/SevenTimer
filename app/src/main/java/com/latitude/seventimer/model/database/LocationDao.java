package com.latitude.seventimer.model.database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * Created by cloud on 2019/7/7.
 */
@Dao
public interface LocationDao {

    @Query("SELECT * FROM location")
    Single<List<WeatherLocation>> getAllLocations();

    @Insert
    Single<Long> insertLocation(WeatherLocation location);

    @Delete
    Single<Integer> deleteLocation(WeatherLocation location);
}
