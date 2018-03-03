package com.scorer.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.scorer.ModalClass.GameModel;
import com.scorer.ModalClass.PlayerModel;
import com.scorer.R;

import java.util.ArrayList;
import java.util.List;

import static com.scorer.Activity.Team_popup.objSelected_players;

/**
 * Created by Android on 11/10/2017.
 */
public class Player_Selection_Adapter extends RecyclerView.Adapter<Player_Selection_Adapter.ItemViewHolder> {
    ItemViewHolder viewHolder;
    private Activity context;
    private List<PlayerModel> objPlayerModel;
    public Player_Selection_Adapter(Activity context, List<PlayerModel> objPlayerModel) {
        this.context = context;
        this.objPlayerModel = objPlayerModel;
    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_item_checked, parent, false);
        viewHolder = new ItemViewHolder(rowView,objPlayerModel,context);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        viewHolder.player_name.setText(objPlayerModel.get(position).getPlayername());
        viewHolder.player_position.setText(objPlayerModel.get(position).getGamename() + " - " + objPlayerModel.get(position).getPlayerposition());
        Glide.with(context)
                .load(objPlayerModel.get(position).getPlayerimage())
                .placeholder(R.drawable.defaultimg)
                .skipMemoryCache(true)
                .override(80, 80)
                .into(viewHolder.player_image);
        viewHolder.Player_disable.setTag(objPlayerModel.get(position).getUniqueid());
        viewHolder.Player_Enable.setTag(objPlayerModel.get(position).getUniqueid());
    }

    @Override
    public int getItemCount() {
        return objPlayerModel.size();
    }


    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        public final ImageView player_image;
        public final ImageView Player_disable;
        public final ImageView Player_Enable;
        public final TextView player_name;
        public final TextView player_position;
        public ItemViewHolder(View convertView,final List<PlayerModel> objOrderModelv, final Context context) {
            super(convertView);
            player_image = (ImageView) convertView.findViewById(R.id.player_image);
            Player_disable = (ImageView) convertView.findViewById(R.id.Player_disable);
            Player_Enable = (ImageView) convertView.findViewById(R.id.Player_Enable);
            player_name = (TextView) convertView.findViewById(R.id.player_name);
            player_position = (TextView) convertView.findViewById(R.id.player_position);
            Player_Enable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        Player_Enable.setVisibility(View.GONE);
                        Player_disable.setVisibility(View.VISIBLE);
                        objSelected_players.remove(view.getTag().toString());
                }
            });
            Player_disable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (GameModel.Game_player_count != objSelected_players.size()) {
                        Player_Enable.setVisibility(View.VISIBLE);
                        Player_disable.setVisibility(View.GONE);
                        objSelected_players.add(view.getTag().toString());
                    } else {
                        Toast.makeText(context, "Limit Exceeded!/nSelect Current Players only", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}