package com.latitude.seventimer.model;

import com.latitude.seventimer.model.http.ApiResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yechy on 2015/9/6.
 */
public class AstroWeatherBinder {
    private List<AstroWeather> astroWeatherList = new ArrayList<>();
    private int timeZone;
    private String updateTime;

    public AstroWeatherBinder() {

    }

    public AstroWeatherBinder(List<AstroWeather> astroWeatherList, int timeZone, String updateTime) {
        this.astroWeatherList = astroWeatherList;
        this.timeZone = timeZone;
        this.updateTime = updateTime;
    }

    public List<AstroWeather> getList() {
        return astroWeatherList;
    }

    public int getTimeZone() {
        return timeZone;
    }

    public String getUpdateTime() {
        return updateTime;
    }
}
