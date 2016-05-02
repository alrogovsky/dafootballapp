package com.sashqua.wowser.utils;

import java.io.IOException;
import java.util.List;

import com.sashqua.wowser.models.Season;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Processor {

    private FootballApi footballApi;

    public Processor(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.football-data.org")
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
}
