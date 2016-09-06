package com.sherlockkk.snail.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.SaveCallback;
import com.sherlockkk.snail.R;

/**
 * Created by Administrator on 2016/9/4.
 */
public class PerSonalNoteActivity extends Activity implements View.OnClickListener{
    private TextView toolbarcenterTextView ;
    private TextView toolbarrightTextView;
    private EditText editText;
    private TextView backTextView;
    public static String PERSONALNOTEOBJECTID;
    private String personalnote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalnote);
        toolbarcenterTextView= (TextView) findViewById(R.id.tv_activity_toolbar_center);
        toolbarrightTextView= (TextView) findViewById(R.id.tv_activity_toolbar_right);
        editText= (EditText) findViewById(R.id.et_personalnote);
        backTextView= (TextView) findViewById(R.id.tv_back);
        toolbarrightTextView.setText("保存");
        toolbarcenterTextView.setText("个性签名");

        toolbarrightTextView.setOnClickListener(this);
        backTextView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_activity_toolbar_right:
                personalnote=editText.getText().toString();
                if (personalnote.length()>18||personalnote.length()==0)
            {
                Toast.makeText(PerSonalNoteActivity.this,"个性签名不符合规范",Toast.LENGTH_SHORT).show();
            }
                else {

                final     AVUser user=AVUser.getCurrentUser();

                    user.put("personalnote", personalnote);
                    user.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null) {
                                Toast.makeText(PerSonalNoteActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                                AVQuery<AVObject> avQuery = new AVQuery<>("_User");
                                avQuery.getInBackground(user.getObjectId(), new GetCallback<AVObject>() {
                                    @Override
                                    public void done(AVObject avObject, AVException e) {
                                        if (avObject != null) {
                                            personalnote = avObject.getString("personalnote");
                                            Intent intent = new Intent(PerSonalNoteActivity.this, MyInfoActivity.class);
                                            intent.putExtra("personalnote", personalnote);
                                            setResult(2, intent);
                                            finish();
                                        }
                                    }
                                });
                            }
                        }
                    });
                    //Toast.makeText(PerSonalNoteActivity.this,RegisterActivity.USEROBJECTID,Toast.LENGTH_SHORT).show();
                }
        }
    }
}
