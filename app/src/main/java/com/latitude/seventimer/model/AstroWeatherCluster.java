package com.latitude.seventimer.model;

import java.util.ArrayList;

/**
 * Created by yechy on 2015/9/6.
 */
public class AstroWeatherCluster {
    private ArrayList<AstroWeather> astroWeatherList = new ArrayList<AstroWeather>();
    private int timeZone;
    private String updateTime;

    public AstroWeatherCluster() {

    }

    public AstroWeatherCluster(ArrayList<AstroWeather> astroWeatherList, int timeZone, String updateTime) {
        this.astroWeatherList = astroWeatherList;
        this.timeZone = timeZone;
        this.updateTime = updateTime;
    }

    public ArrayList<AstroWeather> getList() {
        return astroWeatherList;
    }

    public int getTimeZone() {
        return timeZone;
    }

    public String getUpdateTime() {
        return updateTime;
    }
}
