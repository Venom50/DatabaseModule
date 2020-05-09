package com.example.databasemodule;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.example.databasemodule.Views.AddUserActivity;
import com.example.databasemodule.Views.DownloadUsersActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        DatabaseModule.class
})
public interface ApplicationComponent {
    void inject (TestApplication testApplication);
    void inject (AddUserActivity addUserActivity);
    void inject (DownloadUsersActivity downloadUsersActivity);

    @TestApplication.ApplicationContext
    Context getContext();

    Application getApplication();

    @TestApplication.DatabaseInfo
    String getDatabaseName();
}
