package com.scorer.Activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.scorer.Crop.Crop;
import com.scorer.LocalDatabase.Subscriberdatabase;
import com.scorer.LocalDatabase.Subscriberdatabase;
import com.scorer.ModalClass.Common;
import com.scorer.ModalClass.GameModel;
import com.scorer.ModalClass.GetTeam;
import com.scorer.ModalClass.PlayerModel;
import com.scorer.ModalClass.TeamModel;
import com.scorer.ModalClass.gamesmenu;
import com.scorer.R;
import com.scorer.Validation;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class NewSubscriberActivity extends AppCompatActivity implements GetTeam {
    ImageView Player_logo;
    String Screen_id;
    EditText Edit_Email,Edit_Player;
    Subscriberdatabase objSubscriberdatabase;
    TextInputLayout Edit_Player_input,Edit_Email_input;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_subscriber);
        objSubscriberdatabase = new Subscriberdatabase(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ImageView Backbutton = (ImageView) findViewById(R.id.Backbutton);
        Backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Screen_id = getIntent().getStringExtra("Screenid");
        Player_logo = (ImageView) findViewById(R.id.Player_logo);
        ImageView Galleryimg = (ImageView) findViewById(R.id.Galleryimg);
        Galleryimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gallery_Button();
            }
        });
        TextView Gallerytext = (TextView) findViewById(R.id.Gallerytext);
        Gallerytext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gallery_Button();
            }
        });
        RelativeLayout Gallerylayout = (RelativeLayout) findViewById(R.id.Gallerylayout);
        Gallerylayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gallery_Button();
            }
        });

        ImageView Cameraimg = (ImageView) findViewById(R.id.Cameraimg);
        Cameraimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Camera_Button();
            }
        });
        TextView Cameratext = (TextView) findViewById(R.id.Cameratext);
        Cameratext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Camera_Button();
            }
        });
        RelativeLayout Cameralayout = (RelativeLayout) findViewById(R.id.Cameralayout);
        Cameralayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Camera_Button();
            }
        });

        ImageView RemoveImage = (ImageView) findViewById(R.id.RemoveImage);
        RemoveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Remove_Button();
            }
        });

        Edit_Player_input = (TextInputLayout) findViewById(R.id.Edit_Player_input);
        Edit_Email_input = (TextInputLayout) findViewById(R.id.Edit_Email_input);
        Edit_Player = (EditText) findViewById(R.id.Edit_Player);
        Edit_Email = (EditText) findViewById(R.id.Edit_Email);
        Button Submit = (Button) findViewById(R.id.Submit);
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit_Button();
            }
        });
        Button Pick_contact = (Button) findViewById(R.id.Pick_contact);
        Pick_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Contacts_popup(NewSubscriberActivity.this,NewSubscriberActivity.this).Show();
            }
        });

    }
    void Submit_Button()
    {
        if(!Validation.Text(Edit_Player,"Enter Subscriber Name"))
            return;
        if(!Validation.Email(Edit_Email))
            return;
        PlayerModel objPlayerModel = new PlayerModel();
        objPlayerModel.setUniqueid(objSubscriberdatabase.Get_uniqueid());
        objPlayerModel.setGameid("");
        objPlayerModel.setGamename("");
        objPlayerModel.setPlayerimage(Bgimagebyte);
        objPlayerModel.setPlayername(Edit_Player.getText().toString());
        objPlayerModel.setPlayeremail(Edit_Email.getText().toString());
        objPlayerModel.setPlayerposition("");
        objPlayerModel.setStatus("");
        objSubscriberdatabase.AddRecord(objPlayerModel);
        if(Screen_id.equals("Newteam")) {
            Common.objPlayers.add(objPlayerModel.getUniqueid());
        }
        finish();
    }
    void Gallery_Button()
    {
        Crop.pickImage(NewSubscriberActivity.this);
    }
    void Remove_Button()
    {
        Bgimagebyte = null;
        Glide.with(NewSubscriberActivity.this)
                .load(R.drawable.defaultimg)
                .into(Player_logo);

    }
    void Camera_Button()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(NewSubscriberActivity.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(NewSubscriberActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(NewSubscriberActivity.this, new String[]
                        {
                                WRITE_EXTERNAL_STORAGE,
                                READ_EXTERNAL_STORAGE,
                                Manifest.permission.CAMERA,
                        }, 100);
            } else {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                CreateDirectoryForPictures();
                intent.putExtra(MediaStore.EXTRA_OUTPUT,Camerapath);
                startActivityForResult(intent, 200);
            }
        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            CreateDirectoryForPictures();
            intent.putExtra(MediaStore.EXTRA_OUTPUT,Camerapath);
            startActivityForResult(intent, 200);
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        if (resultCode != RESULT_OK) {
            return;
        }
        if (resultCode == 100) {
            if (ContextCompat.checkSelfPermission(NewSubscriberActivity.this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(NewSubscriberActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                CreateDirectoryForPictures();
                intent.putExtra(MediaStore.EXTRA_OUTPUT,Camerapath);
                startActivityForResult(intent, 200);
            }
        } else if (requestCode == Crop.REQUEST_PICK) {
            Crop.of(result.getData(), Camerapath).asSquare().start(NewSubscriberActivity.this);
        } else if (requestCode == 200) {
            Crop.of(Camerapath, Camerapath).asSquare().start(NewSubscriberActivity.this);
        } else if (requestCode == Crop.REQUEST_CROP) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bgimage = BitmapFactory.decodeFile(Crop.getOutput(result).getPath(), options);
            Player_logo.setImageBitmap(ImageOrientation(Crop.getOutput(result).getPath()));
            if(Bgimage != null) {
                bao = new ByteArrayOutputStream();
                Bgimage.compress(Bitmap.CompressFormat.JPEG, 50, bao);
                Bgimagebyte = bao.toByteArray();
//                public static String imagebase64;
//                public static String encodedImage;
//                encodedImage = Base64.encodeToString(Bgimagebyte, Base64.NO_WRAP);
//                imagebase64 = encodedImage;
//                Log.e("imagebase64", imagebase64);
            }
        }
    }
    Bitmap Bgimage;
    byte[] Bgimagebyte = null;
    ByteArrayOutputStream bao;
    public static Uri Camerapath;
    private Bitmap ImageOrientation(String path) {

        ExifInterface ei;
        try {
            ei = new ExifInterface(path);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    Bgimage = rotateImage(Bgimage, 90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    Bgimage = rotateImage(Bgimage, 180);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    Bgimage = rotateImage(Bgimage, 270);
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Bgimage;
    }
    private Bitmap rotateImage(Bitmap source, float angle) {

        Bitmap bitmap = null;
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        try {
            bitmap = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                    matrix, true);
        } catch (OutOfMemoryError err) {
            err.printStackTrace();
        }
        return bitmap;
    }
    private void CreateDirectoryForPictures()
    {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(NewSubscriberActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(NewSubscriberActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(NewSubscriberActivity.this, new String[]
                        {
                                WRITE_EXTERNAL_STORAGE,
                                READ_EXTERNAL_STORAGE,
                        }, 100);
                return;
            }
        }
        File _dir = new File(android.os.Environment.getExternalStorageDirectory(), "Scorer/images");
        if (!_dir.exists())
        {
            _dir.mkdirs();
        }
        File _file = new File(_dir, "File_"+ UUID.randomUUID().toString()+".jpg");
        Camerapath = Uri.fromFile(_file);
    }

    @Override
    public void Callbackteam(TeamModel result, String players) {
        Edit_Player.setText(result.getTeamname());
        if(result.getGamename() != null && result.getGamename().length() > 0) {
            Edit_Email.setText(result.getGamename());
        }
        if(result.getTeamplayer() != null && result.getTeamplayer().length() > 0) {
            Glide.with(NewSubscriberActivity.this)
                    .load(result.getTeamplayer())
                    .placeholder(R.drawable.defaultimg)
                    .skipMemoryCache(true)
                    .override(80, 80)
                    .into(Player_logo);
        }
    }
}
