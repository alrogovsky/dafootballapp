package com.sashqua.wowser.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sashqua.wowser.Constants;
import com.sashqua.wowser.NetBaseActivity;
import com.sashqua.wowser.R;
import com.sashqua.wowser.models.TeamList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TeamSelectionActivity extends NetBaseActivity {

    private int requestId = -1;
    private TeamList teams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_selection);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onServiceCallback(int requestId, int resultCode, Bundle bundle){
        if (requestId == this.requestId && resultCode == Constants.Codes.CODE_OK) {
            teams = (TeamList) bundle.getSerializable("teams");
            updateTeams();
        }

    }

    public void requestTeams(View view){
        requestId = getServiceHelper().getTeams(398);
    }

    private void updateTeams(){
        if(teams != null){
            List<String> clubNames = new ArrayList<String>();
            for(TeamList.Team t : teams.getTeams()){
                clubNames.add(t.getName());
            }

            Collections.sort(clubNames);

            ListView lv = (ListView) findViewById(R.id.listView);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, clubNames);

            lv.setAdapter(adapter);
        }
    }

}
