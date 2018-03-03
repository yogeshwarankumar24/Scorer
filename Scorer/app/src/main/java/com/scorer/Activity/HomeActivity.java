package com.scorer.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.scorer.Adapter.Home_Adapter;
import com.scorer.Adapter.Menu_Details;
import com.scorer.LocalDatabase.Recentdatabase;
import com.scorer.ModalClass.Common;
import com.scorer.ModalClass.Emailsender;
import com.scorer.ModalClass.GameModel;
import com.scorer.ModalClass.MailModel;
import com.scorer.ModalClass.gamesmenu;
import com.scorer.R;
import com.scorer.SendMail;
import com.scorer.Widgets.AppPreferences;
import com.scorer.Widgets.ExpandableHeightGridView;

import java.util.ArrayList;
import java.util.List;

import static com.scorer.ModalClass.Common.GetGames;

public class HomeActivity extends AppCompatActivity {

    public static int layoutwidth;
    ExpandableHeightGridView mGridView,RecentmGridView;
    LinearLayout RecentLayout;
    Recentdatabase objRecentdatabase;
    List<gamesmenu> objGames = new ArrayList<>();
    List<gamesmenu> objrecentGames = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        objRecentdatabase = new Recentdatabase(this);
        mGridView = (ExpandableHeightGridView) findViewById(R.id.menulistview);
        RecentmGridView = (ExpandableHeightGridView) findViewById(R.id.recentmenulistview);
        RecentLayout = (LinearLayout) findViewById(R.id.recentLayout);
        layoutwidth = getResources().getDisplayMetrics().widthPixels / 3;
        ListView Menubarlist = (ListView) findViewById(R.id.Menubarlist);
        final LinearLayout MenuLeft = (LinearLayout) findViewById(R.id.MenuLeft);
        final DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, 0);
        Menubarlist.setAdapter(new Menu_Details(this, mDrawerLayout, "Home"));
        ImageView Menubutton = (ImageView) findViewById(R.id.Menubutton);
        Menubutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(MenuLeft);
            }
        });
        Common.objPlayers = new ArrayList<>();
        GameModel.Clear();
        LoadData();
        Common.SendNotification(this);
    }
    void LoadData()
    {
        objGames = GetGames();
        List<gamesmenu> objrecentGamestemp = new ArrayList<>();
        objrecentGamestemp = objRecentdatabase.GetRecords();
        if(objrecentGamestemp.size() > 0)
        {
            for(int j=0;j<objrecentGamestemp.size();j++) {
                for (int i = 0; i < objGames.size(); i++) {
                    if (objrecentGames.size() == 2) break;
                    String pass1 = objGames.get(i).getId().toString();
                    String pass2 = objrecentGamestemp.get(j).getId().toString();
                    if (pass1.equals(pass2)) {
                        objrecentGames.add(new gamesmenu(objGames.get(i).getId(), objGames.get(i).getName(), objGames.get(i).getImage()));
                    }

                }
            }
        } else RecentLayout.setVisibility(View.GONE);
        RecentmGridView.setColumnWidth(layoutwidth);
        RecentmGridView.setAdapter(new Home_Adapter(this,objrecentGames));
        RecentmGridView.setExpanded(true);
        mGridView.setColumnWidth(layoutwidth);
        mGridView.setAdapter(new Home_Adapter(this,objGames));
        mGridView.setExpanded(true);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                objRecentdatabase.AddRecord(objGames.get(i));
                Redirect_Games(objGames.get(i).getId().toString(),objGames.get(i).getName().toString(),objGames.get(i).getImage());
            }
        });
        RecentmGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                objRecentdatabase.AddRecord(objrecentGames.get(i));
                Redirect_Games(objrecentGames.get(i).getId().toString(),objrecentGames.get(i).getName().toString(),objrecentGames.get(i).getImage());
            }
        });
    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    void Redirect_Games(String Gamesid,String GamesName,int Gamesimage)
    {
        switch (Gamesid)
        {
            case "G0001":
            {
                GameModel.Game_id = Gamesid;
                GameModel.Game_name = GamesName;
                GameModel.Game_player_count = 2;
                Intent objIntent = new Intent(HomeActivity.this, TeamConfigActivity.class);
                startActivity(objIntent);
                break;
            }
            case "G0002":
            {
                GameModel.Game_id = Gamesid;
                GameModel.Game_name = GamesName;
                GameModel.Game_player_count = 2;
                Intent objIntent = new Intent(HomeActivity.this, TeamConfigActivity.class);
                startActivity(objIntent);
                break;
            }
            case "G0004":
            {
                GameModel.Game_id = Gamesid;
                GameModel.Game_name = GamesName;
                GameModel.Game_player_count = 2;
                Intent objIntent = new Intent(HomeActivity.this, TeamConfigActivity.class);
                startActivity(objIntent);
                break;
            }
            default:
            {
                Intent objIntent = new Intent(HomeActivity.this, VolleyballActivity.class);
                objIntent.putExtra("Gamesid",Gamesid);
                objIntent.putExtra("GamesName",GamesName);
                startActivity(objIntent);
                break;
            }
        }
    }
}
