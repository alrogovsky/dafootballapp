package com.sashqua.wowser.models;

import java.io.Serializable;
import java.util.List;

public class TeamList implements Serializable {

    private int count;
    private List<Team> teams;

    public List<Team> getTeams() {
        return teams;
    }

    public int getCount() {
        return count;
    }
}
