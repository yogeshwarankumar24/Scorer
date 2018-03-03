package com.scorer.LocalDatabase;

import android.app.Activity;
import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.scorer.ModalClass.TennisModel;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Tennisdatabase {
    private SQLiteDatabase sqldb;
    private String sqldb_query,sqldb_path;
    private String sqldb_message;
    private String Databasename = "scorer.db";
    private boolean sqldb_available;
    public Tennisdatabase(Activity context)
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
    public Tennisdatabase() {
    }
    public void CreateDatabase()
    {
        try
        {
            sqldb_message = "";
            if (!Exists())
            {
                sqldb = SQLiteDatabase.openOrCreateDatabase(sqldb_path, null);
                sqldb_query = "CREATE TABLE IF NOT EXISTS Tennisdatabase (uniqueid string,matchname string,gameid string,gamename string,currentteam string,oppteam string,jsonscore string,status string,datetime string,PRIMARY KEY(uniqueid,uniqueid));";
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
            android.database.Cursor cursor = sqldb.rawQuery("select * from Tennisdatabase", null);
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
    public void AddRecord(TennisModel objabout)
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
            insertValuesy.put("jsonscore", objabout.getJsonScore());
            insertValuesy.put("datetime", objabout.getDatetime());
            insertValuesy.put("status", objabout.getStatus());
            sqldb.insert("Tennisdatabase", null, insertValuesy);
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
            android.database.Cursor cursor = sqldb.rawQuery("select * from Tennisdatabase where uniqueid='"+uniqueid+"'", null);
            if (cursor != null)
            {
                sqldb.delete("Tennisdatabase", "uniqueid=?", new String[] {uniqueid});
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
            android.database.Cursor cursor = sqldb.rawQuery("select * from Tennisdatabase", null);
            if (cursor != null)
            {
                sqldb.delete("Tennisdatabase", "uniqueid=?", new String[] {""});
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
            android.database.Cursor cursor = sqldb.rawQuery("select * from Tennisdatabase", null);
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
    public List<TennisModel> GetRecords()
    {
        List<TennisModel> objabout = new ArrayList<>();
        try
        {
            android.database.Cursor cursor = sqldb.rawQuery("select * from Tennisdatabase", null);
            if (cursor != null)
            {
                while(cursor.moveToNext()) {
                    TennisModel objTennisModel = new TennisModel();
                    objTennisModel.setUniqueid(cursor.getString(cursor.getColumnIndex("uniqueid")));
                    objTennisModel.setMatchname(cursor.getString(cursor.getColumnIndex("matchname")));
                    objTennisModel.setGameid(cursor.getString(cursor.getColumnIndex("gameid")));
                    objTennisModel.setGamename(cursor.getString(cursor.getColumnIndex("gamename")));
                    objTennisModel.setCurrentteam(cursor.getString(cursor.getColumnIndex("currentteam")));
                    objTennisModel.setOppteam(cursor.getString(cursor.getColumnIndex("oppteam")));
                    objTennisModel.setDatetime(cursor.getString(cursor.getColumnIndex("datetime")));
                    objTennisModel.setStatus(cursor.getString(cursor.getColumnIndex("status")));
                    objTennisModel.setJsonScore(cursor.getString(cursor.getColumnIndex("jsonscore")));
                    objabout.add(objTennisModel);
                }
            }
        }
        catch (Exception ex)
        {
            sqldb_message = ex.getMessage();
        }
        return Reverse(objabout);//Collections.reverse(objabout);
    }
    static List<TennisModel> Reverse(final List<TennisModel> list) {
        final List<TennisModel> result = new ArrayList<>(list);
        Collections.reverse(result);
        return result;
    }
}
