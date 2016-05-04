package com.sashqua.wowser.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TeamList implements Serializable {

    private int count;
    private ArrayList<Team> teams;

    public ArrayList<Team> getTeams() {
        return teams;
    }

    public ArrayList<Team> getSortedTeams() {
        if(teams != null){
            Collections.sort(teams, new Comparator<Team>() {
                @Override
                public int compare(final Team object1, final Team object2) {
                    return object1.getName().compareTo(object2.getName());
                }
            });
        }

        return teams;
    }

    public int getCount() {
        return count;
    }
}
