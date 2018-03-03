package com.scorer.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.scorer.ModalClass.gamesmenu;
import com.scorer.R;

import java.util.List;

import static com.scorer.Activity.HomeActivity.layoutwidth;

/**
 * Created by Android on 10/30/2017.
 */

public class Home_Adapter  extends BaseAdapter {
    private Context context;
    private List<gamesmenu> launchmodel;

    public Home_Adapter(Context context,List<gamesmenu> launchmodel) {
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
            convertView = mInflater.inflate(R.layout.home_item, null);
        }
        LinearLayout menu_layout = (LinearLayout) convertView.findViewById(R.id.menu_layout);
        ImageView menu_image = (ImageView) convertView.findViewById(R.id.menu_image);
        TextView menu_name = (TextView) convertView.findViewById(R.id.menu_name);
        menu_name.setText(launchmodel.get(position).getName());
        Glide.with(context)
                .load(launchmodel.get(position).getImage())
                .skipMemoryCache(true)
                .override(250,250)
                .into(menu_image);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(context.getResources().getDisplayMetrics().widthPixels/2,context.getResources().getDisplayMetrics().widthPixels/2);
        menu_layout.setLayoutParams(layoutParams);
        return convertView;
    }
}