package com.latitude.seventimer.model.database;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.FlowableOnSubscribe;

/**
 * Created by cloud on 2019/7/7.
 */
public class DbHelper implements IDbHelper {
    private AppDatabase mAppDatabase;

    public DbHelper(AppDatabase database) {
        this.mAppDatabase = database;
    }

    @Override
    public Flowable<List<WeatherLocation>> queryAllLocations() {
        Flowable.create(new FlowableOnSubscribe<Object>() {
        })
        return null;
    }
}
