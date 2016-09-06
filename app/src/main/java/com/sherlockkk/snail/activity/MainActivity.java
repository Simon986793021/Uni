package com.sherlockkk.snail.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.RadioGroup;

import com.avos.avoscloud.AVUser;
import com.sherlockkk.snail.db.CacheDbHelper;
import com.sherlockkk.snail.fragment.CampusFragment;
import com.sherlockkk.snail.fragment.HomeFragment;
import com.sherlockkk.snail.fragment.MineFragment;
import com.sherlockkk.snail.impl.SimpleLoginImpl;
import com.sherlockkk.snail.ui.HomeBanner;
import com.sherlockkk.snail.fragment.DiscoverFragment;
import com.sherlockkk.snail.R;
import com.umeng.comm.core.sdkmanager.LoginSDKManager;

import java.util.Arrays;
import java.util.List;

/**
 * @author SongJian
 * @created 2016/1/16.
 * @e-mail 1129574214@qq.com
 */
public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private HomeBanner mHomeBanner;

    private RadioGroup radioGroup;
    int curCursor;
    private Fragment homeFragment = new HomeFragment();
    private Fragment campusFragment = new CampusFragment();
    private Fragment discoverFragment = new DiscoverFragment();
    private Fragment mimeFragment = new MineFragment();
    private List<Fragment> fragmentList = Arrays.asList(homeFragment, campusFragment, discoverFragment, mimeFragment);

    private FragmentManager fragmentManager;
    private CacheDbHelper cacheDbHelper;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cacheDbHelper = new CacheDbHelper(this,1);
        fragmentManager = getSupportFragmentManager();
        findViews();
        initViews();
        initFootBar();
        useCustomLogin();
    }

    private void findViews() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    private void initViews() {
        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.app_name, R.string.menu_name);
        mDrawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    private void initFootBar() {
        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.foot_bar_home:
                        curCursor = 0;
                        break;
                    case R.id.foot_bar_campus:
                        curCursor = 1;
                        break;
                    case R.id.foot_bar_discover:
                        curCursor = 2;
                        break;
                    case R.id.foot_bar_mime:
                        AVUser currentUser=AVUser.getCurrentUser();

                        if (LoginActivity.isLogin==true||currentUser!=null) {
                            curCursor = 3;
                        }
                        else{
                            Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                            startActivity(intent);
                        }

                            break;
                }
                addFragmentToStack(curCursor);
            }

        });

        addFragmentToStack(0);
    }

    private void addFragmentToStack(int cursor) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentList.get(cursor);
        if (!fragment.isAdded()) {
            fragmentTransaction.add(R.id.fl_content, fragment);
        }
        for (int i = 0; i < fragmentList.size(); i++) {
            Fragment f = fragmentList.get(i);
            if (i == cursor && f.isAdded()) {
                fragmentTransaction.show(f);
            } else if (f != null && f.isAdded() && f.isVisible()) {
                fragmentTransaction.hide(f);
            }
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    public CacheDbHelper getCacheDbHelper(){
        return cacheDbHelper;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = fragmentList.get(curCursor);
        if (fragment != null){
            fragment.onActivityResult(requestCode,resultCode,data);
        }
    }

    protected void useCustomLogin() {
        // 管理器
        LoginSDKManager.getInstance().addAndUse(new SimpleLoginImpl());
    }
}
