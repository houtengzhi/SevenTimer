package com.latitude.seventimer.model.map;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.latitude.seventimer.model.database.WeatherLocation;
import com.latitude.seventimer.util.L;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by cloud on 2019/4/27.
 */
public class MapHelper implements IMapHelper {

    private static final String TAG = "MapHelper";

    private GeoCoder mGeoCoder;
    private SuggestionSearch mSuggestionSearch;

    public MapHelper() {
        mGeoCoder = GeoCoder.newInstance();
        mSuggestionSearch = SuggestionSearch.newInstance();
    }

    OnGetGeoCoderResultListener getGeoCoderResultListener = new OnGetGeoCoderResultListener() {
        @Override
        public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

        }

        @Override
        public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
            if (reverseGeoCodeResult == null || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {

            } else {
                String address = reverseGeoCodeResult.getAddress();
                int adCode = reverseGeoCodeResult.getCityCode();
            }
        }
    };

    @Override
    public Observable<WeatherLocation> reverseGeoCode(final WeatherLocation location) {
        L.d(TAG, "reverseGeoCode(), %s", location.toString());
        return Observable.create(new ObservableOnSubscribe<WeatherLocation>() {
            @Override
            public void subscribe(final ObservableEmitter<WeatherLocation> emitter) throws Exception {
                mGeoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
                    @Override
                    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

                    }

                    @Override
                    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
                        if (reverseGeoCodeResult == null || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
                            L.i(TAG, "onGetReverseGeoCodeResult: error = %s", reverseGeoCodeResult.error.name());
                            emitter.onError(new Throwable());
                        } else {
                            String address = reverseGeoCodeResult.getAddress();
                            int adCode = reverseGeoCodeResult.getCityCode();
                            L.d(TAG, "onGetReverseGeoCodeResult: %s", reverseGeoCodeResult.toString());
                            location.setAddress(address);
                            emitter.onNext(location);
                            emitter.onComplete();
                        }
                    }
                });
                mGeoCoder.reverseGeoCode(new ReverseGeoCodeOption()
                        .location(new LatLng(location.getLatitude(), location.getLongitude()))
                        .radius(500));
            }
        });
    }

    @Override
    public Observable<List<WeatherLocation>> fetchSuggestionLocation(final String city, final String keyword) {

        return Observable.create(new ObservableOnSubscribe<List<WeatherLocation>>() {
            @Override
            public void subscribe(final ObservableEmitter<List<WeatherLocation>> emitter) throws Exception {
                L.d(TAG, "fetchSuggestionLocation(), city:%1$s, keyword:%2$s", city, keyword);
                mSuggestionSearch.setOnGetSuggestionResultListener(new OnGetSuggestionResultListener() {
                    @Override
                    public void onGetSuggestionResult(SuggestionResult suggestionResult) {
                        if (suggestionResult != null && suggestionResult.error == SearchResult.ERRORNO.NO_ERROR) {
                            List<SuggestionResult.SuggestionInfo> results = suggestionResult.getAllSuggestions();
                            L.d(TAG, "onGetSuggestionResult(), suggestionResult:%s", Arrays.toString(results.toArray()));
                            emitter.onNext(MapUtil.convertSuggestionInfoList(results));
                            emitter.onComplete();
                        } else {
                            L.i(TAG, "onGetSuggestionResult(), error:%s", suggestionResult.error.name());
                            emitter.onError(new Throwable());
                        }
                    }
                });
                mSuggestionSearch.requestSuggestion(new SuggestionSearchOption()
                        .city(city)
                        .keyword(keyword));
            }
        });
    }

    @Override
    public void release() {
        if (mGeoCoder != null) mGeoCoder.destroy();
        if (mSuggestionSearch != null) mSuggestionSearch.destroy();
    }
}
