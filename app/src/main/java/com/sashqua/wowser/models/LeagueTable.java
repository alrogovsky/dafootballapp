package com.sashqua.wowser.models;

import java.io.Serializable;
import java.util.ArrayList;

public class LeagueTable implements Serializable{
    private int matchday;
    private String leagueCaption;
    private ArrayList<Standing> standing;

    public ArrayList<Standing> getStanding() {
        return standing;
    }

    public String getLeagueCaption() {
        return leagueCaption;
    }

    public int getMatchday() {
        return matchday;
    }


}
