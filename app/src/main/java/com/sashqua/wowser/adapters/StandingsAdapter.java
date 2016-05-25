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

    static class ViewHolder {
        TextView teamName;
        TextView played;
        TextView wins;
        TextView draws;
        TextView losses;
        TextView points;
    }

    private LayoutInflater vi = LayoutInflater.from(getContext());

    public StandingsAdapter(Context context, int resource, ArrayList<Standing> items, String favouriteTeam) {
        super(context, resource, items);
        this.favouriteTeam = favouriteTeam;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        Standing s = getItem(position);

        ViewHolder holder;
        if (v == null) {

            v = vi.inflate(R.layout.listview_table_item, null);
            holder = new ViewHolder();
            holder.teamName = (TextView) v.findViewById(R.id.tableTeamName);
            holder.played = (TextView) v.findViewById(R.id.tableTeamPlayed);
            holder.wins = (TextView) v.findViewById(R.id.tableTeamWins);
            holder.draws = (TextView) v.findViewById(R.id.tableTeamDraws);
            holder.losses = (TextView) v.findViewById(R.id.tableTeamLosses);
            holder.points = (TextView) v.findViewById(R.id.tableTeamPoints);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        if (s != null) {

            boolean isFavourite = s.getTeamName().equals(favouriteTeam);

            int ourColor;

            if(isFavourite){
                ourColor = getContext().getResources().getColor(R.color.red);
            } else {
                ourColor = getContext().getResources().getColor(R.color.whiteText);
            }

            holder.teamName.setTextColor(ourColor);
            holder.played.setTextColor(ourColor);
            holder.wins.setTextColor(ourColor);
            holder.draws.setTextColor(ourColor);
            holder.losses.setTextColor(ourColor);
            holder.points.setTextColor(ourColor);

            String teamWithPosition = String.valueOf(position+1) + ". " + s.getTeamName();

            holder.teamName.setText(teamWithPosition);
            holder.played.setText(String.valueOf(s.getPlayedGames()));
            holder.wins.setText(String.valueOf(s.getWins()));
            holder.draws.setText(String.valueOf(s.getDraws()));
            holder.losses.setText(String.valueOf(s.getLosses()));
            holder.points.setText(String.valueOf(s.getPoints()));
        }

        return v;
    }
}