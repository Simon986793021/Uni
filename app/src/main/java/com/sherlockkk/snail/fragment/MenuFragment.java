package com.sherlockkk.snail.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.sherlockkk.snail.R;
import com.sherlockkk.snail.activity.LoginActivity;
import com.sherlockkk.snail.activity.MyInfoActivity;
import com.sherlockkk.snail.adapter.MenuAdpater;
import com.sherlockkk.snail.base.BaseFragment;

/**
 * @author SongJian
 * @created 2016/1/16.
 * @e-mail 1129574214@qq.com
 *
 * 侧滑菜单Fragment
 */
public class MenuFragment extends BaseFragment{
    private MenuAdpater menuAdpater;
    private ListView listView;
    private TextView textView;
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu,container,false);

        listView = (ListView) view.findViewById(R.id.lv_item);
        textView = (TextView) view.findViewById(R.id.tv_login);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onLogin();
                }
            });


        menuAdpater = new MenuAdpater(mActivity);
        listView.setAdapter(menuAdpater);
        loginSuccess();

        return view;
    }

    public void onLogin(){
        if (LoginActivity.isLogin==false)
        {
            Intent intent=new Intent(mActivity,LoginActivity.class);
            startActivity(intent);
        }
        else
        {
            Intent intent1=new Intent(mActivity, MyInfoActivity.class);
            startActivity(intent1);
        }
    }
    private void loginSuccess()
    {
        if (LoginActivity.isLogin==true)
        {
            String userName = AVUser.getCurrentUser().getUsername();
            textView.setText(userName);
        }else
        {
            textView.setText("请登录");
        }
    }
}
