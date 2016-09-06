package com.sherlockkk.snail.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.sherlockkk.snail.R;
import com.sherlockkk.snail.adapter.SimpleRecyclerCardAdapter;
import com.sherlockkk.snail.base.BaseActivity;
import com.sherlockkk.snail.model.Secondary;
import com.sherlockkk.snail.utils.AVOSServiceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SongJian
 * @created 2016/3/10.
 * @e-mail 1129574214@qq.com
 */
public class SecondaryActivity extends BaseActivity implements SimpleRecyclerCardAdapter.OnItemActionListener {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private FloatingActionButton fab_secondary;
    private SwipeRefreshLayout refreshLayout;
    SimpleRecyclerCardAdapter simpleRecyclerCardAdapter;
    List<Secondary> lists = new ArrayList<>();

    @Override
    protected void setListeners() {

    }

    @Override
    protected void initDatas() {
        loadDatas();
    }

    private void loadDatas() {
        AVQuery<Secondary> query = AVQuery.getQuery(Secondary.class);
        query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<Secondary>() {
            @Override
            public void done(List<Secondary> list, AVException e) {
                lists = list;
                simpleRecyclerCardAdapter.addList(list);
                refreshLayout.setRefreshing(false);
                System.out.println(list);
            }
        });
    }

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_secondary);
        initToolBar();
        initFab();
    }


    private void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_secondary);
        toolbar.setTitle("校园二手");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    protected void findViews() {
        recyclerView = (RecyclerView) findViewById(R.id.rcv_secondary);
        simpleRecyclerCardAdapter = new SimpleRecyclerCardAdapter(this);
        simpleRecyclerCardAdapter.setOnItemActionListener(this);
        recyclerView.setAdapter(simpleRecyclerCardAdapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_secondary);
        refreshLayout.setColorSchemeColors(R.color.blue, R.color.green, R.color.orange, R.color.day_line_color);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadDatas();
            }
        });
    }

    private void initFab() {
        fab_secondary = (FloatingActionButton) findViewById(R.id.fab_secondary);
        fab_secondary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到发布二手商品界面
                startActivity(SecondaryPubActivity.class, null, false);
            }
        });
    }

    @Override
    public void onItemClickListener(View v, int pos) {
        Secondary secondary = lists.get(pos);
        Bundle bundle = new Bundle();
        bundle.putParcelable("secondary", secondary);
        startActivity(SecondaryDetailActivity.class, bundle, false);
    }

    @Override
    public boolean onItemLongClickListener(View v, int pos) {
        return false;
    }
}
