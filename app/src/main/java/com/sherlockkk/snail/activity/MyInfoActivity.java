package com.sherlockkk.snail.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetCallback;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.sherlockkk.snail.R;
import com.sherlockkk.snail.ui.CircleImageView;
import com.sherlockkk.snail.utils.CacheUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * @author Simon
 * @created 2016/8/27.
 * @e-mail 986793021@qq.com
 */
public class MyInfoActivity extends Activity implements View.OnClickListener {
    private LinearLayout headPicLinearLayout;
    private TextView textView;
    private LinearLayout userNameLinearLayout;
    private LinearLayout personalnoteLinearLayout;
    private AlertDialog selectDialog;
    private TextView usernameTextView;
    private String userName;
    private String dateTime;
    private final int GET_AVATA_FROM_ALBUM = 1;
    private final int GET_AVATA_FROM_CAMERA = 2;
    private final int PHOTO_ZOOM = 3;
    private CircleImageView circleImageView;
    private TextView personalnoteTextView;
    private String personalnote;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        textView = (TextView) findViewById(R.id.tv_back);
        headPicLinearLayout = (LinearLayout) findViewById(R.id.ll_headpic);
        userNameLinearLayout = (LinearLayout) findViewById(R.id.ll_username);
        personalnoteLinearLayout = (LinearLayout) findViewById(R.id.ll_personalnote);
        circleImageView = (CircleImageView) findViewById(R.id.iv_headpic);
        usernameTextView = (TextView) findViewById(R.id.tv_username);
        personalnoteTextView = (TextView) findViewById(R.id.tv_personalnote);

        loadData();


        textView.setOnClickListener(this);
        headPicLinearLayout.setOnClickListener(this);
        userNameLinearLayout.setOnClickListener(this);
        personalnoteLinearLayout.setOnClickListener(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void loadData() {
        userName = AVUser.getCurrentUser().getUsername();
        usernameTextView.setText(userName);
        SharedPreferences sharedPreferences=getSharedPreferences("personalnote",MODE_WORLD_WRITEABLE);
       // personalnote=sharedPreferences.getString("personalnote","");
            AVQuery<AVObject> avQuery=new AVQuery<>("_User");
            avQuery.getInBackground(AVUser.getCurrentUser().getObjectId(), new GetCallback<AVObject>() {
                @Override
                public void done(AVObject avObject, AVException e) {
                    if (avObject!=null)
                    {
                        personalnote = avObject.getString("personalnote");
                    }
                }
            });


       personalnoteTextView.setText(personalnote);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.ll_headpic:
                showSelectDialog();
                break;
            case R.id.ll_personalnote:
                Intent intent = new Intent(MyInfoActivity.this, PerSonalNoteActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.ll_username:
                Intent intent1=new Intent(MyInfoActivity.this,UserNameActivity.class);
                startActivityForResult(intent1,3);
                break;
            default:
                break;

        }


    }
    private void showSelectDialog() {
        selectDialog = new AlertDialog.Builder(MyInfoActivity.this).create();
        selectDialog.setCanceledOnTouchOutside(true);
        View view = LayoutInflater.from(MyInfoActivity.this).inflate(R.layout.dialog_usericon, null);
        selectDialog.show();
        selectDialog.setContentView(view);
        selectDialog.getWindow().setGravity(Gravity.CENTER);

        TextView albumPic = (TextView) view.findViewById(R.id.tv_album_pic);
        TextView cameraPic = (TextView) view.findViewById(R.id.tv_camera_pic);

        albumPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDialog.dismiss();
                getAvataFromAlbum();
            }
        });
        cameraPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDialog.dismiss();
                getAvataFromCamera();
            }
        });

    }

    private void getAvataFromAlbum() {
//        Intent intent1 = new Intent(Intent.ACTION_GET_CONTENT);
        Intent intent1 = new Intent(Intent.ACTION_PICK);
        intent1.setType("image/*");
        startActivityForResult(intent1, GET_AVATA_FROM_ALBUM);
    }

    private void getAvataFromCamera() {
        dateTime = getDateTime();
        File f = new File(CacheUtils.getCacheDirectory(MyInfoActivity.this, true, "icon")
                + dateTime);
        if (f.exists()) {
            f.delete();
        }
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Uri uri = Uri.fromFile(f);
        Log.e("uri", uri + "");

        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        camera.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(camera, GET_AVATA_FROM_CAMERA);

    }

    private String getDateTime() {
        Date date = new Date(System.currentTimeMillis());
        String dateTime = date.getTime() + "";
        return dateTime;
    }

    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");

        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        intent.putExtra("outputX", 40);
        intent.putExtra("outputY", 40);
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);
        // intent.putExtra("noFaceDetection", true);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_ZOOM);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case GET_AVATA_FROM_ALBUM:
                    if (data != null) {
                        Cursor cursor = getContentResolver().query(
                                data.getData(), null, null, null, null);
                        cursor.moveToFirst();
                        int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                        String fileSrc = cursor.getString(index);


                        File file = new File(fileSrc);
                        if (file.exists() && file.length() > 0) {
                            Uri uri = Uri.fromFile(file);
                            startPhotoZoom(uri);
                        }

                    }
                    break;
                case GET_AVATA_FROM_CAMERA:
                    String files = CacheUtils.getCacheDirectory(MyInfoActivity.this, true, "icon") + dateTime;
                    File file = new File(files);
                    if (file.exists() && file.length() > 0) {
                        Uri uri = Uri.fromFile(file);
                        startPhotoZoom(uri);
                    } else {

                    }
                    break;
                case PHOTO_ZOOM:
                    if (data != null) {
                        Bundle extras = data.getExtras();
                        if (extras != null) {
                            Bitmap bitmap = extras.getParcelable("data");

                            String iconUrl = saveToSdCard(bitmap);
                            circleImageView.setImageBitmap(bitmap);
                            //updateIcon(iconUrl);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
        if (requestCode==1&&resultCode==2)
        {
            if (data!=null) {
                personalnote = data.getStringExtra("personalnote");
                SharedPreferences.Editor editor=getSharedPreferences("personalnote",MODE_WORLD_WRITEABLE).edit();
                editor.putString("personalnote",personalnote);
                editor.commit();
            }
            personalnoteTextView.setText(personalnote);
        }
        if (requestCode==3&&resultCode==4)
        {
            if (data!=null)
            {
                userName=data.getStringExtra("username");
                usernameTextView.setText(userName);
            }

        }
        else {}
    }

    public String saveToSdCard(Bitmap bitmap) {
        String files = CacheUtils.getCacheDirectory(MyInfoActivity.this, true, "icon")
                + getDateTime() + "_12.jpg";
        File file = new File(files);
        try {
            FileOutputStream out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)) {
                out.flush();
                out.close();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }
}
