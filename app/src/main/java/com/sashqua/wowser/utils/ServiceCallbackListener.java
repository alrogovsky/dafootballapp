package com.sashqua.wowser.utils;

import android.os.Bundle;

public interface ServiceCallbackListener {
    void onServiceCallback(int requestId, int resultCode, Bundle bundle);
}
