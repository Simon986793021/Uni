/**
 * 
 */
package com.sherlockkk.snail.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkRouteResult;
import com.sherlockkk.snail.R;
import com.sherlockkk.snail.adapter.WalkSegmentListAdapter;
import com.sherlockkk.snail.utils.Utils;

/**
 * @author Simon
 * @category 步行详情页面
 */
public class WalkRouteDetailActivity extends Activity {
    private TextView timeTextView;
    private WalkPath walkPath;
    private WalkRouteResult walkRouteResult;
    private TextView distanceTextView;
    private WalkSegmentListAdapter walkSegmentListAdapter;
    private ListView listView;
    private  TextView backTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.walkroutedetail);
        timeTextView = (TextView) findViewById(R.id.tv_time);
        distanceTextView = (TextView) findViewById(R.id.tv_distance);
        listView = (ListView) findViewById(R.id.lv_drivedetail);
        backTextView= (TextView) findViewById(R.id.tv_back);
        walkPath = getIntent().getParcelableExtra("walkpath");
        walkRouteResult = getIntent().getParcelableExtra("walkresult");
        int dis = (int) walkPath.getDistance();
        int dur = (int) walkPath.getDuration();
        String distance = "步行到达目的地需要" + Utils.getFriendlyLength(dis) + "";
        String time = "距离目的地还有" + Utils.getFriendlyTime(dur) + "";
        timeTextView.setText(time);
        distanceTextView.setText(distance);
        walkSegmentListAdapter = new WalkSegmentListAdapter(
                getApplicationContext(), walkPath.getSteps());
        listView.setAdapter(walkSegmentListAdapter);

        /*
         * 返回
         */
        backTextView = (TextView) findViewById(R.id.tv_back);
        backTextView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });
    }
}
