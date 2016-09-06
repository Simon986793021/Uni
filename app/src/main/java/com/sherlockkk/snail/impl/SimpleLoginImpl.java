package com.sherlockkk.snail.impl;

import android.content.Context;
import android.content.Intent;

import com.sherlockkk.snail.activity.LoginActivity;
import com.umeng.comm.core.login.AbsLoginImpl;
import com.umeng.comm.core.login.LoginListener;

/**
 * @author SongJian
 * @created 2016/3/8.
 * @e-mail 1129574214@qq.com
 */
public class SimpleLoginImpl extends AbsLoginImpl {
    @Override
    protected void onLogin(Context context, LoginListener loginListener) {
        // 包装一下Listener
//        LoginActivity.sLoginListener = loginListener;
        // 跳转到你的Activity
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }
}
