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
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.scorer.Adapter.Player_Adapter;
import com.scorer.Adapter.Player_Adapter_select;
import com.scorer.Crop.Crop;
import com.scorer.LocalDatabase.Playerdatabase;
import com.scorer.LocalDatabase.Teamdatabase;
import com.scorer.ModalClass.Common;
import com.scorer.ModalClass.GetRequest;
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

public class NewTeamActivity extends AppCompatActivity implements GetRequest{
    String Game_id="",Game_name="";
    TextView Select_Game,ErrorMessage;
    ImageView Team_logo;
    ListView PlayerListview;
    Playerdatabase objPlayerdatabase;
    Teamdatabase objTeamdatabase;
    EditText Edit_Team;
    TextInputLayout Edit_Team_input;
    List<PlayerModel> objPlayerslist = new ArrayList<>();
    List<String> objSubscriber = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_team);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ImageView Backbutton = (ImageView) findViewById(R.id.Backbutton);
        Backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        objPlayerdatabase = new Playerdatabase(this);
        objTeamdatabase = new Teamdatabase(this);
        PlayerListview = (ListView)findViewById(R.id.Listview_player);
        ErrorMessage = (TextView) findViewById(R.id.ErrorMessage);
        Select_Game = (TextView) findViewById(R.id.Select_Game);
        Select_Game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Show_Games();
            }
        });

        Team_logo = (ImageView) findViewById(R.id.Team_logo);
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

        ImageView Add_player = (ImageView) findViewById(R.id.Add_player);
        Add_player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent objIntent = new Intent(NewTeamActivity.this,NewPlayerActivity.class);
                objIntent.putExtra("Screenid","Newteam");
                startActivity(objIntent);
            }
        });
        ImageView Pick_player = (ImageView) findViewById(R.id.Pick_player);
        Pick_player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Player_popup(NewTeamActivity.this,Game_id,NewTeamActivity.this).Show();
            }
        });
        ImageView Add_subscriber = (ImageView) findViewById(R.id.Add_subscriber);
        Add_subscriber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        ImageView Pick_subscriber = (ImageView) findViewById(R.id.Pick_subscriber);
        Pick_subscriber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        Edit_Team_input = (TextInputLayout) findViewById(R.id.Edit_Team_input);
        Edit_Team = (EditText) findViewById(R.id.Edit_Team);
        Button Submit = (Button) findViewById(R.id.Submit_Team);
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit_Button();
            }
        });
    }
    void Submit_Button()
    {
        ErrorMessage.setVisibility(View.GONE);
        Edit_Team_input.setError(null);
        if(!Validation.Text(Edit_Team,"Enter Team Name"))
            return;
        TeamModel objTeamModel = new TeamModel();
        objTeamModel.setUniqueid(objTeamdatabase.Get_uniqueid());
        objTeamModel.setTeamimage(Bgimagebyte);
        objTeamModel.setTeamname(Edit_Team.getText().toString());
        if(Game_id.length()>1)
        {
            objTeamModel.setGameid(Game_id);
            objTeamModel.setGamename(Game_name);
        } else {
            ErrorMessage.setVisibility(View.VISIBLE);
            ErrorMessage.setText("*Please Select Game");
            return;
        }
        if(Common.objPlayers.size()==0)
        {
            ErrorMessage.setVisibility(View.VISIBLE);
            ErrorMessage.setText("*Add Players to submit the team");
            return;
        } else {
            objTeamModel.setTeamplayer(Common.ArraytoString(Common.objPlayers));
        }
        if(objSubscriber.size()!=0)
        {
            objTeamModel.setTeamsubscriber(Common.ArraytoString(objSubscriber));
        } else {
            objTeamModel.setTeamsubscriber("");
        }
        objTeamModel.setStatus("");
        objTeamdatabase.AddRecord(objTeamModel);
        startActivity(new Intent(NewTeamActivity.this,TeamsActivity.class));
    }
    void Load_Players()
    {
        objPlayerslist = new ArrayList<>();
        for(int i=0;i<Common.objPlayers.size();i++)
        {
            objPlayerslist.add(objPlayerdatabase.GetRecords_id(Common.objPlayers.get(i)));
        }
        PlayerListview.setAdapter(new Player_Adapter_select(this,objPlayerslist));
        Common.ListViewHeightBasedOnChildren(PlayerListview);
        PlayerListview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
            {
                ShowRemoveAlert(objPlayerslist.get(position).getUniqueid(),objPlayerslist.get(position).getPlayername());
            }
        });
    }

    void ShowRemoveAlert(final String Content,String Name)
    {
        android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(NewTeamActivity.this);
        builder1.setMessage("Are you sure want to remove "+Name+" ?");
        builder1.setCancelable(false);

        builder1.setPositiveButton(
                "Remove",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Common.objPlayers.remove(Content);
                        dialog.cancel();
                        Load_Players();
                    }
                });
        builder1.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        android.app.AlertDialog alert11 = builder1.create();
        alert11.show();
    }
    public void Show_Games()
    {
        List<String> objGames = new ArrayList<>();
        for (gamesmenu objmenu:Common.GetGames()) {
            objGames.add(objmenu.getName().toString());
        }
        //Create sequence of items
        final CharSequence[] Animals = objGames.toArray(new String[objGames.size()]);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Select Game");
        dialogBuilder.setItems(Animals, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                Game_name = Common.GetGames().get(item).getName().toString();
                Game_id = Common.GetGames().get(item).getId().toString();
                Select_Game.setText(Game_name);
            }
        });
        //Create alert dialog object via builder
        AlertDialog alertDialogObject = dialogBuilder.create();
        //Show the dialog
        alertDialogObject.show();
    }
    void Gallery_Button()
    {
        Crop.pickImage(NewTeamActivity.this);
    }
    void Remove_Button()
    {
        Bgimagebyte = null;
        Glide.with(NewTeamActivity.this)
                .load(R.drawable.defaultimg)
                .into(Team_logo);

    }
    void Camera_Button()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(NewTeamActivity.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(NewTeamActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(NewTeamActivity.this, new String[]
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
            if (ContextCompat.checkSelfPermission(NewTeamActivity.this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(NewTeamActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                CreateDirectoryForPictures();
                intent.putExtra(MediaStore.EXTRA_OUTPUT,Camerapath);
                startActivityForResult(intent, 200);
            }
        } else if (requestCode == Crop.REQUEST_PICK) {
            Crop.of(result.getData(), Camerapath).asSquare().start(NewTeamActivity.this);
        } else if (requestCode == 200) {
            Crop.of(Camerapath, Camerapath).asSquare().start(NewTeamActivity.this);
        } else if (requestCode == Crop.REQUEST_CROP) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bgimage = BitmapFactory.decodeFile(Crop.getOutput(result).getPath(), options);
            Team_logo.setImageBitmap(ImageOrientation(Crop.getOutput(result).getPath()));
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
            if (ContextCompat.checkSelfPermission(NewTeamActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(NewTeamActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(NewTeamActivity.this, new String[]
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
        File _file = new File(_dir, "File_"+UUID.randomUUID().toString()+".jpg");
        Camerapath = Uri.fromFile(_file);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Load_Players();
    }

    @Override
    public void Callback(String result) {
        ErrorMessage.setVisibility(View.GONE);
        if(!Common.objPlayers.contains(result)) {
            Common.objPlayers.add(result);
            Load_Players();
        } else {
            Toast.makeText(NewTeamActivity.this,"Player Already Exist",Toast.LENGTH_SHORT).show();
        }
    }
}
