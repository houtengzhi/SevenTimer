package com.latitude.seventimer.model.database;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Single;

/**
 * Created by cloud on 2019/7/7.
 */
public class DbHelper implements IDbHelper {
    private AppDatabase mAppDatabase;

    public DbHelper(AppDatabase database) {
        this.mAppDatabase = database;
    }

    @Override
    public Single<List<WeatherLocation>> queryAllLocations() {
        return mAppDatabase.locationDao().getAllLocations();
    }

    @Override
    public Single<Long> insertLocation(WeatherLocation location) {
        return mAppDatabase.locationDao().insertLocation(location);
    }

    @Override
    public Single<Integer> deleteLocation(WeatherLocation location) {
        return mAppDatabase.locationDao().deleteLocation(location);
    }
}
