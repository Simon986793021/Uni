package com.sherlockkk.snail.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVUser;
import com.sherlockkk.snail.R;

/**
 * Created by Simon on 2016/9/5.
 */
public class UserNameActivity extends Activity implements View.OnClickListener{
    private TextView toolbarcenterTextView;
    private TextView toolbarrightTextView;
    private TextView backTextView;
    private EditText editText;
    private String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_username);
        toolbarcenterTextView= (TextView) findViewById(R.id.tv_activity_toolbar_center);
        toolbarrightTextView= (TextView) findViewById(R.id.tv_activity_toolbar_right);
        backTextView= (TextView) findViewById(R.id.tv_back);
        editText= (EditText) findViewById(R.id.et_username);
        toolbarrightTextView.setText("保存");
        toolbarcenterTextView.setText("更改用户名");
        backTextView.setOnClickListener(this);
        toolbarrightTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_activity_toolbar_right:
                username=editText.getText().toString();
                if (username.length()>8||username.length()==0)
                {
                    Toast.makeText(UserNameActivity.this,"用户名不符合规范",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    AVUser user= AVUser.getCurrentUser();
                    user.put("username",username);
                    user.saveInBackground();
                    Intent intent=new Intent(UserNameActivity.this,MyInfoActivity.class);
                    intent.putExtra("username",username);
                    setResult(4,intent);
                    finish();

                }
                break;
        }

    }
}
