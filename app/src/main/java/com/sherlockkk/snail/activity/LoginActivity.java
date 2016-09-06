package com.sherlockkk.snail.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.sherlockkk.snail.R;
import com.sherlockkk.snail.base.BaseActivity;
import com.sherlockkk.snail.tools.ToolLog;
import com.sherlockkk.snail.tools.ToolString;
import com.sherlockkk.snail.ui.EditText.ClearableEditText;
import com.sherlockkk.snail.utils.JsonParseUtil;
import com.umeng.comm.core.CommunitySDK;
import com.umeng.comm.core.beans.CommUser;
import com.umeng.comm.core.impl.CommunityFactory;
import com.umeng.comm.core.login.LoginListener;

import org.json.JSONException;

import java.util.Random;

/**
 * @author SongJian
 * @created 2016/2/10.
 * @e-mail 1129574214@qq.com
 */
public class LoginActivity extends BaseActivity {
    private static String TAG = "LoginActivity";
    private TextView textView;
    private Button btn_login;
    private Toolbar toolbar;
    private ClearableEditText username;
    private ClearableEditText password;
    public static LoginListener sLoginListener;
    public static boolean isLogin=false;

    /*
    由于退出登录跳转到此页面  点击返回 ，又回到了退出登录页面，出现逻辑错误，强行让其跳转到首页
     */
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void setListeners() {

    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_login);
        /*
        如果退出登录  ，isLogin 改为false
         */
        if (AVUser.getCurrentUser()==null)
        {
            isLogin=false;
        }
    }

    @Override
    protected void findViews() {
        initToolbar();
        textView = (TextView) findViewById(R.id.tv_register);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToolLog.i(TAG, ">>>>>>>>>>立即注册");
                startActivity(RegisterActivity.class, null, false);
            }
        });

        username = (ClearableEditText) findViewById(R.id.et_username_login);
        password = (ClearableEditText) findViewById(R.id.et_password_login);

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = username.getText().toString().trim();
                String passWord = password.getText().toString().trim();
                if (isInputCorrect(userName, passWord)) {
                    login(userName, passWord);
//                    mockLoginData();
                }
            }
        });

    }


    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_login);
        toolbar.setTitle("用户登录");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public boolean isInputCorrect(String pNumber, String pWord) {
        String phoneNum = ToolString.removeAllSpace(pNumber);
        if (phoneNum == null || phoneNum.equals("")) {
            showShortToast(LoginActivity.this, "手机号码不能为空");
            return false;
        } else if (phoneNum.length() < 11) {
            showShortToast(LoginActivity.this, "请输入正确的手机号码");
            return false;
        } else if (pWord == null || pWord.equals("")) {
            showShortToast(LoginActivity.this, "密码不能为空");
            return false;
        }
        return true;
    }


    private void login(String userName, String passWord) {
        AVUser.loginByMobilePhoneNumberInBackground(userName, passWord, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser user, AVException e) {
                if (e == null) {
                    //登陆成功
                    isLogin=true;
                    Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                } else {
                    try {
                        String msg = JsonParseUtil.parseJSONObject(e.getMessage(), "error");
                        ToolLog.i("Login", "---->>" + msg);
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }

                }
            }
        });
    }

    /**
     * 模拟用户登录操作,自定义过程中用户可以构造用户名、密码输入界面
     */
    private void mockLoginData() {

//        Log.i(TAG, "mockLoginData: " + userName + "   " + passWord + "    " + AVUser.getCurrentUser().getUsername());
        Log.d("", "### 使用自己的账户系统登录,然后将标识用户唯一性的id和source传递给社区SDK ");
        CommunitySDK sdk = CommunityFactory.getCommSDK(this);
        Random random = new Random();
        CommUser loginedUser = new CommUser();
//        String userId = "id" + random.nextInt(Integer.MAX_VALUE);
//        loginedUser.id = userId; // 用户id
//        loginedUser.name = "name" + random.nextInt(Integer.MAX_VALUE); // 用户名
//        loginedUser.source = Source.SELF_ACCOUNT;// 登录系统来源
//        loginedUser.gender = CommUser.Gender.FEMALE;// 用户性别
//        loginedUser.level = random.nextInt(100); // 用户等级
//        loginedUser.score = random.nextInt(100);// 积分
//        sdk.loginToUmengServer(this, loginedUser, new LoginListener() {
//            @Override
//            public void onStart() {
//                ToolLog.i(">>>>>>>>>>>>>>>>>", "开始登录....");
//            }
//
//            @Override
//            public void onComplete(int i, CommUser commUser) {
//                ToolLog.i(">>>>>>>>>>>>>>>>>", "登录完成！！！！");
////                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
////                startActivity(intent);
//            }
//        });


//        if (sLoginListener != null) {
//            // 登录完成回调给社区SDK，200代表登录成功
////            sLoginListener.onComplete(200, loginedUser);
//            Log.d("", "### ---------------- ");
//        }
    }


}
