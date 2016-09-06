package com.sherlockkk.snail.activity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sherlockkk.snail.Constants;
import com.sherlockkk.snail.base.BaseActivity;
import com.sherlockkk.snail.tools.ToolLog;
import com.sherlockkk.snail.utils.HttpUtil;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.sherlockkk.snail.R;

import cz.msebera.android.httpclient.Header;

/**
 * @author SongJian
 * @created 2016/2/28.
 * @e-mail 1129574214@qq.com
 */
public class ScheduleActivity extends BaseActivity implements View.OnClickListener {
    private static String TAG = "ScheduleActivity";

    private EditText username, password;
    private Button button;

    @Override
    protected void setListeners() {

    }

    @Override
    protected void initDatas() {
        //loadData();
    }

    private void loadData() {
        //http://localhost/Test/MyServlet
        String url = "http://192.168.1.102/Test/MyServlet";

        RequestParams params = new RequestParams();
        params.add("username", "admin");
        params.add("password", "123");
        HttpUtil.post(this, url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                ToolLog.i(TAG, "--请求失败-->:" + statusCode + " " + responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                ToolLog.i(TAG, "--请求成功，返回-->:" + responseString);
            }
        });
    }

    @Override
    protected void initViews() {
        mToolbar.setTitle("课程表");
        setContentView(R.layout.activity_schedule);
    }

    @Override
    protected void findViews() {
        username = (EditText) findViewById(R.id.editText);
        password = (EditText) findViewById(R.id.editText2);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.i(TAG, "onClick: 登陆");
        String url = "/default6.aspx";
        String name = username.getText().toString().trim();
        String psd = password.getText().toString().trim();
        RequestParams params = new RequestParams();
        params.add("__VIEWSTATE","dDwtMTQxNDAwNjgwODt0PDtsPGk8MD47PjtsPHQ8O2w8aTwyMT47aTwyMz47aTwyNT47aTwyNz47PjtsPHQ8cDxsPGlubmVyaHRtbDs+O2w8XDxsaVw+XDxzcGFuIGNsYXNzPSd0eXBlJ1w+XDwvc3Bhblw+XDxhIGhyZWY9J2dnc20uYXNweD9mYnNqPTIwMTUtMDMtMTEgMTA6MDU6NTEmeXhxeD0yMDE2LTA0LTExJyB0YXJnZXQ9J19ibGFuaycgICBcPuagoee6p+WFrOmAieivvumAieivvuaTjeS9nOaMh+WNl1w8L2FcPlw8c3BhbiBjbGFzcz0nbmV3J1w+bmV3XDwvc3Bhblw+XDxzcGFuIGNsYXNzPSd0aW1lJ1w+MjAxNS0wMy0xMSBcPC9zcGFuXD5cPC9saVw+Oz4+Ozs+O3Q8cDxsPGlubmVyaHRtbDs+O2w8XDxsaSBjbGFzcz0naGEnXD5cPGEgaHJlZj0nIycgaWQ9J2J0MScgaGlkZWZvY3VzPSd0cnVlJyAgb25jbGljaz0ieWVRQ2hhbmdlKCd0bmFtZScsJ2J0MScsJ2hhJywndGJ0bnMnLCdsaXN0MicpIlw+6YCa55+lXDwvYVw+XDwvbGlcPjs+Pjs7Pjt0PHA8bDxpbm5lcmh0bWw7PjtsPFw8bGlcPlw8c3BhbiBjbGFzcz0ndHlwZSdcPlw8L3NwYW5cPlw8YSBocmVmPSdnZ3NtLmFzcHg/ZmJzaj0yMDE2LTAyLTI2IDExOjUyOjM2Jnl4cXg9MjAxNi0wMy0wNycgdGFyZ2V0PSdfYmxhbmsnICAgXD4yMDE1LTIwMTYtMuWtpuacn+mAieivvuehruiupOmAmuefpVw8L2FcPlw8c3BhbiBjbGFzcz0nbmV3J1w+bmV3XDwvc3Bhblw+XDxzcGFuIGNsYXNzPSd0aW1lJ1w+MjAxNi0wMi0yNiBcPC9zcGFuXD5cPC9saVw+XDxsaVw+XDxzcGFuIGNsYXNzPSd0eXBlJ1w+XDwvc3Bhblw+XDxhIGhyZWY9J2dnc20uYXNweD9mYnNqPTIwMTUtMTItMjggMTE6MDc6NDQmeXhxeD0yMDE2LTAzLTEwJyB0YXJnZXQ9J19ibGFuaycgICBcPuWFs+S6jjIwMTUtMjAxNuWtpuW5tOesrOS6jOWtpuacn+W8gOWtpuWIneihpeiAg+i3n+ePreiAg+ivleaKpeWQjeeahOmAmuefpVw8L2FcPlw8c3BhbiBjbGFzcz0nbmV3J1w+bmV3XDwvc3Bhblw+XDxzcGFuIGNsYXNzPSd0aW1lJ1w+MjAxNS0xMi0yOCBcPC9zcGFuXD5cPC9saVw+XDxsaVw+XDxzcGFuIGNsYXNzPSd0eXBlJ1w+XDwvc3Bhblw+XDxhIGhyZWY9J2dnc20uYXNweD9mYnNqPTIwMTMtMTAtMTcgMTE6NDU6MTImeXhxeD0yMDIxLTEwLTI5JyB0YXJnZXQ9J19ibGFuaycgICBcPuaVmeWtpuWSqOivouiBlOezu+aWueW8j1w8L2FcPlw8c3BhbiBjbGFzcz0nbmV3J1w+bmV3XDwvc3Bhblw+XDxzcGFuIGNsYXNzPSd0aW1lJ1w+MjAxMy0xMC0xNyBcPC9zcGFuXD5cPC9saVw+XDxsaVw+XDxzcGFuIGNsYXNzPSd0eXBlJ1w+XDwvc3Bhblw+XDxhIGhyZWY9J2dnc20uYXNweD9mYnNqPTIwMTMtMDUtMjIgMDg6NTc6NDUmeXhxeD0yMDE3LTA5LTA5JyB0YXJnZXQ9J19ibGFuaycgICBcPuWFs+S6juWkp+WtpueUn+e7vOWQiOe0oOi0qOWtpuWIhuiupOWumueahOWunuaWvee7huWImSgyMDEy57qn5Y+K5Lul5ZCO5a2m55SfKVw8L2FcPlw8c3BhbiBjbGFzcz0nbmV3J1w+bmV3XDwvc3Bhblw+XDxzcGFuIGNsYXNzPSd0aW1lJ1w+MjAxMy0wNS0yMiBcPC9zcGFuXD5cPC9saVw+Oz4+Ozs+O3Q8cDxsPGlubmVyaHRtbDs+O2w8Oz4+Ozs+Oz4+Oz4+Oz4/lIwWb0EF9F71zsFZrqd15YTdPg==");
        params.add("tname","bt1");
        params.add("tbtns","bt1");
        params.add("tnameXw","yhdl");
        params.add("tbtnsXw","yhdl|xwxsdl");
        params.add("txtYhm",name);
        params.add("txtXm","");
        params.add("txtMm",psd);
        params.add("rbLjs","学生");
        params.add("btnDl","登 录");
        HttpUtil.post(ScheduleActivity.this, Constants.JW_BASE_URL + url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                ToolLog.i(TAG, "--请求失败-->:" + statusCode + " " + responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                ToolLog.i(TAG, "--请求成功-->:" + statusCode + " " + responseString);
            }
        });
    }
}
