package com.scorer.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.scorer.Adapter.Player_Adapter;
import com.scorer.LocalDatabase.Playerdatabase;
import com.scorer.ModalClass.Common;
import com.scorer.ModalClass.GetRequest;
import com.scorer.ModalClass.PlayerModel;
import com.scorer.ModalClass.gamesmenu;
import com.scorer.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android on 11/14/2017.
 */

public class Player_popup {
    Activity objActivity;
    Dialog Popup_Layout;
    ListView objListView;
    EditText Search_player;
    String Game_id,Game_name;
    TextView Select_Game;
    private List<PlayerModel> objPlayerModel;
    GetRequest objGetRequest;
    public Player_popup(Activity objActivityv,String Game_idv, GetRequest objGetRequestv) {
        objActivity = objActivityv;
        Game_id = Game_idv;
        objGetRequest = objGetRequestv;
        Popup_Layout = new Dialog(objActivity, R.style.MaterialDialogSheet);
        Popup_Layout.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Popup_Layout.setContentView(R.layout.player_list_popup);
        Popup_Layout.setCancelable(true);
        Popup_Layout.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        Popup_Layout.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        ImageView closebutton = (ImageView) Popup_Layout.findViewById(R.id.closebutton);
        objListView = (ListView) Popup_Layout.findViewById(R.id.Listview);
        Search_player = (EditText) Popup_Layout.findViewById(R.id.Search_player);
        closebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Popup_Layout.dismiss();
            }
        });
        Select_Game = (TextView) Popup_Layout.findViewById(R.id.Select_Game);
        Select_Game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Show_Games();
            }
        });
        Load_Players(Game_id);

    }
    void Load_Players(String gameid)
    {
        objPlayerModel = new ArrayList<>();
        Playerdatabase objPlayerdatabase = new Playerdatabase(objActivity);
        if(gameid.length()>1) {
            for (gamesmenu objmenu: Common.GetGames()) {
                if(objmenu.getId().toString().equals(gameid))
                {
                    Game_name = objmenu.getName().toString();
                    break;
                }
            }
            Select_Game.setText(Game_name);
            objPlayerModel = objPlayerdatabase.GetRecords(gameid);
        } else {
            objPlayerModel = objPlayerdatabase.GetRecords();
        }
        objListView.setAdapter(new Player_Adapter(objActivity,objPlayerModel));
        objListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
            {
                String Player_id = objPlayerModel.get(position).getUniqueid();
                objGetRequest.Callback(Player_id);
                Popup_Layout.dismiss();
            }
        });
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
}
