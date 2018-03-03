package com.scorer.GamesController;
import java.util.Vector;

public class SRRTennisSet {
    int team1SetPoint = 0;
    int team2SetPoint = 0;
    boolean completed = false;
    boolean tieBreaker = false;

    Vector<SRRTennisGame> srrTennisGameVec=null;
    //SRRTennisGame srrTennisTieBreakerGame=null;

    public SRRTennisSet(){
        srrTennisGameVec = new Vector<SRRTennisGame>();
        //srrTennisTieBreakerGame = null;
    }

    public Vector<SRRTennisGame> getSrrTennisGameVec() {
        return srrTennisGameVec;
    }

    public void setSrrTennisGameVec(Vector<SRRTennisGame> srrTennisGameVec) {
        this.srrTennisGameVec = srrTennisGameVec;
    }

    /*public SRRTennisGame getSrrTennisTieBreakerGame() {
        return srrTennisTieBreakerGame;
    }

    public void setSrrTennisTieBreakerGame(SRRTennisGame srrTennisTieBreakerGame) {
        this.srrTennisTieBreakerGame = srrTennisTieBreakerGame;
    }*/

    public boolean isTieBreaker() {
        return tieBreaker;
    }

    public void setTieBreaker(boolean tieBreaker) {
        this.tieBreaker = tieBreaker;
    }

    public SRRTennisGame getCurrentGame(){

        if(getSrrTennisGameVec() !=null
                && getSrrTennisGameVec().size()==0){
            getSrrTennisGameVec().add(new SRRTennisGame());
        } else {
            if (getSrrTennisGameVec().get(getSrrTennisGameVec().size()-1).isCompleted()) {
                if(!this.isCompleted()) {
                    getSrrTennisGameVec().add(new SRRTennisGame());
                }
            }
        }
        return getSrrTennisGameVec().get(getSrrTennisGameVec().size()-1);
    }

    public int getTeam1SetPoint() {
        return team1SetPoint;
    }

    public void setTeam1SetPoint(int team1SetPoint) {
        this.team1SetPoint = team1SetPoint;
    }

    public int getTeam2SetPoint() {
        return team2SetPoint;
    }

    public void setTeam2SetPoint(int team2SetPoint) {
        this.team2SetPoint = team2SetPoint;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        if(!this.completed) { // Once it is completed, this attributed cannot be changed.
            this.completed = completed;
        }
    }

    public int getWinner(){
        if(this.completed) {
            if(this.getTeam1SetPoint() > this.getTeam2SetPoint()){
                return 1;
            } else {
                return 2;
            }
        } else {
            return 0;
        }
    }

    public void addTeam1Point(){
        SRRTennisGame game =  getCurrentGame();
        game.addTeam1Point(this.isTieBreaker());
        if(this.isTieBreaker()){
            if (game.getTeam1Point() >= 6) {
                if(game.getTeam1Point() - game.getTeam2Point() > 1){
                    team1SetPoint++;
                    game.setCompleted(true);
                }
            }
        } else if (game.getTeam1Point() == 6) {
            team1SetPoint++;
            game.setCompleted(true);
        }
    }
    public void addTeam2Point(){
        SRRTennisGame game =  getCurrentGame();
        game.addTeam2Point(this.isTieBreaker());
        if(this.isTieBreaker()){
            if (game.getTeam2Point() >= 6) {
                if(game.getTeam2Point() - game.getTeam1Point() > 1){
                    team2SetPoint++;
                    game.setCompleted(true);
                }
            }

        } else if (game.getTeam2Point() == 6) {
            team2SetPoint++;
            game.setCompleted(true);
        }
    }

    public void startTieBreaker(){
        this.tieBreaker=true;
        getCurrentGame().setTieBreaker(true);
    }

    @Override
    public String toString() {

        return "("+getTeam1SetPoint()+" - "+getTeam2SetPoint()+"):"+getCurrentGame().toString();
    }
}