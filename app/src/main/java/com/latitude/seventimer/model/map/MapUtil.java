package com.latitude.seventimer.model.map;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.latitude.seventimer.model.database.WeatherLocation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cloud on 2019/7/7.
 */
public class MapUtil {

    public static WeatherLocation convertSuggestInfo(SuggestionResult.SuggestionInfo suggestionInfo) {
        WeatherLocation location = null;
        if (suggestionInfo != null) {
            location = new WeatherLocation();
            location.setAddress(suggestionInfo.getKey());
            location.setCity(suggestionInfo.getCity());
            LatLng latLng = suggestionInfo.getPt();
            if (latLng != null) {
                location.setLatitude((float) latLng.latitude);
                location.setLongitude((float) latLng.longitude);
            }
        }
        return location;
    }

    public static List<WeatherLocation> convertSuggestionInfoList(
            List<SuggestionResult.SuggestionInfo> infoList) {
        List<WeatherLocation> locationList = null;
        if (infoList != null) {
            locationList = new ArrayList<>(infoList.size());
            for (SuggestionResult.SuggestionInfo info : infoList) {
                WeatherLocation location = convertSuggestInfo(info);
                locationList.add(location);
            }
        }
        return locationList;
    }
}
