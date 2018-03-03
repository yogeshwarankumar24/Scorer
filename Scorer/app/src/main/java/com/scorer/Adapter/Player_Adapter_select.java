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
import com.scorer.R;

import java.util.List;

/**
 * Created by Android on 11/10/2017.
 */

public class Player_Adapter_select extends BaseAdapter {
    private Context context;
    private List<PlayerModel> objPlayerModel;

    public Player_Adapter_select(Context context, List<PlayerModel> objPlayerModel) {
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
            convertView = mInflater.inflate(R.layout.player_item_select, null);
        }
        ImageView player_image = (ImageView) convertView.findViewById(R.id.player_image);
        TextView player_name = (TextView) convertView.findViewById(R.id.player_name);
        player_name.setText(objPlayerModel.get(position).getPlayername());
        Glide.with(context)
                .load(objPlayerModel.get(position).getPlayerimage())
                .placeholder(R.drawable.defaultimg)
                .skipMemoryCache(true)
                .override(40,40)
                .into(player_image);
        return convertView;
    }
}
