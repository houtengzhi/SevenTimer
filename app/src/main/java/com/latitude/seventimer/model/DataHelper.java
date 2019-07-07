package com.latitude.seventimer.model;

import android.content.Context;

import com.latitude.seventimer.model.database.WeatherLocation;
import com.latitude.seventimer.model.http.IHttpHelper;
import com.latitude.seventimer.model.map.IMapHelper;

import io.reactivex.Flowable;
import io.reactivex.Observable;

/**
 * Created by cloud on 2019/4/20.
 */
public class DataHelper implements IDataHelper {
    private Context mContext;
    private IHttpHelper mHttpHelper;
    private IMapHelper mMapHelper;

    public DataHelper(Context context, IHttpHelper httpHelper, IMapHelper mapHelper) {
        this.mContext = context;
        this.mHttpHelper = httpHelper;
        this.mMapHelper = mapHelper;
    }

    @Override
    public Flowable<WeatherLocation> fetchLocationInfo(float latitude, float longitude) {
        return mHttpHelper.fetchLocationInfo(latitude, longitude);
    }

    @Override
    public Flowable<AstroWeatherCluster> fetchAstroWeather(float latitude, float longitude) {
        return mHttpHelper.fetchAstroWeather(latitude, longitude);
    }

    @Override
    public Observable<WeatherLocation> reverseGeoCode(WeatherLocation location) {
        return mMapHelper.reverseGeoCode(location);
    }
}
