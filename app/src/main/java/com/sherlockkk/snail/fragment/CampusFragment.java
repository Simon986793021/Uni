package com.sherlockkk.snail.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.sherlockkk.snail.R;
import com.sherlockkk.snail.activity.ActivityDetailActivity;
import com.sherlockkk.snail.activity.LifeActivity;
import com.sherlockkk.snail.activity.LoginActivity;
import com.sherlockkk.snail.activity.SchoolActivityActivity;
import com.sherlockkk.snail.adapter.ActivityListAdapter;
import com.sherlockkk.snail.base.BaseFragment;
import com.sherlockkk.snail.model.SchoolActivity;
import com.sherlockkk.snail.ui.HomeBanner;

import java.util.List;

/**
 * @author Simon
 * @created 2016/9/1.
 * @e-mail 986793021@qq.com
 */
public class CampusFragment extends BaseFragment implements View.OnClickListener,AdapterView.OnItemClickListener{
    private HomeBanner banner;
    private TextView learningTextView;
    private TextView activityTextView;
    private TextView lifeTextView;
    private ListView listView;
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_campus, container, false);
        View topview=inflater.inflate(R.layout.fragment_campus_top,null,false);
        initBanner(topview);
        learningTextView= (TextView) topview.findViewById(R.id.tv_learning);
        activityTextView= (TextView) topview.findViewById(R.id.tv_activity);
        lifeTextView= (TextView) topview.findViewById(R.id.tv_life);
        listView= (ListView) view.findViewById(R.id.lv_activity_campus);
        listView.addHeaderView(topview);
        listView.setOnItemClickListener(this);

        learningTextView.setOnClickListener(this);
        activityTextView.setOnClickListener(this);
        lifeTextView.setOnClickListener(this);
        loadData();

        return view;
    }


    private void initBanner(View view) {
        banner = (HomeBanner) view.findViewById(R.id.banner_campus);
        String[] titles = new String[]{"喜迎国庆，庆祖国华诞", "科技改变未来", "通往未来的道路从来不曾黑暗", "致我们终将逝去的青春"};
        //homeBanner.setImagesUrl(new String[]{"http://img04.muzhiwan.com/2015/06/16/upload_557fd293326f5.jpg", "http://img02.muzhiwan.com/2015/06/11/upload_557903dc0f165.jpg", "http://img04.muzhiwan.com/2015/06/05/upload_5571659957d90.png", "http://img03.muzhiwan.com/2015/06/16/upload_557fd2a8da7a3.jpg"}, titles);
        banner.setImagesRes(new int[]{R.drawable.home_banner01,R.drawable.home_banner02,R.drawable.home_banner03,R.drawable.home_banner04});
    }
    private void loadData()
    {
        AVQuery avQuery=AVQuery.getQuery(SchoolActivity.class);
        avQuery.orderByDescending("createdAt");
        avQuery.findInBackground(new FindCallback<SchoolActivity>() {
            @Override
            public void done(List <SchoolActivity>list, AVException e) {
                if (list!=null)
                {
                    ActivityListAdapter activityListAdapter=new ActivityListAdapter(mActivity);
                    activityListAdapter.addList(list);
                    listView.setAdapter(activityListAdapter);
                }
            }

        });
    }
    @Override
    public void onClick(View v) {
            switch (v.getId()){
                case R.id.tv_activity:
                    Intent intent=new Intent(mActivity,SchoolActivityActivity.class);
                    startActivity(intent);
                    break;
                case R.id.tv_learning:
                    break;
                case R.id.tv_life:
                    Intent lifeintent=new Intent(mActivity,LifeActivity.class);
                    startActivity(lifeintent);
                    break;
                default:
                    break;
            }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        AVUser avUser=AVUser.getCurrentUser();
        if (LoginActivity.isLogin==true||avUser!=null)
        {
            AVQuery avQuery=AVQuery.getQuery(SchoolActivity.class);
            avQuery.orderByDescending("createdAt");
            avQuery.findInBackground(new FindCallback<SchoolActivity>() {
                @Override
                public void done(List <SchoolActivity>list, AVException e) {
                    SchoolActivity schoolActivity=list.get(position-1);//需要减1
                    Intent intent=new Intent(mActivity,ActivityDetailActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putParcelable("schoolactivity",schoolActivity);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

            });
        }
        else {
            Intent intent=new Intent(mActivity,LoginActivity.class);
            startActivity(intent);
        }

    }
}
