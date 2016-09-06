package com.sherlockkk.snail.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

/**
 * @author SongJian
 * @created 2016/1/19.
 * @e-mail 1129574214@qq.com
 */
public class HttpUtil {
    private static AsyncHttpClient client = new AsyncHttpClient();

    /**
     * GET请求
     * @param url
     * @param responseHandlerInterface
     */
    public static void get (String url, ResponseHandlerInterface responseHandlerInterface){
        client.get(url,responseHandlerInterface);
    }

    /**
     * Cookie持久化的POST请求
     * @param context
     * @param url
     * @param params
     * @param responseHandle
     */
    public static void post(Context context,String url, RequestParams params,ResponseHandlerInterface responseHandle){
        PersistentCookieStore cookieStore =  new PersistentCookieStore(context);
        client.setTimeout(10000); // 设置链接超时，如果不设置，默认为10s
        // 设置请求头
        client.addHeader("Host", "jw.jxust.edu.cn");
        client.addHeader("Referer", "http://jw.jxust.edu.cn/default6.aspx");
        client.addHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:44.0) Gecko/20100101 Firefox/44.0");
        client.setCookieStore(cookieStore);
        client.post(context,url, params, responseHandle);
    }

    /**
     * 判断是否有网络连接
     *
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context){
        if (context!= null){
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo!=null){
                return networkInfo.isAvailable();
            }
        }
        return false;
    }
}
