package com.example.databasemodule;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.example.databasemodule.Views.AddConfigActivity;
import com.example.databasemodule.Views.AddMeasurementActivity;
import com.example.databasemodule.Views.AddUserActivity;
import com.example.databasemodule.Views.DownloadConfigActivity;
import com.example.databasemodule.Views.DownloadMeasurementsActivity;
import com.example.databasemodule.Views.DownloadUsersActivity;
import com.example.databasemodule.Views.frontEnd.ChartActivity;
import com.example.databasemodule.Views.frontEnd.TableActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        DatabaseModule.class
})
public interface ApplicationComponent {
    /*
    Dodanie wstrzykiwania zależności
    Wymagane jest podanie konkretnej nazwy aktywności w której
    wstrzykiwanie ma być wykorzystywane
     */
    void inject (TestApplication testApplication);
    void inject (AddUserActivity addUserActivity);
    void inject (DownloadUsersActivity downloadUsersActivity);
    void inject (AddMeasurementActivity addMeasurementActivity);
    void inject (AddConfigActivity addConfigActivity);
    void inject (DownloadMeasurementsActivity downloadMeasurementsActivity);
    void inject (DownloadConfigActivity downloadConfigActivity);
    void inject (TableActivity tableActivity);
    void inject (ChartActivity chartActivity);

    @TestApplication.ApplicationContext
    Context getContext();

    Application getApplication();

    @TestApplication.DatabaseInfo
    String getDatabaseName();
}
