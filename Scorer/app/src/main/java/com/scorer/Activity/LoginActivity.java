package com.scorer.Activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.api.services.people.v1.PeopleScopes;
import com.scorer.GoogleConstants;
import com.scorer.LocalDatabase.Userdatabase;
import com.scorer.MainActivity;
import com.scorer.ModalClass.Common;
import com.scorer.ModalClass.Userdetails;
import com.scorer.PeopleActivity;
import com.scorer.R;
import com.scorer.Widgets.AppPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    GoogleApiClient mGoogleApiClient;
    final int RC_SIGN_IN = 100;
    private CallbackManager callbackManager;
    String StaticUsername = "";
    Userdatabase objUserdatabase;
    AppPreferences objAppPreferences;
    Button Skipbutton;
    ImageView Facebookbutton,Gmailbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        objUserdatabase = new Userdatabase(this);
        objAppPreferences = new AppPreferences(this);
        ImageView Splashimage = (ImageView)findViewById(R.id.Splashimage);
        Gmailbutton = (ImageView)findViewById(R.id.Gmailbutton);
        Facebookbutton = (ImageView)findViewById(R.id.Facebookbutton);
        Skipbutton = (Button)findViewById(R.id.Skipbutton);
        Glide.with(LoginActivity.this)
                .load(R.drawable.splash)
                .skipMemoryCache(true)
                .override(getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels/3)
                .into(Splashimage);
        if (Build.VERSION.SDK_INT >= 23){
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED )
            {
                ActivityCompat.requestPermissions(this, new String[] {
                        android.Manifest.permission.ACCESS_COARSE_LOCATION,
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        android.Manifest.permission.CAMERA
                },1);
            }
        }

        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                // The serverClientId is an OAuth 2.0 web client ID
                .requestServerAuthCode(GoogleConstants.CLIENT_ID)
                .requestEmail()
                .requestScopes(new Scope(Scopes.PLUS_LOGIN),
                        new Scope(PeopleScopes.CONTACTS_READONLY),
                        new Scope(PeopleScopes.USER_EMAILS_READ),
                        new Scope(PeopleScopes.USERINFO_EMAIL),
                        new Scope(PeopleScopes.USER_PHONENUMBERS_READ))
                .build();


        // To connect with Google Play Services and Sign In
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions)
                .build();


        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // info.setText();
                Log.e("Facebook :    ","User ID:  "+loginResult.getAccessToken().getUserId() + "\n" + "Auth Token: " + loginResult.getAccessToken().getToken());
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                try {
                                    Log.i("JSONObject",object.toString());
                                    Log.i("Response",response.toString());
                                    String email = "",name="";
                                    int generin = 0;
                                    String gender = response.getJSONObject().getString("gender");
                                    String fbname = response.getJSONObject().getString("name");

                                    if(Profile.getCurrentProfile() != null) {
                                        Profile profile = Profile.getCurrentProfile();
                                        String id = profile.getId();
                                        String link = profile.getLinkUri().toString();
                                        if (Profile.getCurrentProfile() != null) {
                                            Log.i("Login", "ProfilePic " + Profile.getCurrentProfile().getProfilePictureUri(300, 300));
                                        }
                                        Log.i("Login" + "LastName", Profile.getCurrentProfile().getName());
                                        Log.i("Login" + "Gender", gender);
                                        if (gender.toLowerCase() == "female")
                                            generin = 1;
                                        if (Profile.getCurrentProfile().getName().contains(" "))
                                            name = Profile.getCurrentProfile().getName().replace(" ", "");
                                        if (response.getJSONObject().toString().contains("email")) {
                                            email = response.getJSONObject().getString("email");
                                            StaticUsername = response.getJSONObject().getString("email");
                                        } else {
                                            StaticUsername = name;
                                        }
                                        ShowRegistration(Profile.getCurrentProfile().getName(), email, "", "", "", generin, Profile.getCurrentProfile().getProfilePictureUri(300, 300).toString(),false);
                                        LoginManager.getInstance().logOut();
                                    } else {
                                        if (response.getJSONObject().toString().contains("email")) {
                                            email = response.getJSONObject().getString("email");
                                            StaticUsername = response.getJSONObject().getString("email");
                                        } else {
                                            if (fbname.contains(" "))
                                                StaticUsername = fbname.replace(" ", "");
                                        }
                                        if (gender.toLowerCase() == "female")
                                            generin = 1;
                                        ShowRegistration(fbname, email, "", "", "", generin, "",false);
                                        LoginManager.getInstance().logOut();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    //(FirstActivity.this,  "Network Error", "Please Try Again",  false);
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,link,email,first_name,last_name,birthday,gender,friendlists{id,name,list_type}");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                //  info.setText("Login attempt cancelled.");
                Log.e("Facebook :   ","Login attempt cancelled.");
            }

            @Override
            public void onError(FacebookException e) {
                // info.setText("Login attempt failed.");Invalid key hash. The key hash gZJ4apVg2eSC7yBS5Xx7i3QxTKc= does not match any stored key hashes. Configure your app key hashes at https://developers.facebook.com/apps/1458708734161482/
                Log.e("Facebook :   ","Login attempt failed.");
            }
        });
        Gmailbutton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticUsername = "";
                signIn();
            }
        });
        Facebookbutton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticUsername = "";
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile","email","user_friends","read_custom_friendlists"));
            }
        });
        GenarateHash();
    }
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    void ShowRegistration(String Name,String Email,String Age,String Mobile,String Location,int gender,String Imagelink,boolean value)
    {
        Log.e("Name :   ",Name);
        Log.e("Email :   ",Email);
        Log.e("Imagelink :   ",Imagelink);
        Log.e("StaticUsername :   ",StaticUsername);
        Userdetails objUserdetails = new Userdetails();
        objUserdetails.setName(Name);
        objUserdetails.setemail(Email);
        objUserdetails.setpassword(StaticUsername);
        objUserdetails.setLocation(Imagelink);
        objUserdetails.setgender(String.valueOf(gender));
        objUserdetails.setId("U001");
        objUserdatabase.AddRecord(objUserdetails);
        if(value) {
            startActivity(new Intent(LoginActivity.this,PeopleActivity.class));
        }else {
            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
        }
    }
    public void GenarateHash()
    {
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.scorer", PackageManager.GET_SIGNATURES);
            for (android.content.pm.Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String sign= Base64.encodeToString(md.digest(), Base64.DEFAULT);
                Log.e("MY KEY HASH:", sign);
            }
        } catch (PackageManager.NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String image="";
        switch (requestCode) {
            case RC_SIGN_IN:
                Log.d("", "sign in result");
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

                if (result.isSuccess()) {
                    GoogleSignInAccount acct = result.getSignInAccount();
                    StaticUsername = result.getSignInAccount().getEmail();
                    objAppPreferences.Set_AuthoToken(acct.getServerAuthCode());
//                    if(acct.getPhotoUrl() != null || acct.getPhotoUrl().toString() != "")
//                        image = acct.getPhotoUrl().toString();
                    Log.d("", "onActivityResult:GET_TOKEN:success:" + result.getStatus().isSuccess());
                    // This is what we need to exchange with the server.
                    Log.d("", "auth Code:" + acct.getServerAuthCode());

                    ShowRegistration(acct.getDisplayName(), acct.getEmail(), "", "", "", 0,image,true);
                    signOut();
                } else {
                    signOut();
                    Log.e("gmailloginerror", result.getStatus().toString() + "\nmsg: " + result.getStatus().getStatusMessage());
                }
                break;
        }
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        Log.i("", "signOut " + status);
                    }
                });
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}
