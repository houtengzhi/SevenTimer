package com.latitude.seventimer.injector.Component;

import android.content.Context;
import com.latitude.seventimer.injector.module.ApplicationModule;
import com.latitude.seventimer.injector.module.DbModule;
import com.latitude.seventimer.injector.module.HttpModule;
import com.latitude.seventimer.injector.qualifier.ContextLife;
import com.latitude.seventimer.model.IDataHelper;
import com.latitude.seventimer.model.database.IDbHelper;
import com.latitude.seventimer.model.http.IHttpHelper;
import com.latitude.seventimer.model.map.IMapHelper;
import com.latitude.seventimer.model.prefs.IPreferencesHelper;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by yechy on 2017/3/31.
 */
@Singleton
@Component(modules = {ApplicationModule.class, HttpModule.class, DbModule.class})
public interface ApplicationComponent {

    @ContextLife()
    Context getApplicationContext();

    IHttpHelper getRetrofitHelper();

    IMapHelper getMapHelper();

    IDataHelper getDataHelper();

    IDbHelper getDbHelper();
}
