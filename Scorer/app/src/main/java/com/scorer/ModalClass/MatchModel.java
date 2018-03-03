package com.scorer.ModalClass;


public class MatchModel {

    private String uniqueid;
    private String matchname;
    private String gameid;
    private String gamename;
    private String currentteam;
    private String oppteam;
    private String currentscore;
    private String oppscore;
    private String scorepoints;

    public String getCurrentscore_2() {
        return currentscore_2;
    }

    public void setCurrentscore_2(String currentscore_2) {
        this.currentscore_2 = currentscore_2;
    }

    public String getOppscore_2() {
        return oppscore_2;
    }

    public void setOppscore_2(String oppscore_2) {
        this.oppscore_2 = oppscore_2;
    }

    public String getScorepoints_2() {
        return scorepoints_2;
    }

    public void setScorepoints_2(String scorepoints_2) {
        this.scorepoints_2 = scorepoints_2;
    }

    public String getCurrentscore_3() {
        return currentscore_3;
    }

    public void setCurrentscore_3(String currentscore_3) {
        this.currentscore_3 = currentscore_3;
    }

    public String getOppscore_3() {
        return oppscore_3;
    }

    public void setOppscore_3(String oppscore_3) {
        this.oppscore_3 = oppscore_3;
    }

    public String getScorepoints_3() {
        return scorepoints_3;
    }

    public void setScorepoints_3(String scorepoints_3) {
        this.scorepoints_3 = scorepoints_3;
    }

    public int getScore_count() {
        return score_count;
    }

    public void setScore_count(int score_count) {
        this.score_count = score_count;
    }

    private String currentscore_2;
    private String oppscore_2;
    private String scorepoints_2;
    private String currentscore_3;
    private String oppscore_3;
    private String scorepoints_3;
    private int score_count;
    private String datetime;
    private String status;

    public MatchModel() {
    }

    public MatchModel(String uniqueid,String matchname, String gameid, String gamename, String currentteam, String oppteam, String currentscore,String oppscore,String scorepoints,String datetime,String status,String currentscore_2,String oppscore_2,String currentscore_3,String oppscore_3,int score_count) {
        this.uniqueid = uniqueid;
        this.matchname = matchname;
        this.gameid = gameid;
        this.gamename = gamename;
        this.currentteam = currentteam;
        this.oppteam = oppteam;
        this.currentscore = currentscore;
        this.oppscore = oppscore;
        this.scorepoints = scorepoints;
        this.datetime = datetime;
        this.status = status;
        this.currentscore_2 = currentscore_2;
        this.oppscore_2 = oppscore_2;
        this.currentscore_3 = currentscore_3;
        this.oppscore_3 = oppscore_3;
        this.score_count = score_count;
    }
    public MatchModel(MatchModel objmodel) {
        this.uniqueid = objmodel.getUniqueid();
        this.matchname = objmodel.getMatchname();
        this.gameid = objmodel.getGameid();
        this.gamename = objmodel.getGamename();
        this.currentteam = objmodel.getCurrentteam();
        this.oppteam = objmodel.getOppteam();
        this.currentscore = objmodel.getCurrentscore();
        this.oppscore = objmodel.getOppscore();
        this.scorepoints = objmodel.getScorepoints();
        this.datetime = objmodel.getDatetime();
        this.status = objmodel.getStatus();
        this.currentscore_2 = objmodel.getCurrentscore_2();
        this.oppscore_2 = objmodel.getOppscore_2();
        this.currentscore_3 = objmodel.getCurrentscore_3();
        this.oppscore_3 = objmodel.getOppscore_3();
        this.score_count = objmodel.getScore_count();
    }

    public String getMatchname() {
        return matchname;
    }

    public void setMatchname(String matchname) {
        this.matchname = matchname;
    }

    public String getUniqueid() {
        return uniqueid;
    }

    public void setUniqueid(String uniqueid) {
        this.uniqueid = uniqueid;
    }

    public String getGameid() {
        return gameid;
    }

    public void setGameid(String gameid) {
        this.gameid = gameid;
    }

    public String getGamename() {
        return gamename;
    }

    public void setGamename(String gamename) {
        this.gamename = gamename;
    }

    public String getCurrentteam() {
        return currentteam;
    }

    public void setCurrentteam(String currentteam) {
        this.currentteam = currentteam;
    }

    public String getOppteam() {
        return oppteam;
    }

    public void setOppteam(String oppteam) {
        this.oppteam = oppteam;
    }

    public String getCurrentscore() {
        return currentscore;
    }

    public void setCurrentscore(String currentscore) {
        this.currentscore = currentscore;
    }

    public String getOppscore() {
        return oppscore;
    }

    public void setOppscore(String oppscore) {
        this.oppscore = oppscore;
    }

    public String getScorepoints() {
        return scorepoints;
    }

    public void setScorepoints(String scorepoints) {
        this.scorepoints = scorepoints;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
