package com.latitude.seventimer.model.http;

import com.latitude.seventimer.model.database.WeatherLocation;
import com.latitude.seventimer.model.AstroWeatherBinder;
import com.latitude.seventimer.model.http.api.SevenTimerApi;
import com.latitude.seventimer.util.DataParserUtil;
import com.latitude.seventimer.util.L;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

/**
 * Created by cloud on 2019/3/31.
 */
public class RetrofitHelper implements IHttpHelper {

    private static final String TAG = Retrofit.class.getSimpleName();
    private SevenTimerApi mSevenTimerApi;

    private CacheProviders mCacheProviders;

    public RetrofitHelper(SevenTimerApi sevenTimerApi, CacheProviders cacheProviders) {
        this.mSevenTimerApi = sevenTimerApi;
        this.mCacheProviders = cacheProviders;
    }

    @Override
    public Flowable<WeatherLocation> fetchLocationInfo(final float latitude, final float longitude) {
        L.d(TAG, "fetchLocationInfo(), latitude:%s, longitude:%s", latitude, longitude);
        String latlng = String.valueOf(latitude) + "," + longitude;
        return mSevenTimerApi.fetchLocationInfo(latlng, String.valueOf(false))
                .map(new Function<ResponseBody, WeatherLocation>() {
                    @Override
                    public WeatherLocation apply(ResponseBody responseBody) throws Exception {
                        try {
                            String response = responseBody.string();
                            return DataParserUtil.parseLocation(response, latitude, longitude);
                        } finally {
                            responseBody.close();
                        }

                    }
                });
    }

    @Override
    public Flowable<AstroWeatherBinder> fetchAstroWeather(final float latitude, final float longitude) {
        L.d(TAG, "fetchAstroWeather(), latitude:%s, longitude:%s", latitude, longitude);
        Map<String, String> map = createQuestMap(latitude, longitude);
        return mCacheProviders.fetchSevenTimerData(mSevenTimerApi.fetchSevenTimerData(map))
               .map(new Function<AstroWeatherResponse, AstroWeatherBinder>() {
                   @Override
                   public AstroWeatherBinder apply(AstroWeatherResponse astroWeatherResponse) throws Exception {
                       if (astroWeatherResponse.isDataValid()) {
                           return astroWeatherResponse.getData();
                       }
                       return null;
                   }
               });
    }

    private Map<String, String> createQuestMap(float latitude, float longitude) {
        Map<String, String> map = new HashMap<>();
        map.put("lon", String.valueOf(longitude));
        map.put("lat", String.valueOf(latitude));
        map.put("ac", String.valueOf(0));
        map.put("lang", "zh-CN");
        map.put("unit", "metric");
        map.put("output", "json");
        map.put("tzshift", String.valueOf(0));
        return map;
    }
}
