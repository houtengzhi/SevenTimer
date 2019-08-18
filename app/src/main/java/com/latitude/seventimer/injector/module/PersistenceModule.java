package com.latitude.seventimer.injector.module;

import android.content.Context;

import com.latitude.seventimer.injector.qualifier.ContextLife;
import com.latitude.seventimer.model.database.AppDatabase;
import com.latitude.seventimer.model.http.CacheProviders;

import javax.inject.Singleton;

import androidx.room.Room;

import java.io.File;

import dagger.Module;
import dagger.Provides;
import io.rx_cache2.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;

/**
 * Created by cloud on 2019/7/7.
 */
@Module
public class PersistenceModule {

    @Singleton
    @Provides
    AppDatabase provideAppDatabase(@ContextLife Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "database.db")
                .fallbackToDestructiveMigration()
                .build();
    }

    @Singleton
    @Provides
    CacheProviders provideCacheProviders(@ContextLife Context context) {
        File file = context.getFilesDir();
        return new RxCache.Builder()
                .persistence(file, new GsonSpeaker())
                .using(CacheProviders.class);
    }
}
