package com.scorer.Activity;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.scorer.Adapter.Menu_Details;
import com.scorer.LocalDatabase.Userdatabase;
import com.scorer.MainActivity;
import com.scorer.R;

public class SettingsActivity extends AppCompatActivity {
    Userdatabase objUserdatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        objUserdatabase = new Userdatabase(this);
        ListView Menubarlist = (ListView)findViewById(R.id.Menubarlist);
        final LinearLayout MenuLeft = (LinearLayout)findViewById(R.id.MenuLeft);
        final DrawerLayout mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, 0);
        Menubarlist.setAdapter(new Menu_Details(this,mDrawerLayout,"Settings"));
        ImageView Menubutton = (ImageView)findViewById(R.id.Menubutton);
        Menubutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(MenuLeft);
            }
        });

        TextView Logoutbutton = (TextView)findViewById(R.id.Logoutbutton);
        Logoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objUserdatabase.Logout();
                startActivity(new Intent(SettingsActivity.this, LoginActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(SettingsActivity.this, HomeActivity.class));
    }
}
