package com.scorer.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.scorer.Adapter.Player_Adapter;
import com.scorer.Adapter.Player_Adapter_select;
import com.scorer.Adapter.Player_Selection_Adapter;
import com.scorer.Adapter.Team_Adapter;
import com.scorer.LocalDatabase.Playerdatabase;
import com.scorer.LocalDatabase.Teamdatabase;
import com.scorer.LocalDatabase.Teamdatabase;
import com.scorer.ModalClass.Common;
import com.scorer.ModalClass.GameModel;
import com.scorer.ModalClass.GetTeam;
import com.scorer.ModalClass.GetTeam;
import com.scorer.ModalClass.PlayerModel;
import com.scorer.ModalClass.TeamModel;
import com.scorer.ModalClass.TeamModel;
import com.scorer.ModalClass.gamesmenu;
import com.scorer.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android on 11/14/2017.
 */

public class Team_popup {
    Activity objActivity;
    Dialog Popup_Layout;
    TextView Select_Game;
    GetTeam objGetTeam;
    EditText Search_player;
    String Game_id,Game_name;
    ImageView player_image;
    TextView player_name,player_position,ErrorMessage;
    TeamModel objselectedTeamModel;
    List<TeamModel> objTeamModel;
    ListView objListView;
    RecyclerView Players_Listview;
    LinearLayout Team_Layout,Team_selected;
    Playerdatabase objPlayerdatabase;
    public static List<String> objSelected_players = new ArrayList<>();
    public Team_popup(Activity objActivityv,String Game_idv, GetTeam objGetTeamv) {
        objActivity = objActivityv;
        objGetTeam = objGetTeamv;
        Game_id = Game_idv;
        objselectedTeamModel = new TeamModel();
        objPlayerdatabase = new Playerdatabase(objActivity);
        Popup_Layout = new Dialog(objActivity, R.style.MaterialDialogSheet);
        Popup_Layout.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Popup_Layout.setContentView(R.layout.team_list_popup);
        Popup_Layout.setCancelable(true);
        Popup_Layout.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        Popup_Layout.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        ImageView closebutton = (ImageView) Popup_Layout.findViewById(R.id.closebutton);
        objListView = (ListView) Popup_Layout.findViewById(R.id.Listview);
        Search_player = (EditText) Popup_Layout.findViewById(R.id.Search_player);
        Team_Layout = (LinearLayout) Popup_Layout.findViewById(R.id.Team_Layout);
        Team_selected = (LinearLayout) Popup_Layout.findViewById(R.id.Team_selected);
        Players_Listview = (RecyclerView) Popup_Layout.findViewById(R.id.Players_Listview);
        closebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Popup_Layout.dismiss();
            }
        });
        ErrorMessage = (TextView) Popup_Layout.findViewById(R.id.ErrorMessage);
        Select_Game = (TextView) Popup_Layout.findViewById(R.id.Select_Game);
        Select_Game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Show_Games();
            }
        });
        Load_Players(Game_id);
        Team_Layout.setVisibility(View.VISIBLE);
        Team_selected.setVisibility(View.GONE);
        objListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
            {
//                objGetTeam.Callbackteam(objTeamModel.get(position));
                objselectedTeamModel = objTeamModel.get(position);
                Team_Layout.setVisibility(View.GONE);
                Team_selected.setVisibility(View.VISIBLE);
                Load_selectedplayers();
            }
        });
        player_image = (ImageView) Popup_Layout.findViewById(R.id.player_image);
        player_name = (TextView) Popup_Layout.findViewById(R.id.player_name);
        player_position = (TextView) Popup_Layout.findViewById(R.id.player_position);
        ImageView Backbutton = (ImageView) Popup_Layout.findViewById(R.id.Backbutton);
        Backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ErrorMessage.setVisibility(View.GONE);
                objselectedTeamModel = new TeamModel();
                Team_Layout.setVisibility(View.VISIBLE);
                Team_selected.setVisibility(View.GONE);
            }
        });
        Button Submit = (Button) Popup_Layout.findViewById(R.id.Submit);
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit_Button();
            }
        });
    }
    void Load_selectedplayers() {
        objSelected_players = new ArrayList<>();
        player_name.setText(objselectedTeamModel.getTeamname());
        player_position.setText(objselectedTeamModel.getGamename());
        Glide.with(objActivity)
                .load(objselectedTeamModel.getTeamimage())
                .placeholder(R.drawable.defaultimg)
                .skipMemoryCache(true)
                .override(80,80)
                .into(player_image);
        List<PlayerModel> objPlayerslist = new ArrayList<>();
        List<String> objPlayersids = new ArrayList<>();
        objPlayersids = Common.StringtoArray(objselectedTeamModel.getTeamplayer());
        for(int i=0;i<objPlayersids.size();i++)
        {
            objPlayerslist.add(objPlayerdatabase.GetRecords_id(objPlayersids.get(i)));
        }
        Player_Selection_Adapter objPlayer_Adapter = new Player_Selection_Adapter(objActivity,objPlayerslist);
        Players_Listview.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(objActivity);
        Players_Listview.setLayoutManager(mLayoutManager);
        Players_Listview.setAdapter(objPlayer_Adapter);
    }
    void Load_Players(String gameid) {
        objTeamModel = new ArrayList<>();
        Teamdatabase objTeamdatabase = new Teamdatabase(objActivity);
        if(gameid.length()>1) {
            for (gamesmenu objmenu: Common.GetGames()) {
                if(objmenu.getId().toString().equals(gameid))
                {
                    Game_name = objmenu.getName().toString();
                    break;
                }
            }
            Select_Game.setText(Game_name);
            objTeamModel = objTeamdatabase.GetRecords(gameid);
        } else {
            objTeamModel = objTeamdatabase.GetRecords();
        }
        objListView.setAdapter(new Team_Adapter(objActivity,objTeamModel));
    }
    public void Show_Games()  {
        List<String> objGames = new ArrayList<>();
        objGames.add("ALL");
        for (gamesmenu objmenu: Common.GetGames()) {
            objGames.add(objmenu.getName().toString());
        }
        //Create sequence of items
        final CharSequence[] Animals = objGames.toArray(new String[objGames.size()]);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(objActivity);
        dialogBuilder.setTitle("Select Game");
        dialogBuilder.setItems(Animals, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if(item == 0)
                {
                    Game_name = "";
                    Game_id = "";
                    Select_Game.setText("ALL");
                    Load_Players("");
                } else {
                    Game_name = Common.GetGames().get(item-1).getName().toString();
                    Game_id = Common.GetGames().get(item-1).getId().toString();
                    Select_Game.setText(Game_name);
                    Load_Players(Game_id);
                }
            }
        });
        //Create alert dialog object via builder
        AlertDialog alertDialogObject = dialogBuilder.create();
        //Show the dialog
        alertDialogObject.show();
    }
    public void Show() {
        Popup_Layout.show();
    }
    public void Dismiss() {
        Popup_Layout.dismiss();
    }
    void Submit_Button() {
        if(objSelected_players.size() == 0)
        {
            ErrorMessage.setVisibility(View.VISIBLE);
            ErrorMessage.setText("* Select the Players from the list Maximum "+ GameModel.Game_player_count);
        } else {
            String Players = Common.ArraytoString(objSelected_players);
            objGetTeam.Callbackteam(objselectedTeamModel,Players);
        }
        Popup_Layout.dismiss();
    }
}
