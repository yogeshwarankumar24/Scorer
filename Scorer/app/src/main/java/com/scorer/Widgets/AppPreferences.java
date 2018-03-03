package com.scorer.Widgets;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Android on 10/13/2017.
 */

public class AppPreferences {

    private SharedPreferences mSharedPrefs;
    private SharedPreferences.Editor mPrefsEditor;
    private Context mContext;

    public AppPreferences(Context context) {
        this.mContext = context;
        mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        mPrefsEditor = mSharedPrefs.edit();
    }

    public void Set_Mute(boolean Mute) {
        mPrefsEditor.putBoolean("Mute", Mute);
        mPrefsEditor.commit();
    }

    public boolean Get_Mute() {
        return mSharedPrefs.getBoolean("Mute", false);
    }

    public void Set_Daymode(boolean Daymode) {
        mPrefsEditor.putBoolean("Daymode", Daymode);
        mPrefsEditor.commit();
    }

    public boolean Get_Daymode() {
        return mSharedPrefs.getBoolean("Daymode", false);
    }


    public void Set_AuthoToken(String AuthoToken) {
        mPrefsEditor.putString("AuthoToken", AuthoToken);
        mPrefsEditor.commit();
    }

    public String Get_AuthoToken() {
        return mSharedPrefs.getString("AuthoToken", "");
    }


    public void Set_FCMToken(String FCMToken) {
        mPrefsEditor.putString("FCMToken", FCMToken);
        mPrefsEditor.commit();
    }

    public String Get_FCMToken() {
        return mSharedPrefs.getString("FCMToken", "");
    }

}
