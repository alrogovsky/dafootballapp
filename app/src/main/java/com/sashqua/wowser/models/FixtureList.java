package com.sashqua.wowser.models;

import java.io.Serializable;
import java.util.ArrayList;

public class FixtureList implements Serializable {
    private String timeFrameStart;
    private String timeFrameEnd;
    private int count;

    private ArrayList<Fixture> fixtures;

    /*    getters   */
    public ArrayList<Fixture> getFixtures() {
        return fixtures;
    }

    public int getCount() {
        return count;
    }

    public String getTimeFrameEnd() {
        return timeFrameEnd;
    }

    public String getTimeFrameStart() {
        return timeFrameStart;
    }

}
