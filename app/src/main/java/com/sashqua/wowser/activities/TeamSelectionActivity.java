package com.sashqua.wowser.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sashqua.wowser.Constants;
import com.sashqua.wowser.NetBaseActivity;
import com.sashqua.wowser.R;
import com.sashqua.wowser.models.Season;
import com.sashqua.wowser.models.Team;
import com.sashqua.wowser.models.TeamList;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TeamSelectionActivity extends NetBaseActivity {

    private int requestId = -1;
    private TeamList teamlist;
    private ListView lv;
    private Team chosenTeam;

    SharedPreferences sPref;

    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_selection);

        lv = (ListView) findViewById(R.id.listView);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Log.d("KEK", "itemClick: position = " + position + ", id = " + id);
                chosenTeam = teamlist.getSortedTeams().get(position);
                Log.d("KEK", "chosen: " + chosenTeam.getShortName() + " "
                        + chosenTeam.getId());
            }
        });

        requestTeams();
    }

    @Override
    public void onServiceCallback(int requestId, int resultCode, Bundle bundle){
        if (requestId == this.requestId && resultCode == Constants.Codes.CODE_OK) {
            teamlist = (TeamList) bundle.getSerializable("teams");
            updateTeams();
        }

    }

    public void requestTeams(){
        requestId = getServiceHelper().getTeams(398);
    }

    private void updateTeams(){
        if(teamlist != null){
            List<String> clubNames = new ArrayList<String>();
            for(Team t : teamlist.getSortedTeams()){
                clubNames.add(t.getName());
                Log.d("KEK", "name="+t.getName());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_single_choice, clubNames){

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View v = super.getView(position, convertView, parent);
                    ((TextView) v).setTextColor(Color.WHITE);
                    return v;
                }

            };

            lv.setAdapter(adapter);
        }
    }

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

}
