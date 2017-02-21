package com.sherlockkk.snail.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVRelation;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.sherlockkk.snail.R;
import com.sherlockkk.snail.model.SchoolActivity;
import com.sherlockkk.snail.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Simon on 2016/9/17.
 * updata by Simon on 2016/12/10
 */
public class ActivityDetailActivity extends Activity implements View.OnClickListener{
    private TextView backTextView;
    private TextView toolbarTextView;
    private SchoolActivity schoolActivity;
    private TextView titleTextView;
    private ImageView imageview;
    private Button button;
    private String phonenum;
    private String Username;
    private Button supportbutton;
    private String payInfo;
   private List<String> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_detail);
        backTextView= (TextView) findViewById(R.id.tv_back);
        toolbarTextView= (TextView) findViewById(R.id.tv_activity_toolbar_center);
        titleTextView= (TextView) findViewById(R.id.tv_title_detail);
        imageview= (ImageView) findViewById(R.id.iv_pic_detail);
        button= (Button) findViewById(R.id.bt_sign_up);
        supportbutton= (Button) findViewById(R.id.bt_support);
        toolbarTextView.setText("活动详情");
        backTextView.setOnClickListener(this);
        supportbutton.setOnClickListener(this);
        button.setOnClickListener(this);
        loadData();
    }

    private void loadData() {
        schoolActivity = (SchoolActivity) getIntent().getParcelableExtra("schoolactivity");
        if (schoolActivity!= null) {
            titleTextView.setText(schoolActivity.getActivityDetail().toString());
            payInfo=schoolActivity.getActivityTitle().toString();
            String PicBase64 = schoolActivity.getPic();
            byte[] bytes= Base64.decode(PicBase64.getBytes(),1);
            Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
            imageview.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.tv_back:
                finish();
                break;
            case R.id.bt_sign_up:
                if (Utils.isNetworkAvailable(ActivityDetailActivity.this))
                {
                    showSelectDialog();
                }
                else {
                    Utils.showNoNetWorkToast(ActivityDetailActivity.this);
                }
                break;
            case R.id.bt_support:
                Intent intent=new Intent(ActivityDetailActivity.this,PayActivity.class);
                Bundle bundle=new Bundle();
                bundle.putParcelable("schoolactivity",schoolActivity);//将整个对象传过去
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void showSelectDialog() {
        final AlertDialog alertdialog=new AlertDialog.Builder(ActivityDetailActivity.this).create();
        alertdialog.setCanceledOnTouchOutside(true);//点击旁边dialog消失
        View view= LayoutInflater.from(this).inflate(R.layout.dialog_sign_up,null,false);
        alertdialog.show();
        alertdialog.setContentView(view);
        alertdialog.getWindow().setGravity(Gravity.CENTER);
        Button surebutton= (Button) view.findViewById(R.id.bt_sure);
        Button cancelbutton= (Button) view.findViewById(R.id.bt_cancel);

        final AVUser avUser=AVUser.getCurrentUser();
        phonenum=avUser.getMobilePhoneNumber().toString();
        Username=avUser.getUsername().toString();
        TextView   alertdialogTextView= (TextView) view.findViewById(R.id.tv_alertdialog);
        alertdialogTextView.setText("您的手机号码为："+phonenum+"，请确认是否报名");

        //报名，将信息提交给后台
        surebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> mylist=new ArrayList<String>();
                schoolActivity = (SchoolActivity) getIntent().getParcelableExtra("schoolactivity");

                mylist=schoolActivity.getList("signupPhonenum");

                if (mylist!=null&&mylist.contains(phonenum))
                {
                    Utils.showToast(ActivityDetailActivity.this,"亲爱的，请勿重复报名哦");
                }

                else {

                    AVObject.saveAllInBackground(Arrays.asList(schoolActivity), new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            AVRelation<AVObject> relation=avUser.getRelation("SchoolActivity");
                            relation.add(schoolActivity);
                            avUser.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(AVException e) {
                                    if (e==null)
                                    {
                                        schoolActivity.add("signupPhonenum",phonenum);//add方法，添加的数据为array型
                                        schoolActivity.saveInBackground();
                                        Utils.showToast(ActivityDetailActivity.this,"报名成功");
                                    }
                                }
                            });
                        }
                    });
                }
                alertdialog.cancel();


            }
        });
        //取消报名
        cancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertdialog.cancel();
            }
        });
    }
}
