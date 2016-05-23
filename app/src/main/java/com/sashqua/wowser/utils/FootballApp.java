package com.sashqua.wowser.utils;

import android.app.Application;
import android.content.Context;

public class FootballApp extends Application {

    private ServiceHelper serviceHelper;
    private static Context mAppContext;


    // TODO
    public static Context getAppContext() {
        return mAppContext;
    }

    public void initServiceHelper() {
        serviceHelper = new ServiceHelper(this);
    }

    public ServiceHelper getServiceHelper() {
        return serviceHelper;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = getApplicationContext();
    }
}
