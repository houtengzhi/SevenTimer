package com.latitude.seventimer.model.map;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.latitude.seventimer.model.database.WeatherLocation;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by cloud on 2019/4/27.
 */
public class MapHelper implements IMapHelper {

    private GeoCoder mGeoCoder;

    public MapHelper() {
        mGeoCoder = GeoCoder.newInstance();
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

                        } else {
                            String address = reverseGeoCodeResult.getAddress();
                            int adCode = reverseGeoCodeResult.getCityCode();
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

    public interface OnGeoCodeListener {
        void onResult(String address);
    }
}
