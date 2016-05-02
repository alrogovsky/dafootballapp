package com.sashqua.wowser;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import com.sashqua.wowser.utils.FootballApi;
import com.sashqua.wowser.models.Season;
import com.sashqua.wowser.utils.Processor;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.converter.gson.GsonConverterFactory;

import retrofit2.Retrofit;


public class ApiService extends Service {

    final String LOG_TAG = "myLogs";
    private Processor processor;

    public ApiService() {
    }

    public void onCreate() {
        super.onCreate();
    }

    public int onStartCommand(Intent intent, int flags, int startId){
        Log.d(LOG_TAG, "Started");
        processor = new Processor();

        kek();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    void kek(){
        Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    List<Season> list = processor.getSeason("2015");
                    for(Season s : list){
                        Log.d("GOT_SHIT", s.league);
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

    }

}
