package com.scorer.Adapter;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.format.Formatter;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scorer.Activity.HistoryActivity;
import com.scorer.Activity.HomeActivity;
import com.scorer.Activity.PlayersActivity;
import com.scorer.Activity.SettingsActivity;
import com.scorer.Activity.SubscriberActivity;
import com.scorer.Activity.TeamsActivity;
import com.scorer.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Menu_Details extends BaseAdapter {

    private Activity context;
    private List<String> menuvalues;
    DrawerLayout mDrawerLayout;
    String Screenname;
    public Menu_Details(Activity context, DrawerLayout vDrawerLayout, String Screennamev) {
        this.context = context;
        menuvalues = new ArrayList<String>();
        menuvalues.add("1");
        mDrawerLayout = vDrawerLayout;
        Screenname = Screennamev;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub

        return menuvalues.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return menuvalues.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
    @Override
    public View getView(final int position, View view, ViewGroup arg2) {
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = mInflater.inflate(R.layout.menu, null);
        ImageView Homeimg = (ImageView) view.findViewById(R.id.Homeimg);
       final TextView Homebutton = (TextView) view.findViewById(R.id.Homebutton);
        Homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Screenname.equals("Home"))
                    context.startActivity(new Intent(context, HomeActivity.class));
                else
                    mDrawerLayout.closeDrawers();
            }
        });
        Homeimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Homebutton.performClick();
            }
        });
        ImageView Aboutimg = (ImageView) view.findViewById(R.id.Shareimg);
        final TextView Aboutbutton = (TextView) view.findViewById(R.id.Sharebutton);
        Aboutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT, "Welcome to scorer. Download Link : https://play.google.com/store/apps/details?id=com.scorer&hl=en");
                    context.startActivity(Intent.createChooser(intent, ""));
                } catch (ActivityNotFoundException ex) {
                } catch (Exception ex) { }
            }
        });
        Aboutimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Aboutbutton.performClick();
            }
        });

        ImageView Historyimg = (ImageView) view.findViewById(R.id.Historyimg);
        final TextView Historybutton = (TextView) view.findViewById(R.id.Historybutton);
        Historybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Screenname.equals("History"))
                    context.startActivity(new Intent(context, HistoryActivity.class));
                else
                    mDrawerLayout.closeDrawers();
            }
        });
        Historyimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Historybutton.performClick();
            }
        });

        ImageView Teamimg = (ImageView) view.findViewById(R.id.Teamimg);
        final TextView Teambutton = (TextView) view.findViewById(R.id.Teambutton);
        Teambutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Screenname.equals("Team"))
                    context.startActivity(new Intent(context, TeamsActivity.class));
                else
                    mDrawerLayout.closeDrawers();
            }
        });
        Teamimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Teambutton.performClick();
            }
        });


        ImageView Playerimg = (ImageView) view.findViewById(R.id.Playerimg);
        final TextView Playerbutton = (TextView) view.findViewById(R.id.Playerbutton);
        Playerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Screenname.equals("Player"))
                    context.startActivity(new Intent(context, PlayersActivity.class));
                else
                    mDrawerLayout.closeDrawers();
            }
        });
        Playerimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Playerbutton.performClick();
            }
        });


        ImageView Subscriberimg = (ImageView) view.findViewById(R.id.Subscriberimg);
        final TextView Subscriberbutton = (TextView) view.findViewById(R.id.Subscriberbutton);
        Subscriberbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Screenname.equals("Subscriber"))
                    context.startActivity(new Intent(context, SubscriberActivity.class));
                else
                    mDrawerLayout.closeDrawers();
            }
        });
        Subscriberimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Subscriberbutton.performClick();
            }
        });

        ImageView Settingsimg = (ImageView) view.findViewById(R.id.Settingsimg);
        final TextView Settingsbutton = (TextView) view.findViewById(R.id.Settingsbutton);
        Settingsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Screenname.equals("Settings"))
                    context.startActivity(new Intent(context, SettingsActivity.class));
                else
                    mDrawerLayout.closeDrawers();
            }
        });
        Settingsimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Settingsbutton.performClick();
            }
        });
        return view;
    }
}