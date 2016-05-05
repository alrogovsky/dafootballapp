package com.sashqua.wowser.activities;

import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sashqua.wowser.Constants;
import com.sashqua.wowser.NetBaseActivity;
import com.sashqua.wowser.R;
import com.sashqua.wowser.models.Fixture;
import com.sashqua.wowser.models.FixtureList;
import com.sashqua.wowser.models.TeamList;
import com.sashqua.wowser.utils.FixtureAdapter;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;

public class FutureMainActivity extends NetBaseActivity {

    private long teamId;
    private String teamName;
    private int fixturesRequestId = -1;
    private int resultsRequestId = -1;
    private FixtureList fixtures;
    private FixtureList results;

    private Drawer mDrawer;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_future_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        new DrawerBuilder().withActivity(this).build();

        SharedPreferences sPref;

        sPref = getSharedPreferences("TEST", MODE_PRIVATE);
        teamId = sPref.getLong(Constants.Data.SAVED_TEAM_ID, 0);
        teamName = sPref.getString(Constants.Data.SAVED_TEAM_NAME, "ERROR");

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mDrawer = new DrawerBuilder().withActivity(this).build();
        mDrawer.setSelection(1);

        getFixtures();

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

    private void getFixtures() {
        fixturesRequestId = getServiceHelper().getTeamNextFixtures(teamId);
        resultsRequestId = getServiceHelper().getTeamResults(teamId);
    }

    @Override
    public void onServiceCallback(int requestId, int resultCode, Bundle bundle) {
        if (requestId == this.fixturesRequestId && resultCode == Constants.Codes.CODE_OK) {
            fixtures = (FixtureList) bundle.getSerializable("fixtures");
        } else if (requestId == this.resultsRequestId && resultCode == Constants.Codes.CODE_OK) {
            results = (FixtureList) bundle.getSerializable("results");
        }

        Log.d("KEK", "DATA GOT?");

        mSectionsPagerAdapter.notifyDataSetChanged();
    }

    /*********************** FRAGMENTS LOGIC ***********************/

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class ResultsFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public ResultsFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static ResultsFragment newInstance(String teamName, FixtureList f, FixtureList r) {
            ResultsFragment fragment = new ResultsFragment();
            Bundle args = new Bundle();
            args.putString("team_name", teamName);
            args.putSerializable("fixtures", f);
            args.putSerializable("results", r);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_future_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.teamName);
            textView.setText(getArguments().getString("team_name"));

            ListView lvFixtures = (ListView) rootView.findViewById(R.id.listView2);
            ListView lvResults = (ListView) rootView.findViewById(R.id.listView3);

            FixtureList fixtures = (FixtureList) getArguments().getSerializable("fixtures");
            FixtureList results = (FixtureList) getArguments().getSerializable("results");

            if(fixtures != null && results != null) {
                FixtureAdapter fixturesAdapter = new FixtureAdapter(getContext(), R.layout.listview_fixture_item,
                fixtures.getFixtures());
                FixtureAdapter resultsAdapter = new FixtureAdapter(getContext(), R.layout.listview_fixture_item,
                        results.getFixtures(), true);

                lvFixtures.setAdapter(fixturesAdapter);
                lvResults.setAdapter(resultsAdapter);
            }


            return rootView;
        }
    }

    public static class StandingsFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public StandingsFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static ResultsFragment newInstance(String teamName) {
            ResultsFragment fragment = new ResultsFragment();
            Bundle args = new Bundle();
            args.putString("team_name", teamName);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_future_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.teamName);
            textView.setText(getArguments().getString("team_name"));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public int getItemPosition(Object object){
            return POSITION_NONE;
        }

        @Override
        public Fragment getItem(int position) {
            Log.d("adapter", "redraw");
            if(position == 0){
                return ResultsFragment.newInstance(teamName, fixtures, results);
            } else {
                return ResultsFragment.newInstance("KEK", fixtures, results);
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
}
