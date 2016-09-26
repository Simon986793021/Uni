package com.sherlockkk.snail.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.sherlockkk.snail.R;
import com.sherlockkk.snail.adapter.JokeListAdapter;
import com.sherlockkk.snail.base.BaseFragment;
import com.sherlockkk.snail.model.NewsDetail;
import com.sherlockkk.snail.model.NewsList;
import com.sherlockkk.snail.model.TopBannerEntity;
import com.sherlockkk.snail.ui.HomeBanner;

import java.util.List;

/**
 * @author SongJian
 * @created 2016/1/17.
 * @e-mail 1129574214@qq.com
 * <p>
 * 首页Fragment
 */
public class HomeFragment extends BaseFragment {
    private HomeBanner homeBanner;
    private List<TopBannerEntity> entity;
    private TopBannerEntity topEntity;
    private SwipeRefreshLayout swipeRefreshLayout;
    private NewsList newsList;
    private NewsDetail newsDetail;
    private Handler handler = new Handler();
    private ListView listView;
    private boolean isLoading = false;


    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        listView = (ListView) view.findViewById(R.id.lv_joke);
        View header = inflater.inflate(R.layout.banner, listView, false);
        homeBanner = (HomeBanner) header.findViewById(R.id.banner);
        homeBanner.setImagesRes(new int[]{R.drawable.home_banner01,R.drawable.home_banner02,R.drawable.home_banner03,R.drawable.home_banner04});
        listView.addHeaderView(header);
        loadData();
        return view;
    }

    private void loadData() {
        AVQuery <AVObject> query=AVQuery.getQuery("Joke");
        query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                JokeListAdapter jokelistadapter=new JokeListAdapter(mActivity.getApplicationContext());
                jokelistadapter.addList(list);
                listView.setAdapter(jokelistadapter);
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();

    }


}
