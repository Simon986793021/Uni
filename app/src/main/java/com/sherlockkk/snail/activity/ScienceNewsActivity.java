package com.sherlockkk.snail.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.sherlockkk.snail.R;
import com.sherlockkk.snail.utils.NewsListAsyncTask;

/**
 * Created by Administrator on 2016/9/26.
 */
public class ScienceNewsActivity extends Activity{
    private TextView backTextView;
    private TextView toolbarTextView;
    private ListView listView;
    private String URL="http://v.juhe.cn/toutiao/index?type=keji&key=65d4c89f2460e131bd8b288f3f70bff6";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_news);
        backTextView= (TextView) findViewById(R.id.tv_back);
        toolbarTextView= (TextView) findViewById(R.id.tv_activity_toolbar_center);
        listView= (ListView) findViewById(R.id.lv_news_list);
        toolbarTextView.setText("科技新闻");
        new NewsListAsyncTask(listView,ScienceNewsActivity.this).execute(URL);
        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
