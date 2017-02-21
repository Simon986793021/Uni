package com.sherlockkk.snail.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetCallback;
import com.sherlockkk.snail.R;
import com.sherlockkk.snail.activity.AboutUsActivity;
import com.sherlockkk.snail.activity.LoginActivity;
import com.sherlockkk.snail.activity.MyActivityActivity;
import com.sherlockkk.snail.activity.MyInfoActivity;
import com.sherlockkk.snail.activity.MyPartTimeJobActivity;
import com.sherlockkk.snail.activity.MySupportActivity;
import com.sherlockkk.snail.base.BaseFragment;
import com.sherlockkk.snail.tools.ToolLog;
import com.sherlockkk.snail.utils.CacheUtils;
import com.sherlockkk.snail.utils.Utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;

import static com.sherlockkk.snail.R.id.tv_logout;

/**
 * @author Simon
 * @created 2016/8/18.
 * @e-mail 986793021@qq.com
 */
public class MineFragment extends BaseFragment  {
    private final String TAG = "MineFragment";

    private ListView listView;
    private ImageView imageView_userIcon;
    private TextView textView_nickName;
    private TextView textView_loc;
    private TextView textView_sex;
    private TextView textView_signature;

    private SimpleAdapter mineListAdapter;
    private String[] options;
    private int[] ids;

    private final int GET_AVATA_FROM_ALBUM = 1;
    private final int GET_AVATA_FROM_CAMERA = 2;
    private final int PHOTO_ZOOM = 3;
    private String headPicUrl;

    @Override
    protected View initView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (Utils.isNetworkAvailable(mActivity)==false)
        {
            View view=inflater.inflate(R.layout.nonetwork,null,false);
            return view;
        }
      else {
            View view = inflater.inflate(R.layout.fragment_mine, container, false);
            listView = (ListView) view.findViewById(R.id.lv_mine_options);
            imageView_userIcon = (ImageView) view.findViewById(R.id.iv_mine_user_icon);
            textView_nickName = (TextView) view.findViewById(R.id.tv_mine_nickname);
            textView_loc = (TextView) view.findViewById(R.id.tv_mine_loc);
            textView_sex = (TextView) view.findViewById(R.id.tv_mine_sex);
            textView_signature = (TextView) view.findViewById(R.id.tv_mine_signature);

            SharedPreferences sharedPreferences=mActivity.getSharedPreferences("headPic",Activity.MODE_PRIVATE);
            String headPic=sharedPreferences.getString("headPic","");
            Bitmap bitmap=null;
            if (headPic!="") {
                byte[] bytes = Base64.decode(headPic.getBytes(),1);
                bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            }
            else {
                AVQuery<AVObject> avQuery=new AVQuery<>("_User");
                avQuery.getInBackground(AVUser.getCurrentUser().getObjectId(), new GetCallback<AVObject>() {
                    @Override
                    public void done(AVObject avObject, AVException e) {
                        headPicUrl=avObject.getString("headPicUrl");
                        if (headPicUrl!=null)
                        {
                            HeadPicAsyncTask headPicAsyncTask=new HeadPicAsyncTask();
                            headPicAsyncTask.execute(headPicUrl);
                        }
                    }
                });


            }
            imageView_userIcon.setImageBitmap(bitmap);


            setListener();

            init();
            mineListAdapter = new SimpleAdapter(mActivity, getData(), R.layout.listview_mine, options, ids);

            listView.setAdapter(mineListAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (Utils.isNetworkAvailable(mActivity)) {

                        switch (position) {
                            case 0:
                                Intent intent = new Intent(mActivity, MyInfoActivity.class);
                                startActivity(intent);
                                break;
                            case 1:
                                Intent MyActivityintent = new Intent(mActivity, MyActivityActivity.class);
                                startActivity(MyActivityintent);
                                break;
                            case 2:
                                Intent parttimejobintent = new Intent(mActivity, MyPartTimeJobActivity.class);
                                startActivity(parttimejobintent);
                                break;
                            case 3:
                                Intent mysupport = new Intent(mActivity, MySupportActivity.class);
                                startActivity(mysupport);
                                break;
                            case 4:
                                Intent aboutusIntent = new Intent(mActivity, AboutUsActivity.class);
                                startActivity(aboutusIntent);
                                break;
                            case 5:
//                            Intent feedbackIntent=new Intent(mActivity,FeedBackActivity.class);
//                            startActivity(feedbackIntent);
                                Utils.showToast(mActivity, "将在第二版中重磅推出，敬请期待");
                                break;
                            case 6:
                                quitDialog();
                                break;
                            default:
                                break;
                        }
                    }
                    else {
                        Utils.showToast(mActivity,"请检查是否有网络");
                    }
                }
                /*
                退出登录  对话框
                 */
                private void quitDialog() {
                    final AlertDialog quitDialog=new AlertDialog.Builder(mActivity).create();
                    View view=inflater.inflate(R.layout.logout,null,false);
                    quitDialog.show();
                /*
                 *  直接从xml设置dialog不能铺满整个宽度 ，通过以下代码设置
                 */
                    Window window=quitDialog.getWindow();
                    window.getDecorView().setPadding(0,0,0,0);
                    WindowManager.LayoutParams layoutParams=window.getAttributes();
                    layoutParams.width=WindowManager.LayoutParams.FILL_PARENT;
                    window.setAttributes(layoutParams);

                    quitDialog.setContentView(view);
                    quitDialog.getWindow().setGravity(Gravity.BOTTOM);

                    TextView logoutTextView= (TextView) view.findViewById(tv_logout);
                    TextView cancelTextView= (TextView) view.findViewById(R.id.tv_cancel);

                    logoutTextView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AVUser.logOut();
                            AVUser currentUser=AVUser.getCurrentUser();
                            Intent intent=new Intent(mActivity, LoginActivity.class);
                            startActivity(intent);
                        }
                    });

                    cancelTextView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            quitDialog.cancel();
                        }
                    });

                }
            });


            return view;
        }
    }
    class HeadPicAsyncTask extends AsyncTask<String,Void,Bitmap>{
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
            SharedPreferences sharedPreferences=mActivity.getSharedPreferences("headPic", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream);
            String headPicBase64=new String(Base64.encodeToString(byteArrayOutputStream.toByteArray(),Base64.DEFAULT));
            editor.putString("headPic",headPicBase64);
            editor.commit();

            imageView_userIcon.setImageBitmap(bitmap);

        }
    }

    private void setListener() {
        imageView_userIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mActivity,MyInfoActivity.class);
                startActivity(intent);
            }
        });
    }

    private void init() {

        options = new String[]{"img_left", "options_list", "img_right"};
        ids = new int[]{R.id.iv_lv_mine_l, R.id.tv_lv_mine, R.id.iv_lv_mine_r};
    }

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("img_left", R.drawable.myinfo);
        map.put("options_list", "我的资料");
        map.put("img_right", R.drawable.go);
        list.add(map);

        map = new HashMap<>();
        map.put("img_left", R.drawable.activity);
        map.put("options_list", "我的活动");
        map.put("img_right", R.drawable.go);
        list.add(map);



        map = new HashMap<>();
        map.put("img_left", R.drawable.share);
        map.put("options_list", "我的兼职");
        map.put("img_right", R.drawable.go);
        list.add(map);

        map=new HashMap<>();
        map.put("img_left",R.drawable.mysupport);
        map.put("options_list","我的赞助");
        map.put("img_right",R.drawable.go);
        list.add(map);

        map = new HashMap<>();
        map.put("img_left", R.drawable.about);
        map.put("options_list", "关于我们");
        map.put("img_right", R.drawable.go);
        list.add(map);

        map =new HashMap<>();
        map.put("img_left",R.drawable.contact);
        map.put("options_list","意见反馈");
        map.put("img_right",R.drawable.go);
        list.add(map);

        map =new HashMap<>();
        map.put("img_left",R.drawable.exit);
        map.put("options_list","退出登录");
        map.put("img_right",R.drawable.go);
        list.add(map);
        return list;


    }
    AlertDialog selectDialog;

    private void showSelectDialog() {
        selectDialog = new AlertDialog.Builder(mActivity).create();
        selectDialog.setCanceledOnTouchOutside(true);
        View view = LayoutInflater.from(mActivity).inflate(R.layout.dialog_usericon, null);
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


    String dateTime;

    private void getAvataFromAlbum() {
//        Intent intent1 = new Intent(Intent.ACTION_GET_CONTENT);
        Intent intent1 = new Intent(Intent.ACTION_PICK);
        intent1.setType("image/*");
        startActivityForResult(intent1, GET_AVATA_FROM_ALBUM);
    }

    private void getAvataFromCamera() {
        dateTime = getDateTime();
        File f = new File(CacheUtils.getCacheDirectory(mActivity, true, "icon")
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

    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");

        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        intent.putExtra("outputX", 120);
        intent.putExtra("outputY", 120);
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
                        Cursor cursor = mActivity.getContentResolver().query(
                                data.getData(), null, null, null, null);
                        cursor.moveToFirst();
                        int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                        String fileSrc = cursor.getString(index);

                        ToolLog.i(TAG, ">>>>>>>>>>>>>>>>>>>>>>>" + fileSrc);

                        File file = new File(fileSrc);
                        if (file.exists() && file.length() > 0) {
                            Uri uri = Uri.fromFile(file);
                            startPhotoZoom(uri);
                        }

                    }
                    break;
                case GET_AVATA_FROM_CAMERA:
                    String files = CacheUtils.getCacheDirectory(mActivity, true, "icon") + dateTime;
                    File file = new File(files);
                    if (file.exists() && file.length() > 0) {
                        Uri uri = Uri.fromFile(file);
                        ToolLog.i(TAG, ">>>>>>>>>uri>>>>>>>>>>>>" + uri.toString());
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
                            ToolLog.i(TAG, ">>>>>>iconUrl>>>>>>>>>>>>" + iconUrl);
                            imageView_userIcon.setImageBitmap(bitmap);
                            //updateIcon(iconUrl);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public String saveToSdCard(Bitmap bitmap) {
        String files = CacheUtils.getCacheDirectory(mActivity, true, "icon")
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
        ToolLog.i(TAG, file.getAbsolutePath());
        return file.getAbsolutePath();
    }

    private String getDateTime() {
        Date date = new Date(System.currentTimeMillis());
        String dateTime = date.getTime() + "";
        return dateTime;
    }
}
