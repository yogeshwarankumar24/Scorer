package com.scorer.ModalClass;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.scorer.R;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;


public class Common {
    public static String Userid;
    public static String Username;
    public static String Useremail;
    public static String Userpassword;
    public static String Userlocation;
    public static String Usergender;

    public static TennisModel Sharedata = new TennisModel();
    public static boolean ConnectingToInternet(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null && connectivity.getActiveNetworkInfo().isAvailable() && connectivity.getActiveNetworkInfo().isConnected()) {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null)
                    for (int i = 0; i < info.length; i++)
                        if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                            return new InternetCheck().execute().get();
                        }

            }
            return false;
        } catch (InterruptedException ex) {
            return false;

        } catch (ExecutionException ex) {
            return false;
        }

    }
    public static class InternetCheck extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... args) {
            try {
                InetAddress ipAddr = InetAddress.getByName("google.com");
                return !ipAddr.equals("");
            } catch (UnknownHostException ex) {
                return false;
            }
        }
        protected void onPostExecute(Boolean th) {
            return;
        }
    }
    public static Bitmap TakeScrollviewScreen(ScrollView scrollView,String Filepath,String Filename) {
        int h = 0;
        Bitmap bitmap = null;
        //get the actual height of scrollview
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
            scrollView.getChildAt(i).setBackgroundResource(R.color.white);
        }
        // create bitmap with target size
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h,
                Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);

        File myDir = new File(Filepath);
        myDir.mkdirs();
        String fname = Filename;
        File file = new File (myDir, fname);
        try {
            if (file.exists ()) file.delete ();
            file.createNewFile();
        } catch (IOException e) {
            // TODO: handle exception
        }
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            if (null != out) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                out.flush();
                out.close();
            }
        } catch (IOException e) {
            // TODO: handle exception
        }
        return bitmap;
    }
    public static Bitmap TakeScreen(View v, int width, int height) {
        try {
            Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(b);
            v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);
            v.draw(c);
            return b;
        } catch (Exception ex) {
            return null;
        }
    }

    public static List<gamesmenu> GetGames()
    {
        List<gamesmenu> objGames = new ArrayList<>();
        objGames.add(new gamesmenu("G0001","Tennis",R.drawable.tennis));
        objGames.add(new gamesmenu("G0002","Ping pong",R.drawable.pingpong));
        objGames.add(new gamesmenu("G0003","Basketball",R.drawable.basketball));
        objGames.add(new gamesmenu("G0004","Batminton",R.drawable.badminton));
        objGames.add(new gamesmenu("G0005","volleyball",R.drawable.volleyball));
        objGames.add(new gamesmenu("G0006","Soccer",R.drawable.soccer));
        objGames.add(new gamesmenu("G0007","American Football",R.drawable.americanfootball));
        objGames.add(new gamesmenu("G0008","Baseball",R.drawable.baseball));
        objGames.add(new gamesmenu("G0009","Cricket",R.drawable.cricket));
        objGames.add(new gamesmenu("G0010","Rummy",R.drawable.rummy));
        objGames.add(new gamesmenu("G0011","5 cards",R.drawable.cards5));
        objGames.add(new gamesmenu("G0012","Poker",R.drawable.poker));
        objGames.add(new gamesmenu("G0013","Blackjack",R.drawable.blackjack));
        objGames.add(new gamesmenu("G0014","Bowling",R.drawable.bowling));
        return objGames;
    }
    public static List<String> objPlayers = new ArrayList<>();
    public static List<String> GetPlayerposition()
    {
        List<String> objPlayer = new ArrayList<>();
        objPlayer.add("Coach");
        objPlayer.add("Senior Player");
        objPlayer.add("Junior Player");
        objPlayer.add("Other");
        return objPlayer;
    }
    public static String ArraytoString(List<String> objArray) {
        String result = "";
        for (int i = 0; i < objArray.size(); i++) {
            if (i > 0) {
                result = result + "#@#";
            }
            String item = objArray.get(i);
            result = result + item;
        }
        return result;
    }
    public static List<String> StringtoArray(String objString) {
        List<String> objArray = new ArrayList<>();
        final String[] objTokens = objString.split("#@#");
        for (int i = 0; i < objTokens.length; i++) {
            objArray.add(objTokens[i]);
        }
        return objArray;
    }
    public static void ListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
    public static String CurrentDatetimeString() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd:MM:yyyy HH:mm:ss");
        String Date_str = formatter.format(new Date(System.currentTimeMillis()));
        return Date_str;
    }
    public static Date StringtoDate(String objString) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd:MM:yyyy HH:mm:ss");
        Date objDate = null;
        try {
            objDate = formatter.parse(objString);
        } catch (ParseException ex) {
        }
        return objDate;
    }
    public static String DatetoString(Date objDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd:MM:yyyy HH:mm:ss");
        String Date_str = formatter.format(objDate);
        return Date_str;
    }
    public static String DatetoStringAMPM(Date objDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy K:mm a");
        String Date_str = formatter.format(objDate);
        return Date_str;
    }
    public static String DatetoDateonly(Date objDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(objDate);
        SimpleDateFormat printFormat = new SimpleDateFormat("dd");
        String Date_str = printFormat.format(calendar.getTime());
        return Date_str;
    }
    public static String DatetoMonthonly(Date objDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(objDate);
        SimpleDateFormat printFormat = new SimpleDateFormat("MM");
        String Date_str = printFormat.format(calendar.getTime());
        return Month(Date_str);
    }
    public static String DatetoTimeAMPM(Date objDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(objDate);
        SimpleDateFormat printFormat = new SimpleDateFormat("K:mm a");
        String Date_str = printFormat.format(calendar.getTime());
        return Date_str;
    }

    public static String Month(String Monthcode)
    {
        switch (Monthcode)
        {
            case "01":
            {
                return "January";
            }
            case "02":
            {
                return "February";
            }
            case "03":
            {
                return "March";
            }
            case "04":
            {
                return "April";
            }
            case "05":
            {
                return "May";
            }
            case "06":
            {
                return "June";
            }
            case "07":
            {
                return "July";
            }
            case "08":
            {
                return "August";
            }
            case "09":
            {
                return "September";
            }
            case "10":
            {
                return "October";
            }
            case "11":
            {
                return "November";
            }
            case "12":
            {
                return "December";
            }
        }
        return "";
    }

    public static void CreateFolder()
    {

    }

    public static void SendNotification(Activity objthis,String googlekey) {

        JSONObject Notificationjson = new JSONObject();
        try {
            Notificationjson.put("to", googlekey);
            JSONObject info = new JSONObject();
            info.put("title", "Notificatoin Title");   // Notification title
            info.put("body", "Hello Test notification"); // Notification body
            Notificationjson.put("notification", info);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        final String URL = "https://fcm.googleapis.com/fcm/send";

        Log.e("Error", Notificationjson.toString());
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, URL, Notificationjson, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("onResponse", response.toString());
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.e("Error", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put("Authorization", "key=" + "AIzaSyB0tYaYjPN7je1uRcvt68mcG7bmGOVvaGM");
                header.put("Content-Type", "application/json");
                Log.e("Headers", "Authorization");
                return header;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(objthis);
        requestQueue.add(req);
    }
    public static void SendNotification(Activity objthis) {

        JSONObject Notificationjson = new JSONObject();
        try {
            Notificationjson.put("to", "/topics/all");
            JSONObject info = new JSONObject();
            info.put("title", "Notificatoin Title");   // Notification title
            info.put("body", "Hello Test notification"); // Notification body
            Notificationjson.put("notification", info);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        final String URL = "https://fcm.googleapis.com/fcm/send";

        Log.e("Error", Notificationjson.toString());
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, URL, Notificationjson, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("onResponse", response.toString());
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.e("Error", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put("Authorization", "key=" + "AIzaSyB0tYaYjPN7je1uRcvt68mcG7bmGOVvaGM");
                header.put("Content-Type", "application/json");
                Log.e("Headers", "Authorization");
                return header;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(objthis);
        requestQueue.add(req);
    }
}
