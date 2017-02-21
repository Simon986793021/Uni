package com.sherlockkk.snail.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.ProgressCallback;
import com.avos.avoscloud.SaveCallback;
import com.sherlockkk.snail.R;
import com.sherlockkk.snail.ui.CircleImageView;
import com.sherlockkk.snail.utils.CacheUtils;
import com.sherlockkk.snail.utils.Utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;

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
    private TextView genderTextView;
    private TextView schoolTextView;
    private TextView phoneTextView;
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
    private String gender;
    private String school;
    private String phoneNumber;
    private AVFile avFile;
    private String headPicUrl;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
 //   private GoogleApiClient client;

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
        genderTextView= (TextView) findViewById(R.id.tv_sex);
        schoolTextView= (TextView) findViewById(R.id.tv_school);
        phoneTextView= (TextView) findViewById(R.id.tv_phone);
        loadData();


        textView.setOnClickListener(this);
        headPicLinearLayout.setOnClickListener(this);
        userNameLinearLayout.setOnClickListener(this);
        personalnoteLinearLayout.setOnClickListener(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void loadData() {

       // SharedPreferences sharedPreferences=getSharedPreferences("personalnote",MODE_WORLD_WRITEABLE);
       // personalnote=sharedPreferences.getString("personalnote","");
        AVQuery<AVObject> avQuery=new AVQuery<>("_User");
        avQuery.getInBackground(AVUser.getCurrentUser().getObjectId(), new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                personalnote=avObject.getString("personalnote");
                gender=avObject.getString("gender");
                school=avObject.getString("school");
                phoneNumber=avObject.getString("mobilePhoneNumber");
                userName=avObject.getString("username");
                headPicUrl=avObject.getString("headPicUrl");
                SharedPreferences sharedPreferences=getSharedPreferences("headPic",Activity.MODE_PRIVATE);
                String headPic=sharedPreferences.getString("headPic","");
                Bitmap bitmap=null;
                if (headPic!="") {
                    byte[] bytes = Base64.decode(headPic.getBytes(),1);
                  //  byte[] bytes =headPic.getBytes();
                    bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                    circleImageView.setImageBitmap(bitmap);
                }
               else {
                    if (headPicUrl != null) {
                        HeadPicAsyncTask myAsyncTask = new HeadPicAsyncTask(circleImageView, MyInfoActivity.this);
                        myAsyncTask.execute(headPicUrl);
                    }
                }
                /*
                异步操作，更新UI需要在异步操作结束后才能获取到数据
                 */

                personalnoteTextView.setText(personalnote);
                genderTextView.setText(gender);
                schoolTextView.setText(school);
                phoneTextView.setText(phoneNumber);
                usernameTextView.setText(userName);

            }
        });




    }

    class HeadPicAsyncTask extends AsyncTask<String,Void,Bitmap>
    {
        private ImageView imageView;
        private Context context;
        public HeadPicAsyncTask(ImageView imageView, Context context)
        {}
        @Override
        protected Bitmap doInBackground(String... params) {
            HttpClient httpClient=new DefaultHttpClient();
            HttpGet httpGet=new HttpGet(params[0]);
            Bitmap bitmap=null;
            try {
                HttpResponse httpResponse=httpClient.execute(httpGet);
                if (httpResponse.getStatusLine().getStatusCode()==200)
                {
                    HttpEntity httpEntity=httpResponse.getEntity();
                    byte[] bytes= EntityUtils.toByteArray(httpEntity);
                    bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;

        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            /*
            将图片转化成64位字节流，存储到本地
             */
            SharedPreferences sharedPreferences=getSharedPreferences("headPic", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream);
            String headPicBase64=new String(Base64.encodeToString(byteArrayOutputStream.toByteArray(),Base64.DEFAULT));
            editor.putString("headPic",headPicBase64);
            editor.commit();

            circleImageView.setImageBitmap(bitmap);

        }
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
                if (Utils.isNetworkAvailable(MyInfoActivity.this))
                {
                    getAvataFromAlbum();
                }
                else {
                    Utils.showNoNetWorkToast(MyInfoActivity.this);
                }
            }
        });
        cameraPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDialog.dismiss();
                if (Utils.isNetworkAvailable(MyInfoActivity.this))
                {
                    getAvataFromCamera();
                }
               else {
                    Utils.showNoNetWorkToast(MyInfoActivity.this);
                }
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
        intent.putExtra("aspectY", 1);  //裁剪框的比例，1：1

        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);//裁剪后输出图片的尺寸大小
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
                           // AVFile avfile=new AVFile("head.png",data.getData())

                            String iconUrl = saveToSdCard(bitmap);
                            try {
                              avFile = AVFile.withAbsoluteLocalPath("head.png",iconUrl );
                                AVUser.getCurrentUser().add("headPic",avFile);
                                AVUser.getCurrentUser().saveInBackground();
                                avFile.saveInBackground(new SaveCallback() {
                                                            @Override
                                                            public void done(AVException e) {
                                                               headPicUrl=  avFile.getUrl();
                                                                AVUser.getCurrentUser().put("headPicUrl",headPicUrl);
                                                                AVUser.getCurrentUser().saveInBackground();
                                                                HeadPicAsyncTask myAsyncTask=new HeadPicAsyncTask(circleImageView,MyInfoActivity.this);
                                                                myAsyncTask.execute(headPicUrl);
                                                                // Toast.makeText(MyInfoActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                                                            }
                                                        },
                                        new ProgressCallback() {
                                            @Override
                                            public void done(Integer integer) {
                                                if (integer==100)
                                                {
                                                    Toast.makeText(MyInfoActivity.this,"成功上传",Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }
                                );
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }

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
