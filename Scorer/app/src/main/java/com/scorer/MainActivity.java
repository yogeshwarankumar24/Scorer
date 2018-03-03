package com.scorer;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.scorer.Activity.HomeActivity;
import com.scorer.Activity.LoginActivity;
import com.scorer.Activity.ScoreActivity;
import com.scorer.LocalDatabase.Userdatabase;
import com.scorer.ModalClass.GameModel;
import com.scorer.ModalClass.Userdetails;
import com.scorer.Widgets.AppPreferences;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2000;
    Userdatabase objUserdatabase;
    Userdetails objUserdetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        FirebaseApp.initializeApp(MainActivity.this, FirebaseOptions.fromResource(MainActivity.this));
        objUserdatabase = new Userdatabase(this);
        ImageView Splashimage = (ImageView)findViewById(R.id.Splashimage);
        Glide.with(MainActivity.this)
                .load(R.drawable.splash)
                .skipMemoryCache(true)
                .override(getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels/3)
                .into(Splashimage);
        objUserdetails = new Userdetails();
        objUserdetails = objUserdatabase.GetRecords();
//        Subscribe_firebase();
        FirebaseMessaging.getInstance().subscribeToTopic("all");
        String tokenvalue = FirebaseInstanceId.getInstance().getToken();
        AppPreferences objAppPreferences = new AppPreferences(this);
        objAppPreferences.Set_AuthoToken(tokenvalue);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(objUserdetails.getId() != null && objUserdetails.getId().length()>1) {
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                } else {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
                // activity finish
                MainActivity.this.finish();
            }
        }, SPLASH_TIME_OUT);
    }

    SharedPreferences sharedpreferences;
    String regID;
    public void Subscribe_firebase()
    {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    InstanceID instanceID = InstanceID.getInstance(MainActivity.this);

                    regID = instanceID.getToken("956762772902",GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);

                    Log.i("", "GCM Registration Token: " + regID);

                    System.out.println("---async reg id back---"+regID);

                    if(!regID.equals("")){
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("regId",regID);
                        editor.commit();
                    }

                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();

                }
                catch (NullPointerException e){
                    msg = "Error :" + e.getMessage();
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                System.out.println("---async reg id---"+regID);
            }
        }.execute(null, null, null);
        //open activity

    }
//    void Subscribe_firebase()
//    {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            // Create channel to show notifications.
//            String channelId  = getString(R.string.default_notification_channel_id);
//            String channelName = getString(R.string.default_notification_channel_name);
//            NotificationManager notificationManager = getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(new NotificationChannel(channelId,channelName, NotificationManager.IMPORTANCE_DEFAULT));
//        }
//        if (getIntent().getExtras() != null) {
//            for (String key : getIntent().getExtras().keySet()) {
//                Object value = getIntent().getExtras().get(key);
//                Log.d("", "Key: " + key + " Value: " + value);
//            }
//        }
//        FirebaseMessaging.getInstance().subscribeToTopic("Scorer");
//
//        //open activity
//        if(objUserdetails.getId() != null && objUserdetails.getId().length()>1) {
//            startActivity(new Intent(MainActivity.this, HomeActivity.class));
//        } else {
//            startActivity(new Intent(MainActivity.this, LoginActivity.class));
//        }
//        // activity finish
//        MainActivity.this.finish();
//    }
//    // Method to send Notifications from server to client end.
//    public final static String AUTH_KEY_FCM = "ApidhfkIjd_cAdhpa-ZZ065hskiH53Hw3g";
//    public final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";
//    // userDeviceIdKey is the device id you will query from your database
//    public static void pushFCMNotification(String userDeviceIdKey) throws     Exception{
//
//        String authKey = AUTH_KEY_FCM;   // You FCM AUTH key
//        String FMCurl = API_URL_FCM;
//
//        URL url = new URL(FMCurl);
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//
//        conn.setUseCaches(false);
//        conn.setDoInput(true);
//        conn.setDoOutput(true);
//
//        conn.setRequestMethod("POST");
//        conn.setRequestProperty("Authorization","key="+authKey);
//        conn.setRequestProperty("Content-Type","application/json");
//
//        JSONObject json = new JSONObject();
//        json.put("to",userDeviceIdKey.trim());
//        JSONObject info = new JSONObject();
//        info.put("title", "Notificatoin Title");   // Notification title
//        info.put("body", "Hello Test notification"); // Notification body
//        json.put("notification", info);
//
//        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
//        wr.write(json.toString());
//        wr.flush();
//        conn.getInputStream();
//    }
}