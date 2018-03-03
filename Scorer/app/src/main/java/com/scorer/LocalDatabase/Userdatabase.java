package com.scorer.LocalDatabase;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Path;
import android.os.Environment;

import com.scorer.ModalClass.Common;
import com.scorer.ModalClass.Userdetails;

import java.io.File;


public class Userdatabase {
    private SQLiteDatabase sqldb;
    private String sqldb_query,sqldb_path;
    private String sqldb_message;
    private String Databasename = "scorer.db";
    private boolean sqldb_available;
    public Userdatabase(Activity context)
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
        catch (android.database.SQLException ex)
        {
            sqldb_message = ex.getMessage();
        }
    }
    public Userdatabase() {
    }
    public void CreateDatabase()
    {
        try
        {
            sqldb_message = "";
            if (!Exists())
            {
                sqldb = SQLiteDatabase.openOrCreateDatabase(sqldb_path, null);
                sqldb_query = "CREATE TABLE IF NOT EXISTS Userdetails (userid string,name string,email VARCHAR,gender VARCHAR,location VARCHAR,password VARCHAR,PRIMARY KEY(userid,userid));";
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
        catch (android.database.SQLException ex)
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
            android.database.Cursor cursor = sqldb.rawQuery("select * from Userdetails", null);
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
    public void AddRecord(Userdetails objabout)
    {
        try
        {
            ContentValues insertValuesy = new ContentValues();
            insertValuesy.put("userid", objabout.getId());
            insertValuesy.put("name", objabout.getName());
            insertValuesy.put("email", objabout.getemail());
            insertValuesy.put("gender", objabout.getgender());
            insertValuesy.put("location", objabout.getLocation());
            insertValuesy.put("password", objabout.getpassword());
            sqldb.insert("Userdetails", null, insertValuesy);
        }
        catch (android.database.SQLException ex)
        {
            sqldb_message = ex.getMessage();
        }
    }
    public void DeleteRecord()
    {
        try
        {
            android.database.Cursor cursor = sqldb.rawQuery("select * from Userdetails", null);
            if (cursor != null)
            {
                sqldb.delete("Userdetails", "userid=?", new String[] {Common.Userid});
                sqldb_message = "Record Deleted";
            }
        }
        catch (NullPointerException ex)
        {
            sqldb_message = ex.getMessage();
        }
        catch (android.database.SQLException ex)
        {
            sqldb_message = ex.getMessage();
        }
    }
    public void Logout()
    {
        try
        {
            sqldb.delete("Userdetails", null, null);
        }
        catch (NullPointerException ex)
        {
            sqldb_message = ex.getMessage();
        }
        catch (android.database.SQLException ex)
        {
            sqldb_message = ex.getMessage();
        }

        try
        {
            sqldb.delete("Matchdatabase", null, null);
        }
        catch (NullPointerException ex)
        {
            sqldb_message = ex.getMessage();
        }
        catch (android.database.SQLException ex)
        {
            sqldb_message = ex.getMessage();
        }
        try
        {
            sqldb.delete("Playerdatabase", null, null);
        }
        catch (NullPointerException ex)
        {
            sqldb_message = ex.getMessage();
        }
        catch (android.database.SQLException ex)
        {
            sqldb_message = ex.getMessage();
        }
        try
        {
            sqldb.delete("Recentgames", null, null);
        }
        catch (NullPointerException ex)
        {
            sqldb_message = ex.getMessage();
        }
        catch (android.database.SQLException ex)
        {
            sqldb_message = ex.getMessage();
        }
        try
        {
            sqldb.delete("Scoredatabase", null, null);
        }
        catch (NullPointerException ex)
        {
            sqldb_message = ex.getMessage();
        }
        catch (android.database.SQLException ex)
        {
            sqldb_message = ex.getMessage();
        }

        try
        {
            sqldb.delete("Subscriberdatabase", null, null);
        }
        catch (NullPointerException ex)
        {
            sqldb_message = ex.getMessage();
        }
        catch (android.database.SQLException ex)
        {
            sqldb_message = ex.getMessage();
        }

        try
        {
            sqldb.delete("Teamdatabase", null, null);
        }
        catch (NullPointerException ex)
        {
            sqldb_message = ex.getMessage();
        }
        catch (android.database.SQLException ex)
        {
            sqldb_message = ex.getMessage();
        }

        try
        {
            sqldb.delete("Contactdatabase", null, null);
        }
        catch (NullPointerException ex)
        {
            sqldb_message = ex.getMessage();
        }
        catch (android.database.SQLException ex)
        {
            sqldb_message = ex.getMessage();
        }

    }
    public Userdetails GetRecords()
    {
        Userdetails objabout = new Userdetails();
        try
        {
            android.database.Cursor cursor = sqldb.rawQuery("select * from Userdetails", null);
            if (cursor != null)
            {
                cursor.moveToFirst();
                objabout.setId(cursor.getString(cursor.getColumnIndex("userid")));
                objabout.setName(cursor.getString(cursor.getColumnIndex("name")));
                objabout.setemail(cursor.getString(cursor.getColumnIndex("email")));
                objabout.setgender(cursor.getString(cursor.getColumnIndex("gender")));
                objabout.setLocation(cursor.getString(cursor.getColumnIndex("location")));
                objabout.setpassword(cursor.getString(cursor.getColumnIndex("password")));
            }
        }
        catch (Exception ex)
        {
            sqldb_message = ex.getMessage();
        }
        return objabout;
    }
}
