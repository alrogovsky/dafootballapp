package com.sashqua.wowser.content.contracts;


import android.provider.BaseColumns;

public final class TeamsContract {

    public TeamsContract(){}

    public static abstract class Teams implements BaseColumns{
        public static final String TABLE_NAME = "teams";
        public static final String COLUMN_NAME_TEAM_ID = "api_id";
        public static final String COLUMN_NAME_TEAM_NAME= "team_name";
        public static final String COLUMN_NAME_SHORT_NAME = "short_name";
    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INTEGER";
    private static final String COMMA_SEP = ", ";
    public static final String SQL_CREATE =
            "CREATE TABLE " + Teams.TABLE_NAME + " (" +
                    Teams._ID + INT_TYPE + " PRIMARY KEY, " +
                    Teams.COLUMN_NAME_TEAM_ID + INT_TYPE + COMMA_SEP +
                    Teams.COLUMN_NAME_TEAM_NAME + TEXT_TYPE + COMMA_SEP +
                    Teams.COLUMN_NAME_SHORT_NAME + TEXT_TYPE +
                    ")";

    public static final String SQL_DELETE =
            "DROP TABLE IF EXISTS " + Teams.TABLE_NAME;
}
