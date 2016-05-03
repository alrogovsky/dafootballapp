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
    protected void onPause() {
        super.onPause();
        serviceHelper.removeListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        serviceHelper.addListener(this);
    }

    public ServiceHelper getServiceHelper() {
        return serviceHelper;
    }

}
