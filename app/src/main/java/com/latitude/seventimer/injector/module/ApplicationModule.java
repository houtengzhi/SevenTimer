package com.latitude.seventimer.injector.module;

import android.content.Context;

import com.latitude.seventimer.App;
import com.latitude.seventimer.injector.qualifier.ContextLife;
import com.latitude.seventimer.injector.qualifier.DatabaseInfo;
import com.latitude.seventimer.model.DataHelper;
import com.latitude.seventimer.model.IDataHelper;
import com.latitude.seventimer.model.http.IHttpHelper;
import com.latitude.seventimer.model.http.RetrofitHelper;
import com.latitude.seventimer.model.http.api.SevenTimerApi;
import com.latitude.seventimer.util.L;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yechy on 2017/3/30.
 */
@Module
public class ApplicationModule {
    private App mApp;
    private static final String TAG = ApplicationModule.class.getSimpleName();

    public ApplicationModule(App mApp) {
        L.d(TAG, "ApplicationModule()");
        this.mApp = mApp;
    }

    @Provides
    @Singleton
    @ContextLife()
    public Context provideApplicationContext() {
        return mApp.getApplicationContext();
    }

    @Provides
    @Singleton
    public IHttpHelper provideRetrofitHelper(SevenTimerApi sevenTimerApi) {
        return new RetrofitHelper(sevenTimerApi);
    }

    @Provides
    @DatabaseInfo
    public String provideDatabaseName() {
        L.d(TAG, "provideDatabaseName()");
        return "dvb_channel.db";
    }

    @Provides
    @Singleton
    public IDataHelper provideDataHelper(@ContextLife Context context, IHttpHelper httpHelper) {
        L.d(TAG, "provideDataHelper()");
        return new DataHelper(context, httpHelper);
    }

}
