package com.example.databasemodule;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;

import com.example.databasemodule.Controllers.ConfigDao;
import com.example.databasemodule.Controllers.MeasurementDao;
import com.example.databasemodule.Controllers.UserDao;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {
    @TestApplication.ApplicationContext
    private final Context mContext;

    @TestApplication.DatabaseInfo
    private final String mDBName = "database.db";

    public DatabaseModule (@TestApplication.ApplicationContext Context context) {
        mContext = context;
    }

    @Singleton
    @Provides
    AppDatabase provideDatabase() {
        return Room.databaseBuilder(
                mContext,
                AppDatabase.class,
                mDBName
        ).fallbackToDestructiveMigration().build();
    }

    @Provides
    @TestApplication.DatabaseInfo
    String provideDatabaseName() { return mDBName; }

    @Singleton
    @Provides
    UserDao provideUserDao (AppDatabase db) { return db.userDao(); }

    @Singleton
    @Provides
    MeasurementDao provideMeasurementDao (AppDatabase db) { return db.measurementDao(); }

    @Singleton
    @Provides
    ConfigDao provideConfigDao (AppDatabase db) { return db.configDao(); }

    /*
    @Provides @Named("name")
    String provideDatabaseName();

    @Provides @Named("table_name")
    String provideDatabaseTableName();
    */
}
