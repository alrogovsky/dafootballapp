package com.sashqua.wowser;

public final class Constants {
    public static final String API_KEY = "a46a00ae84e54eeca4344d935648fd6a";
    public static final String API_BASE_URL = "http://api.football-data.org/";

    public static final String RECEIVER = "com.sashqua.wowser.extra.RECEIVER";

    public final static class Data {
        public static final String SAVED_TEAM_ID = "team_id";
        public static final String EXTRA_LEAGUE_ID = "com.sashqua.wowser.extra.LEAGUE_ID";
        public static final String EXTRA_SEASON = "com.sashqua.wowser.extra.SEASON";
    }

    public final static class Action {
        public static final String GET_TEAMS = "com.sashqua.wowser.actions.GET_TEAMS";
        public static final String GET_SEASONS = "com.sashqua.wowser.actions.GET_SEASONS";
    }

    public static final class Codes {
        public static final int CODE_OK = 200;
        public static final int CODE_FAIL = 500;
    }
}
