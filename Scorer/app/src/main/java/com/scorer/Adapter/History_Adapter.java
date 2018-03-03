package com.scorer.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.scorer.ModalClass.Common;
import com.scorer.ModalClass.TennisModel;
import com.scorer.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Android on 11/10/2017.
 */

public class History_Adapter extends BaseAdapter {
    private Context context;
    private List<TennisModel> launchmodel;

    public History_Adapter(Context context, List<TennisModel> launchmodel) {
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
            convertView = mInflater.inflate(R.layout.history_item, null);
        }
        TextView Matchname = (TextView) convertView.findViewById(R.id.Matchname);
        Matchname.setText(launchmodel.get(position).getGamename()+" - "+launchmodel.get(position).getMatchname());
        TextView Teamname1 = (TextView) convertView.findViewById(R.id.Teamname1);
        Teamname1.setText(launchmodel.get(position).getCurrentteam());
        TextView Teamname2 = (TextView) convertView.findViewById(R.id.Teamname2);
        Teamname2.setText(launchmodel.get(position).getOppteam());
        TextView Matchdate = (TextView) convertView.findViewById(R.id.Matchdate);
        Matchdate.setText(Common.DatetoDateonly(Common.StringtoDate(launchmodel.get(position).getDatetime())));
        TextView Matchmonthtime = (TextView) convertView.findViewById(R.id.Matchmonthtime);
        Matchmonthtime.setText(Common.DatetoMonthonly(Common.StringtoDate(launchmodel.get(position).getDatetime()))+"\n"+Common.DatetoTimeAMPM(Common.StringtoDate(launchmodel.get(position).getDatetime())));
        ImageView Teamname1image = (ImageView) convertView.findViewById(R.id.Teamname1image);
        ImageView Teamname2image = (ImageView) convertView.findViewById(R.id.Teamname2image);
        if(launchmodel.get(position).getStatus().equals("Won"))
        {
            try {
                JSONObject objscore = new JSONObject(launchmodel.get(position).getJsonScore());
                if (objscore.getInt("winner") == 1) {
                    Teamname1image.setVisibility(View.VISIBLE);
                    Teamname2image.setVisibility(View.GONE);
                } else {
                    Teamname1image.setVisibility(View.GONE);
                    Teamname2image.setVisibility(View.VISIBLE);
                }
            } catch (JSONException ex)
            {

            }
        } else {
            Teamname1image.setVisibility(View.GONE);
            Teamname2image.setVisibility(View.GONE);
        }
        return convertView;
    }
}
