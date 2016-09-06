package com.sherlockkk.snail.activity;

import android.os.Bundle;

import com.sherlockkk.snail.R;
import com.sherlockkk.snail.base.BaseActivity;
import com.sherlockkk.snail.model.Secondary;
import com.sherlockkk.snail.ui.HomeBanner;

import java.util.List;

/**
 * @author SongJian
 * 30@created 2016/3/17.
 * @e-mail 1129574214@qq.com
 */
public class SecondaryDetailActivity extends BaseActivity {
    private HomeBanner banner_pic;
    Secondary secondary;

    @Override
    protected void setListeners() {

    }

    @Override
    protected void initDatas() {
        Bundle bundle = getIntent().getBundleExtra(getPackageName());
        secondary = bundle.getParcelable("secondary");
        List<String> pics = secondary.getPicUrlList();
        String[] arr = (String[]) pics.toArray(new String[pics.size()]);
        for (int i = 0; i < pics.size(); i++) {
            System.out.println(arr[i]);
        }

//        banner_pic.setImagesUrl( pics.toArray());
    }

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_secondary_detail);
    }

    @Override
    protected void findViews() {
        banner_pic = (HomeBanner) findViewById(R.id.secondary_detail_pic);

    }
}
