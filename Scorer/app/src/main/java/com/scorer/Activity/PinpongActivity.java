package com.scorer.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.scorer.Adapter.Game_Adapter;
import com.scorer.GamesController.Pingpong;
import com.scorer.GamesController.Pingpong;
import com.scorer.LocalDatabase.Matchdatabase;
import com.scorer.LocalDatabase.Playerdatabase;
import com.scorer.ModalClass.Common;
import com.scorer.ModalClass.GameModel;
import com.scorer.ModalClass.MailModel;
import com.scorer.ModalClass.MatchModel;
import com.scorer.ModalClass.PlayerModel;
import com.scorer.R;
import com.scorer.SendMail;
import com.scorer.Validation;
import com.scorer.Widgets.AppPreferences;

import java.util.ArrayList;
import java.util.List;

public class PinpongActivity extends AppCompatActivity {
    Pingpong objPingpong;
    String uniqueid = "",Match_name = "";
    Matchdatabase objMatchdatabase;
    TextView Team1text,Team2text;
    ImageView Team_image1,Team_image2;
    TextView Score1,Score2,Scorepoints;
    MatchModel objMatchModel = new MatchModel();
    Playerdatabase objPlayerdatabase;
    List<PlayerModel> objPlayerslist;
    List<PlayerModel> objPlayerslist2;
    AppPreferences objAppPreferences;
    boolean Swap_Team;
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
        objPingpong = new Pingpong();
        objPlayerdatabase = new Playerdatabase(this);
        objMatchdatabase = new Matchdatabase(this);
        objAppPreferences = new AppPreferences(this);
        Headertext = (TextView) findViewById(R.id.Headertext);
        Headertext.setText(GameModel.Game_name);
        Team1text = (TextView) findViewById(R.id.Team1text);
        Team2text = (TextView) findViewById(R.id.Team2text);
        Team_image1 = (ImageView) findViewById(R.id.Team_image1);
        Team_image2 = (ImageView) findViewById(R.id.Team_image2);
//        PerviousPoints = (TextView) findViewById(R.id.PerviousPoints);
//        PerviousPoints.setText("");
        Score1 = (TextView) findViewById(R.id.Score1);
        Score2 = (TextView) findViewById(R.id.Score2);
        Scorepoints = (TextView) findViewById(R.id.Scorepoints);
        MenuLeft = (LinearLayout) findViewById(R.id.MenuLeft);
        final DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        int width = getResources().getDisplayMetrics().widthPixels / 3;
        DrawerLayout.LayoutParams params = (android.support.v4.widget.DrawerLayout.LayoutParams) MenuLeft.getLayoutParams();
        params.width = width;
        uniqueid = objMatchdatabase.Get_uniqueid();
        MenuLeft.setLayoutParams(params);
        Homebutton = (ImageView) findViewById(R.id.Homebutton);
        Homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (objPingpong.Current_round != 0 && objPingpong.getCurrent_teamScore().size() != 0) {
                    ShowSaveAlert(PinpongActivity.this, true);
                } else {
                    startActivity(new Intent(PinpongActivity.this, HomeActivity.class));
                }
            }
        });
        Menubutton = (ImageView) findViewById(R.id.Menubutton);
        Menubutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(MenuLeft);
            }
        });
        Button Team1Addscore = (Button) findViewById(R.id.Team1Addscore);
        Team1Addscore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlaySound();
                if (Swap_Team) {
                    objPingpong.Add_Opp_teamScore();
                } else {
                    objPingpong.Add_Current_teamScore();
                }
                UpdateScore();

            }
        });
        Button Team2Addscore = (Button) findViewById(R.id.Team2Addscore);
        Team2Addscore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlaySound();
                if (Swap_Team) {
                    objPingpong.Add_Current_teamScore();
                } else {
                    objPingpong.Add_Opp_teamScore();
                }
                UpdateScore();
            }
        });

        Shareimg = (ImageView) findViewById(R.id.Shareimg);
        Sharebutton = (TextView) findViewById(R.id.Sharebutton);
        Sharebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Save_Game(Match_name);
//                Common.Sharedata = objMatchModel;
                mDrawerLayout.closeDrawers();
                startActivity(new Intent(PinpongActivity.this, ScoreviewActivity.class));
            }
        });
        RelativeLayout Scorelayout = (RelativeLayout) findViewById(R.id.Scorelayout);
        Scorelayout.setVisibility(View.VISIBLE);
        Scoreimage = (ImageView) findViewById(R.id.Scoreimage);
        Scorebutton = (TextView) findViewById(R.id.Scorebutton);
        Scorebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (objPingpong.End_Score == 11) {
                    objPingpong.End_Score = 21;
                    Scorebutton.setText("Change Love Score to 11");
                } else {
                    objPingpong.End_Score = 11;
                    Scorebutton.setText("Change Love Score to 21");
                }
            }
        });
        if (GameModel.Game_id.equals("G0004")) {
            Scorelayout.setVisibility(View.GONE);
            objPingpong.End_Score = 21;
        }
        Saveimage = (ImageView) findViewById(R.id.Saveimage);
        SaveButton = (TextView) findViewById(R.id.SaveButton);
        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowSaveAlert(PinpongActivity.this, false);
            }
        });

        Chatimage = (ImageView) findViewById(R.id.Chatimage);
        Chatbutton = (TextView) findViewById(R.id.Chatbutton);
        Chatbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        Undobutton = (ImageView) findViewById(R.id.Undobutton);
        Undobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objPingpong.Undo_Score();
                UpdateScore();
            }
        });

        Redobutton = (ImageView) findViewById(R.id.Redobutton);
        Redobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objPingpong.Redo_Score();
                UpdateScore();
            }
        });

        Swapbutton = (ImageView) findViewById(R.id.Swapbutton);
        Swapbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Swap_Team = !Swap_Team;
                UpdateScore();
                LoadTeam();
            }
        });

        Muteimage = (ImageView) findViewById(R.id.Muteimage);
        Mutebutton = (TextView) findViewById(R.id.Mutebutton);
        Mutebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objAppPreferences.Set_Mute(!objAppPreferences.Get_Mute());
                mDrawerLayout.closeDrawers();
                Update_Mute();
            }
        });

        Daymodeimage = (ImageView) findViewById(R.id.Daymodeimage);
        Daymodebutton = (TextView) findViewById(R.id.Daymodebutton);
        Daymodebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objAppPreferences.Set_Daymode(!objAppPreferences.Get_Daymode());
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
        Scoreimage.setColorFilter(Color);
        Daymodeimage.setColorFilter(Color);
        Sharebutton.setTextColor(Color);
        SaveButton.setTextColor(Color);
        Chatbutton.setTextColor(Color);
        Mutebutton.setTextColor(Color);
        Daymodebutton.setTextColor(Color);
        Scorebutton.setTextColor(Color);
        LoadTeam();
    }
    void LoadTeam() {
        if (Swap_Team) {
            if (GameModel.Game_single_status) {
                Team2text.setText(GameModel.Game_player_1.getPlayername());
                Team1text.setText(GameModel.Game_player_2.getPlayername());
                Glide.with(PinpongActivity.this)
                        .load(GameModel.Game_player_1.getPlayerimage())
                        .placeholder(R.drawable.teamicon)
                        .skipMemoryCache(true)
                        .override(50, 50)
                        .into(Team_image2);
                Glide.with(PinpongActivity.this)
                        .load(GameModel.Game_player_2.getPlayerimage())
                        .placeholder(R.drawable.teamicon)
                        .skipMemoryCache(true)
                        .override(50, 50)
                        .into(Team_image1);
//                List<PlayerModel> objPlayerslist = new ArrayList<>();
//                objPlayerslist.add(GameModel.Game_player_1);
//                TeamPager2.setAdapter(new Game_Adapter(PinpongActivity.this, objPlayerslist, true));
//                TeamPager2.setCurrentItem(0);
//                List<PlayerModel> objPlayerslist2 = new ArrayList<>();
//                objPlayerslist2.add(GameModel.Game_player_2);
//                TeamPager1.setAdapter(new Game_Adapter(PinpongActivity.this, objPlayerslist2, false));
//                TeamPager1.setCurrentItem(0);
            } else {
                Team2text.setText(GameModel.Game_team_1.getTeamname());
                Team1text.setText(GameModel.Game_team_2.getTeamname());
                Glide.with(PinpongActivity.this)
                        .load(GameModel.Game_team_1.getTeamimage())
                        .placeholder(R.drawable.teamicon)
                        .skipMemoryCache(true)
                        .override(50, 50)
                        .into(Team_image2);
                Glide.with(PinpongActivity.this)
                        .load(GameModel.Game_team_2.getTeamimage())
                        .placeholder(R.drawable.teamicon)
                        .skipMemoryCache(true)
                        .override(50, 50)
                        .into(Team_image1);

                objPlayerslist = new ArrayList<>();
//                List<String> objPlayersids = new ArrayList<>();
//                objPlayersids = Common.StringtoArray(GameModel.Game_team_1.getTeamplayer());
//                for (int i = 0; i < objPlayersids.size(); i++) {
//                    objPlayerslist.add(objPlayerdatabase.GetRecords_id(objPlayersids.get(i)));
//                }
//                TeamPager2.setAdapter(new Game_Adapter(PinpongActivity.this, objPlayerslist, true));
//                TeamPager2.setCurrentItem(0);
//                objPlayerslist2 = new ArrayList<>();
//                List<String> objPlayersids2 = new ArrayList<>();
//                objPlayersids2 = Common.StringtoArray(GameModel.Game_team_2.getTeamplayer());
//                for (int i = 0; i < objPlayersids2.size(); i++) {
//                    objPlayerslist2.add(objPlayerdatabase.GetRecords_id(objPlayersids2.get(i)));
//                }
//                TeamPager1.setAdapter(new Game_Adapter(PinpongActivity.this, objPlayerslist2, false));
//                TeamPager1.setCurrentItem(0);
            }
        } else {
            if (GameModel.Game_single_status) {
                Team1text.setText(GameModel.Game_player_1.getPlayername());
                Team2text.setText(GameModel.Game_player_2.getPlayername());
                Glide.with(PinpongActivity.this)
                        .load(GameModel.Game_player_1.getPlayerimage())
                        .placeholder(R.drawable.teamicon)
                        .skipMemoryCache(true)
                        .override(50, 50)
                        .into(Team_image1);
                Glide.with(PinpongActivity.this)
                        .load(GameModel.Game_player_2.getPlayerimage())
                        .placeholder(R.drawable.teamicon)
                        .skipMemoryCache(true)
                        .override(50, 50)
                        .into(Team_image2);
//                List<PlayerModel> objPlayerslist = new ArrayList<>();
//                objPlayerslist.add(GameModel.Game_player_1);
//                TeamPager1.setAdapter(new Game_Adapter(PinpongActivity.this, objPlayerslist, true));
//                TeamPager1.setCurrentItem(0);
//                List<PlayerModel> objPlayerslist2 = new ArrayList<>();
//                objPlayerslist2.add(GameModel.Game_player_2);
//                TeamPager2.setAdapter(new Game_Adapter(PinpongActivity.this, objPlayerslist2, false));
//                TeamPager2.setCurrentItem(0);
            } else {
                Team1text.setText(GameModel.Game_team_1.getTeamname());
                Team2text.setText(GameModel.Game_team_2.getTeamname());
                Glide.with(PinpongActivity.this)
                        .load(GameModel.Game_team_1.getTeamimage())
                        .placeholder(R.drawable.teamicon)
                        .skipMemoryCache(true)
                        .override(50, 50)
                        .into(Team_image1);
                Glide.with(PinpongActivity.this)
                        .load(GameModel.Game_team_2.getTeamimage())
                        .placeholder(R.drawable.teamicon)
                        .skipMemoryCache(true)
                        .override(50, 50)
                        .into(Team_image2);

                objPlayerslist = new ArrayList<>();
//                List<String> objPlayersids = new ArrayList<>();
//                objPlayersids = Common.StringtoArray(GameModel.Game_team_1.getTeamplayer());
//                for (int i = 0; i < objPlayersids.size(); i++) {
//                    objPlayerslist.add(objPlayerdatabase.GetRecords_id(objPlayersids.get(i)));
//                }
//                TeamPager1.setAdapter(new Game_Adapter(PinpongActivity.this, objPlayerslist, true));
//                TeamPager1.setCurrentItem(0);
//                objPlayerslist2 = new ArrayList<>();
//                List<String> objPlayersids2 = new ArrayList<>();
//                objPlayersids2 = Common.StringtoArray(GameModel.Game_team_2.getTeamplayer());
//                for (int i = 0; i < objPlayersids2.size(); i++) {
//                    objPlayerslist2.add(objPlayerdatabase.GetRecords_id(objPlayersids2.get(i)));
//                }
//                TeamPager2.setAdapter(new Game_Adapter(PinpongActivity.this, objPlayerslist2, false));
//                TeamPager2.setCurrentItem(0);
            }

        }
    }
    void UpdateScore()
    {
        if (Swap_Team) {
            Score2.setText(objPingpong.getCurrent_Score());
            Score1.setText(objPingpong.getOpp_Score());
            String point1 = String.valueOf(objPingpong.getCurrent_point());
            String point2 = String.valueOf(objPingpong.getOpp_point());
            Scorepoints.setText(point2 + "-" + point1);
//            if(objPingpong.Current_round == 1)
//            {
//                PerviousPoints.setText(objPingpong.Opp_point_1 +"-"+objPingpong.Current_point_1);
//            } else if(objPingpong.Current_round == 2)
//            {
//                PerviousPoints.setText(objPingpong.Opp_point_1 +"-"+objPingpong.Current_point_1 +"\n"+ objPingpong.Opp_point_2 +"-"+objPingpong.Current_point_2);
//            } else PerviousPoints.setText("");
        } else {
            Score1.setText(objPingpong.getCurrent_Score());
            Score2.setText(objPingpong.getOpp_Score());
            String point1 = String.valueOf(objPingpong.getCurrent_point());
            String point2 = String.valueOf(objPingpong.getOpp_point());
            Scorepoints.setText(point1 + "-" + point2);
//            if(objPingpong.Current_round == 1)
//            {
//                PerviousPoints.setText(objPingpong.Current_point_1 +"-"+objPingpong.Opp_point_1);
//            } else if(objPingpong.Current_round == 2)
//            {
//                PerviousPoints.setText(objPingpong.Current_point_1 +"-"+objPingpong.Opp_point_1 +"\n"+ objPingpong.Current_point_2 +"-"+objPingpong.Opp_point_2);
//            } else PerviousPoints.setText("");
        }

        if(objPingpong.getCurrent_point()==(objPingpong.End_Score+2))
        {
            if(GameModel.Game_single_status) {
                ShowWonAlert(GameModel.Game_player_1.getPlayername() + " Won the Match");
            } else {
                ShowWonAlert(GameModel.Game_team_1.getTeamname() + " Won the Match");
            }
        }
        if(objPingpong.getOpp_point()==(objPingpong.End_Score+2))
        {
            if(GameModel.Game_single_status) {
                ShowWonAlert(GameModel.Game_player_2.getPlayername() + " Won the Match");
            } else {
                ShowWonAlert(GameModel.Game_team_2.getTeamname() + " Won the Match");
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
        if(objPingpong.Current_round != 0 && objPingpong.getCurrent_teamScore().size() != 0) {
            ShowSaveAlert(PinpongActivity.this, true);
        } else {
            startActivity(new Intent(PinpongActivity.this, HomeActivity.class));
        }
    }
    void ShowSaveAlert(Activity objActivity,final boolean Redirect)
    {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(objActivity);
        builder1.setMessage("Save Changes?");
        builder1.setCancelable(false);
        final EditText Edit_Matchname = new EditText(PinpongActivity.this);
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
                            startActivity(new Intent(PinpongActivity.this, HomeActivity.class));
                        }
                    }
                });

        if(Redirect) {
            builder1.setNegativeButton(
                    "Discard",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            startActivity(new Intent(PinpongActivity.this, HomeActivity.class));
                        }
                    });
        }
        builder1.setNeutralButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        if(objPingpong.Current_round == 3) {
                            startActivity(new Intent(PinpongActivity.this, HomeActivity.class));
                        }
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
        objPingpong.Opp_teamScore = new ArrayList<>();
        objPingpong.Opp_teamScore_str = new ArrayList<>();
        objPingpong.Current_teamScore = new ArrayList<>();
        objPingpong.Current_teamScore_str = new ArrayList<>();
        objPingpong.Opp_teamScore.add(0);
        objPingpong.Current_teamScore.add(0);
        objPingpong.Opp_teamScore_str.add("0");
        objPingpong.Current_teamScore_str.add("0");
        UpdateScore();
    }
    void ShowWonAlert(String Content)
    {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(PinpongActivity.this);
        builder1.setMessage(Content);
        builder1.setCancelable(false);
        if(objPingpong.Current_round != 2) {
            builder1.setPositiveButton(
                    "Continue",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            if (objPingpong.Current_round == 0) {
                                objPingpong.Current_team_1 = Common.ArraytoString(objPingpong.getCurrent_teamScore_str());
                                objPingpong.Opp_team_1 = Common.ArraytoString(objPingpong.getOpp_teamScore_str());
                                objPingpong.Current_point_1 = String.valueOf(objPingpong.getCurrent_point());
                                objPingpong.Opp_point_1 = String.valueOf(objPingpong.getOpp_point());
                                objPingpong.Current_round = 1;
                            } else if (objPingpong.Current_round == 1) {
                                objPingpong.Current_team_2 = Common.ArraytoString(objPingpong.getCurrent_teamScore_str());
                                objPingpong.Opp_team_2 = Common.ArraytoString(objPingpong.getOpp_teamScore_str());
                                objPingpong.Current_point_2 = String.valueOf(objPingpong.getCurrent_point());
                                objPingpong.Opp_point_2 = String.valueOf(objPingpong.getOpp_point());
                                objPingpong.Current_round = 2;
                            }
                            New_Game();
                            dialog.cancel();
                        }
                    });
        } else {
            builder1.setPositiveButton(
                    "Finish",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            objPingpong.Current_round = 3;
                            ShowSaveAlert(PinpongActivity.this, true);
                            dialog.cancel();
                        }
                    });
        }
        builder1.setNeutralButton(
                "New Game",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ShowSaveAlert(PinpongActivity.this,false);
                        dialog.cancel();
                    }
                });
        builder1.setNegativeButton(
                "Quit",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ShowSaveAlert(PinpongActivity.this,true);
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
        if (objPingpong.Current_round == 0) {
            objMatchModel.setCurrentscore(objPingpong.Current_team_1);
            objMatchModel.setOppscore(objPingpong.Opp_team_1);
            objMatchModel.setScorepoints(objPingpong.Current_point_1 + "-" + objPingpong.Opp_point_1);
        } else if (objPingpong.Current_round == 1) {
            objMatchModel.setCurrentscore(objPingpong.Current_team_1);
            objMatchModel.setOppscore(objPingpong.Opp_team_1);
            objMatchModel.setScorepoints(objPingpong.Current_point_1 + "-" + objPingpong.Opp_point_1);

            objMatchModel.setCurrentscore_2(Common.ArraytoString(objPingpong.getCurrent_teamScore_str()));
            objMatchModel.setOppscore_2(Common.ArraytoString(objPingpong.getOpp_teamScore_str()));
            objMatchModel.setScorepoints_2(Scorepoints.getText().toString());
        } else {
            objMatchModel.setCurrentscore(objPingpong.Current_team_1);
            objMatchModel.setOppscore(objPingpong.Opp_team_1);
            objMatchModel.setScorepoints(objPingpong.Current_point_1 + "-" + objPingpong.Opp_point_1);

            objMatchModel.setCurrentscore_2(objPingpong.Current_team_2);
            objMatchModel.setOppscore_2(objPingpong.Opp_team_2);
            objMatchModel.setScorepoints_2(objPingpong.Current_point_2 + "-" + objPingpong.Opp_point_2);

            objMatchModel.setCurrentscore_3(Common.ArraytoString(objPingpong.getCurrent_teamScore_str()));
            objMatchModel.setOppscore_3(Common.ArraytoString(objPingpong.getOpp_teamScore_str()));
            objMatchModel.setScorepoints_3(Scorepoints.getText().toString());
        }
        objMatchModel.setScore_count(objPingpong.Current_round);
        objMatchModel.setCurrentscore(Common.ArraytoString(objPingpong.getCurrent_teamScore_str()));
        objMatchModel.setOppscore(Common.ArraytoString(objPingpong.getOpp_teamScore_str()));
        objMatchModel.setScorepoints(Scorepoints.getText().toString());
        objMatchModel.setDatetime(Common.CurrentDatetimeString());
        objMatchModel.setStatus("Drop");
        if (objPingpong.getCurrent_point() == (objPingpong.End_Score+2)) {
            objMatchModel.setStatus("Won");
            Team1status = "Won";
            Team2Status = "Lose";
        }
        if (objPingpong.getOpp_point() == (objPingpong.End_Score+2)) {
            objMatchModel.setStatus("Won");
            Team1status = "Lose";
            Team2Status = "Won";
        }
        objMatchdatabase.AddRecord(objMatchModel);
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
