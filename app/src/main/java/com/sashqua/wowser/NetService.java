package com.sashqua.wowser;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.os.ResultReceiver;

import com.sashqua.wowser.models.TeamList;
import com.sashqua.wowser.utils.Processor;


public class NetService extends IntentService {

    private Processor processor;

    public NetService(){
        super("NetService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        processor = new Processor();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final ResultReceiver receiver = intent.getParcelableExtra(Constants.RECEIVER);
            final String action = intent.getAction();

            if(Constants.Action.GET_TEAMS.equals(action)){
                final long id = intent.getLongExtra(Constants.Data.EXTRA_LEAGUE_ID, -1);
                handleGetTeams(receiver, id);
            }

        }
    }

    private void handleGetTeams(ResultReceiver receiver, long id){
        TeamList teams = processor.getTeams(id);
        if(teams != null){
            Bundle bundle = new Bundle();
            bundle.putSerializable("teams", teams);
            receiver.send(Constants.Codes.CODE_OK, bundle);
        } else {
            receiver.send(Constants.Codes.CODE_FAIL, null);
        }
    }
}
