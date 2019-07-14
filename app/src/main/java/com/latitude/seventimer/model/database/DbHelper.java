package com.latitude.seventimer.model.database;

import com.latitude.seventimer.util.L;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;

/**
 * Created by cloud on 2019/7/7.
 */
public class DbHelper implements IDbHelper {
    private static final String TAG = "DbHelper";
    private AppDatabase mAppDatabase;

    public DbHelper(AppDatabase database) {
        this.mAppDatabase = database;
    }

    @Override
    public Single<List<WeatherLocation>> queryAllLocations() {
        L.d(TAG, "queryAllLocations()");
        return mAppDatabase.locationDao().getAllLocations();
    }

    @Override
    public Single<WeatherLocation> queryLocationById(int id) {
        L.d(TAG, "queryLocationById(), id:%d", id);
        return mAppDatabase.locationDao().getLocationById(id);
    }

    @Override
    public Single<Long> insertLocation(WeatherLocation location) {
        L.d(TAG, "insertLocation()");
        return mAppDatabase.locationDao().insertLocation(location);
    }

    @Override
    public Single<Integer> updateLocation(WeatherLocation location) {
        L.d(TAG, "updateLocation(), %s", location.toString());
        return mAppDatabase.locationDao().updateLocation(location);
    }

    @Override
    public Single<Integer> deleteLocation(WeatherLocation location) {
        return mAppDatabase.locationDao().deleteLocation(location);
    }

    @Override
    public Single<Long> insertOrUpdateLocation(final WeatherLocation location) {
        L.d(TAG, "insertOrUpdateLocation(), location: %s", location.toString());
        return queryLocationById(location.getId())
                .onErrorReturn(new Function<Throwable, WeatherLocation>() {
                    @Override
                    public WeatherLocation apply(Throwable throwable) throws Exception {
                        L.w(TAG, "insertOrUpdateLocation: onErrorReturn");
                        throwable.printStackTrace();
                        return null;
                    }
                })
                .flatMap(new Function<WeatherLocation, SingleSource<? extends Long>>() {
                    @Override
                    public SingleSource<? extends Long> apply(WeatherLocation oldLocation) throws Exception {
                        L.d(TAG, "insertOrUpdateLocation: flatMap");
                        return updateLocation(location)
                                .map(new Function<Integer, Long>() {
                                    @Override
                                    public Long apply(Integer integer) throws Exception {
                                        return Long.valueOf(integer);
                                    }
                                });
                    }
                })
                .onErrorResumeNext(new Function<Throwable, SingleSource<? extends Long>>() {
                    @Override
                    public SingleSource<? extends Long> apply(Throwable throwable) throws Exception {
                        L.d(TAG, "insertOrUpdateLocation: onErrorResumeNext");
                        return insertLocation(location);
                    }
                });
    }
}
