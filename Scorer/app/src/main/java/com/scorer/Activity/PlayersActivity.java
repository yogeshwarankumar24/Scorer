package com.scorer.Activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.scorer.Adapter.History_Adapter;
import com.scorer.Adapter.Menu_Details;
import com.scorer.Adapter.Player_Adapter;
import com.scorer.LocalDatabase.Matchdatabase;
import com.scorer.LocalDatabase.Playerdatabase;
import com.scorer.R;

public class PlayersActivity extends AppCompatActivity {
    ListView PlayerListview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);
        ListView Menubarlist = (ListView) findViewById(R.id.Menubarlist);
        final LinearLayout MenuLeft = (LinearLayout) findViewById(R.id.MenuLeft);
        final DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, 0);
        Menubarlist.setAdapter(new Menu_Details(this, mDrawerLayout, "Player"));
        ImageView Menubutton = (ImageView) findViewById(R.id.Menubutton);
        Menubutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(MenuLeft);
            }
        });
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.floating_action_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent objintent = new Intent(PlayersActivity.this, NewPlayerActivity.class);
                objintent.putExtra("Screenid","Player");
                startActivity(objintent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(PlayersActivity.this, HomeActivity.class));
    }
    void Load_Players() {

        Playerdatabase objPlayerdatabase = new Playerdatabase(this);
        PlayerListview = (ListView) findViewById(R.id.PlayerListview);
        PlayerListview.setAdapter(new Player_Adapter(this, objPlayerdatabase.GetRecords()));
        PlayerListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
//                Intent objIntent = new Intent(PlayersActivity.this, HistoryviewActivity.class);
//                objIntent.putExtra("position",position);
//                startActivity(objIntent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Load_Players();
    }
}
