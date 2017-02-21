package com.sherlockkk.snail.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sherlockkk.snail.R;
import com.sherlockkk.snail.activity.EntertainmentNewsActivity;
import com.sherlockkk.snail.activity.FashionNewsActivity;
import com.sherlockkk.snail.activity.InternationalNewsActivity;
import com.sherlockkk.snail.activity.ScienceNewsActivity;
import com.sherlockkk.snail.activity.SportNewsActivity;
import com.sherlockkk.snail.activity.TopNewsActivity;
import com.sherlockkk.snail.base.BaseFragment;
import com.sherlockkk.snail.utils.Utils;

/**
 * @author Simon
 * @created 2016/1/18.
 * @e-mail 1129574214@qq.com
 */
public class DiscoverFragment extends BaseFragment implements View.OnClickListener{
    private LinearLayout campuslinearlayout;
    private LinearLayout toplinearlayout;
    private LinearLayout entertainmentlinearlayout;
    private LinearLayout sciencelinearlayout;
    private LinearLayout sportlinearlayout;
    private LinearLayout internationallinearlayout;
    private LinearLayout fashionlinearlayout;
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (Utils.isNetworkAvailable(mActivity)==false)
        {
            View view=inflater.inflate(R.layout.nonetwork,null,false);
            return view;
        }
        else
        {
            View view = inflater.inflate(R.layout.fragment_discover, container, false);
            campuslinearlayout = (LinearLayout) view.findViewById(R.id.ll_campus);
            toplinearlayout= (LinearLayout) view.findViewById(R.id.ll_top);
            entertainmentlinearlayout= (LinearLayout) view.findViewById(R.id.ll_yule);
            sciencelinearlayout= (LinearLayout) view.findViewById(R.id.ll_keji);
            sportlinearlayout= (LinearLayout) view.findViewById(R.id.ll_tiyu);
            internationallinearlayout= (LinearLayout) view.findViewById(R.id.ll_guoji);
            fashionlinearlayout= (LinearLayout) view.findViewById(R.id.ll_shishang);
            campuslinearlayout.setOnClickListener(this);
            toplinearlayout.setOnClickListener(this);
            entertainmentlinearlayout.setOnClickListener(this);
            sciencelinearlayout.setOnClickListener(this);
            sportlinearlayout.setOnClickListener(this);
            internationallinearlayout.setOnClickListener(this);
            fashionlinearlayout.setOnClickListener(this);
            return view;
        }

    }


    @Override
    public void onClick(View v) {
        if (Utils.isNetworkAvailable(mActivity))
        {
            switch (v.getId())
            {
                case R.id.ll_campus:
                    Utils.showToast(mActivity,"将在第二版重磅推出，敬请期待");
                    break;
                case R.id.ll_top:
                    Intent topintent=new Intent(mActivity,TopNewsActivity.class);
                    startActivity(topintent);
                    break;
                case R.id.ll_yule:
                    Intent entertainmentintent=new Intent(mActivity,EntertainmentNewsActivity.class);
                    startActivity(entertainmentintent);
                    break;
                case R.id.ll_keji:
                    Intent scienceintent=new Intent(mActivity,ScienceNewsActivity.class);
                    startActivity(scienceintent);
                    break;
                case R.id.ll_tiyu:
                    Intent sportintent=new Intent(mActivity,SportNewsActivity.class);
                    startActivity(sportintent);
                    break;
                case R.id.ll_guoji:
                    Intent internationalintent=new Intent(mActivity,InternationalNewsActivity.class);
                    startActivity(internationalintent);
                    break;
                case R.id.ll_shishang:
                    Intent fashionintent=new Intent(mActivity,FashionNewsActivity.class);
                    startActivity(fashionintent);
                    break;
                default:
                    break;
            }

        }
        else {
            Utils.showToast(mActivity,"请检查是否有网络");
        }
    }

}
