package com.sherlockkk.snail.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sherlockkk.snail.activity.MainActivity;
import com.sherlockkk.snail.activity.SecondaryActivity;
import com.sherlockkk.snail.adapter.CampusGridviewAdapter;
import com.sherlockkk.snail.base.BaseFragment;
import com.sherlockkk.snail.R;
import com.sherlockkk.snail.ui.HomeBanner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SongJian
 * @created 2016/1/18.
 * @e-mail 1129574214@qq.com
 */
public class CampusFragment extends BaseFragment {
    private RecyclerView recyclerView;
    private HomeBanner banner;
    private TextView tv_secondary;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_campus, container, false);

        initBanner(view);
        initRecyclerView(view);
        initTextView(view);

        return view;
    }


    private void initBanner(View view) {
        banner = (HomeBanner) view.findViewById(R.id.banner_campus);
        String[] titles = new String[]{"江西理工与牛津大学达成合作办学", "公安部打黑除恶", "纸老虎纸老虎纸老虎", "郊区王者吊打一区螃蟹！"};
        banner.setImagesUrl(new String[]{"http://img04.muzhiwan.com/2015/06/16/upload_557fd293326f5.jpg", "http://img02.muzhiwan.com/2015/06/11/upload_557903dc0f165.jpg", "http://img04.muzhiwan.com/2015/06/05/upload_5571659957d90.png", "http://img03.muzhiwan.com/2015/06/16/upload_557fd2a8da7a3.jpg"}, titles);
    }

    private void initTextView(View view) {
        tv_secondary = (TextView) view.findViewById(R.id.tv_secondary);
        tv_secondary.setClickable(false);
        tv_secondary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, SecondaryActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.rcv_grid_campus);
        List<String> mDatas = new ArrayList<String>();
        for (int i = 'A'; i <= 'z'; i++) {
            mDatas.add(String.valueOf((char) i));
        }
        CampusGridviewAdapter campusGridviewAdapter = new CampusGridviewAdapter(mActivity, mDatas);
        recyclerView.setAdapter(campusGridviewAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(mActivity, 2));
        campusGridviewAdapter.setOnItemActionListener(new CampusGridviewAdapter.OnItemActionListener() {
            @Override
            public void onItemClickListener(View v, int pos) {
                Toast.makeText(mActivity, "-单击-" + pos, Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onItemLongClickListener(View v, int pos) {
                Toast.makeText(mActivity, "-长按-" + pos, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }


}
