package com.sashqua.wowser.utils;

import com.sashqua.wowser.models.Season;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.Call;

public interface FootballApi {

    @GET("v1/soccerseasons/")
    Call<List<Season>> getSeasons(@Query("season") String season);

}
