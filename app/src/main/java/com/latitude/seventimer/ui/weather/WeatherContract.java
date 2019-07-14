package com.latitude.seventimer.ui.weather;

import com.latitude.seventimer.base.BaseContract;
import com.latitude.seventimer.model.database.WeatherLocation;
import com.latitude.seventimer.model.AstroWeatherCluster;

/**
 * Created by cloud on 2019/4/20.
 */
public interface WeatherContract {
    interface IView extends BaseContract.IView {
        void refreshLocationInfo(WeatherLocation address);
        void refreshWeather(AstroWeatherCluster cluster);
    }

    interface IPresenter<T> extends BaseContract.IPresenter<T> {
        void fetchLocationInfo(float latitude, float longitude);
        void fetchLocationInfo(WeatherLocation location);
        void fetchAstroWeather(float latitude, float longitude);

        void insertLocation(WeatherLocation location);
        void insertOrUpdateLocation(WeatherLocation location);
    }
}
