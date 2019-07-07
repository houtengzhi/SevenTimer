package com.latitude.seventimer.model.map;

import com.latitude.seventimer.model.database.WeatherLocation;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;

/**
 * Created by cloud on 2019/4/27.
 */
public interface IMapHelper {

    public Observable<WeatherLocation> reverseGeoCode(WeatherLocation location);

    public Observable<List<WeatherLocation>> fetchSuggestionLocation(String city, String keyword);

    public void release();
}
