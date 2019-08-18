package com.latitude.seventimer.model.http;

import com.latitude.seventimer.model.AstroWeatherBinder;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.rx_cache2.EvictProvider;
import io.rx_cache2.LifeCache;
import io.rx_cache2.ProviderKey;

/**
 * Created by cloud on 2019-08-17.
 */
public interface CacheProviders {

    @ProviderKey("cache-seven-timer")
    @LifeCache(duration = 1, timeUnit = TimeUnit.MINUTES)
    Flowable<AstroWeatherResponse> fetchSevenTimerData(
            Flowable<AstroWeatherResponse> responseFlowable);

    @ProviderKey("cache-seven-timer")
    Flowable<AstroWeatherResponse> fetchSevenTimerData(
            Flowable<AstroWeatherResponse> responseFlowable, EvictProvider evictProvider);
}
