package com.latitude.seventimer.model.http.api;

import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by cloud on 2019/4/20.
 */
public interface SevenTimerApi {

    @GET("http://maps.googleapis.com/maps/api/geocode/json")
    Flowable<ResponseBody> fetchLocationInfo(@Query("latlng") String latlng, @Query("sensor") String sensor);

    @GET("bin/astro.php")
    Flowable<ResponseBody> fetchSevenTimerData(@QueryMap Map<String, String> map);
}
