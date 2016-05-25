package com.sashqua.wowser.activities;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.sashqua.wowser.Constants;
import com.sashqua.wowser.NetBaseActivity;
import com.sashqua.wowser.R;
import com.sashqua.wowser.models.Team;
import com.sashqua.wowser.models.TeamList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.sashqua.wowser.R.*;

public class LigueSelectionActivity extends NetBaseActivity {

    private int requestId = -1;
    private TeamList teams;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_team_selection);

        pd = new ProgressDialog(this, style.AppCompatAlertDialogStyle);
        pd.setTitle("Please wait");
        pd.setMessage("Loading data...");
        pd.show();

        requestTeams();
    }

    @Override
    public void onServiceCallback(int requestId, int resultCode, Bundle bundle){
        if (requestId == this.requestId && resultCode == Constants.Codes.CODE_OK) {
            teams = (TeamList) bundle.getSerializable("teams");
            updateTeams();
        }
        pd.dismiss();
    }

    public void requestTeams(){
        requestId = getServiceHelper().getTeams(398);
    }

    private void updateTeams(){
        if(teams != null){
            List<String> clubNames = new ArrayList<String>();
            for(Team t : teams.getTeams()){
                clubNames.add(t.getName());
            }

            Collections.sort(clubNames);

            ListView lv = (ListView) findViewById(id.listView);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_single_choice, clubNames){

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View v = super.getView(position, convertView, parent);
                    ((TextView) v).setTextColor(getResources().getColor(color.whiteText));
                    return v;
                }

            };

            lv.setAdapter(adapter);
        }
    }

}
