package com.sashqua.wowser;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.os.ResultReceiver;
import android.util.Log;

import com.sashqua.wowser.models.FixtureList;
import com.sashqua.wowser.models.LeagueTable;
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

            switch (action) {
                case Constants.Action.GET_TEAMS: {
                    final long id = intent.getLongExtra(Constants.Data.EXTRA_SEASON_ID, -1);
                    handleGetTeams(receiver, id);
                    break;
                }
                case Constants.Action.GET_SEASONS: {
                    final String season = intent.getStringExtra(Constants.Data.EXTRA_SEASON);
                    handleGetSeasons(receiver, season);
                    break;
                }
                case Constants.Action.GET_NEXT_FIXTURES: {
                    final long id = intent.getLongExtra(Constants.Data.EXTRA_TEAM_ID, -1);
                    handleGetNextFixtures(receiver, id);
                    break;
                }
                case Constants.Action.GET_RESULTS: {
                    final long id = intent.getLongExtra(Constants.Data.EXTRA_TEAM_ID, -1);
                    handleGetResults(receiver, id);
                }
                case Constants.Action.GET_LEAGUE_TABLE: {
                    final long id = intent.getLongExtra(Constants.Data.EXTRA_SEASON_ID, -1);
                    handleGetLeagueTable(receiver, id);
                }
                default:
                    break;
            }
        }
    }

    private void handleGetTeams(ResultReceiver receiver, long teamId){
        TeamList teams = processor.getTeams(teamId);
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

    private void handleGetNextFixtures(ResultReceiver receiver, long teamId){
        FixtureList fixtures = processor.getNextTeamFixtures(teamId);
        if(fixtures != null){
            Bundle bundle = new Bundle();
            bundle.putSerializable("fixtures", fixtures);
            receiver.send(Constants.Codes.CODE_OK, bundle);
        } else {
            receiver.send(Constants.Codes.CODE_FAIL, null);
        }
    }

    private void handleGetResults(ResultReceiver receiver, long teamId){
        FixtureList fixtures = processor.getTeamResults(teamId);
        if(fixtures != null){
            Bundle bundle = new Bundle();
            bundle.putSerializable("results", fixtures);
            receiver.send(Constants.Codes.CODE_OK, bundle);
        } else {
            receiver.send(Constants.Codes.CODE_FAIL, null);
        }
    }

    private void handleGetLeagueTable(ResultReceiver receiver, long seasonId){
        LeagueTable leagueTable = processor.getLeagueTable(seasonId);
        if(leagueTable != null){
            Bundle bundle = new Bundle();
            bundle.putSerializable("league_table", leagueTable);
            receiver.send(Constants.Codes.CODE_OK, bundle);
        } else {
            receiver.send(Constants.Codes.CODE_FAIL, null);
        }
    }
}
