package com.sherlockkk.snail.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.avos.avoscloud.AVObject;
import com.sherlockkk.snail.R;

import java.util.List;

/**
 * Created by Simon on 2016/12/22.
 */
public class MySupportListAdapter extends BaseAdapter {
    private List<AVObject> list;
    private LayoutInflater layoutInflater;
    public MySupportListAdapter(Context context)
    {
        layoutInflater=LayoutInflater.from(context);
    }
    public void addList(List<AVObject> list){
        this.list=list;
        notifyDataSetChanged();
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
       ViewHolder viewHolder;
        if (convertView==null)
        {
            viewHolder=new ViewHolder();
            convertView=layoutInflater.inflate(R.layout.item_listview_mysupport,null,false);
            viewHolder.supportprice= (TextView) convertView.findViewById(R.id.tv_support_price);
            viewHolder.supporttitle= (TextView) convertView.findViewById(R.id.tv_support_title);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
            viewHolder.supporttitle.setText("赞助"+list.get(position).getString("activityTitle").toString().trim());
            viewHolder.supportprice.setText(list.get(position).getString("SupportPrice").toString().trim()+"元");
        return convertView;
    }
    private class  ViewHolder{
        private TextView supporttitle;
        private TextView supportprice;
    }
}
