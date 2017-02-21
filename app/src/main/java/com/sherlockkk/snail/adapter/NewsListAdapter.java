package com.sherlockkk.snail.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.sherlockkk.snail.R;
import com.sherlockkk.snail.model.NewsList;

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
        DisplayImageOptions options=new DisplayImageOptions.Builder()
               // .showStubImage(R.mipmap.ic_launcher)          // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.ic_launcher)  // 设置图片Uri为空或是错误的时候显示的图片
                //.showImageOnFail(R.mipmap.ic_launcher)       // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)                          // 设置下载的图片是否缓存在SD卡中
                .build();                                   // 创建配置过得DisplayImageOption对象
    //    ImageLoader.getInstance().displayImage(url,viewHolder.imageView,options);
        ImageLoader.getInstance().displayImage
                (url,new ImageViewAware(viewHolder.imageView,false),options);//解决图片重复加载问题
        return convertView;
    }
    private class ViewHolder {
        private TextView titletextView;
        private TextView timetextView;
        private ImageView imageView;
        private TextView realtypeTextView;
    }
}
