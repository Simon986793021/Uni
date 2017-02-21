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
import com.sherlockkk.snail.adapter.PartTimeJobListAdapter;
import com.sherlockkk.snail.model.PartTimeJob;
import com.sherlockkk.snail.utils.Utils;

import java.util.List;

/**
 * Created by Simon  on 2016/9/30.
 */
public class PartTimeActivity extends Activity implements AdapterView.OnItemClickListener{
    private TextView backTextView;
    private TextView toolbarTextView;
    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parttime);
        backTextView= (TextView) findViewById(R.id.tv_back);
        toolbarTextView= (TextView) findViewById(R.id.tv_activity_toolbar_center);
        swipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.srl_activity);
        listView= (ListView) findViewById(R.id.lv_parttime_list);
        listView.setOnItemClickListener(this);
        toolbarTextView.setText("校园兼职");
        loadData();
        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
        AVQuery avQuery=AVQuery.getQuery(PartTimeJob.class);
        avQuery.orderByDescending("createdAt");
        avQuery.findInBackground(new FindCallback<PartTimeJob>() {
            @Override
            public void done(List<PartTimeJob> list, AVException e) {
                if (list!=null) {

                    swipeRefreshLayout.setRefreshing(false);
                    PartTimeJobListAdapter partTimeJobListAdapter = new PartTimeJobListAdapter(PartTimeActivity.this);
                    partTimeJobListAdapter.addList(list);
                    listView.setAdapter(partTimeJobListAdapter);
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        if (Utils.isNetworkAvailable(PartTimeActivity.this)) {
            AVUser avUser = AVUser.getCurrentUser();
            if (LoginActivity.isLogin || avUser != null) {
                AVQuery avQuery = AVQuery.getQuery(PartTimeJob.class);
                avQuery.orderByDescending("createdAt");
                avQuery.findInBackground(new FindCallback<PartTimeJob>() {
                    @Override
                    public void done(List<PartTimeJob> list, AVException e) {
                        if (list != null) {
                            PartTimeJob partTimeJob = list.get(position);
                            Intent intent = new Intent(PartTimeActivity.this, PartTimeJobDetailActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putParcelable("partTimeJob", partTimeJob);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    }
                });
            } else {
                Intent intent = new Intent(PartTimeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        }
        else {
            Utils.showNoNetWorkToast(PartTimeActivity.this);
        }
    }
}
