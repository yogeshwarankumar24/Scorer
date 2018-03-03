package com.scorer.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.scorer.Adapter.Contact_Adapter;
import com.scorer.Adapter.Player_Adapter;
import com.scorer.LocalDatabase.Contactdatabase;
import com.scorer.LocalDatabase.Playerdatabase;
import com.scorer.ModalClass.Common;
import com.scorer.ModalClass.ContactModel;
import com.scorer.ModalClass.GetRequest;
import com.scorer.ModalClass.GetTeam;
import com.scorer.ModalClass.PlayerModel;
import com.scorer.ModalClass.TeamModel;
import com.scorer.ModalClass.Userdetails;
import com.scorer.ModalClass.gamesmenu;
import com.scorer.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by Android on 11/14/2017.
 */

public class Contacts_popup {
    Activity objActivity;
    Dialog Popup_Layout;
    ListView objListView;
    EditText Search_contact;
    private List<Userdetails> objContactModel;
    Contactdatabase objContactdatabase;
    GetTeam objGetRequest;
    public Contacts_popup(Activity objActivityv, GetTeam objGetRequestv) {
        objActivity = objActivityv;
        objGetRequest = objGetRequestv;
        objContactdatabase = new Contactdatabase(objActivity);
        Popup_Layout = new Dialog(objActivity, R.style.MaterialDialogSheet);
        Popup_Layout.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Popup_Layout.setContentView(R.layout.contact_popup);
        Popup_Layout.setCancelable(true);
        Popup_Layout.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        Popup_Layout.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        ImageView closebutton = (ImageView) Popup_Layout.findViewById(R.id.closebutton);
        objListView = (ListView) Popup_Layout.findViewById(R.id.Listview);
        Search_contact = (EditText) Popup_Layout.findViewById(R.id.Search_contact);
        closebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Popup_Layout.dismiss();
            }
        });
        Contacts_Button();
    }
    void Load_Players()
    {
        objContactModel = new ArrayList<>();
        objContactModel = objContactdatabase.GetRecords();
        getContacts(objActivity);
        objListView.setAdapter(new Contact_Adapter(objActivity,objContactModel));
        objListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
            {
                TeamModel objteam = new TeamModel();
                objteam.setTeamname(objContactModel.get(position).getName());
                objteam.setGamename(objContactModel.get(position).getemail());
                objteam.setTeamplayer(objContactModel.get(position).getLocation());
                objteam.setUniqueid(objContactModel.get(position).getId());
                objGetRequest.Callbackteam(objteam,objteam.getTeamname());
                Popup_Layout.dismiss();
            }
        });
    }
    void Contacts_Button()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(objActivity, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(objActivity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(objActivity, new String[]
                        {
                                WRITE_EXTERNAL_STORAGE,
                                READ_EXTERNAL_STORAGE,
                                Manifest.permission.READ_CONTACTS,
                        }, 100);
            } else {
                Load_Players();
            }
        } else {
            Load_Players();
        }
    }
    public void getContacts(Context ctx) {
        int userid = objContactModel.size();
        ContentResolver contentResolver = ctx.getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                if (cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor cursorInfo = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                    InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(ctx.getContentResolver(),
                            ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, new Long(id)));

                    Uri person = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, new Long(id));
                    Uri pURI = Uri.withAppendedPath(person, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);

                    Bitmap photo = null;
                    if (inputStream != null) {
                        photo = BitmapFactory.decodeStream(inputStream);
                    }
                    while (cursorInfo.moveToNext()) {
                        userid++;
                        Userdetails info = new Userdetails();
                        info.setId(String.valueOf(userid));
                        info.setName(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
                        info.setpassword(cursorInfo.getString(cursorInfo.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                        info.setLocation(pURI.getPath());
                        objContactModel.add(info);
                    }

                    cursorInfo.close();
                }
            }
        }
    }
    public void Show() {
        Popup_Layout.show();
    }
    public void Dismiss() {
        Popup_Layout.dismiss();
    }



}
