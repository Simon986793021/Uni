package com.sherlockkk.snail.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.sherlockkk.snail.R;

/**
 * Created by Simon on 2016/9/28.
 */
public class NewDetailActivity extends Activity{
    private TextView backTextView;
    private TextView toolbarTextView;
    private WebView webView;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        backTextView= (TextView) findViewById(R.id.tv_back);
        toolbarTextView= (TextView) findViewById(R.id.tv_activity_toolbar_center);
        webView= (WebView) findViewById(R.id.wv_news_detail);
        toolbarTextView.setText("新闻详情");
        Intent intent=getIntent();
        url=intent.getStringExtra("url");
        initWebView();
        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initWebView() {
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;//返回为true默认webview自带浏览器打开，false调用三方浏览器
            }
        });
    }
}
