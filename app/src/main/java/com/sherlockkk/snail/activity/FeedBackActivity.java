package com.sherlockkk.snail.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.feedback.FeedbackAgent;
import com.sherlockkk.snail.R;

/**
 * Created by Simon on 2016/8/30.
 */
public class FeedBackActivity extends Activity{
    private TextView toolbarText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AVOSCloud.initialize(this,"VRAlHF2hVDOkAB0tmvTASssp-gzGzoHsz","htpf8hvJY0A75UfgPwDPvFKz");
        setContentView(R.layout.activity_feedback);
        toolbarText= (TextView) findViewById(R.id.tv_activity_toolbar_center);
        toolbarText.setText("用户反馈");
        FeedbackAgent feedbackAgent=new FeedbackAgent(getApplicationContext());
        feedbackAgent.startDefaultThreadActivity();
        feedbackAgent.sync();
    }
}
