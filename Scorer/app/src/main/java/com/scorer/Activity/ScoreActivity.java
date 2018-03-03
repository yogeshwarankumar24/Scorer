package com.scorer.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.scorer.Adapter.Game_Adapter;
import com.scorer.Adapter.Scoretennis_Adapter;
import com.scorer.GamesController.SRRTennisMatchObj;
import com.scorer.GamesController.SRRTennisSet;
import com.scorer.GamesController.Tennis;
import com.scorer.LocalDatabase.Tennisdatabase;
import com.scorer.LocalDatabase.Playerdatabase;
import com.scorer.ModalClass.Common;
import com.scorer.ModalClass.GameModel;
import com.scorer.ModalClass.MailModel;
import com.scorer.ModalClass.MatchModel;
import com.scorer.ModalClass.PlayerModel;
import com.scorer.ModalClass.TennisModel;
import com.scorer.R;
import com.scorer.SendMail;
import com.scorer.Validation;
import com.scorer.Widgets.AppPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ScoreActivity extends AppCompatActivity {
    SRRTennisMatchObj objTennis;
    String uniqueid = "",Match_name = "";
    Tennisdatabase objTennisdatabase;
    TextView Team1text,Team2text;
    TextView Team1_name,Team2_name;
    ImageView Team_image1,Team_image2;
    TextView Score1,Score2,Scorepoints;
    TennisModel objMatchModel = new TennisModel();
    Playerdatabase objPlayerdatabase;
    List<PlayerModel> objPlayerslist;
    List<PlayerModel> objPlayerslist2;
    AppPreferences objAppPreferences;
    boolean Swap_Team;
    GridView ScoreGridview;
    TextView Headertext;
    ImageView Chatimage,Saveimage,Shareimg;
    TextView Sharebutton,SaveButton,Chatbutton;
    LinearLayout Layout_main,MenuLeft;
    TextView Mutebutton,Daymodebutton,Scorebutton;
    ImageView Muteimage,Daymodeimage,Scoreimage;
    ImageView Homebutton,Menubutton,Redobutton,Swapbutton,Undobutton;
    String Team1status = "Drop", Team2Status = "Drop";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        Layout_main = (LinearLayout) findViewById(R.id.Layout_main);
//        TeamPager1 = (ViewPager) findViewById(R.id.TeamPager1);
//        TeamPager2 = (ViewPager) findViewById(R.id.TeamPager2);
        objTennis = new SRRTennisMatchObj();
        objPlayerdatabase = new Playerdatabase(this);
        objTennisdatabase = new Tennisdatabase(this);
        objAppPreferences = new AppPreferences(this);
        Headertext = (TextView)findViewById(R.id.Headertext);
        Headertext.setText(GameModel.Game_name);
        Team1text = (TextView)findViewById(R.id.Team1text);
        Team2text = (TextView)findViewById(R.id.Team2text);
        Team1_name = (TextView)findViewById(R.id.Team1_name);
        Team2_name = (TextView)findViewById(R.id.Team2_name);
        Team_image1 = (ImageView)findViewById(R.id.Team_image1);
        Team_image2 = (ImageView)findViewById(R.id.Team_image2);
        ScoreGridview = (GridView)findViewById(R.id.ScoreGridview);
//        PerviousPoints = (TextView)findViewById(R.id.PerviousPoints);
//        PerviousPoints.setText("");
        Score1 = (TextView)findViewById(R.id.Score1);
        Score2 = (TextView)findViewById(R.id.Score2);
        Scorepoints = (TextView)findViewById(R.id.Scorepoints);
        MenuLeft = (LinearLayout)findViewById(R.id.MenuLeft);
        final DrawerLayout mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        int width = getResources().getDisplayMetrics().widthPixels/3;
        DrawerLayout.LayoutParams params = (android.support.v4.widget.DrawerLayout.LayoutParams) MenuLeft.getLayoutParams();
        params.width = width;
        uniqueid = objTennisdatabase.Get_uniqueid();
        MenuLeft.setLayoutParams(params);
        Homebutton = (ImageView)findViewById(R.id.Homebutton);
        Homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(objTennis.getSrrTennisSetVec().size() != 0) {
                    ShowSaveAlert(ScoreActivity.this, true);
                } else {
                    startActivity(new Intent(ScoreActivity.this, HomeActivity.class));
                }
            }
        });
        RelativeLayout Scorelayout = (RelativeLayout) findViewById(R.id.Scorelayout);
        Scorelayout.setVisibility(View.VISIBLE);
        Scoreimage = (ImageView) findViewById(R.id.Scoreimage);
        Scorebutton = (TextView) findViewById(R.id.Scorebutton);
        objTennis.setWinningPoints(3);
        Scorebutton.setText("Change to 3 sets (2 wins)");
        Scorebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (objTennis.getWinningPoints() == 2) {
                    objTennis.setWinningPoints(3);
                    Scorebutton.setText("Change to 3 sets (2 wins)");
                } else {
                    objTennis.setWinningPoints(2);
                    Scorebutton.setText("Change 5 sets (3 wins)");
                }
            }
        });
        Menubutton = (ImageView)findViewById(R.id.Menubutton);
        Menubutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(MenuLeft);
            }
        });
        Button Team1Addscore = (Button)findViewById(R.id.Team1Addscore);
        Team1Addscore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlaySound();
                if (Swap_Team) {
                    objTennis.addTeam2Point();
                } else {
                    objTennis.addTeam1Point();
                }
                UpdateScore();

            }
        });
        Button Team2Addscore = (Button)findViewById(R.id.Team2Addscore);
        Team2Addscore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlaySound();
                if (Swap_Team) {
                    objTennis.addTeam1Point();
                } else {
                    objTennis.addTeam2Point();
                }
                UpdateScore();
            }
        });

        Shareimg = (ImageView)findViewById(R.id.Shareimg);
        Sharebutton = (TextView)findViewById(R.id.Sharebutton);
        Sharebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Save_Game(Match_name);
                Common.Sharedata = objMatchModel;
                mDrawerLayout.closeDrawers();
                startActivity(new Intent(ScoreActivity.this,ScoreviewActivity.class));
            }
        });

        Saveimage = (ImageView)findViewById(R.id.Saveimage);
        SaveButton = (TextView)findViewById(R.id.SaveButton);
        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowSaveAlert(ScoreActivity.this,false);
            }
        });

        Chatimage = (ImageView)findViewById(R.id.Chatimage);
        Chatbutton = (TextView)findViewById(R.id.Chatbutton);
        Chatbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        Undobutton = (ImageView)findViewById(R.id.Undobutton);
        Undobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                objTennis.Undo_Score();
                UpdateScore();
            }
        });

        Redobutton = (ImageView)findViewById(R.id.Redobutton);
        Redobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                objTennis.Redo_Score();
                UpdateScore();
            }
        });

        Swapbutton = (ImageView)findViewById(R.id.Swapbutton);
        Swapbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Swap_Team = !Swap_Team;
                UpdateScore();
                LoadTeam();
            }
        });

        Muteimage = (ImageView)findViewById(R.id.Muteimage);
        Mutebutton = (TextView)findViewById(R.id.Mutebutton);

        Mutebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(objAppPreferences.Get_Mute())
                {
                    objAppPreferences.Set_Mute(false);
                } else {
                    objAppPreferences.Set_Mute(true);
                }
                mDrawerLayout.closeDrawers();
                Update_Mute();
            }
        });

        Daymodeimage = (ImageView)findViewById(R.id.Daymodeimage);
        Daymodebutton = (TextView)findViewById(R.id.Daymodebutton);
        Daymodebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(objAppPreferences.Get_Daymode())
            {
                objAppPreferences.Set_Daymode(false);
            } else {
                objAppPreferences.Set_Daymode(true);
            }
            mDrawerLayout.closeDrawers();
            Update_DayNight();
            }
        });
        Update_Mute();
        Update_DayNight();
        LoadTeam();
        New_Game();
    }
    void Update_Mute()
    {
        if(objAppPreferences.Get_Mute())
        {
            Muteimage.setImageResource(R.drawable.sound);
            Mutebutton.setText("Sound");
        } else {
            Muteimage.setImageResource(R.drawable.mute);
            Mutebutton.setText("Mute");
        }
    }
    void Update_DayNight() {
        int Color;
        if (objAppPreferences.Get_Daymode()) {
            Color = getResources().getColor(R.color.white);
            Layout_main.setBackgroundColor(getResources().getColor(R.color.black));
            MenuLeft.setBackgroundColor(getResources().getColor(R.color.black));
            Daymodeimage.setImageResource(R.drawable.day);
            Daymodebutton.setText("Day mode");
        } else {
            Color = getResources().getColor(R.color.black);
            Layout_main.setBackgroundColor(getResources().getColor(R.color.white));
            MenuLeft.setBackgroundColor(getResources().getColor(R.color.white));
            Daymodeimage.setImageResource(R.drawable.night);
            Daymodebutton.setText("Night mode");
        }
        Headertext.setTextColor(Color);
        Menubutton.setColorFilter(Color);
        Undobutton.setColorFilter(Color);
        Redobutton.setColorFilter(Color);
        Homebutton.setColorFilter(Color);
        Swapbutton.setColorFilter(Color);
        Team1text.setTextColor(Color);
        Team2text.setTextColor(Color);
        Scorepoints.setTextColor(Color);
        Chatimage.setColorFilter(Color);
        Saveimage.setColorFilter(Color);
        Shareimg.setColorFilter(Color);
        Muteimage.setColorFilter(Color);
        Daymodeimage.setColorFilter(Color);
        Sharebutton.setTextColor(Color);
        SaveButton.setTextColor(Color);
        Chatbutton.setTextColor(Color);
        Mutebutton.setTextColor(Color);
        Daymodebutton.setTextColor(Color);
        Scorebutton.setTextColor(Color);
        Scoreimage.setColorFilter(Color);
        LoadTeam();
    }
    void LoadTeam() {
        if (GameModel.Game_single_status) {
            Team2_name.setText(GameModel.Game_player_1.getPlayername());
            Team1_name.setText(GameModel.Game_player_2.getPlayername());
            Glide.with(ScoreActivity.this)
                    .load(GameModel.Game_player_1.getPlayerimage())
                    .placeholder(R.drawable.teamicon)
                    .skipMemoryCache(true)
                    .override(50, 50)
                    .into(Team_image2);
            Glide.with(ScoreActivity.this)
                    .load(GameModel.Game_player_2.getPlayerimage())
                    .placeholder(R.drawable.teamicon)
                    .skipMemoryCache(true)
                    .override(50, 50)
                    .into(Team_image1);
        }
        else {
            Team2_name.setText(GameModel.Game_team_1.getTeamname());
            Team1_name.setText(GameModel.Game_team_2.getTeamname());
            Glide.with(ScoreActivity.this)
                    .load(GameModel.Game_team_1.getTeamimage())
                    .placeholder(R.drawable.teamicon)
                    .skipMemoryCache(true)
                    .override(50, 50)
                    .into(Team_image2);
            Glide.with(ScoreActivity.this)
                    .load(GameModel.Game_team_2.getTeamimage())
                    .placeholder(R.drawable.teamicon)
                    .skipMemoryCache(true)
                    .override(50, 50)
                    .into(Team_image1);

        }
            if (Swap_Team) {
            if (GameModel.Game_single_status) {
                Team2text.setText(GameModel.Game_player_1.getPlayername());
                Team1text.setText(GameModel.Game_player_2.getPlayername());
            } else {
                Team2text.setText(GameModel.Game_team_1.getTeamname());
                Team1text.setText(GameModel.Game_team_2.getTeamname());
                objPlayerslist = new ArrayList<>();
                List<String> objPlayersids = new ArrayList<>();
                objPlayersids = Common.StringtoArray(GameModel.Game_team_1.getTeamplayer());
                for (int i = 0; i < objPlayersids.size(); i++) {
                    objPlayerslist.add(objPlayerdatabase.GetRecords_id(objPlayersids.get(i)));
                }

            }
        } else {
            if (GameModel.Game_single_status) {
                Team1text.setText(GameModel.Game_player_1.getPlayername());
                Team2text.setText(GameModel.Game_player_2.getPlayername());
            } else {
                Team1text.setText(GameModel.Game_team_1.getTeamname());
                Team2text.setText(GameModel.Game_team_2.getTeamname());
            }

        }
    }
    void UpdateScore()
    {
        if (Swap_Team) {
            Score2.setText(objTennis.getCurrentSet().getCurrentGame().getTeam1PointLabel());
            Score1.setText(objTennis.getCurrentSet().getCurrentGame().getTeam2PointLabel());
            String point1 = String.valueOf(objTennis.getCurrentSet().getCurrentGame().getTeam1Point());
            String point2 = String.valueOf(objTennis.getCurrentSet().getCurrentGame().getTeam2Point());
            Scorepoints.setText(point2 + "-" + point1);
            ScoreGridview.setAdapter(new Scoretennis_Adapter(this,objTennis.getSrrTennisSetVec()));
        } else {
            Score1.setText(objTennis.getCurrentSet().getCurrentGame().getTeam1PointLabel());
            Score2.setText(objTennis.getCurrentSet().getCurrentGame().getTeam2PointLabel());
            String point1 = String.valueOf(objTennis.getCurrentSet().getCurrentGame().getTeam1Point());
            String point2 = String.valueOf(objTennis.getCurrentSet().getCurrentGame().getTeam2Point());
            Scorepoints.setText(point1 + "-" + point2);
            ScoreGridview.setAdapter(new Scoretennis_Adapter(this,objTennis.getSrrTennisSetVec()));
        }

        if(objTennis.isCompleted())
        {
            if(objTennis.getWinner() == 1) {
                if (GameModel.Game_single_status) {
                    ShowWonAlert(GameModel.Game_player_1.getPlayername() + " Won the Match");
                } else {
                    ShowWonAlert(GameModel.Game_team_1.getTeamname() + " Won the Match");
                }
            } else {
                if (GameModel.Game_single_status) {
                    ShowWonAlert(GameModel.Game_player_2.getPlayername() + " Won the Match");
                } else {
                    ShowWonAlert(GameModel.Game_team_2.getTeamname() + " Won the Match");
                }
            }
        }
    }
    public void PlaySound(){
        if(!objAppPreferences.Get_Mute()) {
            try {
                final MediaPlayer objMediaPlayer = MediaPlayer.create(this, R.raw.button_click);
                objMediaPlayer.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void onBackPressed() {
        if(objTennis.getSrrTennisSetVec().size() != 0) {
            ShowSaveAlert(ScoreActivity.this, true);
        } else {
            startActivity(new Intent(ScoreActivity.this, HomeActivity.class));
        }
    }
    void ShowSaveAlert(Activity objActivity,final boolean Redirect)
    {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(objActivity);
        builder1.setMessage("Save Changes?");
        builder1.setCancelable(false);
        final EditText Edit_Matchname = new EditText(ScoreActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        lp.leftMargin = 10;
        lp.rightMargin = 10;
        Edit_Matchname.setLayoutParams(lp);
        Edit_Matchname.setHint("Enter Game Name");
        Edit_Matchname.setText(Match_name);
        builder1.setView(Edit_Matchname);
        builder1.setPositiveButton(
                "Save",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(!Validation.Text(Edit_Matchname,"Enter Game Name"))
                            return;
                        Match_name = Edit_Matchname.getText().toString();
                        Save_Game(Match_name);
                        if(Redirect) {
                            startActivity(new Intent(ScoreActivity.this, HomeActivity.class));
                        }
                    }
                });

        if(Redirect) {
            builder1.setNegativeButton(
                    "Discard",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            startActivity(new Intent(ScoreActivity.this, HomeActivity.class));
                        }
                    });
        }
        builder1.setNeutralButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
//                        if(objTennis.Current_round == 3) {
//                            startActivity(new Intent(ScoreActivity.this, HomeActivity.class));
//                        }
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
    void New_Game()
    {
        Score1.setText("0");
        Score2.setText("0");
        Scorepoints.setText("0-0");
        UpdateScore();
    }
    void ShowWonAlert(String Content)
    {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(ScoreActivity.this);
        builder1.setMessage(Content);
        builder1.setCancelable(false);
        builder1.setPositiveButton(
                "New Game",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ShowSaveAlert(ScoreActivity.this,false);
                        dialog.cancel();
                    }
                });
        builder1.setNegativeButton(
                "Quit",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ShowSaveAlert(ScoreActivity.this,true);
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
    void Save_Game(String Match_name) {
        objMatchModel.setUniqueid(uniqueid);
        objMatchModel.setMatchname(Match_name);
        objMatchModel.setGameid(GameModel.Game_id);
        objMatchModel.setGamename(GameModel.Game_name);
        if (GameModel.Game_single_status) {
            objMatchModel.setCurrentteam(GameModel.Game_player_1.getPlayername());
            objMatchModel.setOppteam(GameModel.Game_player_2.getPlayername());
        } else {
            objMatchModel.setCurrentteam(GameModel.Game_team_1.getTeamname());
            objMatchModel.setOppteam(GameModel.Game_team_2.getTeamname());
        }
        objMatchModel.setDatetime(Common.CurrentDatetimeString());
        objMatchModel.setStatus("Drop");
        if(objTennis.isCompleted())
        {
            if(objTennis.getWinner() == 1) {
                objMatchModel.setStatus("Won");
                Team1status = "Won";
                Team2Status = "Lose";
            } else {
                objMatchModel.setStatus("Won");
                Team1status = "Lose";
                Team2Status = "Won";
            }
        }

        JSONObject Scorejson = new JSONObject();
        try {
            Scorejson.put("winpoints", objTennis.getWinningPoints());
            Scorejson.put("winner", objTennis.getWinner());
            Scorejson.put("completed", objTennis.isCompleted()?1:0);
            Scorejson.put("matchpoint1", objTennis.getTeam1MatchPoint());
            Scorejson.put("matchpoint2", objTennis.getTeam2MatchPoint());
            Vector<SRRTennisSet> objtennis_sets = objTennis.getSrrTennisSetVec();
            JSONArray jsonArray_sets = new JSONArray();
            for (SRRTennisSet objset:objtennis_sets) {
                JSONObject info = new JSONObject();
                info.put("team1setpoint", objset.getTeam1SetPoint());
                info.put("team2setpoint", objset.getTeam2SetPoint());
                jsonArray_sets.put(info);
            }
            Scorejson.put("scores", jsonArray_sets);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        objMatchModel.setJsonScore(Scorejson.toString());
        objTennisdatabase.AddRecord(objMatchModel);
        if (!GameModel.Game_single_status) {
            List<String> objString = new ArrayList<>();
            for (int i = 0; i < objPlayerslist.size(); i++) {
                objString.add(objPlayerslist.get(i).getPlayeremail());
            }
            for (int j = 0; j < objPlayerslist2.size(); j++) {
                objString.add(objPlayerslist.get(j).getPlayeremail());
            }
            Sendmail(objString);
        } else {
            if (GameModel.Game_player_1.getUniqueid() != null && GameModel.Game_player_1.getUniqueid().length() > 0) {
                List<String> objString = new ArrayList<>();
                objString.add(GameModel.Game_player_1.getPlayeremail());
                objString.add(GameModel.Game_player_2.getPlayeremail());
                Sendmail(objString);
            }
        }
    }
    void Sendmail(List<String> objString)
    {
        MailModel objMailModel = new MailModel();
        objMailModel.setSendmailto(objString);
        objMailModel.setMailSubject("Scorer App Game Update");
        objMailModel.setMailBody(objMatchModel.getMatchname() +"\n\n" +objMatchModel.getCurrentteam() + " VS " + objMatchModel.getOppteam() + "\n\n" + objMatchModel.getCurrentteam() +"\t" +Team1status + " Game\n\n"+ objMatchModel.getOppteam() +"\t" +Team2Status +" Game\n\n\n\n\n\nScorer For More Details Download Link : https://play.google.com/store/apps/details?id=com.scorer&hl=en");
        new SendMail().execute(objMailModel);
    }
}
