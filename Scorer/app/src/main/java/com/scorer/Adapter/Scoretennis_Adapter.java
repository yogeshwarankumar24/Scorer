package com.scorer.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.scorer.GamesController.SRRTennisGame;
import com.scorer.GamesController.SRRTennisSet;
import com.scorer.ModalClass.gamesmenu;
import com.scorer.R;

import java.util.List;
import java.util.Vector;

/**
 * Created by Android on 10/30/2017.
 */

public class Scoretennis_Adapter extends BaseAdapter {
    private Context context;
    private Vector<SRRTennisSet> launchmodel;

    public Scoretennis_Adapter(Context context, Vector<SRRTennisSet> launchmodel) {
        this.context = context;
        this.launchmodel = launchmodel;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub

        return launchmodel.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return launchmodel.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        // TODO Auto-generated method stub
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.tennis_item, null);
        }
        TextView menu1_name = (TextView) convertView.findViewById(R.id.menu1_name);
        menu1_name.setText(String.valueOf(launchmodel.get(position).getTeam1SetPoint()));

        TextView menu2_name = (TextView) convertView.findViewById(R.id.menu2_name);
        menu2_name.setText(String.valueOf(launchmodel.get(position).getTeam2SetPoint()));
        return convertView;
    }
}