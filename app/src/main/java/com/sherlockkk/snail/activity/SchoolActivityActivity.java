package com.sherlockkk.snail.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.sherlockkk.snail.R;
import com.sherlockkk.snail.adapter.ActivityListAdapter;
import com.sherlockkk.snail.model.SchoolActivity;

import java.util.List;

/**
 * @Author Simon
 * @Create By 2016/9/9
 * @E-mail  986793021@qq.com
 */
public class SchoolActivityActivity extends Activity implements View.OnClickListener,AdapterView.OnItemClickListener{
    private TextView backTextView;
    private TextView TitleTextView;
    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schoolactivity);
        backTextView= (TextView) findViewById(R.id.tv_back);
        TitleTextView= (TextView) findViewById(R.id.tv_activity_toolbar_center);
        listView= (ListView) findViewById(R.id.lv_schoolactivity);
        swipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.srl_activity);
        TitleTextView.setText("校园活动");
        backTextView.setOnClickListener(this);
        listView.setOnItemClickListener(this);
        loadData();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                loadData();
            }
        });
    }
        /*
        从后台查找更新数据
         */
        private void loadData()
        {
            AVQuery avQuery=AVQuery.getQuery(SchoolActivity.class);
            avQuery.orderByDescending("createdAt");
            avQuery.findInBackground(new FindCallback<SchoolActivity>() {
                @Override
                public void done(List<SchoolActivity> list, AVException e) {
                    if (list!=null) {

                        swipeRefreshLayout.setRefreshing(false);
                        ActivityListAdapter activityListAdapter = new ActivityListAdapter(SchoolActivityActivity.this);
                        activityListAdapter.addList(list);
                        listView.setAdapter(activityListAdapter);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

        AVUser currentUser=AVUser.getCurrentUser();

        if (LoginActivity.isLogin==true||currentUser!=null) {
            AVQuery avQuery=AVQuery.getQuery(SchoolActivity.class);
            avQuery.orderByDescending("createdAt");

            avQuery.findInBackground(new FindCallback<SchoolActivity>() {
                @Override
                public void done(List <SchoolActivity>list, AVException e) {
                    SchoolActivity schoolActivity=list.get(position);
                    Intent intent=new Intent(SchoolActivityActivity.this,ActivityDetailActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putParcelable("schoolactivity",schoolActivity);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

            });
        }
        else{
            Intent intent=new Intent(SchoolActivityActivity.this,LoginActivity.class);
            startActivity(intent);
        }
    }
}
