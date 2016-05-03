package com.sashqua.wowser.utils;

import java.io.IOException;
import java.util.List;

import com.sashqua.wowser.Constants;
import com.sashqua.wowser.models.Season;
import com.sashqua.wowser.models.TeamList;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Processor {

    private FootballApi footballApi;

    public Processor(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        footballApi = retrofit.create(FootballApi.class);
    }

    public List<Season> getSeason(String s){
        Response<List<Season>> response;
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
            return response.body();
        }

        return null;
    }
}
