package com.sashqua.wowser.models;


import java.io.Serializable;

public class Result implements Serializable{

    private int goalsHomeTeam;
    private int goalsAwayTeam;

    public int getGoalsHomeTeam() {
        return goalsHomeTeam;
    }

    public int getGoalsAwayTeam() {
        return goalsAwayTeam;
    }
}
