package com.sashqua.wowser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sashqua.wowser.utils.FootballApp;
import com.sashqua.wowser.utils.ServiceCallbackListener;
import com.sashqua.wowser.utils.ServiceHelper;

public abstract class NetBaseActivity extends AppCompatActivity implements ServiceCallbackListener {

    private ServiceHelper serviceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        serviceHelper = ((FootballApp) getApplication()).getServiceHelper();

        if (serviceHelper == null) {
            ((FootballApp) getApplication()).initServiceHelper();
            serviceHelper = ((FootballApp) getApplication()).getServiceHelper();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        serviceHelper.removeListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        serviceHelper.addListener(this);
    }

    public ServiceHelper getServiceHelper() {
        return serviceHelper;
    }

}
