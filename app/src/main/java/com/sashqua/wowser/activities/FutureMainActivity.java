package com.sashqua.wowser.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.support.v4.widget.DrawerLayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.sashqua.wowser.Constants;
import com.sashqua.wowser.NetBaseActivity;
import com.sashqua.wowser.R;
import com.sashqua.wowser.fragments.StandingsFragment;
import com.sashqua.wowser.fragments.ResultsFragment;
import com.sashqua.wowser.models.FixtureList;
import com.sashqua.wowser.models.LeagueTable;


public class FutureMainActivity extends NetBaseActivity implements FragmentDrawer.FragmentDrawerListener {

    private long teamId;
    private String teamName;
    private int fixturesRequestId = -1;
    private int resultsRequestId = -1;
    private int leagueTableRequestId = -1;
    private FixtureList fixtures;
    private FixtureList results;
    private LeagueTable leagueTable;

    private FragmentDrawer drawerFragment;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_future_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

//        new DrawerBuilder().withActivity(this).build();

        SharedPreferences sPref;

        sPref = getSharedPreferences("TEST", MODE_PRIVATE);
        teamId = sPref.getLong(Constants.Data.SAVED_TEAM_ID, 0);
        teamName = sPref.getString(Constants.Data.SAVED_TEAM_NAME, "ERROR");

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

//        mDrawer = new DrawerBuilder().withActivity(this).build();
//        mDrawer.setSelection(1);

        getData();

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
        drawerFragment.setDrawerListener(this);

        displayView(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_future_main, menu);
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

    private void getData() {
        fixturesRequestId = getServiceHelper().getTeamNextFixtures(teamId);
        resultsRequestId = getServiceHelper().getTeamResults(teamId);
        leagueTableRequestId = getServiceHelper().getLeagueTable(398);
    }

    @Override
    public void onServiceCallback(int requestId, int resultCode, Bundle bundle) {
        if (requestId == this.fixturesRequestId && resultCode == Constants.Codes.CODE_OK) {
            fixtures = (FixtureList) bundle.getSerializable("fixtures");
        } else if (requestId == this.resultsRequestId && resultCode == Constants.Codes.CODE_OK) {
            results = (FixtureList) bundle.getSerializable("results");
        } else if (requestId == this.leagueTableRequestId && resultCode == Constants.Codes.CODE_OK){
            leagueTable = (LeagueTable) bundle.getSerializable("league_table");
        }

        mSectionsPagerAdapter.notifyDataSetChanged();
    }

    /*********************** FRAGMENTS LOGIC ***********************/

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public int getItemPosition(Object object){
            return POSITION_NONE;
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0){
                return ResultsFragment.newInstance(teamName, fixtures, results);
            } else {
                return StandingsFragment.newInstance(leagueTable, teamName);
            }

        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Results";
                case 1:
                    return "Standings";
            }
            return null;
        }
    }


    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
//        String title = getString(R.string.app_name);
        switch (position) {
            case 1:
                Intent intent = new Intent(FutureMainActivity.this, TeamSelectionActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
