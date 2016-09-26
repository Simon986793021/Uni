/**
 * 
 */
package com.sherlockkk.snail.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sherlockkk.snail.R;
import com.sherlockkk.snail.adapter.RecomandAdapter;
import com.sherlockkk.snail.model.PositionEntity;
import com.sherlockkk.snail.task.InputTipTask;
import com.sherlockkk.snail.task.PoiSearchTask;
import com.sherlockkk.snail.task.RouteTask;
import com.sherlockkk.snail.utils.Utils;

/**
 * @author Simon
 * @category 目的地输入页面
 */
public class DestinationActivity extends Activity implements OnClickListener,
        OnItemClickListener, TextWatcher {
    private RecomandAdapter recomandAdapter;
    private ListView listView;
    private EditText editText;
    private RouteTask routeTask;
    private PositionEntity positionEntity;
    private String city;// 搜索的城市
    private TextView toolbarTextView;
    private TextView backTextView;
    private ImageView searchImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detination);
        listView = (ListView) findViewById(R.id.recommend_list);
        toolbarTextView= (TextView) findViewById(R.id.tv_activity_toolbar_center);
        backTextView= (TextView) findViewById(R.id.tv_back);
        searchImageView= (ImageView) findViewById(R.id.iv_sousuo);
        toolbarTextView.setText("目的地");


        backTextView.setOnClickListener(this);

        searchImageView.setOnClickListener(this);

        editText = (EditText) findViewById(R.id.et_search);
        editText.addTextChangedListener(this);
        recomandAdapter = new RecomandAdapter(getApplicationContext());
        listView.setAdapter(recomandAdapter);
        listView.setOnItemClickListener(this);
        routeTask = RouteTask.getInstance(getApplicationContext());
        positionEntity = routeTask.getStartPoint();
        city = positionEntity.city;
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.text.TextWatcher#beforeTextChanged(java.lang.CharSequence,
     * int, int, int)
     */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
            int after) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see android.text.TextWatcher#onTextChanged(java.lang.CharSequence, int,
     * int, int)
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // TODO Auto-generated method stub
        InputTipTask.getInstance(getApplicationContext(), recomandAdapter)
                .searchTips(s.toString(), city);
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.text.TextWatcher#afterTextChanged(android.text.Editable)
     */
    @Override
    public void afterTextChanged(Editable s) {
        // TODO Auto-generated method stub

    }

    /*
     * ListView点击事件
     * 
     * @see
     * android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget
     * .AdapterView, android.view.View, int, long)
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {
        // TODO Auto-generated method stub
        PositionEntity entity = (PositionEntity) recomandAdapter
                .getItem(position);
        if (entity.latitue == 0 && entity.longitude == 0) {

            PoiSearchTask poiSearchTask = new PoiSearchTask(
                    getApplicationContext(), recomandAdapter);
            poiSearchTask.search(entity.address,
                    RouteTask.getInstance(getApplicationContext())
                            .getStartPoint().city);// 第二个参数是起点所在的城市
        } else {
            routeTask = RouteTask.getInstance(getApplicationContext());
            routeTask.setEndPoint(entity);
            Utils.showToast(DestinationActivity.this,entity.address);
            editText.setText(entity.address);

        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
        case R.id.iv_sousuo:
            PoiSearchTask poiSearchTask = new PoiSearchTask(
                    getApplicationContext(), recomandAdapter);
            poiSearchTask.search(editText.getText().toString(), city);
            Intent intent2 = new Intent(DestinationActivity.this,
                    NavigationActivity.class);
            startActivity(intent2);
            finish();
            break;
            case R.id.tv_back:
                Intent intent = new Intent(DestinationActivity.this,
                        NavigationActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
                break;
        default:
            break;
        }
    }
}
