package com.scorer.GamesController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android on 11/9/2017.
 */

public class Tennis {

    public String Current_team_1 = "";
    public String Opp_team_1 = "";
    public String Current_point_1 = "";
    public String Opp_point_1 = "";

    public String Current_team_2 = "";
    public String Opp_team_2 = "";
    public String Current_point_2 = "";
    public String Opp_point_2 = "";

    public int Current_round;

    public List<Integer> Current_teamScore = new ArrayList<>();
    List<Integer> Current_teamScore_undo = new ArrayList<>();
    public List<Integer> getCurrent_teamScore() {
        return Current_teamScore;
    }

    public List<Integer> Opp_teamScore = new ArrayList<>();
    List<Integer> Opp_teamScore_undo = new ArrayList<>();
    public List<Integer> getOpp_teamScore() {
        return Opp_teamScore;
    }

    public List<String> Current_teamScore_str = new ArrayList<>();
    List<String> Current_teamScore_str_undo = new ArrayList<>();
    public List<String> getCurrent_teamScore_str() {
        return Current_teamScore_str;
    }

    public List<String> Opp_teamScore_str = new ArrayList<>();
    List<String> Opp_teamScore_str_undo = new ArrayList<>();
    public List<String> getOpp_teamScore_str() {
        return Opp_teamScore_str;
    }

    public String getCurrent_Score() {
        String Score;
        if(Current_teamScore_str.size()>0) {
            Score = Current_teamScore_str.get(Current_teamScore_str.size() - 1);
        } else {
            Score = "0";
        }
        return Score;
    }

    public String getOpp_Score() {
        String Score;
        if(Opp_teamScore_str.size()>0) {
            Score = Opp_teamScore_str.get(Opp_teamScore_str.size() - 1);
        } else {
            Score = "0";
        }
        return Score;
    }

    public int getCurrent_point() {
        int Score;
        if(Current_teamScore.size()>0) {
            Score = Current_teamScore.get(Current_teamScore.size() - 1);
        } else {
            Score = 0;
        }
        return Score;
    }

    public int getOpp_point() {
        int Score;
        if(Opp_teamScore.size()>0) {
            Score = Opp_teamScore.get(Opp_teamScore.size() - 1);
        } else {
            Score = 0;
        }
        return Score;
    }
    public Tennis()
    {
        Add_Current_teamScore();
        Add_Opp_teamScore();
    }
    public void Add_Current_teamScore(){
        Add_teamScore(Current_teamScore, Opp_teamScore, Current_teamScore_str, Opp_teamScore_str, Current_teamScore_undo);
    }


    public void Add_teamScore(List<Integer> team1, List<Integer> team2,
                              List<String> team1_str, List<String> team2_str,
                              List<Integer> team1_undo)
    {
        team1_undo = new ArrayList<>();
        if(team1.size() > 0) {
            switch (team1.get(team1.size() - 1)) {
                case 0:
                {
                    team1.add(1);
                    team1_str.add("15");

                    break;
                }
                case 1:
                {
                    team1.add(2);
                    team1_str.add("30");
                    
                    break;
                }
                case 2:
                {
                    if(team2.get(team2.size()-1) < 3)
                    {
                        team1.add(3);
                        team1_str.add("Win");
                        //team2.add(3);
                        //team2_str.add("45");
                    }
                    // If Opponent is in duece, this will not happen this block because this 45 (3) block, this will only happen in Duece (4) block
                    else if(team2.get(team2.size()-1) == 3) {
                        team1.add(4);
                        team1_str.add("Dueue");
                        team2.add(4);
                        team2_str.add("Dueue");
                    }
                    break;
                }
                case 3:
                {
                  /*  if(team2.get(team2.size()-1) >= 3)
                    {
                        team1.add(4);
                        team1_str.add("Deuce");
                        team2.add(3);
                        team2_str.add("45");
                    } else  {
                        team1.add(5);
                        team1_str.add("Love");
                        Current_CheckLove();
                    }*/
                    // If Opponent is 30 or less
                    if(team2.get(team2.size()-1) < 3)
                    {
                        team1.add(6);
                        team1_str.add("Win");
                        //team2.add(3);
                        //team2_str.add("45");
                    }
                    // If Opponent is in duece, this will not happen this block because this 45 (3) block, this will only happen in Duece (4) block
                    else if(team2.get(team2.size()-1) == 3) {
                        team1.add(4);
                        team1_str.add("Dueue");
                        team2.add(4);
                        team2_str.add("Dueue");
                    }
                    // If Opponent is in duece, this will not happen this block because this 45 (3) block, this will only happen in Duece (4) block
                    else if(team2.get(team2.size()-1) == 4) {
                        team1.add(5);
                        team1_str.add("Adv");
                        team2.add(3);
                        team2_str.add("45");
                    }
                    // If Opponent is in Advantage
                    else if(team2.get(team2.size()-1) == 5) {
                        team1.add(4);
                        team1_str.add("Dueue");
                        team2.add(4);
                        team2_str.add("Dueue");
                        //Current_CheckLove();
                    } else {
                        // this will not happen, control will come to this block with current score Adv (5) or more.
                    }

                    break;
                }
                case 4: // Duece
                {
                    team1.add(5);
                    team1_str.add("Adv");
                    team2.add(3);
                    team2_str.add("45");
                    break;
                }
                case 5: // Adv
                {
                    team1.add(6);
                    team1_str.add("Won");
                    team2.add(team2.get(team2.size() - 1));
                    team2_str.add("Lose");
                    break;
                }
            }
        } else {
            team1.add(0);
            team1_str.add("0");
        }

    }

    void Current_CheckLove() {
        if (Opp_teamScore.size() > 0) {
            if (Opp_teamScore.get(Opp_teamScore.size() - 1) >= 3) {
                Opp_teamScore.add(3);
                Opp_teamScore_str.add("45");
            } else {
                Opp_teamScore.add(Opp_teamScore.get(Opp_teamScore.size() - 1));
                Opp_teamScore_str.add(Opp_teamScore_str.get(Opp_teamScore_str.size() - 1));
            }
        } else {
            Opp_teamScore.add(0);
            Opp_teamScore_str.add("0");
        }
    }
    public void Add_Opp_teamScore()
    {
        Add_teamScore(Opp_teamScore, Current_teamScore, Opp_teamScore_str, Current_teamScore_str, Opp_teamScore_undo);
    }
    void Opp_CheckLove()
    {
        if (Current_teamScore.size() > 0) {
            if (Current_teamScore.get(Current_teamScore.size() - 1) >= 3) {
                Current_teamScore.add(3);
                Current_teamScore_str.add("45");
            } else {
                Current_teamScore.add(Current_teamScore.get(Current_teamScore.size() - 1));
                Current_teamScore_str.add(Current_teamScore_str.get(Current_teamScore_str.size() - 1));
            }
        } else {
            Current_teamScore.add(0);
            Current_teamScore_str.add("0");
        }
    }

    public void Undo_Score() {
        if (Current_teamScore_undo.size() < 3) {

            Current_teamScore_undo.add(Current_teamScore.get(Current_teamScore.size()-1));
            Current_teamScore.remove(Current_teamScore.get(Current_teamScore.size()-1));

            Current_teamScore_str_undo.add(Current_teamScore_str.get(Current_teamScore_str.size()-1));
            Current_teamScore_str.remove(Current_teamScore_str.get(Current_teamScore_str.size()-1));

            Opp_teamScore_undo.add(Opp_teamScore.get(Opp_teamScore.size()-1));
            Opp_teamScore.remove(Opp_teamScore.get(Opp_teamScore.size()-1));

            Opp_teamScore_str_undo.add(Opp_teamScore_str.get(Opp_teamScore_str.size()-1));
            Opp_teamScore_str.remove(Opp_teamScore_str.get(Opp_teamScore_str.size()-1));
        }
    }
    public void Redo_Score()
    {
        if(Current_teamScore_undo.size() != 0 && Current_teamScore_undo.size() <= 3)
        {
            Current_teamScore.add(Current_teamScore_undo.get(Current_teamScore_undo.size()-1));
            Current_teamScore_undo.remove(Current_teamScore_undo.get(Current_teamScore_undo.size()-1));

            Current_teamScore_str.add(Current_teamScore_str_undo.get(Current_teamScore_str_undo.size()-1));
            Current_teamScore_str_undo.remove(Current_teamScore_str_undo.get(Current_teamScore_str_undo.size()-1));

            Opp_teamScore.add(Opp_teamScore_undo.get(Opp_teamScore_undo.size()-1));
            Opp_teamScore_undo.remove(Opp_teamScore_undo.get(Opp_teamScore_undo.size()-1));

            Opp_teamScore_str.add(Opp_teamScore_str_undo.get(Opp_teamScore_str_undo.size()-1));
            Opp_teamScore_str_undo.remove(Opp_teamScore_str_undo.get(Opp_teamScore_str_undo.size()-1));
        }
    }
}
