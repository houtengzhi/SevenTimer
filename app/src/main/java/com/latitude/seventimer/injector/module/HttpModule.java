package com.latitude.seventimer.injector.module;


import android.util.Log;

import com.latitude.seventimer.App;
import com.latitude.seventimer.model.http.api.SevenTimerApi;
import com.latitude.seventimer.support.exception.CustomException;
import com.latitude.seventimer.support.exception.ExceptionCode;
import com.latitude.seventimer.support.retrofit.RetrofitUrlManager;
import com.latitude.seventimer.util.ConstantConfig;
import com.latitude.seventimer.util.L;
import com.latitude.seventimer.util.NetUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by yechy on 2017/4/18.
 */
@Module
public class HttpModule {
    private static final String TAG = HttpModule.class.getSimpleName();
    public HttpModule() {
        L.d(TAG, "HttpModule()");
    }

    @Singleton
    @Provides
    SevenTimerApi provideSevenTimerApi(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, ConstantConfig.SEVEN_TIMER_DOMAIN, null)
                .create(SevenTimerApi.class);
    }

    @Singleton
    @Provides
    Retrofit.Builder provideRetrofitBuilder() {
        return new Retrofit.Builder();
    }

    @Singleton
    @Provides
    OkHttpClient.Builder provideOkHttpBuilder() {
        return new OkHttpClient.Builder();
    }

    private Retrofit createRetrofit(Retrofit.Builder builder, OkHttpClient client, String baseUrl,
                                    Converter.Factory factory) {
        L.d(TAG, "createRetrofit(), baseUrl = " + baseUrl);
        builder.baseUrl(baseUrl)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        if (factory != null) {
            builder.addConverterFactory(factory);
        }
        return builder.build();
    }

    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);
        RetrofitUrlManager.getInstance().with(builder);

        if (L.logSwitch || L.logLevel <= Log.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }

        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetUtil.isNetworkAvailable(App.getInstance().getApplicationContext())) {
                    L.e("HttpModule", "无可用网络！");
                    throw new CustomException(ExceptionCode.ERROR_NO_NETWORK);
                }
                return chain.proceed(request);
            }
        });

        return builder.build();
    }
}
