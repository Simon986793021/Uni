package com.sherlockkk.snail.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;

import com.sherlockkk.snail.adapter.NewsListAdapter;
import com.sherlockkk.snail.model.NewsList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Simon on 2016/9/26.
 */
public class NewsListAsyncTask extends AsyncTask<String,Void,List<NewsList>>{
    private ListView listView;
    private Context context;
    public  static  List<NewsList> list;
    public NewsListAsyncTask()
    {

    }

    public NewsListAsyncTask (ListView listView,Context context)
    {
        this.listView=listView;
        this.context=context;
    }
    @Override
    protected List<NewsList> doInBackground(String... params) {

        list=getJsonData(params[0]);
        return list;
    }

    private List<NewsList> getJsonData(String param) {
        List<NewsList> list =new ArrayList<>();
        String jsonString="";
        try {
            jsonString=readString(new URL(param).openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        NewsList newslist=null;
        JSONObject jsonobject=null;
        try {
            jsonobject=new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            try {
                jsonobject = jsonobject.getJSONObject("result");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JSONArray jsonarray = new JSONArray();
            jsonarray = jsonobject.getJSONArray("data");
            for (int i = 0; i < jsonarray.length(); i++) {
                jsonobject = jsonarray.getJSONObject(i);
                newslist = new NewsList();
                newslist.realtype=jsonobject.getString("realtype");
                newslist.url = jsonobject.getString("url");
                newslist.picture = jsonobject.getString("thumbnail_pic_s");
                newslist.time = jsonobject.getString("date");
                newslist.title = jsonobject.getString("title");
                list.add(newslist);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  list;
    }

    private String readString(InputStream is) {
        InputStreamReader isr = null;
        String result = "";
        String line = "";
        try {
            isr = new InputStreamReader(is, "utf-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        BufferedReader bufferedReader = new BufferedReader(isr);
        try {
            while ((line = bufferedReader.readLine()) != null) {
                result += line;

            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(List<NewsList> newsLists) {
        super.onPostExecute(newsLists);
        NewsListAdapter adapter=new NewsListAdapter(context,newsLists);
        listView.setAdapter(adapter);
        list=newsLists;
    }
}
