package com.scorer.GamesController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android on 11/9/2017.
 */

public class Pingpong {

    public String Current_team_1 = "";
    public String Opp_team_1 = "";
    public String Current_point_1 = "";
    public String Opp_point_1 = "";

    public String Current_team_2 = "";
    public String Opp_team_2 = "";
    public String Current_point_2 = "";
    public String Opp_point_2 = "";

    public int Current_round;

    public int End_Score = 11;

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
    public Pingpong()
    {
        Add_Current_teamScore();
        Add_Opp_teamScore();
    }
    public void Add_Current_teamScore()
    {
        Current_teamScore_undo = new ArrayList<>();
        if(Current_teamScore.size() > 0) {
            int Currentscore = Current_teamScore.get(Current_teamScore.size() - 1);
            int Opponentscore = Opp_teamScore.get(Opp_teamScore.size() - 1);
            if(Currentscore >= End_Score)
            {
                if(End_Score+1 == Currentscore) {
                    Current_teamScore.add(Currentscore + 1);
                    Current_teamScore_str.add("Love");
                    Current_CheckLove();
                }
                else if(End_Score+2 == Currentscore)
                {
                    Current_teamScore.add(Currentscore);
                    Current_teamScore_str.add("Won");
                    Opp_teamScore.add(Opp_teamScore.get(Opp_teamScore.size() - 1));
                    Opp_teamScore_str.add("Lose");
                }
                else {
                    Current_teamScore.add(Currentscore + 1);
                    Current_teamScore_str.add("Deuce");
                    if(Opponentscore >= End_Score) {
                        Opp_teamScore.add(End_Score);
                        Opp_teamScore_str.add(String.valueOf(End_Score));
                    } else {
                        Current_CheckLove();
                    }
                }
            } else {
                Current_teamScore.add(Currentscore + 1);
                Current_teamScore_str.add(String.valueOf(Currentscore + 1));
                Current_CheckLove();
            }
        } else {
            Current_teamScore.add(0);
            Current_teamScore_str.add("0");
        }

    }
    void Current_CheckLove() {
        if (Opp_teamScore.size() > 0) {
            Opp_teamScore.add(Opp_teamScore.get(Opp_teamScore.size() - 1));
            Opp_teamScore_str.add(Opp_teamScore_str.get(Opp_teamScore_str.size() - 1));
        } else {
            Opp_teamScore.add(0);
            Opp_teamScore_str.add("0");
        }
    }
    public void Add_Opp_teamScore()
    {
        Current_teamScore_undo = new ArrayList<>();
        if(Opp_teamScore.size() > 0) {
            int Currentscore = (Opp_teamScore.get(Opp_teamScore.size() - 1));
            int Opponentscore = Current_teamScore.get(Current_teamScore.size() - 1);
            if(Currentscore >= End_Score) {
                if(End_Score+1 == Currentscore) {
                    Opp_teamScore.add(Currentscore + 1);
                    Opp_teamScore_str.add("Love");
                    Current_CheckLove();
                }
                else if(End_Score+2 == Currentscore)
                {
                    Opp_teamScore.add(Currentscore);
                    Opp_teamScore_str.add("Won");
                    Current_teamScore.add(Current_teamScore.get(Current_teamScore.size() - 1));
                    Current_teamScore_str.add("Lose");
                }
                else {
                    Opp_teamScore.add(Currentscore+1);
                    Opp_teamScore_str.add("Deuce");
                    if(Opponentscore >= End_Score) {
                        Current_teamScore.add(End_Score);
                        Current_teamScore_str.add(String.valueOf(End_Score));
                    } else {
                        Current_CheckLove();
                    }
                }
            } else {
                Opp_teamScore.add(Currentscore + 1);
                Opp_teamScore_str.add(String.valueOf(Currentscore + 1));
                Current_CheckLove();
            }
        } else {
            Opp_teamScore.add(0);
            Opp_teamScore_str.add("0");
        }
        Opp_CheckLove();
    }
    void Opp_CheckLove() {
        if (Current_teamScore.size() > 0) {
            Current_teamScore.add(Current_teamScore.get(Current_teamScore.size() - 1));
            Current_teamScore_str.add(Current_teamScore_str.get(Current_teamScore_str.size() - 1));
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
