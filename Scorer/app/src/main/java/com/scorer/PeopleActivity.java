package com.scorer;

import android.*;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.api.services.people.v1.People;
import com.google.api.services.people.v1.PeopleScopes;
import com.google.api.services.people.v1.model.EmailAddress;
import com.google.api.services.people.v1.model.ListConnectionsResponse;
import com.google.api.services.people.v1.model.Name;
import com.google.api.services.people.v1.model.Person;
import com.google.api.services.people.v1.model.PhoneNumber;
import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.contacts.ContactFeed;
import com.google.gdata.data.extensions.Email;
import com.scorer.Activity.HomeActivity;
import com.scorer.Adapter.Contact_Adapter;
import com.scorer.LocalDatabase.Contactdatabase;
import com.scorer.ModalClass.Userdetails;
import com.scorer.Widgets.AppPreferences;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class PeopleActivity extends AppCompatActivity {

    private static final String TAG = "PeopleActivity";
    AppPreferences objAppPreferences;
    ListView objListView;
    RelativeLayout SyncLayout;
    LinearLayout Contactslayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gmail_contacts);
        objAppPreferences = new AppPreferences(this);
        SyncLayout = (RelativeLayout)findViewById(R.id.SyncLayout);
        Contactslayout = (LinearLayout)findViewById(R.id.Contactslayout);
        SyncLayout.setVisibility(View.VISIBLE);
        Contactslayout.setVisibility(View.GONE);
        TextView Syncbutton = (TextView)findViewById(R.id.Syncbutton);
        Syncbutton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GoogleAuthToken(context).execute(objAppPreferences.Get_AuthoToken());
            }
        });
        TextView Skipbutton = (TextView)findViewById(R.id.Skipbutton);
        Skipbutton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PeopleActivity.this, HomeActivity.class));
            }
        });
        TextView Submitbutton = (TextView)findViewById(R.id.Submitbutton);
        Submitbutton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PeopleActivity.this, HomeActivity.class));
            }
        });

        objListView = (ListView) findViewById(R.id.Listview);
    }

    final Context context = PeopleActivity.this;

    private class GoogleAuthToken extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;
        private Context context;

        public GoogleAuthToken(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Sync Google Contacts ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    finish();
                }
            });
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {
            String authCode = args[0];
            GetAccessToken jParser = new GetAccessToken();
            JSONObject json = jParser.gettoken(GoogleConstants.TOKEN_URL,
                    authCode, GoogleConstants.CLIENT_ID,
                    GoogleConstants.CLIENT_SECRET,
                    GoogleConstants.REDIRECT_URI, GoogleConstants.GRANT_TYPE);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            pDialog.dismiss();
            if (json != null) {
                try {
                    String tok = json.getString("access_token");
                    String expire = json.getString("expires_in");
                    new GetGoogleContacts(context).execute(tok);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class GetGoogleContacts extends AsyncTask<String, String, List<ContactEntry>> {

        private ProgressDialog pDialog;
        private Context context;

        public GetGoogleContacts(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Sync Google Contacts ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    finish();
                }
            });
            pDialog.show();
        }

        @Override
        protected List<ContactEntry> doInBackground(String... args) {
            String accessToken = args[0];
            ContactsService contactsService = new ContactsService(GoogleConstants.APP);
            contactsService.setHeader("Authorization", "Bearer " + accessToken);
            contactsService.setHeader("GData-Version", "3.0");
            List<ContactEntry> contactEntries = null;
            try {
                URL feedUrl = new URL(GoogleConstants.CONTACTS_URL);
                ContactFeed resultFeed = contactsService.getFeed(feedUrl,ContactFeed.class);
                contactEntries = resultFeed.getEntries();
            } catch (Exception e) {
                e.printStackTrace();
                pDialog.dismiss();
//                Toast.makeText(context, "Failed to get Contacts",Toast.LENGTH_SHORT).show();
            }
            return contactEntries;
        }

        @Override
        protected void onPostExecute(List<ContactEntry> googleContacts) {
            if (null != googleContacts && googleContacts.size() > 0) {
                List<Userdetails> contacts = new ArrayList<Userdetails>();
                int i = 0;
                for (ContactEntry contactEntry : googleContacts) {
                    String name = "";
                    String email = "";
                    String phone = "";

                    if (contactEntry.hasName()) {
                        com.google.gdata.data.extensions.Name tmpName = contactEntry.getName();
                        if (tmpName.hasFullName()) {
                            name = tmpName.getFullName().getValue();
                        } else {
                            if (tmpName.hasGivenName()) {
                                name = tmpName.getGivenName().getValue();
                                if (tmpName.getGivenName().hasYomi()) {
                                    name += " ("
                                            + tmpName.getGivenName().getYomi()
                                            + ")";
                                }
                                if (tmpName.hasFamilyName()) {
                                    name += tmpName.getFamilyName().getValue();
                                    if (tmpName.getFamilyName().hasYomi()) {
                                        name += " ("
                                                + tmpName.getFamilyName()
                                                .getYomi() + ")";
                                    }
                                }
                            }
                        }
                    }
                    List<Email> emails = contactEntry.getEmailAddresses();
                    if (null != emails && emails.size() > 0) {
                        Email tempEmail = (Email) emails.get(0);
                        email = tempEmail.getAddress();
                    }

                    List<com.google.gdata.data.extensions.PhoneNumber> Phone = contactEntry.getPhoneNumbers();
                    if (null != Phone && Phone.size() > 0) {
                        com.google.gdata.data.extensions.PhoneNumber tempPhone = (com.google.gdata.data.extensions.PhoneNumber) Phone.get(0);
                        phone = tempPhone.getPhoneNumber();
                    }

                    i++;
                    Userdetails contact = new Userdetails(String.valueOf(i),name, email, phone,"","");
                    contacts.add(contact);
                }
                setContactList(contacts);

            } else {
                Log.e(TAG, "No Contact Found.");
                Toast.makeText(context, "No Contact Found.", Toast.LENGTH_SHORT)
                        .show();
            }
            pDialog.dismiss();
        }

    }

    private void setContactList(List<Userdetails> contacts) {
//        Log.e("Values",contacts.toString());
        Contactdatabase objContactdatabase = new Contactdatabase(PeopleActivity.this);
        objContactdatabase.AddRecord(contacts);
        objListView.setAdapter(new Contact_Adapter(PeopleActivity.this,contacts));
        SyncLayout.setVisibility(View.GONE);
        Contactslayout.setVisibility(View.VISIBLE);
    }
}
