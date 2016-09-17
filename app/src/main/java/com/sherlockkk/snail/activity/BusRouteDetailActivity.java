/**
 * 
 */
package com.sherlockkk.snail.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.services.route.BusPath;
import com.amap.api.services.route.BusRouteResult;
import com.sherlockkk.snail.R;
import com.sherlockkk.snail.adapter.BusSegmentListAdapter;
import com.sherlockkk.snail.utils.Utils;

/**
 * @author Simon
 * @category 公交详情页面
 */
public class BusRouteDetailActivity extends Activity {
    private TextView timeTextView;
    private TextView moneyTextView;
    private BusPath busPath;
    private BusRouteResult busRouteResult;
    private String time;
    private String distance;
    private String money;
    private TextView backTextView;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.busroutedetail);
        timeTextView = (TextView) findViewById(R.id.tv_time);
        moneyTextView = (TextView) findViewById(R.id.tv_money);
        backTextView = (TextView) findViewById(R.id.tv_back);
        Intent intent = getIntent();
        busPath = intent.getParcelableExtra("buspath");
        busRouteResult = intent.getParcelableExtra("busresult");
        time = Utils.getFriendlyTime((int) busPath.getDuration());
        distance = Utils.getFriendlyTime((int) busPath.getBusDistance());
        money = Math.round(busRouteResult.getTaxiCost()) + "";
        timeTextView.setText(time + "(" + distance + ")");
        moneyTextView.setText("打车约" + money + "元");
        listView = (ListView) findViewById(R.id.lv_busroutedetail);
        BusSegmentListAdapter busSegmentListAdapter = new BusSegmentListAdapter(
                getApplicationContext(), busPath.getSteps());
        listView.setAdapter(busSegmentListAdapter);

        /*
         * 返回
         */
        backTextView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });
    }
}
