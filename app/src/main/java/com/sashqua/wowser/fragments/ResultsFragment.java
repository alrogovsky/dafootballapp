package com.sashqua.wowser.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.sashqua.wowser.R;
import com.sashqua.wowser.models.FixtureList;
import com.sashqua.wowser.utils.FixtureAdapter;

public class ResultsFragment extends Fragment {

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

        View rootView = inflater.inflate(R.layout.fragment_fixtures_main, container, false);
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