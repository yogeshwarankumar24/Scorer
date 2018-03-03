package com.scorer.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.scorer.ModalClass.Common;
import com.scorer.ModalClass.MatchModel;
import com.scorer.R;

import java.util.List;

/**
 * Created by Android on 11/10/2017.
 */

public class Score_Adapter extends BaseAdapter {
    private Context context;
    private List<String> CurrentScore;
    private List<String> OpponendScore;

    public Score_Adapter(Context context, List<String> CurrentScore,List<String> OpponendScore) {
        this.context = context;
        this.CurrentScore = CurrentScore;
        this.OpponendScore = OpponendScore;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if (OpponendScore.size() == CurrentScore.size()) {
            return CurrentScore.size();

        } else {
            if (OpponendScore.size() > CurrentScore.size())
                return CurrentScore.size();
            if (CurrentScore.size() > OpponendScore.size())
                return OpponendScore.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return CurrentScore.get(position);
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
            convertView = mInflater.inflate(R.layout.historyview_item2, null);
        }
        TextView Teamname1 = (TextView) convertView.findViewById(R.id.Teamname1);
        Teamname1.setText(CurrentScore.get(position));
        TextView Teamname2 = (TextView) convertView.findViewById(R.id.Teamname2);
        Teamname2.setText(OpponendScore.get(position));
        return convertView;
    }
}
