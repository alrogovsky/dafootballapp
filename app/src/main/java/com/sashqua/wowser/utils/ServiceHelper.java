package com.sashqua.wowser.utils;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.os.ResultReceiver;

import com.sashqua.wowser.Constants;
import com.sashqua.wowser.NetService;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class ServiceHelper implements ServiceCallbackListener {

    private Application application;
    private AtomicInteger idCounter = new AtomicInteger();
    private ArrayList<ServiceCallbackListener> currentListeners = new ArrayList<>();

    public ServiceHelper(Application app){
        this.application = app;
        currentListeners.add(this);
    }

    public void addListener(ServiceCallbackListener currentListener) {
        currentListeners.add(currentListener);
    }

    public void removeListener(ServiceCallbackListener currentListener) {
        currentListeners.remove(currentListener);
    }

    private Intent createIntent(String action, final int requestId) {
        Intent i = new Intent(application, NetService.class);
        i.setAction(action);

        i.putExtra(Constants.RECEIVER, new ResultReceiver(new Handler()) {
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                for (ServiceCallbackListener currentListener : currentListeners) {
                    if (currentListener != null) {
                        currentListener.onServiceCallback(requestId, resultCode, resultData);
                    }
                }
            }
        });

        return i;
    }

    private int createId() {
        return idCounter.getAndIncrement();
    }

    public int getTeams(long leagueId){
        final int requestId = createId();
        Intent intent = createIntent(Constants.Action.GET_TEAMS, requestId);
        intent.putExtra(Constants.Data.EXTRA_SEASON_ID, leagueId);

        application.startService(intent);

        return requestId;
    }

    public int getSeasons(String season){
        final int requestId = createId();
        Intent intent = createIntent(Constants.Action.GET_SEASONS, requestId);
        intent.putExtra(Constants.Data.EXTRA_SEASON, season);

        application.startService(intent);

        return requestId;
    }

    public int getTeamNextFixtures(long teamId){
        final int requestId = createId();
        Intent intent = createIntent(Constants.Action.GET_NEXT_FIXTURES, requestId);
        intent.putExtra(Constants.Data.EXTRA_TEAM_ID, teamId);

        application.startService(intent);

        return requestId;
    }

    public int getTeamResults(long teamId){
        final int requestId = createId();
        Intent intent = createIntent(Constants.Action.GET_RESULTS, requestId);
        intent.putExtra(Constants.Data.EXTRA_TEAM_ID, teamId);

        application.startService(intent);

        return requestId;
    }

    public int getLeagueTable(long seasonId){
        final int requestId = createId();
        Intent intent = createIntent(Constants.Action.GET_LEAGUE_TABLE, requestId);
        intent.putExtra(Constants.Data.EXTRA_SEASON_ID, seasonId);

        application.startService(intent);

        return requestId;
    }

    @Override
    public void onServiceCallback(int requestId, int resultCode, Bundle bundle){
    }

}
