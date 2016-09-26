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
 * Created by Simon on 2016/9/22.
 */
public class JokeListAdapter  extends BaseAdapter{
    private Context context;
    private List<AVObject> list;

    public  JokeListAdapter(Context mcontext)
    {
        context=mcontext;
    }
    public void addList(List<AVObject> mlist)
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
            convertView= LayoutInflater.from(context).inflate(R.layout.item_listview_joke,null,false);
            viewHolder.textView= (TextView) convertView.findViewById(R.id.tv_joke);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder= (ViewHolder) convertView.getTag();
        }
            viewHolder.textView.setText(list.get(position).get("content").toString());
        return convertView;
    }
    private class ViewHolder {
        private TextView textView;
    }
}
