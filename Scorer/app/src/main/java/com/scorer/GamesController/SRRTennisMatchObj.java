package com.scorer.GamesController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Vector;

/**
 * Created by mathanj on 2/11/18.
 */

/**
 * @author mathanj
 *
 */
public class SRRTennisMatchObj {
    Vector<SRRTennisSet> srrTennisSetVec=null;
    int team1MatchPoint = 0;
    int team2MatchPoint = 0;
    // Two formats of matches are 3 sets and 5 sets.
    // As soon as one team gets the winning points, match will be consider as completed.
    // So winning points for 3 set format is 2
    // and winning points for 5 set format is 3
    // User screen should show "5 sets (3 wins)" or "3 sets (2 wins)"
    int winningPoints = 2;
    int winner = 0;
    boolean completed = false;


    public SRRTennisMatchObj(){
        srrTennisSetVec = new Vector<SRRTennisSet>();
    }

    public Vector<SRRTennisSet> getSrrTennisSetVec() {
        return srrTennisSetVec;
    }

    public int getTeam1MatchPoint() {
        return team1MatchPoint;
    }

    public void setTeam1MatchPoint(int team1MatchPoint) {
        this.team1MatchPoint = team1MatchPoint;
    }

    public int getTeam2MatchPoint() {
        return team2MatchPoint;
    }

    public void setTeam2MatchPoint(int team2MatchPoint) {
        this.team2MatchPoint = team2MatchPoint;
    }

    public void setSrrTennisSetVec(Vector<SRRTennisSet> srrTennisSetVec) {
        this.srrTennisSetVec = srrTennisSetVec;
    }




    public int getWinningPoints() {
        return winningPoints;
    }

    public void setWinningPoints(int winningPoints) {
        if(winningPoints==2 || winningPoints==3) {
            this.winningPoints = winningPoints;
        }
    }

    public int getWinner() {
        return winner;
    }

    public void setWinner(int winner) {
        if (winner==1 || winner==2) {
            this.setCompleted(true);
        }
        this.winner = winner;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public SRRTennisSet getCurrentSet(){
        if(getSrrTennisSetVec() !=null
                && getSrrTennisSetVec().size()==0){
            getSrrTennisSetVec().add(new SRRTennisSet());
        } else if (getSrrTennisSetVec().get(getSrrTennisSetVec().size()-1).isCompleted()){
            if(!this.isCompleted()) {
                getSrrTennisSetVec().add(new SRRTennisSet());
            }
        }
        return getSrrTennisSetVec().get(getSrrTennisSetVec().size()-1);
    }

    public void addTeam1Point(){

        getCurrentSet().addTeam1Point();
        //check if there is a win
        checkWinner();

    }
    public void addTeam2Point(){
        getCurrentSet().addTeam2Point();
        //check if there is a win
        checkWinner();
    }

    // This method will return
    // 0 - if match is still going on
    // 1 - if team one is a winner
    // 2 - if team two is a winner
    public int checkWinner(){

        if (getCurrentSet().getTeam1SetPoint() == 7) {
            getCurrentSet().setCompleted(true);
            team1MatchPoint++;
        } else if (getCurrentSet().getTeam2SetPoint() == 7) {
            getCurrentSet().setCompleted(true);
            team2MatchPoint++;
        } else if (getCurrentSet().getTeam1SetPoint() == 6 || getCurrentSet().getTeam2SetPoint() == 6) {
            if (getCurrentSet().getTeam1SetPoint() == getCurrentSet().getTeam2SetPoint()) {
                // call tie-breaker
                getCurrentSet().startTieBreaker();
            } else if (getCurrentSet().getTeam1SetPoint() - getCurrentSet().getTeam2SetPoint() >= 2) {
                getCurrentSet().setCompleted(true);
                team1MatchPoint++;
            } else if (getCurrentSet().getTeam2SetPoint() - getCurrentSet().getTeam1SetPoint() >= 2) {
                getCurrentSet().setCompleted(true);
                team2MatchPoint++;
            }
        }


        if(this.team1MatchPoint >= this.winningPoints){
            System.out.println("Team 1 is winner");
            this.setWinner(1);
            this.completed=true;
        }
        if(this.team2MatchPoint >= this.winningPoints){
            System.out.println("Team 2 is winner");
            this.setWinner(2);
            this.completed=true;
        }
        return this.getWinner();

    }

    @Override
    public String toString() {
        StringBuffer tempStr = new StringBuffer("");
        tempStr.append("Match: ["+getTeam1MatchPoint()+"-"+getTeam2MatchPoint()+"] - ");
        for (int i=0; i<srrTennisSetVec.size()-1; i++){
            tempStr.append("Set "+(i+1)+":("+srrTennisSetVec.get(i).getTeam1SetPoint()+"-"+srrTennisSetVec.get(i).getTeam2SetPoint()+")");
        }
        tempStr.append(getCurrentSet().toString());
        return tempStr.toString();
    }

    public static void main(String[] arg) throws Exception{

        /*int a=1;
        int b=0;

        System.out.println("Input is 1, output is "+Math.abs(1-a));
        System.out.println("Input is 0, output is  "+Math.abs(1-b));
        */
        System.out.println("in main");
        SRRTennisMatchObj match = new SRRTennisMatchObj();
        System.out.println("Score: "+match);
        InputStreamReader input  = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader(input);;

        int c = 1;
        while (c != 99 ) {
            System.out.print("      Who?  ");
            c = Integer.parseInt(bufferedReader.readLine());
            if(c == 1 || c == 2) {
                //System.out.print("c="+c);
                if (c==1) {
                    match.addTeam1Point();
                } else if (c==2){
                    match.addTeam2Point();
                }
                System.out.print("Score: "+match);
            } else {
                System.out.print("Unknown Team !!!");
            }
        }
        System.out.print("Score: "+match);
    }
}


