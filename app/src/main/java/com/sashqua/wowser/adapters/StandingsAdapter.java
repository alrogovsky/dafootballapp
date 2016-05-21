package com.sashqua.wowser.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sashqua.wowser.R;
import com.sashqua.wowser.models.Standing;

import java.util.ArrayList;

public class StandingsAdapter extends ArrayAdapter<Standing> {

    private String favouriteTeam;

    public StandingsAdapter(Context context, int resource, ArrayList<Standing> items, String favouriteTeam) {
        super(context, resource, items);
        this.favouriteTeam = favouriteTeam;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.listview_table_item, null);
        }

        Standing s = getItem(position);

        if (s != null) {
            TextView teamName = (TextView) v.findViewById(R.id.tableTeamName);
            TextView played = (TextView) v.findViewById(R.id.tableTeamPlayed);
            TextView wins = (TextView) v.findViewById(R.id.tableTeamWins);
            TextView draws = (TextView) v.findViewById(R.id.tableTeamDraws);
            TextView losses = (TextView) v.findViewById(R.id.tableTeamLosses);
            TextView points = (TextView) v.findViewById(R.id.tableTeamPoints);

            if (teamName != null) {
                String teamWithPosition = String.valueOf(position+1) + ". " + s.getTeamName();
                teamName.setText(teamWithPosition);
                if(s.getTeamName().equals(favouriteTeam)){
                    Log.d("KEK", s.getTeamName() + " / " + favouriteTeam + " / " + teamWithPosition);
                    teamName.setTextColor(Color.RED);
                } else {
                    teamName.setTextColor(Color.BLACK);
                }
            }

            if (played != null) {
                played.setText(String.valueOf(s.getPlayedGames()));
            }

            if (wins != null) {
                wins.setText(String.valueOf(s.getWins()));
            }

            if (draws != null) {
                draws.setText(String.valueOf(s.getDraws()));
            }

            if (losses != null) {
                losses.setText(String.valueOf(s.getLosses()));
            }

            if (points != null) {
                points.setText(String.valueOf(s.getPoints()));
            }
        }

        return v;
    }
}