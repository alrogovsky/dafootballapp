package com.sashqua.wowser.adapters;

import android.content.Context;
import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sashqua.wowser.R;
import com.sashqua.wowser.models.Fixture;
import com.sashqua.wowser.models.TeamList;

public class FixtureAdapter extends ArrayAdapter<Fixture> {

    private boolean isResults = false;

    public FixtureAdapter(Context context, int resource, ArrayList<Fixture> items, boolean isResults) {
        super(context, resource, items);
        this.isResults = isResults;
    }

    public FixtureAdapter(Context context, int resource, ArrayList<Fixture> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.listview_fixture_item, null);
        }

        Fixture f = getItem(position);

        if (f != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.fixtureTeamHome);
            TextView tt2 = (TextView) v.findViewById(R.id.fixtureScore);
            TextView tt3 = (TextView) v.findViewById(R.id.fixtureTeamAway);

            if (tt1 != null) {
                tt1.setText(f.getHomeTeamName());
            }

            if (tt2 != null) {
                String scoreStr;
                if(isResults) {
                    scoreStr = f.getResult().getGoalsHomeTeam() + "-" + f.getResult().getGoalsAwayTeam();
                } else {
                    scoreStr = " - ";
                }

                tt2.setText(scoreStr);
            }

            if (tt3 != null) {
                tt3.setText(f.getAwayTeamName());
            }
        }

        return v;
    }

}
