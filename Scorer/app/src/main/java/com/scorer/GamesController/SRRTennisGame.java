package com.scorer.GamesController;

import java.util.HashMap;

public class SRRTennisGame {
    int team1Point = 0;
    int team2Point = 0;
    boolean completed = false;
    boolean tieBreaker = false;

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isTieBreaker() {
        return tieBreaker;
    }

    public void setTieBreaker(boolean tieBreaker) {
        this.tieBreaker = tieBreaker;
    }

    static HashMap<Integer, String> pointMap = null;

    static {
        pointMap = new HashMap<Integer, String>();
        pointMap.put(0, "Love");
        pointMap.put(1, "15");
        pointMap.put(2, "30");
        pointMap.put(3, "45");
        pointMap.put(4, "Deuce");
        pointMap.put(5, "Adv");
        pointMap.put(6, "Win");
    }

    public int getTeam1Point() {
        return team1Point;
    }

    public void setTeam1Point(int team1Point) {
        this.team1Point = team1Point;
    }

    public int getTeam2Point() {
        return team2Point;
    }

    public void setTeam2Point(int team2Point) {
        this.team2Point = team2Point;
    }

    public String getTeam1PointLabel() {
        if(this.isTieBreaker()) {
            return ""+this.team1Point;
        } else {
            return pointMap.get(this.team1Point);
        }
    }
    public String getTeam2PointLabel() {
        if(this.isTieBreaker()) {
            return ""+this.team2Point;
        } else {
            return pointMap.get(this.team2Point);
        }
    }

    public void addTeam1Point(boolean tieBreaker){
        int[] result = addPoint(team1Point, team2Point, tieBreaker);
        team1Point=result[0];
        team2Point=result[1];
    }
    public void addTeam2Point(boolean tieBreaker){
        int[] result = addPoint(team2Point, team1Point, tieBreaker);
        team2Point=result[0];
        team1Point=result[1];
    }

    @Override
    public String toString() {
        if(this.isTieBreaker()) {
            return getTeam1Point() + " - " + getTeam2Point();
        } else {
            return getTeam1PointLabel() + " - " + getTeam2PointLabel();
        }
    }

    public int[] addPoint(Integer refPoint, Integer oppPoint, boolean tieBreaker){

        if(tieBreaker){
            setTeam1Point(refPoint++);
        } else {
            if (refPoint == 0 || refPoint == 1) {
                setTeam1Point(refPoint++);
            } else if (refPoint == 2) {
                if (oppPoint < 3) {
                    refPoint++;
                    ;
                }
                // If Opponent is in duece, this will not happen this block because this 45 (3) block, this will only happen in Duece (4) block
                else if (oppPoint == 3) {
                    refPoint = 4;
                    oppPoint = 4;
                }
            } else if (refPoint == 3) {
                if (oppPoint < 3) {
                    refPoint = 6;
                }
                // If Opponent is in duece, this will not happen this block because this 45 (3) block, this will only happen in Duece (4) block
                else if (oppPoint == 3) {
                    refPoint = 4;
                    oppPoint = 4;
                }
                // If Opponent is in duece, this will not happen this block because this 45 (3) block, this will only happen in Duece (4) block
                else if (oppPoint == 4) {
                    refPoint = 5;
                    oppPoint = 3;

                }
                // If Opponent is in Advantage
                else if (oppPoint == 4) {
                    refPoint = 4;
                    oppPoint = 4;
                }
                // if opponent is in Advantage
                else if (oppPoint == 5) {
                    refPoint = 4;
                    oppPoint = 4;
                } else {
                    // this will not happen, control will come to this block with current score Adv (5) or more.
                }
            } else if (refPoint == 4) {
                refPoint = 5;
                oppPoint = 3;
            } else if (refPoint == 5) {
                refPoint = 6;
            }
        }
        //System.out.println(refPoint + "-"+oppPoint);

        return new int[]{refPoint, oppPoint};


    }

    /*public int[] addTieBreakerPoint(Integer refPoint, Integer oppPoint){
        refPoint++;
        return new int[]{refPoint, oppPoint};
    }*/



}