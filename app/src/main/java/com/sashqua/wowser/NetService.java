package com.sashqua.wowser;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.os.ResultReceiver;
import android.util.Log;

import com.sashqua.wowser.models.FixtureList;
import com.sashqua.wowser.models.Season;
import com.sashqua.wowser.models.TeamList;
import com.sashqua.wowser.utils.Processor;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


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
            } else if(Constants.Action.GET_SEASONS.equals(action)){
                final String season = intent.getStringExtra(Constants.Data.EXTRA_SEASON);
                handleGetSeasons(receiver, season);
            } else if(Constants.Action.GET_NEXT_FIXTURES.equals(action)){
                final long id = intent.getLongExtra(Constants.Data.EXTRA_TEAM_ID, -1);
                handleGetNextFixtures(receiver, id);
                Log.d("KEK", "HANDLING");
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

    private void handleGetSeasons(ResultReceiver receiver, String season){
        ArrayList<Season> seasons = processor.getSeason(season);
        if(seasons != null){
            Bundle bundle = new Bundle();
            bundle.putSerializable("seasons", seasons);
            receiver.send(Constants.Codes.CODE_OK, bundle);
        } else {
            receiver.send(Constants.Codes.CODE_FAIL, null);
        }
    }

    private void handleGetNextFixtures(ResultReceiver receiver, long id){
        FixtureList fixtures = processor.getNextTeamFixtures(id);
        if(fixtures != null){
            Bundle bundle = new Bundle();
            bundle.putSerializable("fixtures", fixtures);
            receiver.send(Constants.Codes.CODE_OK, bundle);
        } else {
            receiver.send(Constants.Codes.CODE_FAIL, null);
        }
    }
}
