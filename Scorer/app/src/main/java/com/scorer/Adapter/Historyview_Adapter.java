package com.scorer.Adapter;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.scorer.ModalClass.Common;
import com.scorer.ModalClass.TennisModel;
import com.scorer.ModalClass.gamesmenu;
import com.scorer.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android on 10/30/2017.
 */

public class Historyview_Adapter extends PagerAdapter {
    Activity mContext;
    List<TennisModel> objOrderModel = new ArrayList<>();
    private List<String> CurrentScore  = new ArrayList<>();
    private List<String> OpponendScore  = new ArrayList<>();
    public Historyview_Adapter(Activity context, List<TennisModel> objOrderModelv) {
        mContext = context;
        objOrderModel = objOrderModelv;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return objOrderModel.size();
    }

    @Override
    public Object instantiateItem(ViewGroup parent, int position) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.historyview_item, parent, false);

        TextView Matchname = (TextView) itemView.findViewById(R.id.Matchname);
        Matchname.setText(objOrderModel.get(position).getGamename()+" - "+objOrderModel.get(position).getMatchname());
        TextView Teamname1 = (TextView) itemView.findViewById(R.id.Teamname1);
        Teamname1.setText(objOrderModel.get(position).getCurrentteam());
        TextView Teamname2 = (TextView) itemView.findViewById(R.id.Teamname2);
        Teamname2.setText(objOrderModel.get(position).getOppteam());
        TextView MatchScorepoints = (TextView) itemView.findViewById(R.id.MatchScorepoints);
        MatchScorepoints.setText(objOrderModel.get(position).getStatus());
        TextView Matchmonthtime = (TextView) itemView.findViewById(R.id.Matchmonthtime);
        Matchmonthtime.setText(Common.DatetoStringAMPM(Common.StringtoDate(objOrderModel.get(position).getDatetime())));
        ListView ScoreListview = (ListView)itemView.findViewById(R.id.ScoreListview);
        ImageView Teamname1image = (ImageView) itemView.findViewById(R.id.Teamname1image);
        ImageView Teamname2image = (ImageView) itemView.findViewById(R.id.Teamname2image);
        try {
            JSONObject objscore = new JSONObject(objOrderModel.get(position).getJsonScore());
            if(objOrderModel.get(position).getStatus().equals("Won"))
            {
                    if (objscore.getInt("winner") == 1) {
                        Teamname1image.setVisibility(View.VISIBLE);
                        Teamname2image.setVisibility(View.GONE);
                    } else {
                        Teamname1image.setVisibility(View.GONE);
                        Teamname2image.setVisibility(View.VISIBLE);
                    }
            } else {
                Teamname1image.setVisibility(View.GONE);
                Teamname2image.setVisibility(View.GONE);
            }
            JSONArray objscorearray = objscore.getJSONArray("scores");
            for(int i=0;i<objscorearray.length();i++)
            {
                JSONObject objscorevalue = new JSONObject(objscorearray.get(i).toString());
                CurrentScore.add(objscorevalue.get("team1setpoint").toString());
                OpponendScore.add(objscorevalue.get("team2setpoint").toString());
            }
        } catch (JSONException ex)
        {

        }
        ScoreListview.setAdapter(new Score_Adapter(mContext,CurrentScore,OpponendScore));
        parent.addView(itemView, 0);
        return itemView;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

}