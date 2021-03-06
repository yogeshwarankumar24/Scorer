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

import com.scorer.Adapter.Menu_Details;
import com.scorer.Adapter.Player_Adapter;
import com.scorer.Adapter.Team_Adapter;
import com.scorer.LocalDatabase.Playerdatabase;
import com.scorer.LocalDatabase.Teamdatabase;
import com.scorer.ModalClass.Common;
import com.scorer.R;

import java.util.ArrayList;

public class TeamsActivity extends AppCompatActivity {
ListView TeamListview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams);
        ListView Menubarlist = (ListView)findViewById(R.id.Menubarlist);
        final LinearLayout MenuLeft = (LinearLayout)findViewById(R.id.MenuLeft);
        final DrawerLayout mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, 0);
        Menubarlist.setAdapter(new Menu_Details(this,mDrawerLayout,"Team"));
        ImageView Menubutton = (ImageView)findViewById(R.id.Menubutton);
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
                Common.objPlayers = new ArrayList<>();
                startActivity(new Intent(TeamsActivity.this, NewTeamActivity.class));
            }
        });

    }
    void Load_Teams()
    {
        Teamdatabase objTeamdatabase = new Teamdatabase(this);
        TeamListview = (ListView)findViewById(R.id.TeamListview);
        TeamListview.setAdapter(new Team_Adapter(this,objTeamdatabase.GetRecords()));
        TeamListview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
            {
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(TeamsActivity.this, HomeActivity.class));
    }
    @Override
    protected void onResume() {
        super.onResume();
        Load_Teams();
    }
}
