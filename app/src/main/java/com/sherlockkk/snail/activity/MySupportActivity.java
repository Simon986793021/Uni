package com.sherlockkk.snail.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVRelation;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.sherlockkk.snail.R;
import com.sherlockkk.snail.adapter.MySupportListAdapter;

import java.util.List;

/**
 * Created by Simon on 2016/12/22.
 */
public class MySupportActivity extends Activity implements View.OnClickListener{
    private TextView toolbarcentertextview;
    private TextView backtextview;
    private ListView listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_support);
        toolbarcentertextview= (TextView) findViewById(R.id.tv_activity_toolbar_center);
        backtextview= (TextView) findViewById(R.id.tv_back);
        listview= (ListView) findViewById(R.id.lv_my_support);
        toolbarcentertextview.setText("我的赞助");
        backtextview.setOnClickListener(this);
        loadData();
    }

    private void loadData() {
        AVObject avUser=AVObject.createWithoutData("_User", AVUser.getCurrentUser().getObjectId());
        AVRelation<AVObject> relation=avUser.getRelation("SupportActivity");
        AVQuery<AVObject>query=relation.getQuery();
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                    if (list!=null)
                    {
                        MySupportListAdapter adapter=new MySupportListAdapter(MySupportActivity.this);
                        adapter.addList(list);
                        listview.setAdapter(adapter);
                    }
            }
        });
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
