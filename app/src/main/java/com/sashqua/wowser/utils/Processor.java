package com.sashqua.wowser.utils;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

import com.sashqua.wowser.Constants;
import com.sashqua.wowser.content.contracts.TeamsContract;
import com.sashqua.wowser.models.FixtureList;
import com.sashqua.wowser.models.LeagueTable;
import com.sashqua.wowser.models.Season;
import com.sashqua.wowser.models.Team;
import com.sashqua.wowser.models.TeamList;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Processor {

    private FootballApi footballApi;
    private FootballApp context;

    private final Uri CONTENT_URI = Uri.parse("content://com.sashqua.wowser.dafootball.provider/teams");

    public Processor(FootballApp context){
        this.context = context;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        footballApi = retrofit.create(FootballApi.class);
    }

    public ArrayList<Season> getSeason(String s){
        Response<ArrayList<Season>> response;
        try{
            response = footballApi.getSeasons(s).execute();
        } catch (IOException e) {
            return null;
        }
        if(response.isSuccessful()){
            return response.body();
        }

        return null;
    }

    public TeamList getTeams(long id){
        Response<TeamList> response;
        try{
            response = footballApi.getTeams(id).execute();
        } catch (IOException e) {
            return null;
        }
        if(response.isSuccessful()){
            TeamList teams = response.body();
            ContentValues contentValues = new ContentValues();

            for(Team team : teams.getTeams()){
                contentValues.put(TeamsContract.Teams.COLUMN_NAME_TEAM_ID, team.getId());
                contentValues.put(TeamsContract.Teams.COLUMN_NAME_TEAM_NAME, team.getName());
                contentValues.put(TeamsContract.Teams.COLUMN_NAME_SHORT_NAME, team.getShortName());

                context.getContentResolver().insert(CONTENT_URI, contentValues);
                Log.d("KEK", "ZAPISAL V BAZU KEK");
            }

            return teams;
        }

        return null;
    }

    public FixtureList getNextTeamFixtures(long id){
        Response<FixtureList> response;
        try{
            response = footballApi.getTeamNextFixtures(id).execute();
        } catch (IOException e) {
            return null;
        }
        if(response.isSuccessful()){
            return response.body();
        }

        return null;
    }

    public FixtureList getTeamResults(long id){
        Response<FixtureList> response;
        try{
            response = footballApi.getTeamResults(id).execute();
        } catch (IOException e) {
            return null;
        }
        if(response.isSuccessful()){
            return response.body();
        }

        return null;
    }

    public LeagueTable getLeagueTable(long seasonId){
        Response<LeagueTable> response;
        try{
            response = footballApi.getLeagueTable(seasonId).execute();
        } catch (IOException e) {
            return null;
        }
        if(response.isSuccessful()){
            return response.body();
        }

        return null;
    }


}
