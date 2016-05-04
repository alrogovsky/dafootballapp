package com.sashqua.wowser.utils;

import com.sashqua.wowser.Constants;
import com.sashqua.wowser.models.Fixture;
import com.sashqua.wowser.models.FixtureList;
import com.sashqua.wowser.models.Season;
import com.sashqua.wowser.models.TeamList;

import java.util.ArrayList;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Headers;
import retrofit2.Call;

public interface FootballApi {

    @Headers({"X-Auth-Token: " + Constants.API_KEY, "X-Response-Control: minified"})
    @GET("v1/soccerseasons/")
    Call<ArrayList<Season>> getSeasons(@Query("season") String season);

    @Headers({"X-Auth-Token: " + Constants.API_KEY, "X-Response-Control: minified"})
    @GET("v1/soccerseasons/{season_id}/teams/")
    Call<TeamList> getTeams(@Path("season_id") long id);

    @Headers({"X-Auth-Token: " + Constants.API_KEY, "X-Response-Control: minified"})
    @GET("v1/teams/{team_id}/fixtures?timeFrame=n14")
    Call<FixtureList> getTeamNextFixtures(@Path("team_id") long id);

    @Headers({"X-Auth-Token: " + Constants.API_KEY, "X-Response-Control: minified"})
    @GET("v1/teams/{team_id}/fixtures?timeFrame=p14")
    Call<FixtureList> getTeamResults(@Path("team_id") long id);

}
