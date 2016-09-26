package com.sherlockkk.snail.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sherlockkk.snail.R;
import com.sherlockkk.snail.model.NewsList;
import com.sherlockkk.snail.utils.ImageLoader;

import java.util.List;

/**
 * Created by Simon on 2016/9/26.
 */
public class NewsListAdapter extends BaseAdapter {
    private Context context;
    private List<NewsList>list;
    public NewsListAdapter(Context context,List<NewsList> list)
    {
        this.context=context;
        this.list=list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            viewHolder=new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_news_list,null,false);
            viewHolder.titletextView= (TextView) convertView.findViewById(R.id.tv_news_list_title);
            viewHolder.timetextView= (TextView) convertView.findViewById(R.id.tv_news_list_time);
            viewHolder.imageView= (ImageView) convertView.findViewById(R.id.iv_news_list_image);
            viewHolder.realtypeTextView= (TextView) convertView.findViewById(R.id.tv_news_list_realtype);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.titletextView.setText(list.get(position).title);
        viewHolder.timetextView.setText(list.get(position).time);
        viewHolder.realtypeTextView.setText(list.get(position).realtype);
        String url=list.get(position).picture;
        Log.i("SimonYan",url);
        new ImageLoader()
                .showImageByAsyncTask(viewHolder.imageView, url);
        return convertView;
    }
    private class ViewHolder {
        private TextView titletextView;
        private TextView timetextView;
        private ImageView imageView;
        private TextView realtypeTextView;
    }
}
