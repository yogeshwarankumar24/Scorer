package com.scorer.ModalClass;

/**
 * Created by impowersolutionspvtltd on 31/05/17.
 */

public class PlayerModel {


    private String uniqueid;
    private String playername;
    private String gameid;
    private String gamename;
    private String playeremail;
    private byte[] playerimage;
    private String playerposition;
    private String status;

    public PlayerModel() {
    }

    public PlayerModel(String uniqueid, String gameid, String gamename, String playername, String playeremail, String playerposition,String status,byte[] playerimage) {
        this.uniqueid = uniqueid;
        this.gameid = gameid;
        this.gamename = gamename;
        this.playerimage = playerimage;
        this.playername = playername;
        this.playeremail = playeremail;
        this.playerposition = playerposition;
        this.status = status;
    }
    public PlayerModel(PlayerModel objmodel) {
        this.uniqueid = objmodel.getUniqueid();
        this.gameid = objmodel.getGameid();
        this.gamename = objmodel.getGamename();
        this.playername = objmodel.getPlayername();
        this.playeremail = objmodel.getPlayeremail();
        this.playerposition = objmodel.getPlayerposition();
        this.status = objmodel.getStatus();
        this.playerimage = objmodel.getPlayerimage();
    }

    public String getUniqueid() {
        return uniqueid;
    }

    public void setUniqueid(String uniqueid) {
        this.uniqueid = uniqueid;
    }

    public String getPlayername() {
        return playername;
    }

    public void setPlayername(String playername) {
        this.playername = playername;
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

    public String getPlayeremail() {
        return playeremail;
    }

    public void setPlayeremail(String playeremail) {
        this.playeremail = playeremail;
    }

    public String getPlayerposition() {
        return playerposition;
    }

    public void setPlayerposition(String playerposition) {
        this.playerposition = playerposition;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public byte[] getPlayerimage() {
        return playerimage;
    }

    public void setPlayerimage(byte[] playerimage) {
        this.playerimage = playerimage;
    }

}
