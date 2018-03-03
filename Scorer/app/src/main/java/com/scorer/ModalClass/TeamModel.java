package com.scorer.ModalClass;

/**
 * Created by impowersolutionspvtltd on 31/05/17.
 */

public class TeamModel {

    private String uniqueid;
    private String teamname;
    private String gameid;
    private String gamename;
    private byte[] teamimage;
    private String teamplayer;
    private String teamsubscriber;
    private String status;

    public TeamModel() {
    }

    public TeamModel(String uniqueid, String gameid, String gamename, String teamname, String teamsubscriber, String teamplayer, String status, byte[] teamimage) {
        this.uniqueid = uniqueid;
        this.gameid = gameid;
        this.gamename = gamename;
        this.teamimage = teamimage;
        this.teamname = teamname;
        this.teamsubscriber = teamsubscriber;
        this.teamplayer = teamplayer;
        this.status = status;
    }
    public TeamModel(TeamModel objmodel) {
        this.uniqueid = objmodel.getUniqueid();
        this.gameid = objmodel.getGameid();
        this.gamename = objmodel.getGamename();
        this.teamname = objmodel.getTeamname();
        this.teamsubscriber = objmodel.getTeamsubscriber();
        this.teamplayer = objmodel.getTeamplayer();
        this.status = objmodel.getStatus();
        this.teamimage = objmodel.getTeamimage();
    }


    public String getUniqueid() {
        return uniqueid;
    }

    public void setUniqueid(String uniqueid) {
        this.uniqueid = uniqueid;
    }

    public String getTeamname() {
        return teamname;
    }

    public void setTeamname(String teamname) {
        this.teamname = teamname;
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

    public byte[] getTeamimage() {
        return teamimage;
    }

    public void setTeamimage(byte[] teamimage) {
        this.teamimage = teamimage;
    }

    public String getTeamplayer() {
        return teamplayer;
    }

    public void setTeamplayer(String teamplayer) {
        this.teamplayer = teamplayer;
    }

    public String getTeamsubscriber() {
        return teamsubscriber;
    }

    public void setTeamsubscriber(String teamsubscriber) {
        this.teamsubscriber = teamsubscriber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
