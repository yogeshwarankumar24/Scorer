package com.scorer.LocalDatabase;

import android.app.Activity;
import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.scorer.ModalClass.Common;
import com.scorer.ModalClass.PlayerModel;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Playerdatabase {
    private SQLiteDatabase sqldb;
    private String sqldb_query,sqldb_path;
    private String sqldb_message;
    private String Databasename = "scorer.db";
    private boolean sqldb_available;
    public Playerdatabase(Activity context)
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
    public Playerdatabase() {
    }
    public void CreateDatabase()
    {
        try
        {
            sqldb_message = "";
            if (!Exists())
            {
                //String uniqueid, String gameid, String gamename, String playername, String playeremail, String playerposition,String status,byte[] playerimage
                sqldb = SQLiteDatabase.openOrCreateDatabase(sqldb_path, null);
                sqldb_query = "CREATE TABLE IF NOT EXISTS Playerdatabase (uniqueid string,gameid string,gamename string,playername string,playeremail string,playerposition string,status string,playerimage BYTE,PRIMARY KEY(uniqueid,uniqueid));";
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
            android.database.Cursor cursor = sqldb.rawQuery("select * from Playerdatabase", null);
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
    public void AddRecord(PlayerModel objabout)
    {
        try
        {
            
            DeleteRecord(objabout.getUniqueid().toString());
            ContentValues insertValuesy = new ContentValues();
            insertValuesy.put("uniqueid", objabout.getUniqueid());
            insertValuesy.put("gameid", objabout.getGameid());
            insertValuesy.put("gamename", objabout.getGamename());
            insertValuesy.put("playername", objabout.getPlayername());
            insertValuesy.put("playeremail", objabout.getPlayeremail());
            insertValuesy.put("playerposition", objabout.getPlayerposition());
            insertValuesy.put("status", objabout.getStatus());
            insertValuesy.put("playerimage", objabout.getPlayerimage());
            sqldb.insert("Playerdatabase", null, insertValuesy);
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
            android.database.Cursor cursor = sqldb.rawQuery("select * from Playerdatabase where uniqueid='"+uniqueid+"'", null);
            if (cursor != null)
            {
                sqldb.delete("Playerdatabase", "uniqueid=?", new String[] {uniqueid});
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
            android.database.Cursor cursor = sqldb.rawQuery("select * from Playerdatabase", null);
            if (cursor != null)
            {
                sqldb.delete("Playerdatabase", "uniqueid=?", new String[] {""});
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
            android.database.Cursor cursor = sqldb.rawQuery("select * from Playerdatabase", null);
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
    public List<PlayerModel> GetRecords()
    {
        List<PlayerModel> objabout = new ArrayList<>();
        try
        {
            android.database.Cursor cursor = null;
            if(Common.objPlayers.size()>0)
            {
                String value="";
                for (int i = 0; i < Common.objPlayers.size(); i++) {
                    if(value.length()>0)
                        value = value + ",";
                    String item = Common.objPlayers.get(i);
                    value = value + "'"+item+"'";
                }
                cursor = sqldb.rawQuery("select * from Playerdatabase where uniqueid NOT IN ("+value+")", null);
            }
            else
            {
                cursor = sqldb.rawQuery("select * from Playerdatabase", null);
            }
//            android.database.Cursor cursor = sqldb.rawQuery("select * from Playerdatabase", null);
            if (cursor != null)
            {
                while(cursor.moveToNext()) {
                    PlayerModel objPlayerModel = new PlayerModel();
                    objPlayerModel.setUniqueid(cursor.getString(cursor.getColumnIndex("uniqueid")));
                    objPlayerModel.setGameid(cursor.getString(cursor.getColumnIndex("gameid")));
                    objPlayerModel.setGamename(cursor.getString(cursor.getColumnIndex("gamename")));
                    objPlayerModel.setPlayername(cursor.getString(cursor.getColumnIndex("playername")));
                    objPlayerModel.setPlayeremail(cursor.getString(cursor.getColumnIndex("playeremail")));
                    objPlayerModel.setPlayerposition(cursor.getString(cursor.getColumnIndex("playerposition")));
                    objPlayerModel.setPlayerimage(cursor.getBlob(cursor.getColumnIndex("playerimage")));
                    objPlayerModel.setStatus(cursor.getString(cursor.getColumnIndex("status")));
                    objabout.add(objPlayerModel);
                }
            }
        }
        catch (Exception ex)
        {
            sqldb_message = ex.getMessage();
        }
        return Reverse(objabout);//Collections.reverse(objabout);
    }
    public List<PlayerModel> GetRecords(String Gameid)
    {
        List<PlayerModel> objabout = new ArrayList<>();
        try
        {
            android.database.Cursor cursor = null;
            if(Common.objPlayers.size()>0)
            {
                String value="";
                for (int i = 0; i < Common.objPlayers.size(); i++) {
                    if(value.length()>0)
                        value = value + ",";
                    String item = Common.objPlayers.get(i);
                    value = value + "'"+item+"'";
                }
                cursor = sqldb.rawQuery("select * from Playerdatabase where uniqueid NOT IN ("+value+") AND gameid='"+Gameid+"'", null);
            }
            else
            {
                cursor = sqldb.rawQuery("select * from Playerdatabase where gameid='"+Gameid+"'", null);
            }
            if (cursor != null)
            {
                while(cursor.moveToNext()) {
                    PlayerModel objPlayerModel = new PlayerModel();
                    objPlayerModel.setUniqueid(cursor.getString(cursor.getColumnIndex("uniqueid")));
                    objPlayerModel.setGameid(cursor.getString(cursor.getColumnIndex("gameid")));
                    objPlayerModel.setGamename(cursor.getString(cursor.getColumnIndex("gamename")));
                    objPlayerModel.setPlayername(cursor.getString(cursor.getColumnIndex("playername")));
                    objPlayerModel.setPlayeremail(cursor.getString(cursor.getColumnIndex("playeremail")));
                    objPlayerModel.setPlayerposition(cursor.getString(cursor.getColumnIndex("playerposition")));
                    objPlayerModel.setPlayerimage(cursor.getBlob(cursor.getColumnIndex("playerimage")));
                    objPlayerModel.setStatus(cursor.getString(cursor.getColumnIndex("status")));
                    objabout.add(objPlayerModel);
                }
            }
        }
        catch (Exception ex)
        {
            sqldb_message = ex.getMessage();
        }
        return Reverse(objabout);//Collections.reverse(objabout);
    }
    public PlayerModel GetRecords_id(String Gameid)
    {
        PlayerModel objPlayerModel = new PlayerModel();
        try
        {
            android.database.Cursor cursor = sqldb.rawQuery("select * from Playerdatabase where uniqueid='"+Gameid+"'", null);
            if (cursor != null)
            {
                cursor.moveToFirst();
                objPlayerModel.setUniqueid(cursor.getString(cursor.getColumnIndex("uniqueid")));
                objPlayerModel.setGameid(cursor.getString(cursor.getColumnIndex("gameid")));
                objPlayerModel.setGamename(cursor.getString(cursor.getColumnIndex("gamename")));
                objPlayerModel.setPlayername(cursor.getString(cursor.getColumnIndex("playername")));
                objPlayerModel.setPlayeremail(cursor.getString(cursor.getColumnIndex("playeremail")));
                objPlayerModel.setPlayerposition(cursor.getString(cursor.getColumnIndex("playerposition")));
                objPlayerModel.setPlayerimage(cursor.getBlob(cursor.getColumnIndex("playerimage")));
                objPlayerModel.setStatus(cursor.getString(cursor.getColumnIndex("status")));
            }
        }
        catch (Exception ex)
        {
            sqldb_message = ex.getMessage();
        }
        return objPlayerModel;
    }
    static List<PlayerModel> Reverse(final List<PlayerModel> list) {
        final List<PlayerModel> result = new ArrayList<>(list);
        Collections.reverse(result);
        return result;
    }
}
