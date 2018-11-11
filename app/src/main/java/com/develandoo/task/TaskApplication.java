package com.develandoo.task;

import android.app.Application;
import android.arch.lifecycle.LifecycleObserver;
import android.content.Context;

import com.develandoo.task.helpers.NetworkHelper;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class TaskApplication extends Application implements LifecycleObserver {
    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        appContext = this;
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(configuration);
        NetworkHelper.init();


    }


    public static Context getContext() {
        return appContext;
    }


}
