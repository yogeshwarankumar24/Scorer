package com.scorer.GamesController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android on 11/9/2017.
 */

public class Tennis2 {

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
    public Tennis2()
    {
        Add_Current_teamScore();
        Add_Opp_teamScore();
    }
    public void Add_Current_teamScore()
    {
        Current_teamScore_undo = new ArrayList<>();
        if(Current_teamScore.size() > 0) {
            switch (Current_teamScore.get(Current_teamScore.size() - 1)) {
                case 0:
                {
                    Current_teamScore.add(1);
                    Current_teamScore_str.add("15");
                    Current_CheckLove();
                    break;
                }
                case 1:
                {
                    Current_teamScore.add(2);
                    Current_teamScore_str.add("30");
                    Current_CheckLove();
                    break;
                }
                case 2:
                {
                    Current_teamScore.add(3);
                    Current_teamScore_str.add("45");
                    Current_CheckLove();
                    break;
                }
                case 3:
                {
                    if(Opp_teamScore.get(Opp_teamScore.size()-1) >= 3)
                    {
                        Current_teamScore.add(4);
                        Current_teamScore_str.add("Deuce");
                        Opp_teamScore.add(3);
                        Opp_teamScore_str.add("45");
                    } else  {
                        Current_teamScore.add(5);
                        Current_teamScore_str.add("Love");
                        Current_CheckLove();
                    }
                    break;
                }
                case 4:
                {
                    Current_teamScore.add(5);
                    Current_teamScore_str.add("Love");
                    Current_CheckLove();
                    break;
                }
                case 5:
                {
                    Current_teamScore.add(6);
                    Current_teamScore_str.add("Won");
                    Opp_teamScore.add(Opp_teamScore.get(Opp_teamScore.size() - 1));
                    Opp_teamScore_str.add("Lose");
                    break;
                }
            }
        } else {
            Current_teamScore.add(0);
            Current_teamScore_str.add("0");
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
        Current_teamScore_undo = new ArrayList<>();
        if(Opp_teamScore.size() > 0) {
            switch (Opp_teamScore.get(Opp_teamScore.size() - 1)) {
                case 0:
                {
                    Opp_teamScore.add(1);
                    Opp_teamScore_str.add("15");

                    break;
                }
                case 1:
                {
                    Opp_teamScore.add(2);
                    Opp_teamScore_str.add("30");
                    Opp_CheckLove();
                    break;
                }
                case 2:
                {
                    Opp_teamScore.add(3);
                    Opp_teamScore_str.add("45");
                    Opp_CheckLove();
                    break;
                }
                case 3:
                {
                    if(Current_teamScore.get(Current_teamScore.size()-1) >= 3)
                    {
                        Opp_teamScore.add(4);
                        Opp_teamScore_str.add("Deuce");
                        Current_teamScore.add(3);
                        Current_teamScore_str.add("45");
                    } else {
                        Opp_teamScore.add(5);
                        Opp_teamScore_str.add("Love");
                        Opp_CheckLove();
                    }
                    break;
                }
                case 4:
                {
                    Opp_teamScore.add(5);
                    Opp_teamScore_str.add("Love");
                    Opp_CheckLove();
                    break;
                }
                case 5:
                {
                    Opp_teamScore.add(6);
                    Opp_teamScore_str.add("Won");
                    Current_teamScore.add(Current_teamScore.get(Current_teamScore.size() - 1));
                    Current_teamScore_str.add("Lose");
                    break;
                }
            }
        } else {
            Opp_teamScore.add(0);
            Opp_teamScore_str.add("0");
        }
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
