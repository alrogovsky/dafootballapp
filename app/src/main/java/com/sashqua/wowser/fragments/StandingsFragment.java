package com.sashqua.wowser.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.sashqua.wowser.R;
import com.sashqua.wowser.models.LeagueTable;
import com.sashqua.wowser.adapters.StandingsAdapter;

public class StandingsFragment extends Fragment {

    public StandingsFragment() {
    }

    public static StandingsFragment newInstance(LeagueTable lt, String teamName) {
        StandingsFragment fragment = new StandingsFragment();
        Bundle args = new Bundle();
        args.putSerializable("league_table", lt);
        args.putString("favourite_team", teamName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_table_main, container, false);
        LeagueTable table = (LeagueTable) getArguments().getSerializable("league_table");
        String favouriteTeam = getArguments().getString("favourite_team");

        ListView lvTable = (ListView) rootView.findViewById(R.id.listView4);
        if(table != null){
            StandingsAdapter adapter = new StandingsAdapter(getContext(), R.layout.listview_table_item,
                    table.getStandings(), favouriteTeam);

            lvTable.setAdapter(adapter);
        }

        return rootView;
    }
}