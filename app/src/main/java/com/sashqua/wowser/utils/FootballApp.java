package com.sashqua.wowser.utils;

import android.app.Application;

public class FootballApp extends Application {

    private ServiceHelper serviceHelper;

    public void initServiceHelper() {
        serviceHelper = new ServiceHelper(this);
    }

    public ServiceHelper getServiceHelper() {
        return serviceHelper;
    }

}
