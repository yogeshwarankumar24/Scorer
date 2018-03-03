package com.scorer.LocalDatabase;

import android.app.Activity;
import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.scorer.ModalClass.TeamModel;
import com.scorer.ModalClass.TeamModel;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Teamdatabase {
    private SQLiteDatabase sqldb;
    private String sqldb_query,sqldb_path;
    private String sqldb_message;
    private String Databasename = "scorer.db";
    private boolean sqldb_available;
    public Teamdatabase(Activity context)
    {
        try
        {
            sqldb_message = "";
            sqldb_available = false;
            sqldb_path = context.getFilesDir()+"/databases/";
            File file = new File(sqldb_path);
            file.mkdir();
            CreateFile(file);
        }
        catch (SQLException ex)
        {
            sqldb_message = ex.getMessage();
        }
    }
    public Teamdatabase() {
    }
    public void CreateDatabase()
    {
        try
        {
            sqldb_message = "";
            if (!Exists())
            {
                //String uniqueid, String gameid, String gamename, String teamname, String teamsubscriber, String teamplayer, String status, byte[] teamimage
                sqldb = SQLiteDatabase.openOrCreateDatabase(sqldb_path, null);
                sqldb_query = "CREATE TABLE IF NOT EXISTS Teamdatabase (uniqueid string,gameid string,gamename string,teamname string,teamsubscriber string,teamplayer string,status string,teamimage BYTE,PRIMARY KEY(uniqueid,uniqueid));";
                sqldb.execSQL(sqldb_query);
                sqldb_message = "Database: created";
            }
            else
            {
                sqldb = SQLiteDatabase.openDatabase(sqldb_path, null, SQLiteDatabase.OPEN_READWRITE);
                sqldb_message = "Database: opened";
            }
            sqldb_available = true;
        }
        catch (SQLException ex)
        {
            sqldb_message = ex.getMessage();
        }
    }
    private void CreateFile(File dir) {
        if (!dir.exists()&& dir.mkdirs()){
            sqldb_path += Databasename;
            CreateDatabase();
        }
        if(dir.exists()) {
            File datafile = new File(sqldb_path);
            if (datafile.exists()) {
                sqldb_path += Databasename;
                CreateDatabase();
            }
        }
    }
    public boolean Exists()
    {
        try
        {
            android.database.Cursor cursor = sqldb.rawQuery("select * from Teamdatabase", null);
            return true;
        }
        catch (SQLException ex)
        {
            return false;
        }
        catch (Exception ex)
        {
            return false;
        }
    }
    public void AddRecord(TeamModel objabout)
    {
        try
        {
            
            DeleteRecord(objabout.getUniqueid().toString());
            ContentValues insertValuesy = new ContentValues();
            insertValuesy.put("uniqueid", objabout.getUniqueid());
            insertValuesy.put("gameid", objabout.getGameid());
            insertValuesy.put("gamename", objabout.getGamename());
            insertValuesy.put("teamname", objabout.getTeamname());
            insertValuesy.put("teamsubscriber", objabout.getTeamsubscriber());
            insertValuesy.put("teamplayer", objabout.getTeamplayer());
            insertValuesy.put("status", objabout.getStatus());
            insertValuesy.put("teamimage", objabout.getTeamimage());
            sqldb.insert("Teamdatabase", null, insertValuesy);
        }
        catch (SQLException ex)
        {
            sqldb_message = ex.getMessage();
        }
    }
    public void DeleteRecord(String uniqueid)
    {
        try
        {
            android.database.Cursor cursor = sqldb.rawQuery("select * from Teamdatabase where uniqueid='"+uniqueid+"'", null);
            if (cursor != null)
            {
                sqldb.delete("Teamdatabase", "uniqueid=?", new String[] {uniqueid});
                sqldb_message = "Record Deleted";
            }
        }
        catch (NullPointerException ex)
        {
            sqldb_message = ex.getMessage();
        }
        catch (SQLException ex)
        {
            sqldb_message = ex.getMessage();
        }
    }
    public void DeleteRecord()
    {
        try
        {
            android.database.Cursor cursor = sqldb.rawQuery("select * from Teamdatabase", null);
            if (cursor != null)
            {
                sqldb.delete("Teamdatabase", "uniqueid=?", new String[] {""});
                sqldb_message = "Record Deleted";
            }
        }
        catch (NullPointerException ex)
        {
            sqldb_message = ex.getMessage();
        }
        catch (SQLException ex)
        {
            sqldb_message = ex.getMessage();
        }
    }
    public String Get_uniqueid()
    {
        try
        {
            android.database.Cursor cursor = sqldb.rawQuery("select * from Teamdatabase", null);
            if (cursor != null)
            {
                if(cursor.getCount()>0) {
                    return String.valueOf(cursor.getCount()+1);
                } else {
                    return "1";
                }
            }
        }
        catch (NullPointerException ex)
        {
            return "1";
        }
        catch (SQLException ex)
        {
            return "1";
        }
        return "1";
    }
    public List<TeamModel> GetRecords()
    {
        List<TeamModel> objabout = new ArrayList<>();
        try
        {
            android.database.Cursor cursor = sqldb.rawQuery("select * from Teamdatabase", null);
            if (cursor != null)
            {
                while(cursor.moveToNext()) {
                    TeamModel objTeamModel = new TeamModel();
                    objTeamModel.setUniqueid(cursor.getString(cursor.getColumnIndex("uniqueid")));
                    objTeamModel.setGameid(cursor.getString(cursor.getColumnIndex("gameid")));
                    objTeamModel.setGamename(cursor.getString(cursor.getColumnIndex("gamename")));
                    objTeamModel.setTeamname(cursor.getString(cursor.getColumnIndex("teamname")));
                    objTeamModel.setTeamsubscriber(cursor.getString(cursor.getColumnIndex("teamsubscriber")));
                    objTeamModel.setTeamplayer(cursor.getString(cursor.getColumnIndex("teamplayer")));
                    objTeamModel.setTeamimage(cursor.getBlob(cursor.getColumnIndex("teamimage")));
                    objTeamModel.setStatus(cursor.getString(cursor.getColumnIndex("status")));
                    objabout.add(objTeamModel);
                }
            }
        }
        catch (Exception ex)
        {
            sqldb_message = ex.getMessage();
        }
        return Reverse(objabout);//Collections.reverse(objabout);
    }
    public List<TeamModel> GetRecords(String Gameid)
    {
        List<TeamModel> objabout = new ArrayList<>();
        try
        {
            android.database.Cursor cursor = sqldb.rawQuery("select * from Teamdatabase where gameid='"+Gameid+"'", null);
            if (cursor != null)
            {
                while(cursor.moveToNext()) {
                    TeamModel objTeamModel = new TeamModel();
                    objTeamModel.setUniqueid(cursor.getString(cursor.getColumnIndex("uniqueid")));
                    objTeamModel.setGameid(cursor.getString(cursor.getColumnIndex("gameid")));
                    objTeamModel.setGamename(cursor.getString(cursor.getColumnIndex("gamename")));
                    objTeamModel.setTeamname(cursor.getString(cursor.getColumnIndex("teamname")));
                    objTeamModel.setTeamsubscriber(cursor.getString(cursor.getColumnIndex("teamsubscriber")));
                    objTeamModel.setTeamplayer(cursor.getString(cursor.getColumnIndex("teamplayer")));
                    objTeamModel.setTeamimage(cursor.getBlob(cursor.getColumnIndex("teamimage")));
                    objTeamModel.setStatus(cursor.getString(cursor.getColumnIndex("status")));
                    objabout.add(objTeamModel);
                }
            }
        }
        catch (Exception ex)
        {
            sqldb_message = ex.getMessage();
        }
        return Reverse(objabout);//Collections.reverse(objabout);
    }
    public TeamModel GetRecords_id(String Gameid)
    {
        TeamModel objTeamModel = new TeamModel();
        try
        {
            android.database.Cursor cursor = sqldb.rawQuery("select * from Teamdatabase where uniqueid='"+Gameid+"'", null);
            if (cursor != null)
            {
                cursor.moveToFirst();
                objTeamModel.setUniqueid(cursor.getString(cursor.getColumnIndex("uniqueid")));
                objTeamModel.setGameid(cursor.getString(cursor.getColumnIndex("gameid")));
                objTeamModel.setGamename(cursor.getString(cursor.getColumnIndex("gamename")));
                objTeamModel.setTeamname(cursor.getString(cursor.getColumnIndex("teamname")));
                objTeamModel.setTeamsubscriber(cursor.getString(cursor.getColumnIndex("teamsubscriber")));
                objTeamModel.setTeamplayer(cursor.getString(cursor.getColumnIndex("teamplayer")));
                objTeamModel.setTeamimage(cursor.getBlob(cursor.getColumnIndex("teamimage")));
                objTeamModel.setStatus(cursor.getString(cursor.getColumnIndex("status")));
            }
        }
        catch (Exception ex)
        {
            sqldb_message = ex.getMessage();
        }
        return objTeamModel;
    }
    static List<TeamModel> Reverse(final List<TeamModel> list) {
        final List<TeamModel> result = new ArrayList<>(list);
        Collections.reverse(result);
        return result;
    }
}
