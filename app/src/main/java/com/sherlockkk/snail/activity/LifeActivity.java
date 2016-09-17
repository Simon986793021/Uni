package com.sherlockkk.snail.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.sherlockkk.snail.R;

/**
 * Created by Simon on 2016/9/12.
 */
public class LifeActivity extends Activity implements View.OnClickListener,LocationSource,AMapLocationListener{
    private TextView toolbarcenterTextView;
    private TextView backTextView;
    private LinearLayout weatherLinearLayout;
    private LinearLayout mapLinearLayout;
    private LinearLayout navigationLinearLayout;
    private AMap aMap;
    private MapView mapView;
    private LocationSource.OnLocationChangedListener onLocationChangedListener;
    private AMapLocationClient aMapLocationClient;
    private AMapLocationClientOption aMapLocationClientOption;
    private String cityName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life);
        toolbarcenterTextView= (TextView) findViewById(R.id.tv_activity_toolbar_center);
        backTextView= (TextView) findViewById(R.id.tv_back);
        weatherLinearLayout= (LinearLayout) findViewById(R.id.ll_weather);
        mapLinearLayout= (LinearLayout) findViewById(R.id.ll_map);
        navigationLinearLayout= (LinearLayout) findViewById(R.id.ll_navigation);


        toolbarcenterTextView.setText("生活助手");

        backTextView.setOnClickListener(this);
        weatherLinearLayout.setOnClickListener(this);
        mapLinearLayout.setOnClickListener(this);
        navigationLinearLayout.setOnClickListener(this);
        /*
        获取当前城市位置，传给天气查询，显示当前城市天气情况
         */
        mapView= (MapView) findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        aMap=mapView.getMap();
        aMap.setLocationSource(this);
        aMap.setMyLocationEnabled(true);
        startLocation();

    }
    /*
    开始定位
     */
    private void startLocation() {
    aMapLocationClient=new AMapLocationClient(LifeActivity.this);
        aMapLocationClient.setLocationListener(this);
        aMapLocationClientOption=new AMapLocationClientOption();
        aMapLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        aMapLocationClientOption.setInterval(2000);
        aMapLocationClientOption.setNeedAddress(true);
        aMapLocationClient.setLocationOption(aMapLocationClientOption);
        aMapLocationClient.startLocation();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.tv_back:
                finish();
                break;
            case R.id.ll_weather:
                Intent weatherintent = new Intent(LifeActivity.this,
                        WeatherActivity.class);
                weatherintent.putExtra("cityName", cityName);
                startActivity(weatherintent);
                break;
            case R.id.ll_map:
                Intent mapintent=new Intent(LifeActivity.this,MapActivity.class);
                startActivity(mapintent);
                break;
            case R.id.ll_navigation:
                Intent navigationintent=new Intent(LifeActivity.this,NavigationActivity.class);
                startActivity(navigationintent);
                break;
            default:
                break;
        }
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation!=null)
        {
            if (aMapLocation.getErrorCode()==0)
            {
                onLocationChangedListener.onLocationChanged(aMapLocation);
                cityName=aMapLocation.getCity();
            }
        }
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
       this.onLocationChangedListener=onLocationChangedListener;
    }

    @Override
    public void deactivate() {
        onLocationChangedListener=null;
    }
}
