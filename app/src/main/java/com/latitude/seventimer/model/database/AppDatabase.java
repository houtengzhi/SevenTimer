package com.latitude.seventimer.model.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * Created by cloud on 2019/7/7.
 */
@Database(entities = {WeatherLocation.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract LocationDao locationDao();
}
