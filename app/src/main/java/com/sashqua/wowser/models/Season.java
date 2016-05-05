package com.sashqua.wowser.models;

import java.io.Serializable;

public class Season implements Serializable{
    public long id;
    public String caption;
    public String league;
    public String year;
    public int numberOfTeams;
    public int numberOfGames;
    public String lastUpdated;
}
