package com.latitude.seventimer.model;

import android.content.Context;

import com.latitude.seventimer.model.database.IDbHelper;
import com.latitude.seventimer.model.database.WeatherLocation;
import com.latitude.seventimer.model.http.IHttpHelper;
import com.latitude.seventimer.model.map.IMapHelper;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by cloud on 2019/4/20.
 */
public class DataHelper implements IDataHelper {
    private Context mContext;
    private IHttpHelper mHttpHelper;
    private IMapHelper mMapHelper;
    private IDbHelper mDbHelper;

    public DataHelper(Context context, IHttpHelper httpHelper, IMapHelper mapHelper, IDbHelper dbHelper) {
        this.mContext = context;
        this.mHttpHelper = httpHelper;
        this.mMapHelper = mapHelper;
        this.mDbHelper = dbHelper;
    }

    @Override
    public Flowable<WeatherLocation> fetchLocationInfo(float latitude, float longitude) {
        return mHttpHelper.fetchLocationInfo(latitude, longitude);
    }

    @Override
    public Flowable<AstroWeatherBinder> fetchAstroWeather(float latitude, float longitude) {
        return mHttpHelper.fetchAstroWeather(latitude, longitude);
    }

    @Override
    public Observable<WeatherLocation> reverseGeoCode(WeatherLocation location) {
        return mMapHelper.reverseGeoCode(location);
    }

    public Observable<List<WeatherLocation>> fetchSuggestionLocation(String city, String keyword) {
        return mMapHelper.fetchSuggestionLocation(city, keyword);
    }

    @Override
    public Single<List<WeatherLocation>> queryAllLocations() {
        return mDbHelper.queryAllLocations();
    }

    @Override
    public Single<Long> insertLocation(WeatherLocation location) {
        return mDbHelper.insertLocation(location);
    }

    @Override
    public Single<Long> insertOrUpdateLocation(WeatherLocation location) {
        return mDbHelper.insertOrUpdateLocation(location);
    }

    @Override
    public Single<Integer> updateLocation(WeatherLocation location) {
        return mDbHelper.updateLocation(location);
    }

    @Override
    public Single<Integer> deleteLocation(WeatherLocation location) {
        return mDbHelper.deleteLocation(location);
    }

    @Override
    public void release() {
        mMapHelper.release();
    }
}
