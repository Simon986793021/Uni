package com.sherlockkk.snail.activity;

import android.app.Activity;
import android.os.Bundle;
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

import java.util.List;

/**
 * Created by Simon on 2016/10/12.
 */
public class MyPartTimeJobActivity extends Activity{
    private TextView backTextView;
    private TextView toolbarTextView;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myparttimejob);
        backTextView= (TextView) findViewById(R.id.tv_back);
        listView= (ListView) findViewById(R.id.lv_my_parttimejob);
        toolbarTextView= (TextView) findViewById(R.id.tv_activity_toolbar_center);
        toolbarTextView.setText("我的兼职");
        loadData();
        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadData() {
        AVObject avUser= AVObject.createWithoutData("_User", AVUser.getCurrentUser().getObjectId());
        AVRelation<AVObject> relation=avUser.getRelation("PartTimeJob");
        AVQuery<AVObject> query=relation.getQuery();
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (list!=null)
                {
                    MyActivityListAdapter myActivityListAdapter = new MyActivityListAdapter(MyPartTimeJobActivity.this);
                    myActivityListAdapter.addList(list);
                    listView.setAdapter(myActivityListAdapter);
                }
            }
        });
    }
}
