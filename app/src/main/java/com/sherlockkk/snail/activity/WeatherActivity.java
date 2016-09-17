/**
 * 
 */
package com.sherlockkk.snail.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.services.weather.LocalDayWeatherForecast;
import com.amap.api.services.weather.LocalWeatherForecast;
import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLive;
import com.amap.api.services.weather.LocalWeatherLiveResult;
import com.amap.api.services.weather.WeatherSearch;
import com.amap.api.services.weather.WeatherSearch.OnWeatherSearchListener;
import com.amap.api.services.weather.WeatherSearchQuery;
import com.sherlockkk.snail.R;

import java.util.List;

/**
 * @author Simon
 * @category 天气查询
 */
public class WeatherActivity extends Activity implements OnClickListener {
    private EditText editText;
    private ImageView imageView;
    private TextView cityTextView;
    private TextView timeTextView;
    private TextView weatherTextView;
    private TextView temperatureTextView;
    private TextView windDirectionTextView;
    private TextView windLeverTextView;
    private TextView percenTextView;
    private WeatherSearchQuery weatherSearchQuery;
    private WeatherSearch weatherSearch;
    private LocalWeatherLive localWeatherLive;
    private LocalWeatherForecast localWeatherForecast;
    private String cityName;
    private List<LocalDayWeatherForecast> forecastlist = null;
    private TextView futureTimeTextView;
    private TextView forecastTimeTextView;
    private String inCityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather);
        intiView();

        inCityName = getIntent().getStringExtra("cityName");// 获取当前位置城市

        firstSearchForecastWeather();
        firstSearchLiveWeather();
        imageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        // TODO Auto-generated method stub
        firstSearchForecastWeather();
        firstSearchLiveWeather();
    }

    /**
     * 初始化
     */
    private void intiView() {
        // TODO Auto-generated method stub
        editText = (EditText) findViewById(R.id.et_search);
        imageView= (ImageView) findViewById(R.id.iv_sousuo);
        cityTextView = (TextView) findViewById(R.id.tv_city);
        timeTextView = (TextView) findViewById(R.id.tv_time);
        weatherTextView = (TextView) findViewById(R.id.tv_weather);
        temperatureTextView = (TextView) findViewById(R.id.tv_temperature);
        windDirectionTextView = (TextView) findViewById(R.id.tv_winddirection);
        windLeverTextView = (TextView) findViewById(R.id.tv_windlever);
        percenTextView = (TextView) findViewById(R.id.tv_percent);
        futureTimeTextView = (TextView) findViewById(R.id.tv_futuretime);
        forecastTimeTextView = (TextView) findViewById(R.id.tv_forecasttime);
    }

    /*
     * 获取未来天气
     */
    public void firstSearchForecastWeather() {
        // TODO Auto-generated method stub
        cityName = editText.getText().toString();
        if (cityName.equals("")) {
            cityName = inCityName;
        }
        weatherSearchQuery = new WeatherSearchQuery(cityName,
                WeatherSearchQuery.WEATHER_TYPE_FORECAST);
        weatherSearch = new WeatherSearch(WeatherActivity.this);
        weatherSearch.setOnWeatherSearchListener(new OnWeatherSearchListener() {

            @Override
            public void onWeatherLiveSearched(LocalWeatherLiveResult arg0,
                    int arg1) {
                // TODO Auto-generated method stub

            }

            /*
             * 未来天气回调结果
             * 
             * @see com.amap.api.services.weather.WeatherSearch.
             * OnWeatherSearchListener #onWeatherForecastSearched(
             * com.amap.api.services.weather .LocalWeatherForecastResult, int)
             */
            @Override
            public void onWeatherForecastSearched(
                    LocalWeatherForecastResult arg0, int arg1) {
                // TODO Auto-generated method stub
                if (arg1 == 1000) {
                    if (arg0 != null
                            && arg0.getForecastResult() != null
                            && arg0.getForecastResult().getWeatherForecast() != null
                            && arg0.getForecastResult().getWeatherForecast()
                                    .size() > 0) {
                        localWeatherForecast = arg0.getForecastResult();
                        forecastlist = localWeatherForecast
                                .getWeatherForecast();
                        showForecastWeather();
                    }
                }
            }

            // 显示未来天气信息
            private void showForecastWeather() {
                // TODO Auto-generated method stub
                futureTimeTextView.setText(localWeatherForecast.getReportTime()
                        + "发布");

                String time = "";
                String temperature = null;
                for (int i = 0; i < forecastlist.size(); i++) {
                    LocalDayWeatherForecast localDayWeatherForecast = forecastlist
                            .get(i);
                    String week = null;
                    switch (Integer.valueOf(localDayWeatherForecast.getWeek())) {
                    case 1:
                        week = "周一";
                        break;
                    case 2:
                        week = "周二";
                        break;
                    case 3:
                        week = "周三";
                        break;
                    case 4:
                        week = "周四";
                        break;
                    case 5:
                        week = "周五";
                        break;
                    case 6:
                        week = "周六";
                        break;
                    case 7:
                        week = "周日";
                        break;
                    default:
                        break;
                    }
                    temperature = localDayWeatherForecast.getDayTemp()
                            .toString()
                            + "℃/"
                            + localDayWeatherForecast.getNightTemp().toString()
                            + "℃";
                    /*
                     * 注意：需要将天气信息相加起来
                     */
                    time += localDayWeatherForecast.getDate().toString() + " "
                            + week + "               " + temperature + "\n\n";

                }

                forecastTimeTextView.setText(time);
            }
        });
        weatherSearch.setQuery(weatherSearchQuery);
        weatherSearch.searchWeatherAsyn();// 异步搜索
    }

    /*
     * 获取实时天气
     */
    public void firstSearchLiveWeather() {
        // TODO Auto-generated method stub
        cityName = editText.getText().toString();
        if (cityName.equals("")) {
            cityName = inCityName;
        }
        weatherSearchQuery = new WeatherSearchQuery(cityName,
                WeatherSearchQuery.WEATHER_TYPE_LIVE);
        weatherSearch = new WeatherSearch(WeatherActivity.this);
        weatherSearch.setOnWeatherSearchListener(new OnWeatherSearchListener() {
            /*
             * 实时天气回调函数
             */
            @Override
            public void onWeatherLiveSearched(LocalWeatherLiveResult arg0,
                    int arg1) {
                // TODO Auto-generated method stub
                if (arg1 == 1000) {
                    if (arg0 != null && arg0.getLiveResult() != null) {
                        localWeatherLive = arg0.getLiveResult();
                        timeTextView.setText(localWeatherLive.getReportTime()
                                .toString() + "发布");
                        weatherTextView.setText(localWeatherLive.getWeather()
                                .toString());
                        temperatureTextView.setText(localWeatherLive
                                .getTemperature().toString() + "℃");
                        windDirectionTextView.setText(localWeatherLive
                                .getWindDirection().toString() + "风");
                        windLeverTextView.setText(localWeatherLive
                                .getWindPower().toString() + "级");
                        percenTextView.setText(localWeatherLive.getHumidity()
                                .toString() + "%");
                        cityTextView.setText(cityName);
                    }
                }
            }

            /*
             * (non-Javadoc)未来天气回调函数
             * 
             * @see com.amap.api.services.weather.WeatherSearch.
             * OnWeatherSearchListener #onWeatherForecastSearched(
             * com.amap.api.services.weather .LocalWeatherForecastResult, int)
             */
            @Override
            public void onWeatherForecastSearched(
                    LocalWeatherForecastResult arg0, int arg1) {
                // TODO Auto-generated method stub

            }
        });

        weatherSearch.setQuery(weatherSearchQuery);
        weatherSearch.searchWeatherAsyn();
    }

}
