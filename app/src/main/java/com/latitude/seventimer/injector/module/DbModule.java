package com.latitude.seventimer.injector.module;

import android.content.Context;

import com.latitude.seventimer.injector.qualifier.ContextLife;
import com.latitude.seventimer.model.database.AppDatabase;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;

/**
 * Created by cloud on 2019/7/7.
 */
@Module
public class DbModule {

    @Singleton
    @Provides
    AppDatabase provideAppDatabase(@ContextLife Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "database.db")
                .fallbackToDestructiveMigration()
                .build();
    }
}
