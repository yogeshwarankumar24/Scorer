package com.scorer.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.scorer.ModalClass.PlayerModel;
import com.scorer.ModalClass.TeamModel;
import com.scorer.R;

import java.util.List;

/**
 * Created by Android on 11/10/2017.
 */

public class Team_Adapter extends BaseAdapter {
    private Context context;
    private List<TeamModel> objPlayerModel;

    public Team_Adapter(Context context, List<TeamModel> objPlayerModel) {
        this.context = context;
        this.objPlayerModel = objPlayerModel;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
            return objPlayerModel.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return objPlayerModel.get(position);
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
            convertView = mInflater.inflate(R.layout.player_item, null);
        }
        ImageView player_image = (ImageView) convertView.findViewById(R.id.player_image);
        TextView player_name = (TextView) convertView.findViewById(R.id.player_name);
        player_name.setText(objPlayerModel.get(position).getTeamname());
        TextView player_position = (TextView) convertView.findViewById(R.id.player_position);
        player_position.setText(objPlayerModel.get(position).getGamename());
        Glide.with(context)
                .load(objPlayerModel.get(position).getTeamimage())
                .placeholder(R.drawable.defaultimg)
                .skipMemoryCache(true)
                .override(80,80)
                .into(player_image);
        return convertView;
    }
}
