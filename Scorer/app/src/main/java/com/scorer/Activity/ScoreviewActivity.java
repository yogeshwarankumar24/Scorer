package com.scorer.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.scorer.Adapter.Score_Adapter;
import com.scorer.ModalClass.Common;
import com.scorer.ModalClass.TennisModel;
import com.scorer.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class ScoreviewActivity extends AppCompatActivity {

    TennisModel objTennisModel = new TennisModel();
    private List<String> CurrentScore = new ArrayList<>();
    private List<String> OpponendScore = new ArrayList<>();
    Uri Score_image = null;
    String Score_filename;
    File _dir;
    ScrollView Scrollview_Layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreview);
        objTennisModel = Common.Sharedata;
        Scrollview_Layout = (ScrollView) findViewById(R.id.scrollView);
        TextView Matchname = (TextView) findViewById(R.id.Matchname);
        Matchname.setText(objTennisModel.getGamename()+" - "+objTennisModel.getMatchname());
        TextView Teamname1 = (TextView) findViewById(R.id.Teamname1);
        Teamname1.setText(objTennisModel.getCurrentteam());
        TextView Teamname2 = (TextView) findViewById(R.id.Teamname2);
        Teamname2.setText(objTennisModel.getOppteam());
        TextView MatchScorepoints = (TextView) findViewById(R.id.MatchScorepoints);
        MatchScorepoints.setText(objTennisModel.getStatus());
        TextView Matchmonthtime = (TextView) findViewById(R.id.Matchmonthtime);
        Matchmonthtime.setText(Common.DatetoStringAMPM(Common.StringtoDate(objTennisModel.getDatetime())));
        ListView ScoreListview = (ListView)findViewById(R.id.ScoreListview);
        Common.ListViewHeightBasedOnChildren(ScoreListview);
        ImageView Teamname1image = (ImageView) findViewById(R.id.Teamname1image);
        ImageView Teamname2image = (ImageView) findViewById(R.id.Teamname2image);
        try {
            JSONObject objscore = new JSONObject(objTennisModel.getJsonScore());
            if(objTennisModel.getStatus().equals("Won"))
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
        ScoreListview.setAdapter(new Score_Adapter(this,CurrentScore,OpponendScore));
        ImageView Backbutton = (ImageView) findViewById(R.id.Backbutton);
        Backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        final ImageView Copy_image = (ImageView)findViewById(R.id.Copy_image);
        Copy_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareImage_Link();
            }
        });
        RelativeLayout Copy_layout = (RelativeLayout)findViewById(R.id.Copy_layout);
        Copy_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareImage_Link();
            }
        });
        TextView Copy_text = (TextView)findViewById(R.id.Copy_text);
        Copy_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareImage_Link();
            }
        });

        final ImageView Whatapp_image = (ImageView)findViewById(R.id.Whatapp_image);
        Whatapp_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareImage_Whatsapp();
            }
        });
        RelativeLayout Whatapp_layout = (RelativeLayout)findViewById(R.id.Whatapp_layout);
        Whatapp_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareImage_Whatsapp();
            }
        });
        TextView Whatapp_text = (TextView)findViewById(R.id.Whatapp_text);
        Whatapp_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareImage_Whatsapp();
            }
        });
        
        final ImageView Facebook_image = (ImageView)findViewById(R.id.Facebook_image);
        Facebook_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareImage_Facebook();
            }
        });
        RelativeLayout Facebook_layout = (RelativeLayout)findViewById(R.id.Facebook_layout);
        Facebook_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareImage_Facebook();
            }
        });
        TextView Facebook_text = (TextView)findViewById(R.id.Facebook_text);
        Facebook_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareImage_Facebook();
            }
        });

        final ImageView More_image = (ImageView)findViewById(R.id.More_image);
        More_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareImage_More();
            }
        });
        RelativeLayout More_layout = (RelativeLayout)findViewById(R.id.More_layout);
        More_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareImage_More();
            }
        });
        TextView More_text = (TextView)findViewById(R.id.More_text);
        More_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareImage_More();
            }
        });

        LoadImage();
    }
    void LoadImage()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(ScoreviewActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(ScoreviewActivity.this, new String[]
                        {
                                WRITE_EXTERNAL_STORAGE,
                                READ_EXTERNAL_STORAGE,
                        }, 100);
            } else {
                Score_image = CreateDirectoryForPictures(ScoreviewActivity.this);
            }
        } else {
            Score_image = CreateDirectoryForPictures(ScoreviewActivity.this);
        }
    }

    void ShareImage_Link() {
        if (Score_image == null) {
            LoadImage();
        }
        Common.TakeScrollviewScreen(Scrollview_Layout,_dir.toString(),Score_filename);
        int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(Score_image.toString());
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Scorerlink",Score_image.toString());
            clipboard.setPrimaryClip(clip);
        }
        Toast.makeText(ScoreviewActivity.this,"Link Copied.",Toast.LENGTH_SHORT).show();
    }
    void ShareImage_Whatsapp()
    {
        if(Score_image == null)
        {
            LoadImage();
        }
        Common.TakeScrollviewScreen(Scrollview_Layout,_dir.toString(),Score_filename);
        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        whatsappIntent.setType("text/plain");
        whatsappIntent.setPackage("com.whatsapp");
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Scorer For More Details Download Link : https://play.google.com/store/apps/details?id=com.scorer&hl=en");
        whatsappIntent.putExtra(Intent.EXTRA_STREAM, Score_image);
        whatsappIntent.setType("image/jpeg");
        whatsappIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {
            startActivity(whatsappIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(ScoreviewActivity.this,"Whatsapp have not been installed.",Toast.LENGTH_SHORT).show();
        }
    }
    void ShareImage_Facebook()
    {
        if(Score_image == null)
        {
            LoadImage();
        }
        Common.TakeScrollviewScreen(Scrollview_Layout,_dir.toString(),Score_filename);
        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        whatsappIntent.setType("text/plain");
        whatsappIntent.setPackage("com.facebook.katana");
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Scorer For More Details Download Link : https://play.google.com/store/apps/details?id=com.scorer&hl=en");
        whatsappIntent.putExtra(Intent.EXTRA_STREAM, Score_image);
        whatsappIntent.setType("image/jpeg");
        whatsappIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {
            startActivity(whatsappIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(ScoreviewActivity.this,"Facebook have not been installed.",Toast.LENGTH_SHORT).show();
        }
    }
    void ShareImage_More()
    {
        if(Score_image == null)
        {
            LoadImage();
        }
        Common.TakeScrollviewScreen(Scrollview_Layout,_dir.toString(),Score_filename);
        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        whatsappIntent.setType("text/plain");
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Scorer For More Details Download Link : https://play.google.com/store/apps/details?id=com.scorer&hl=en");
        whatsappIntent.putExtra(Intent.EXTRA_STREAM, Score_image);
        whatsappIntent.setType("image/jpeg");
        whatsappIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {
            Intent Intentchooser = Intent.createChooser(whatsappIntent, "Share with...");
            startActivity(Intentchooser);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(ScoreviewActivity.this,"Facebook have not been installed.",Toast.LENGTH_SHORT).show();
        }
    }
//    public void StoreimageinLocal(Bitmap bitmap) {
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//        try {
//            FileOutputStream fo = new FileOutputStream(new File(Score_image.toString()));
//            fo.write(bytes.toByteArray());
//            fo.flush();
//            fo.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }

    public Uri CreateDirectoryForPictures(Activity objActivity)
    {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(objActivity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(objActivity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(objActivity, new String[]
                        {
                                WRITE_EXTERNAL_STORAGE,
                                READ_EXTERNAL_STORAGE,
                        }, 100);
                return null;
            }
        }
        _dir = new File(android.os.Environment.getExternalStorageDirectory(), "Scorer");
        if (!_dir.exists())
        {
            _dir.mkdirs();
        }
        Score_filename = "File_"+ UUID.randomUUID().toString()+".jpg";
        File _file = new File(_dir, Score_filename);
        Uri path = Uri.fromFile(_file);
        return path;
    }
}
