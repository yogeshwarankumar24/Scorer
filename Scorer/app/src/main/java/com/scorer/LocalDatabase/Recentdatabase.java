package com.scorer.LocalDatabase;

import android.app.Activity;
import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.scorer.ModalClass.Common;
import com.scorer.ModalClass.gamesmenu;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Recentdatabase {
    private SQLiteDatabase sqldb;
    private String sqldb_query,sqldb_path;
    private String sqldb_message;
    private String Databasename = "scorer.db";
    private boolean sqldb_available;
    public Recentdatabase(Activity context)
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
    public Recentdatabase() {
    }
    public void CreateDatabase()
    {
        try
        {
            sqldb_message = "";
            if (!Exists())
            {
                sqldb = SQLiteDatabase.openOrCreateDatabase(sqldb_path, null);
                sqldb_query = "CREATE TABLE IF NOT EXISTS Recentgames (userid string,name string,image VARCHAR,PRIMARY KEY(userid,userid));";
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
            android.database.Cursor cursor = sqldb.rawQuery("select * from Recentgames", null);
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
    public void AddRecord(gamesmenu objabout)
    {
        try
        {
            DeleteRecord(objabout.getId().toString());
            ContentValues insertValuesy = new ContentValues();
            insertValuesy.put("userid", objabout.getId());
            insertValuesy.put("name", objabout.getName());
            insertValuesy.put("image", objabout.getImage());
            sqldb.insert("Recentgames", null, insertValuesy);
        }
        catch (SQLException ex)
        {
            sqldb_message = ex.getMessage();
        }
    }
    public void DeleteRecord(String Userid)
    {
        try
        {
            android.database.Cursor cursor = sqldb.rawQuery("select * from Recentgames where userid='"+Userid+"'", null);
            if (cursor != null)
            {
                sqldb.delete("Recentgames", "userid=?", new String[] {Userid});
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
            android.database.Cursor cursor = sqldb.rawQuery("select * from Recentgames", null);
            if (cursor != null)
            {
                sqldb.delete("Recentgames", "userid=?", new String[] {Common.Userid});
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
    public List<gamesmenu> GetRecords()
    {
        List<gamesmenu> objabout = new ArrayList<>();
        try
        {
            android.database.Cursor cursor = sqldb.rawQuery("select * from Recentgames", null);
            if (cursor != null)
            {
                while(cursor.moveToNext()) {
                    objabout.add(new gamesmenu(cursor.getString(cursor.getColumnIndex("userid")), cursor.getString(cursor.getColumnIndex("name")), cursor.getInt(cursor.getColumnIndex("image"))));
                }
            }
        }
        catch (Exception ex)
        {
            sqldb_message = ex.getMessage();
        }
        return Reverse(objabout);//Collections.reverse(objabout);
    }
    static List<gamesmenu> Reverse(final List<gamesmenu> list) {
        final List<gamesmenu> result = new ArrayList<>(list);
        Collections.reverse(result);
        return result;
    }
}
