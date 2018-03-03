package com.scorer.Activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.scorer.Adapter.Historyview_Adapter;
import com.scorer.LocalDatabase.Tennisdatabase;
import com.scorer.ModalClass.Common;
import com.scorer.ModalClass.TennisModel;
import com.scorer.R;

import java.util.ArrayList;
import java.util.List;

public class HistoryviewActivity extends AppCompatActivity {

    ViewPager mPager;
    int Currentposition = 0;
    List<TennisModel> objMatchList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historyview);
        mPager = (ViewPager) findViewById(R.id.pager);
        Tennisdatabase objTennisdatabase = new Tennisdatabase(this);
        objMatchList = objTennisdatabase.GetRecords();
        mPager.setAdapter(new Historyview_Adapter(HistoryviewActivity.this, objMatchList));
        Currentposition = getIntent().getIntExtra("position",0);
        mPager.setCurrentItem(Currentposition);
        ImageView Backbutton = (ImageView) findViewById(R.id.Backbutton);
        Backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ImageView Sharebutton = (ImageView) findViewById(R.id.Sharebutton);
        Sharebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Common.Sharedata = objMatchList.get(mPager.getCurrentItem());
                startActivity(new Intent(HistoryviewActivity.this,ScoreviewActivity.class));
            }
        });
    }
}
