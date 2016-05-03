package com.sashqua.wowser.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.sashqua.wowser.Constants;
import com.sashqua.wowser.R;

public class LoadingActivity extends AppCompatActivity {

    private final int WAIT_TIME = 2500;
    private SharedPreferences sPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                sPref = getSharedPreferences("TEST", MODE_PRIVATE);
                long saved_id = sPref.getLong(Constants.Data.SAVED_TEAM_ID, 0);

                if(saved_id != 0){
                    Log.d("KEK", "SAVED_ID= " + saved_id);
                    Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
                    LoadingActivity.this.startActivity(intent);
                    LoadingActivity.this.finish();
                } else {
                    Intent mainIntent = new Intent(LoadingActivity.this, TeamSelectionActivity.class);
                    LoadingActivity.this.startActivity(mainIntent);
                    LoadingActivity.this.finish();
                }

            }
        }, WAIT_TIME);
    }
}
