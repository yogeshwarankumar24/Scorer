package com.scorer.ModalClass;


public class TennisModel {

    private String uniqueid;
    private String matchname;
    private String gameid;
    private String gamename;
    private String currentteam;
    private String oppteam;
    private String datetime;
    private String status;
    private String jsonScore;

    public TennisModel() {
    }

    public TennisModel(TennisModel objmodel) {
        this.uniqueid = objmodel.getUniqueid();
        this.matchname = objmodel.getMatchname();
        this.gameid = objmodel.getGameid();
        this.gamename = objmodel.getGamename();
        this.currentteam = objmodel.getCurrentteam();
        this.oppteam = objmodel.getOppteam();
        this.datetime = objmodel.getDatetime();
        this.status = objmodel.getStatus();
        this.jsonScore = objmodel.getJsonScore();
    }

    public String getJsonScore() {
        return jsonScore;
    }

    public void setJsonScore(String jsonScore) {
        this.jsonScore = jsonScore;
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
