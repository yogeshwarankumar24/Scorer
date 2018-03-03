package com.scorer.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.scorer.Adapter.History_Adapter;
import com.scorer.Adapter.Menu_Details;
import com.scorer.LocalDatabase.Tennisdatabase;
import com.scorer.ModalClass.MailModel;
import com.scorer.ModalClass.TennisModel;
import com.scorer.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoryActivity extends AppCompatActivity {
    ListView HistoryListview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ListView Menubarlist = (ListView)findViewById(R.id.Menubarlist);
        final LinearLayout MenuLeft = (LinearLayout)findViewById(R.id.MenuLeft);
        final DrawerLayout mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, 0);
        Menubarlist.setAdapter(new Menu_Details(this,mDrawerLayout,"History"));
        ImageView Menubutton = (ImageView)findViewById(R.id.Menubutton);
        Menubutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(MenuLeft);
            }
        });
        Tennisdatabase objTennisdatabase = new Tennisdatabase(this);
        HistoryListview = (ListView)findViewById(R.id.HistoryListview);
        List<TennisModel> objrecords = new ArrayList<>();
        objrecords = objTennisdatabase.GetRecords();
        HistoryListview.setAdapter(new History_Adapter(this,objrecords));
        HistoryListview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
            {
                Intent objIntent = new Intent(HistoryActivity.this, HistoryviewActivity.class);
                objIntent.putExtra("position",position);
                startActivity(objIntent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(HistoryActivity.this, HomeActivity.class));
    }

}
