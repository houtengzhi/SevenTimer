package com.latitude.seventimer.model.database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

/**
 * Created by cloud on 2019/7/7.
 */
@Dao
public interface LocationDao {

    @Query("SELECT * FROM location")
    List<WeatherLocation> getAllLocations();

    @Insert
    void insertLocation(WeatherLocation location);

    @Delete
    void deleteLocation(WeatherLocation location);
}
