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

    static class ViewHolder {
        TextView homeTeamName;
        TextView score;
        TextView awayTeamName;
    }

    private LayoutInflater vi = LayoutInflater.from(getContext());
    private ViewHolder holder;

    public FixtureAdapter(Context context, int resource, ArrayList<Fixture> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            v = vi.inflate(R.layout.listview_fixture_item, null);
            holder = new ViewHolder();
            holder.homeTeamName = (TextView) v.findViewById(R.id.fixtureTeamHome);
            holder.score = (TextView) v.findViewById(R.id.fixtureScore);
            holder.awayTeamName = (TextView) v.findViewById(R.id.fixtureTeamAway);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        Fixture f = getItem(position);

        if (f != null) {

            if (holder.homeTeamName != null) {
                holder.homeTeamName.setText(f.getHomeTeamName());
            }

            if (holder.score != null) {
                String scoreStr;
                if(isResults) {
                    scoreStr = f.getResult().getGoalsHomeTeam() + "-" + f.getResult().getGoalsAwayTeam();
                } else {
                    scoreStr = " - ";
                }

                holder.score.setText(scoreStr);
            }

            if (holder.awayTeamName != null) {
                holder.awayTeamName.setText(f.getAwayTeamName());
            }
        }

        return v;
    }

}
