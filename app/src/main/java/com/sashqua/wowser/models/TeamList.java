package com.sashqua.wowser.models;

import java.io.Serializable;
import java.util.List;

public class TeamList implements Serializable {

    public class Team {
        private int id;
        private String name;
        private String shortName;
        private String squadMarketValue;
        private String crestUrl;

        public int getId() {
            return id;
        }

        public String getShortName() {
            return shortName;
        }

        public String getSquadMarketValue() {
            return squadMarketValue;
        }

        public String getCrestUrl() {
            return crestUrl;
        }

        public String getName() {
            return name;
        }


    }

    private int count;
    private List<Team> teams;

    public List<Team> getTeams() {
        return teams;
    }

    public int getCount() {
        return count;
    }
}
