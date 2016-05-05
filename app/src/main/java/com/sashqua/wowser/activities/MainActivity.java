package com.sashqua.wowser.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sashqua.wowser.Constants;
import com.sashqua.wowser.NetBaseActivity;
import com.sashqua.wowser.R;
import com.sashqua.wowser.models.Fixture;
import com.sashqua.wowser.models.FixtureList;
import com.sashqua.wowser.models.Team;
import com.sashqua.wowser.models.TeamList;
import com.sashqua.wowser.utils.FixtureAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class MainActivity extends NetBaseActivity {

    private long teamId;
    private String teamName;
    private TeamList teamList;
    private int fixturesRequestId = -1;
    private int resultsRequestId = -1;
    private FixtureList fixtures;
    private FixtureList results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences sPref;
        sPref = getSharedPreferences("TEST", MODE_PRIVATE);
        teamId = sPref.getLong(Constants.Data.SAVED_TEAM_ID, 0);
        teamName = sPref.getString(Constants.Data.SAVED_TEAM_NAME, "ERROR");

        Gson gson = new Gson();
        String teamListJson = sPref.getString("teamList", "{}");
        teamList = gson.fromJson(teamListJson, TeamList.class);

        TextView tv1 = (TextView) findViewById(R.id.textView3);
        tv1.setText(teamName);

        getFixtures();
    }

    private void getFixtures() {
        fixturesRequestId = getServiceHelper().getTeamNextFixtures(teamId);
        resultsRequestId = getServiceHelper().getTeamResults(teamId);
    }

    private void drawFixtures() {
        if (fixtures != null && results != null) {
            ListView lvFixtures = (ListView) findViewById(R.id.listView2);
            ListView lvResults = (ListView) findViewById(R.id.listView3);

            FixtureAdapter fixturesAdapter = new FixtureAdapter(this, R.layout.listview_fixture_item,
                    fixtures.getFixtures());
            FixtureAdapter resultsAdapter = new FixtureAdapter(this, R.layout.listview_fixture_item,
                    results.getFixtures(), true);

            lvFixtures.setAdapter(fixturesAdapter);
            lvResults.setAdapter(resultsAdapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onServiceCallback(int requestId, int resultCode, Bundle bundle) {
        if (requestId == this.fixturesRequestId && resultCode == Constants.Codes.CODE_OK) {
            fixtures = (FixtureList) bundle.getSerializable("fixtures");
            drawFixtures();
        } else if (requestId == this.resultsRequestId && resultCode == Constants.Codes.CODE_OK) {
            results = (FixtureList) bundle.getSerializable("results");
            drawFixtures();

        }
    }
}
