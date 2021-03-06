package com.sherlockkk.snail.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.sherlockkk.snail.R;
import com.sherlockkk.snail.base.BaseActivity;
import com.sherlockkk.snail.tools.ToolLog;
import com.sherlockkk.snail.tools.ToolString;
import com.sherlockkk.snail.ui.EditText.ClearableEditText;
import com.sherlockkk.snail.utils.JsonParseUtil;
import com.sherlockkk.snail.utils.Utils;

import org.json.JSONException;

/**
 * @author SongJian
 * @created 2016/2/10.
 * @e-mail 1129574214@qq.com
 */
public class LoginActivity extends BaseActivity {
    private static String TAG = "LoginActivity";
    private TextView textView;
    private Button btn_login;
    private ClearableEditText username;
    private ClearableEditText password;
    public static boolean isLogin=false;
    private TextView backtextView;
    private TextView toobarTextView;

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
        backtextView= (TextView) findViewById(R.id.tv_back);
        toobarTextView= (TextView) findViewById(R.id.tv_activity_toolbar_center);
        toobarTextView.setText("登录");
        backtextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = username.getText().toString().trim();
                String passWord = password.getText().toString().trim();
                if (isInputCorrect(userName, passWord)) {
                    login(userName, passWord);
                }
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
                    Utils.showToast(LoginActivity.this,"登录成功");
                   // Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
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
}
