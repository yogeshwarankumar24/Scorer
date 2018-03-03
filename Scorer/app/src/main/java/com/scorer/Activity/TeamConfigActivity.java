package com.scorer.Activity;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.scorer.Adapter.Player_Adapter_select;
import com.scorer.LocalDatabase.Playerdatabase;
import com.scorer.ModalClass.Common;
import com.scorer.ModalClass.GameModel;
import com.scorer.ModalClass.GetRequest;
import com.scorer.ModalClass.GetTeam;
import com.scorer.ModalClass.PlayerModel;
import com.scorer.ModalClass.TeamModel;
import com.scorer.R;
import com.scorer.Validation;

import java.util.ArrayList;
import java.util.List;

public class TeamConfigActivity extends AppCompatActivity implements GetRequest,GetTeam {
    TextView Single_player,Singleline,Teamline,Team_player;
    boolean Single_status = true;
    public static boolean Current_status;
    Playerdatabase objPlayerdatabase;
    TextInputLayout Edit_Player1_input,Edit_Player2_input;
    EditText Edit_Player1,Edit_Player2;
    ImageView Add_player1,Pick_player1,Delete_player1;
    ImageView Add_player2,Pick_player2,Delete_player2;
    LinearLayout Single_Layout,Team_Layout;
    LinearLayout Player_layout1,Player_layout2;
    ImageView player_image1,player_image2;
    Button Team_Select1,Team_Select2;
    ImageView Team_image1,Team_image2;
    TextView Team_name1,Team_name2;
    ListView Team_Listview1,Team_Listview2;
    TextView ErrorMessage,player_name1,player_name2,player_position1,player_position2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_config);
        Single_Layout = (LinearLayout) findViewById(R.id.Single_Layout);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        Team_Layout = (LinearLayout) findViewById(R.id.Team_Layout);
        Single_player = (TextView) findViewById(R.id.Single_player);
        ErrorMessage = (TextView) findViewById(R.id.ErrorMessage);
        ImageView Backbutton = (ImageView) findViewById(R.id.Backbutton);
        Backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TeamConfigActivity.this,HomeActivity.class));
            }
        });
        Team_Select1 = (Button) findViewById(R.id.Team_Select1);
        Team_Select2 = (Button) findViewById(R.id.Team_Select2);
        Singleline = (TextView) findViewById(R.id.Singleline);
        Single_player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Player_Status(1);
            }
        });
        Team_player = (TextView) findViewById(R.id.Team_player);
        Teamline = (TextView) findViewById(R.id.Teamline);
        Team_player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Player_Status(2);
            }
        });
        player_image1 = (ImageView) findViewById(R.id.player_image1);
        player_name1 = (TextView) findViewById(R.id.player_name1);
        player_position1 = (TextView) findViewById(R.id.player_position1);
        player_image2 = (ImageView) findViewById(R.id.player_image2);
        player_name2 = (TextView) findViewById(R.id.player_name2);
        Team_image1 = (ImageView) findViewById(R.id.Team_image1);
        Team_name1 = (TextView) findViewById(R.id.Team_name1);
        Team_image2 = (ImageView) findViewById(R.id.Team_image2);
        Team_name2 = (TextView) findViewById(R.id.Team_name2);
        Team_Listview1 = (ListView) findViewById(R.id.Team_Listview1);
        Team_Listview2 = (ListView) findViewById(R.id.Team_Listview2);
        player_position2 = (TextView) findViewById(R.id.player_position2);
        objPlayerdatabase = new Playerdatabase(this);
        Player_layout1 = (LinearLayout) findViewById(R.id.Player_layout1);
        Player_layout2 = (LinearLayout) findViewById(R.id.Player_layout2);
        Edit_Player1_input = (TextInputLayout) findViewById(R.id.Edit_player1_input);
        Edit_Player2_input = (TextInputLayout) findViewById(R.id.Edit_player2_input);
        Edit_Player1 = (EditText) findViewById(R.id.Edit_player1);
        Edit_Player2 = (EditText) findViewById(R.id.Edit_player2);
        Add_player1 = (ImageView) findViewById(R.id.Add_player1);
        Add_player1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Current_status = true;
                Intent objIntent = new Intent(TeamConfigActivity.this,NewPlayerActivity.class);
                objIntent.putExtra("Screenid","Teamconfig");
                startActivity(objIntent);
            }
        });
        Pick_player1 = (ImageView) findViewById(R.id.Pick_player1);
        Pick_player1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Current_status = true;
                new Player_popup(TeamConfigActivity.this,GameModel.Game_id,TeamConfigActivity.this).Show();
            }
        });
        Delete_player1 = (ImageView) findViewById(R.id.Delete_player1);
        Delete_player1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GameModel.Game_player_id_1 = "";
                Common.objPlayers = new ArrayList<>();
                Common.objPlayers.add(GameModel.Game_player_id_2);
                Edit_Player1_input.setVisibility(View.VISIBLE);
                Add_player1.setVisibility(View.VISIBLE);
                Pick_player1.setVisibility(View.VISIBLE);
                Delete_player1.setVisibility(View.GONE);
                Player_layout1.setVisibility(View.GONE);
            }
        });
        Add_player2 = (ImageView) findViewById(R.id.Add_player2);
        Add_player2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Current_status = false;
                Intent objIntent = new Intent(TeamConfigActivity.this,NewPlayerActivity.class);
                objIntent.putExtra("Screenid","Teamconfig");
                startActivity(objIntent);
            }
        });
        Pick_player2 = (ImageView) findViewById(R.id.Pick_player2);
        Pick_player2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Current_status = false;
                new Player_popup(TeamConfigActivity.this,GameModel.Game_id,TeamConfigActivity.this).Show();
            }
        });

        Delete_player2 = (ImageView) findViewById(R.id.Delete_player2);
        Delete_player2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GameModel.Game_player_id_2 = "";
                Common.objPlayers = new ArrayList<>();
                Common.objPlayers.add(GameModel.Game_player_id_1);
                Edit_Player2_input.setVisibility(View.VISIBLE);
                Add_player2.setVisibility(View.VISIBLE);
                Pick_player2.setVisibility(View.VISIBLE);
                Delete_player2.setVisibility(View.GONE);
                Player_layout2.setVisibility(View.GONE);
            }
        });
        Button Submit = (Button) findViewById(R.id.Submit);
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Single_status){
                    Submit_Single();
                }else{
                    Submit_Team();
                }
            }
        });
        Team_Select1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Current_status = true;
                new Team_popup(TeamConfigActivity.this,GameModel.Game_id,TeamConfigActivity.this).Show();
            }
        });
        Team_Select2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Current_status = false;
                new Team_popup(TeamConfigActivity.this,GameModel.Game_id,TeamConfigActivity.this).Show();
            }
        });
        Player_Status(1);
    }
    void Load_Single_Players() {
        if (GameModel.Game_player_id_1 != null && GameModel.Game_player_id_1.length()>0) {
            Edit_Player1_input.setVisibility(View.GONE);
            Add_player1.setVisibility(View.GONE);
            Pick_player1.setVisibility(View.GONE);
            Delete_player1.setVisibility(View.VISIBLE);
            Player_layout1.setVisibility(View.VISIBLE);
            GameModel.Game_player_1 = objPlayerdatabase.GetRecords_id(GameModel.Game_player_id_1);
            player_name1.setText(GameModel.Game_player_1.getPlayername());
            player_position1.setText(GameModel.Game_player_1.getGamename()+" - "+GameModel.Game_player_1.getPlayerposition());
            Glide.with(TeamConfigActivity.this)
                    .load(GameModel.Game_player_1.getPlayerimage())
                    .placeholder(R.drawable.defaultimg)
                    .skipMemoryCache(true)
                    .override(80,80)
                    .into(player_image1);
        } else {
            Edit_Player1_input.setVisibility(View.VISIBLE);
            Add_player1.setVisibility(View.VISIBLE);
            Pick_player1.setVisibility(View.VISIBLE);
            Delete_player1.setVisibility(View.GONE);
            Player_layout1.setVisibility(View.GONE);
        }
        if (GameModel.Game_player_id_2 != null && GameModel.Game_player_id_2.length()>0) {
            Edit_Player2_input.setVisibility(View.GONE);
            Add_player2.setVisibility(View.GONE);
            Pick_player2.setVisibility(View.GONE);
            Delete_player2.setVisibility(View.VISIBLE);
            Player_layout2.setVisibility(View.VISIBLE);
            GameModel.Game_player_2 = objPlayerdatabase.GetRecords_id(GameModel.Game_player_id_2);
            player_name2.setText(GameModel.Game_player_2.getPlayername());
            player_position2.setText(GameModel.Game_player_2.getGamename()+" - "+GameModel.Game_player_2.getPlayerposition());
            Glide.with(TeamConfigActivity.this)
                    .load(GameModel.Game_player_2.getPlayerimage())
                    .placeholder(R.drawable.defaultimg)
                    .skipMemoryCache(true)
                    .override(80,80)
                    .into(player_image2);
        } else {
            Edit_Player2_input.setVisibility(View.VISIBLE);
            Add_player2.setVisibility(View.VISIBLE);
            Pick_player2.setVisibility(View.VISIBLE);
            Delete_player2.setVisibility(View.GONE);
            Player_layout2.setVisibility(View.GONE);
        }
    }
    void Player_Status(int value)
    {
      if(value == 1)
      {
          Single_status = true;
          Load_Single_Players();
          Single_player.setTextColor(getResources().getColor(R.color.black));
          Singleline.setBackgroundColor(getResources().getColor(R.color.black));
          Team_player.setTextColor(getResources().getColor(R.color.aaa));
          Teamline.setBackgroundColor(getResources().getColor(R.color.aaa));
          Single_Layout.setVisibility(View.VISIBLE);
          Team_Layout.setVisibility(View.GONE);
      } else {
          Single_status = false;
          Common.objPlayers = new ArrayList<>();
          GameModel.Game_player_id_1 = "";
          GameModel.Game_player_id_2 = "";
          Single_player.setTextColor(getResources().getColor(R.color.aaa));
          Singleline.setBackgroundColor(getResources().getColor(R.color.aaa));
          Team_player.setTextColor(getResources().getColor(R.color.black));
          Teamline.setBackgroundColor(getResources().getColor(R.color.black));
          Single_Layout.setVisibility(View.GONE);
          Team_Layout.setVisibility(View.VISIBLE);
      }
    }
    void Submit_Single() {
        if (GameModel.Game_player_id_1 != null && GameModel.Game_player_id_1.length() > 0) {

        } else {
            if (!Validation.Text(Edit_Player1, "Enter Player Name or Select Player")) {
                return;
            } else {
                GameModel.Game_player_id_1 = "";
                GameModel.Game_player_name_1 = Edit_Player1.getText().toString();
                GameModel.Game_player_1 = new PlayerModel();
                GameModel.Game_player_1.setPlayername(Edit_Player1.getText().toString());
                GameModel.Game_player_1.setPlayerimage(null);
            }
        }
        if (GameModel.Game_player_id_2 != null && GameModel.Game_player_id_2.length() > 0) {

        } else {
            if (!Validation.Text(Edit_Player2, "Enter Player Name or Select Player")) {
                return;
            } else {
                GameModel.Game_player_id_2 = "";
                GameModel.Game_player_name_2 = Edit_Player2.getText().toString();
                GameModel.Game_player_2 = new PlayerModel();
                GameModel.Game_player_2.setPlayername(Edit_Player2.getText().toString());
                GameModel.Game_player_2.setPlayerimage(null);
            }
        }
        GameModel.Game_single_status = true;
        StartGame();
    }
    void Submit_Team()
    {
        if (GameModel.Game_team_id_1 != null && GameModel.Game_team_id_1.length() > 0) {

        } else {
            ErrorMessage.setText("*Select Current Team to Proceed");
            return;
        }
        if (GameModel.Game_team_id_2 != null && GameModel.Game_team_id_2.length() > 0) {

        } else {
            ErrorMessage.setText("*Select Opponent Team to Proceed");
            return;
        }
        GameModel.Game_single_status = false;
        StartGame();
    }
    void StartGame() {

        switch (GameModel.Game_id) {
            case "G0001": {
                Intent objIntent = new Intent(TeamConfigActivity.this, ScoreActivity.class);
                startActivity(objIntent);
                break;
            }
            case "G0002": {
                Intent objIntent = new Intent(TeamConfigActivity.this, PinpongActivity.class);
                startActivity(objIntent);
                break;
            }
            case "G0004": {
                Intent objIntent = new Intent(TeamConfigActivity.this, PinpongActivity.class);
                startActivity(objIntent);
                break;
            }
            default: {
                Intent objIntent = new Intent(TeamConfigActivity.this, VolleyballActivity.class);
                startActivity(objIntent);
                break;
            }
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        Load_Single_Players();
    }
    @Override
    public void Callback(String result) {
        if(Current_status)
        {
            GameModel.Game_player_id_1 = result;
        } else {
            GameModel.Game_player_id_2 = result;
        }
        Common.objPlayers = new ArrayList<>();
        Common.objPlayers.add(GameModel.Game_player_id_1);
        Common.objPlayers.add(GameModel.Game_player_id_2);
        Load_Single_Players();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(TeamConfigActivity.this,HomeActivity.class));
    }

    @Override
    public void Callbackteam(TeamModel result,String players) {
        if(Current_status)
        {
            GameModel.Game_player_id_1 = players;
            GameModel.Game_team_id_1 = result.getUniqueid();
            Team_name1.setText(result.getTeamname());
            Glide.with(TeamConfigActivity.this)
                .load(result.getTeamimage())
                .placeholder(R.drawable.defaultimg)
                .skipMemoryCache(true)
                .override(100,100)
                .into(Team_image1);
            GameModel.Game_team_1 = result;
        } else {
            GameModel.Game_player_id_2 = players;
            GameModel.Game_team_id_2 = result.getUniqueid();
            Team_name2.setText(result.getTeamname());
            Glide.with(TeamConfigActivity.this)
                    .load(result.getTeamimage())
                    .placeholder(R.drawable.defaultimg)
                    .skipMemoryCache(true)
                    .override(100,100)
                    .into(Team_image2);
            GameModel.Game_team_2 = result;
        }
        Load_Team_players();
    }
    void Load_Team_players()
    {
        if (GameModel.Game_player_id_1 != null && GameModel.Game_player_id_1.length()>0) {
            List<String> objvalues = new ArrayList<>();
            objvalues = Common.StringtoArray(GameModel.Game_player_id_1);
            List<PlayerModel> objPlayerslist = new ArrayList<>();
            for(int i=0;i<objvalues.size();i++)
            {
                objPlayerslist.add(objPlayerdatabase.GetRecords_id(objvalues.get(i)));
            }
            Team_Listview1.setAdapter(new Player_Adapter_select(this,objPlayerslist));
        }
        if (GameModel.Game_player_id_2 != null && GameModel.Game_player_id_2.length()>0) {
            List<String> objvalues = new ArrayList<>();
            objvalues = Common.StringtoArray(GameModel.Game_player_id_2);
            List<PlayerModel> objPlayerslist = new ArrayList<>();
            for(int i=0;i<objvalues.size();i++)
            {
                objPlayerslist.add(objPlayerdatabase.GetRecords_id(objvalues.get(i)));
            }
            Team_Listview2.setAdapter(new Player_Adapter_select(this,objPlayerslist));
        }
    }
}
