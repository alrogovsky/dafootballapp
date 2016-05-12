package com.sashqua.wowser.activities;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sashqua.wowser.Constants;
import com.sashqua.wowser.NetBaseActivity;
import com.sashqua.wowser.R;
import com.sashqua.wowser.content.contracts.TeamsContract;
import com.sashqua.wowser.models.Team;
import com.sashqua.wowser.models.TeamList;

import java.util.ArrayList;
import java.util.List;

public class TeamSelectionActivity extends NetBaseActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private int requestId = -1;
    private TeamList teamlist;
    private ListView lv;
    private Team chosenTeam;

    private final int LOADER_ID = 0;
    private final Uri CONTENT_URI = Uri.parse("content://com.sashqua.wowser.dafootball.provider/teams");

    private SimpleCursorAdapter simpleCursorAdapter;


    SharedPreferences sPref;

//    public View getViewByPosition(int pos, ListView listView) {
//        final int firstListItemPosition = listView.getFirstVisiblePosition();
//        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;
//
//        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
//            return listView.getAdapter().getView(pos, null, listView);
//        } else {
//            final int childIndex = pos - firstListItemPosition;
//            return listView.getChildAt(childIndex);
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_selection);

        String[] from = new String[] {
                TeamsContract.Teams.COLUMN_NAME_TEAM_NAME,
        };

        int[] to = new int[] {
                android.R.id.text1,
        };

        simpleCursorAdapter = new SimpleCursorAdapter(
                getApplicationContext(),
                android.R.layout.simple_list_item_single_choice,
                null, from, to, 0
        );

        lv = (ListView) findViewById(R.id.listView);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                chosenTeam = teamlist.getSortedTeams().get(position);
            }
        });

        lv.setAdapter(simpleCursorAdapter);

        requestTeams();

    }

    @Override
    public void onServiceCallback(int requestId, int resultCode, Bundle bundle){
        if (requestId == this.requestId && resultCode == Constants.Codes.CODE_OK) {
            teamlist = (TeamList) bundle.getSerializable("teams");
//            updateTeams();
            Log.d("KEK", "INIT LOADER");
            getLoaderManager().initLoader(LOADER_ID, null, this);
        }

    }

    public void requestTeams(){
        requestId = getServiceHelper().getTeams(398);
    }

//    private void updateTeams(){
//        if(teamlist != null){
//            List<String> clubNames = new ArrayList<String>();
//            for(Team t : teamlist.getSortedTeams()){
//                clubNames.add(t.getName());
//            }
//
//            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                    android.R.layout.simple_list_item_single_choice, clubNames){
//
//                @Override
//                public View getView(int position, View convertView, ViewGroup parent) {
//                    View v = super.getView(position, convertView, parent);
//                    ((TextView) v).setTextColor(Color.WHITE);
//                    return v;
//                }
//
//            };
//
//            lv.setAdapter(adapter);
//        }
//    }

    public void makeChoice(View view){
        if(chosenTeam != null) {
            sPref = getSharedPreferences("TEST", MODE_PRIVATE);
            SharedPreferences.Editor ed = sPref.edit();
            Log.d("KEK", "PUT ID " + chosenTeam.getId());
            ed.putLong(Constants.Data.SAVED_TEAM_ID, chosenTeam.getId());
            ed.putString(Constants.Data.SAVED_TEAM_NAME, chosenTeam.getName());

            // Save team info
            Gson gson = new Gson();
            String json = gson.toJson(teamlist);
            ed.putString("teamList", json);

            ed.commit();

            Intent intent = new Intent(TeamSelectionActivity.this, FutureMainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.d("KEK", "SOZDAU LOADER");
        if (id == LOADER_ID) {
            Log.d("KEK", "SOZDAL LOADER");
            return new CursorLoader(getApplicationContext(), CONTENT_URI, new String[]{
                    "_ID",
                    TeamsContract.Teams.COLUMN_NAME_TEAM_NAME,
            }, null, null, TeamsContract.Teams.COLUMN_NAME_TEAM_NAME + " ASC");
        }
        Log.d("KEK", "XUEVO");
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (loader.getId() == LOADER_ID) {
            simpleCursorAdapter.swapCursor(data);
            simpleCursorAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if (loader.getId() == LOADER_ID) {
            simpleCursorAdapter.swapCursor(null);
            simpleCursorAdapter.notifyDataSetChanged();
        }
    }

}
