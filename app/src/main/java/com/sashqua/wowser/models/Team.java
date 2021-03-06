package com.sashqua.wowser.models;

import java.io.Serializable;

@SuppressWarnings("unused")
public class Team implements Serializable {
    private long id;
    private String name;
    private String shortName;
    private String squadMarketValue;
    private String crestUrl;

    public long getId() {
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