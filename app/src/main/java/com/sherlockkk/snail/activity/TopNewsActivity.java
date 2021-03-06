package com.sherlockkk.snail.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.sherlockkk.snail.R;
import com.sherlockkk.snail.utils.NewsListAsyncTask;

/**
 * Created by Simon on 2016/9/26.
 */
public class TopNewsActivity extends Activity implements AdapterView.OnItemClickListener {
    private TextView backTextView;
    private TextView toolbarTextView;
    private ListView listView;
    private String URL="http://v.juhe.cn/toutiao/index?type=top&key=65d4c89f2460e131bd8b288f3f70bff6";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_news);
        backTextView= (TextView) findViewById(R.id.tv_back);
        toolbarTextView= (TextView) findViewById(R.id.tv_activity_toolbar_center);
        listView= (ListView) findViewById(R.id.lv_news_list);
        toolbarTextView.setText("新闻头条");
        new NewsListAsyncTask(listView, TopNewsActivity.this).execute(URL);
        listView.setOnItemClickListener(this);
        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String url=NewsListAsyncTask.list.get(position).url;
        Intent intent=new Intent(this,NewDetailActivity.class);
        intent.putExtra("url",url);
        startActivity(intent);
    }
}
