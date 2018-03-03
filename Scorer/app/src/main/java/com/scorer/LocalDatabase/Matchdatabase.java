package com.scorer.LocalDatabase;

import android.app.Activity;
import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.scorer.ModalClass.Common;
import com.scorer.ModalClass.MatchModel;
import com.scorer.ModalClass.MatchModel;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Matchdatabase {
    private SQLiteDatabase sqldb;
    private String sqldb_query,sqldb_path;
    private String sqldb_message;
    private String Databasename = "scorer.db";
    private boolean sqldb_available;
    public Matchdatabase(Activity context)
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
    public Matchdatabase() {
    }
    public void CreateDatabase()
    {
        try
        {
            sqldb_message = "";
            if (!Exists())
            {
                sqldb = SQLiteDatabase.openOrCreateDatabase(sqldb_path, null);
                sqldb_query = "CREATE TABLE IF NOT EXISTS Matchdatabase (uniqueid string,matchname string,gameid string,gamename string,currentteam string,oppteam string,currentscore string,oppscore string,currentscore_2 string,oppscore_2 string,currentscore_3 string,oppscore_3 string,scorecount INTEGER,scorepoints string,scorepoints_2 string,scorepoints_3 string,status string,datetime string,PRIMARY KEY(uniqueid,uniqueid));";
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
            android.database.Cursor cursor = sqldb.rawQuery("select * from Matchdatabase", null);
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
    public void AddRecord(MatchModel objabout)
    {
        try
        {
            DeleteRecord(objabout.getUniqueid().toString());
            ContentValues insertValuesy = new ContentValues();
            insertValuesy.put("uniqueid", objabout.getUniqueid());
            insertValuesy.put("matchname", objabout.getMatchname());
            insertValuesy.put("gameid", objabout.getGameid());
            insertValuesy.put("gamename", objabout.getGamename());
            insertValuesy.put("currentteam", objabout.getCurrentteam());
            insertValuesy.put("oppteam", objabout.getOppteam());
            insertValuesy.put("currentscore", objabout.getCurrentscore());
            insertValuesy.put("oppscore", objabout.getOppscore());
            insertValuesy.put("scorepoints", objabout.getScorepoints());
            insertValuesy.put("currentscore_2", objabout.getCurrentscore_2());
            insertValuesy.put("oppscore_2", objabout.getOppscore_2());
            insertValuesy.put("scorepoints_2", objabout.getScorepoints_2());
            insertValuesy.put("currentscore_3", objabout.getCurrentscore_3());
            insertValuesy.put("oppscore_3", objabout.getOppscore_3());
            insertValuesy.put("scorepoints_3", objabout.getScorepoints_3());
            insertValuesy.put("scorecount", objabout.getScore_count());
            insertValuesy.put("datetime", objabout.getDatetime());
            insertValuesy.put("status", objabout.getStatus());
            sqldb.insert("Matchdatabase", null, insertValuesy);
            GetRecords();
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
            android.database.Cursor cursor = sqldb.rawQuery("select * from Matchdatabase where uniqueid='"+uniqueid+"'", null);
            if (cursor != null)
            {
                sqldb.delete("Matchdatabase", "uniqueid=?", new String[] {uniqueid});
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
            android.database.Cursor cursor = sqldb.rawQuery("select * from Matchdatabase", null);
            if (cursor != null)
            {
                sqldb.delete("Matchdatabase", "uniqueid=?", new String[] {""});
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
            android.database.Cursor cursor = sqldb.rawQuery("select * from Matchdatabase", null);
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
    public List<MatchModel> GetRecords()
    {
        List<MatchModel> objabout = new ArrayList<>();
        try
        {
            android.database.Cursor cursor = sqldb.rawQuery("select * from Matchdatabase", null);
            if (cursor != null)
            {
                while(cursor.moveToNext()) {
                    MatchModel objMatchModel = new MatchModel();
                    objMatchModel.setUniqueid(cursor.getString(cursor.getColumnIndex("uniqueid")));
                    objMatchModel.setMatchname(cursor.getString(cursor.getColumnIndex("matchname")));
                    objMatchModel.setGameid(cursor.getString(cursor.getColumnIndex("gameid")));
                    objMatchModel.setGamename(cursor.getString(cursor.getColumnIndex("gamename")));
                    objMatchModel.setCurrentteam(cursor.getString(cursor.getColumnIndex("currentteam")));
                    objMatchModel.setOppteam(cursor.getString(cursor.getColumnIndex("oppteam")));
                    objMatchModel.setCurrentscore(cursor.getString(cursor.getColumnIndex("currentscore")));
                    objMatchModel.setOppscore(cursor.getString(cursor.getColumnIndex("oppscore")));
                    objMatchModel.setScorepoints(cursor.getString(cursor.getColumnIndex("scorepoints")));
                    objMatchModel.setCurrentscore_2(cursor.getString(cursor.getColumnIndex("currentscore_2")));
                    objMatchModel.setOppscore_2(cursor.getString(cursor.getColumnIndex("oppscore_2")));
                    objMatchModel.setScorepoints_2(cursor.getString(cursor.getColumnIndex("scorepoints_2")));
                    objMatchModel.setCurrentscore_3(cursor.getString(cursor.getColumnIndex("currentscore_3")));
                    objMatchModel.setOppscore_3(cursor.getString(cursor.getColumnIndex("oppscore_3")));
                    objMatchModel.setScorepoints_3(cursor.getString(cursor.getColumnIndex("scorepoints_3")));
                    objMatchModel.setDatetime(cursor.getString(cursor.getColumnIndex("datetime")));
                    objMatchModel.setStatus(cursor.getString(cursor.getColumnIndex("status")));
                    objMatchModel.setScore_count(cursor.getInt(cursor.getColumnIndex("scorecount")));
                    objabout.add(objMatchModel);
                }
            }
        }
        catch (Exception ex)
        {
            sqldb_message = ex.getMessage();
        }
        return Reverse(objabout);//Collections.reverse(objabout);
    }
    static List<MatchModel> Reverse(final List<MatchModel> list) {
        final List<MatchModel> result = new ArrayList<>(list);
        Collections.reverse(result);
        return result;
    }
}
