package com.sherlockkk.snail.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sherlockkk.snail.R;
import com.sherlockkk.snail.base.BaseFragment;
import com.umeng.comm.core.CommunitySDK;
import com.umeng.comm.core.impl.CommunityFactory;

/**
 * @author SongJian
 * @created 2016/1/18.
 * @e-mail 1129574214@qq.com
 */
public class DiscoverFragment extends BaseFragment {
    private LinearLayout ll_circle;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discover, container, false);
        ll_circle = (LinearLayout) view.findViewById(R.id.ll_circle);
        ll_circle.setOnClickListener(onClickListener);

        return view;
    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           CommunitySDK mCommSDK = CommunityFactory.getCommSDK(mActivity);
                mCommSDK.openCommunity(mActivity);
        }
    };
}
