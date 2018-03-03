package com.scorer.Adapter;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.scorer.ModalClass.GameModel;
import com.scorer.ModalClass.PlayerModel;
import com.scorer.ModalClass.gamesmenu;
import com.scorer.R;
import com.scorer.Widgets.AppPreferences;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android on 10/30/2017.
 */

public class Game_Adapter extends PagerAdapter {
    Activity mContext;
    List<PlayerModel> objOrderModel = new ArrayList<>();
    TextView menu_name,menu_position;
    public ImageView menu_image,menu_select;
    boolean Status;
    AppPreferences objAppPreferences;
    public Game_Adapter(Activity context, List<PlayerModel> objOrderModelv,boolean Statusv) {
        mContext = context;
        objOrderModel = objOrderModelv;
        Status = Statusv;
        objAppPreferences = new AppPreferences(context);
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
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_item, parent, false);
        menu_image = (ImageView) itemView.findViewById(R.id.menu_image);
        menu_select = (ImageView) itemView.findViewById(R.id.menu_select);
        menu_name = (TextView) itemView.findViewById(R.id.menu_name);
        menu_position = (TextView) itemView.findViewById(R.id.menu_position);
        menu_name.setText(objOrderModel.get(position).getPlayername());
        menu_position.setText(objOrderModel.get(position).getPlayerposition());
        Glide.with(mContext)
                .load(objOrderModel.get(position).getPlayerimage())
                .skipMemoryCache(true)
                .placeholder(R.drawable.defaultimg)
                .override(70, 70)
                .into(menu_image);

        if (!GameModel.Game_single_status) {
            if (Status) {
                if (GameModel.Game_player_id_1.contains(objOrderModel.get(position).getUniqueid()))
                    menu_select.setVisibility(View.VISIBLE);
                else
                    menu_select.setVisibility(View.GONE);
            } else {
                if (GameModel.Game_player_id_2.contains(objOrderModel.get(position).getUniqueid()))
                    menu_select.setVisibility(View.VISIBLE);
                else
                    menu_select.setVisibility(View.GONE);
            }
        } else {
            if (Status) {
                menu_select.setVisibility(View.VISIBLE);
            } else {
               menu_select.setVisibility(View.VISIBLE);
            }
        }

        if (objAppPreferences.Get_Daymode()) {
            menu_name.setTextColor(mContext.getResources().getColor(R.color.black));
        } else {
            menu_name.setTextColor(mContext.getResources().getColor(R.color.white));
        }
        parent.addView(itemView, 0);
        return itemView;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

}