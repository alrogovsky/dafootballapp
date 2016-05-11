package com.sashqua.wowser.models;


import java.io.Serializable;

public class Fixture implements Serializable {
    private long id;
    private long soccerseasonId;
    private String date;
    private int matchday;
    private String homeTeamName;
    private String awayTeamName;
    private long homeTeamId;
    private long awayTeamId;
    private Result result;

    public long getId() {
        return id;
    }

    public long getSoccerseasonId() {
        return soccerseasonId;
    }

    public String getDate() {
        return date;
    }

    public int getMatchday() {
        return matchday;
    }

    public String getHomeTeamName() {
        return homeTeamName;
    }

    public String getAwayTeamName() {
        return awayTeamName;
    }

    public long getHomeTeamId() {
        return homeTeamId;
    }

    public long getAwayTeamId() {
        return awayTeamId;
    }

    public Result getResult() {
        return result;
    }
}
