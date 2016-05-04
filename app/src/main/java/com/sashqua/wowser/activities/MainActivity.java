package com.sashqua.wowser.activities;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.sashqua.wowser.Constants;
import com.sashqua.wowser.NetBaseActivity;
import com.sashqua.wowser.R;
import com.sashqua.wowser.models.Fixture;
import com.sashqua.wowser.models.FixtureList;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class MainActivity extends NetBaseActivity {

    private long teamId;
    private String teamName;
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
            ListView lv = (ListView) findViewById(R.id.listView2);
            ListView lv2 = (ListView) findViewById(R.id.listView3);

            List<String> futureMatches = new ArrayList<>();
            for (Fixture f : fixtures.getFixtures()) {
                Log.d("KEK", f.getHomeTeamName() + " - " + f.getAwayTeamName());
                futureMatches.add(f.getHomeTeamName() + " - " + f.getAwayTeamName());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, futureMatches) {

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View v = super.getView(position, convertView, parent);
                    ((TextView) v).setTextColor(Color.WHITE);
                    ((TextView) v).setGravity(Gravity.CENTER);
                    ((TextView) v).setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
                    return v;
                }

            };

            List<String> resultMatches = new ArrayList<>();
            for (Fixture r : results.getFixtures()) {
                String resultLine = r.getHomeTeamName() + " " + r.getResult().getGoalsHomeTeam() +
                        "-" + r.getResult().getGoalsAwayTeam() + " " + r.getAwayTeamName();
                Log.d("KEK", resultLine);
                resultMatches.add(resultLine);
            }

            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, resultMatches) {

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View v = super.getView(position, convertView, parent);
                    ((TextView) v).setTextColor(Color.WHITE);
                    ((TextView) v).setGravity(Gravity.CENTER);
                    ((TextView) v).setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
                    return v;
                }

            };

            lv.setAdapter(adapter);
            lv2.setAdapter(adapter2);
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
