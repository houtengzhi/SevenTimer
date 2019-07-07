package com.latitude.seventimer.ui.location;

import com.latitude.seventimer.base.BaseContract;
import com.latitude.seventimer.model.database.WeatherLocation;

import java.util.List;

/**
 * Created by cloud on 2019/4/21.
 */
public interface LocationSettingsContract {
    interface IView extends BaseContract.IView {
        void refreshSuggestionLocation(List<WeatherLocation> locationList);
        void refreshLocationList(List<WeatherLocation> locationList);
    }

    interface IPresenter<T> extends BaseContract.IPresenter<T> {
        void getAllLocations();
        void insertLocation(WeatherLocation location);
        void deleteLocation(WeatherLocation location);

        void registerSearchLocation();
        void startSearchLocation(String city, String keyword);
        void fetchSuggestionLocation(String city, String keyword);
    }
}
