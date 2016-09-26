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
 * Created by Simon on 2016/9/21.
 */
public class MyActivityListAdapter extends BaseAdapter{
    private List<AVObject> list;
    private LayoutInflater layoutInflater;

    public MyActivityListAdapter(Context mcontext)
    {
            this.layoutInflater=LayoutInflater.from(mcontext);
    }

    public void addList (List<AVObject> mlist)
    {
        list=mlist;
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
        ViewHolder viewHolder=null;
        if (convertView==null)
        {
            viewHolder=new ViewHolder();
            convertView=layoutInflater.inflate(R.layout.item_listview_myactivity,null,false);
            viewHolder.titleTextView= (TextView) convertView.findViewById(R.id.tv_myactivity_title);
            viewHolder.personacountTextView= (TextView) convertView.findViewById(R.id.tv_myactivity_personacount);
            viewHolder.startTextView= (TextView) convertView.findViewById(R.id.tv_myactivity_time);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.titleTextView.setText(list.get(position).getString("activityTitle").trim());
        viewHolder.startTextView.setText(list.get(position).getString("activityTime").trim());
        viewHolder.personacountTextView.setText(list.get(position).getString("activityPersonAcount").trim());
        return convertView;
    }
    private class  ViewHolder
    {
        private TextView titleTextView;
        private TextView startTextView;
        private  TextView personacountTextView;
    }
}
