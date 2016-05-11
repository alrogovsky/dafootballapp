package com.sashqua.wowser.models;


import java.io.Serializable;

public class Standing implements Serializable{
    private int position;
    private String teamName;
    private int playedGames;
    private String crestURI;
    private int points;
    private int goals;
    private int goalsAgainst;
    private int goalsDifference;
    private int wins;
    private int draws;
    private int losses;

    public int getLosses() {
        return losses;
    }

    public int getDraws() {
        return draws;
    }

    public int getWins() {
        return wins;
    }

    public int getGoalsDifference() {
        return goalsDifference;
    }

    public int getGoalsAgainst() {
        return goalsAgainst;
    }

    public int getGoals() {
        return goals;
    }

    public int getPoints() {
        return points;
    }

    public String getCrestURI() {
        return crestURI;
    }

    public int getPlayedGames() {
        return playedGames;
    }

    public String getTeamName() {
        return teamName;
    }

    public int getPosition() {
        return position;
    }
}
