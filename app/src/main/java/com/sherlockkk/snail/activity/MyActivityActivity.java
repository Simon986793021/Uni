package com.sherlockkk.snail.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVRelation;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.sherlockkk.snail.R;
import com.sherlockkk.snail.adapter.MyActivityListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Simon on 2016/9/19.
 */
public class MyActivityActivity extends Activity implements View.OnClickListener{
    private TextView backTextView;
    private TextView toolbarTextView;
    private ListView listView;
    private List<String> objectidlist=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_activity);
        backTextView= (TextView) findViewById(R.id.tv_back);
        toolbarTextView= (TextView) findViewById(R.id.tv_activity_toolbar_center);
        listView= (ListView) findViewById(R.id.lv_my_activity);
        toolbarTextView.setText("我的活动");
        backTextView.setOnClickListener(this);
            loadData();
    }

    private void loadData()  {
        AVObject avUser=  AVObject.createWithoutData("_User",AVUser.getCurrentUser().getObjectId());
        AVRelation<AVObject>relation=avUser.getRelation("SchoolActivity");
        AVQuery<AVObject> query=relation.getQuery();
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                    if (list!=null) {
                        MyActivityListAdapter myActivityListAdapter = new MyActivityListAdapter(MyActivityActivity.this);
                        myActivityListAdapter.addList(list);
                        listView.setAdapter(myActivityListAdapter);
                    }
                else {
                        LayoutInflater layoutInflater=LayoutInflater.from(MyActivityActivity.this);
                        View view=layoutInflater.inflate(R.layout.activity_noactivity,null,false);
                        setContentView(view);
                        backTextView= (TextView) findViewById(R.id.tv_back);
                        toolbarTextView= (TextView) findViewById(R.id.tv_activity_toolbar_center);
                        toolbarTextView.setText("我的活动");
                        backTextView.setOnClickListener(MyActivityActivity.this);
                    }

            }
        });



    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.tv_back:
                finish();
                break;
            default:
                break;
        }
    }
}
