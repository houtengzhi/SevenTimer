package com.latitude.seventimer.model;

import android.content.Context;

import com.latitude.seventimer.model.http.IHttpHelper;

import io.reactivex.Flowable;

/**
 * Created by cloud on 2019/4/20.
 */
public class DataHelper implements IDataHelper {
    private Context mContext;
    private IHttpHelper mHttpHelper;

    public DataHelper(Context context, IHttpHelper httpHelper) {
        this.mContext = context;
        this.mHttpHelper = httpHelper;
    }

    @Override
    public Flowable<WeatherLocation> fetchLocationInfo(float latitude, float longitude) {
        return mHttpHelper.fetchLocationInfo(latitude, longitude);
    }

    @Override
    public Flowable<AstroWeatherCluster> fetchAstroWeather(float latitude, float longitude) {
        return mHttpHelper.fetchAstroWeather(latitude, longitude);
    }
}
