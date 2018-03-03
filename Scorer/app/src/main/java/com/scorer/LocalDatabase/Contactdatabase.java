package com.scorer.LocalDatabase;

import android.app.Activity;
import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.scorer.ModalClass.Userdetails;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class Contactdatabase {
    private SQLiteDatabase sqldb;
    private String sqldb_query,sqldb_path;
    private String sqldb_message;
    private String Databasename = "scorer.db";
    private boolean sqldb_available;
    public Contactdatabase(Activity context)
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
    public Contactdatabase() {
    }
    public void CreateDatabase()
    {
        try
        {
            sqldb_message = "";
            if (!Exists())
            {
                sqldb = SQLiteDatabase.openOrCreateDatabase(sqldb_path, null);
                sqldb_query = "CREATE TABLE IF NOT EXISTS Contactdatabase (userid string,name string,email VARCHAR,gender VARCHAR,location VARCHAR,password VARCHAR,PRIMARY KEY(userid,userid));";
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
            android.database.Cursor cursor = sqldb.rawQuery("select * from Contactdatabase", null);
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
    public void AddRecord(List<Userdetails> objaboutv)
    {
        try {
            for (Userdetails objabout : objaboutv) {
                ContentValues insertValuesy = new ContentValues();
                insertValuesy.put("userid", objabout.getId());
                insertValuesy.put("name", objabout.getName());
                insertValuesy.put("email", objabout.getemail());
                insertValuesy.put("gender", objabout.getgender());
                insertValuesy.put("location", objabout.getLocation());
                insertValuesy.put("password", objabout.getpassword());
                sqldb.insert("Contactdatabase", null, insertValuesy);
            }
        }
        catch (SQLException ex)
        {
            sqldb_message = ex.getMessage();
        }
    }

    public List<Userdetails> GetRecords()
    {
        List<Userdetails> objaboutlist = new ArrayList<>();
        try
        {
            android.database.Cursor cursor = sqldb.rawQuery("select * from Contactdatabase", null);
            if (cursor != null)
            {
                while(cursor.moveToNext()) {
                    Userdetails objabout = new Userdetails();
                    objabout.setId(cursor.getString(cursor.getColumnIndex("userid")));
                    objabout.setName(cursor.getString(cursor.getColumnIndex("name")));
                    objabout.setemail(cursor.getString(cursor.getColumnIndex("email")));
                    objabout.setgender(cursor.getString(cursor.getColumnIndex("gender")));
                    objabout.setLocation(cursor.getString(cursor.getColumnIndex("location")));
                    objabout.setpassword(cursor.getString(cursor.getColumnIndex("password")));
                    objaboutlist.add(objabout);
                }
            }
        }
        catch (Exception ex)
        {
            sqldb_message = ex.getMessage();
        }
        return objaboutlist;
    }
}
