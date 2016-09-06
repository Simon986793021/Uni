package com.sherlockkk.snail.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sherlockkk.snail.R;
import com.sherlockkk.snail.model.SecondaryCatgory;

import java.util.List;
import java.util.zip.Inflater;

/**
 * @author SongJian
 * @created 2016/3/11.
 * @e-mail 1129574214@qq.com
 */
public class SecondaryCatgoryAdapter extends BaseAdapter {
    private List<SecondaryCatgory> mDatas;
    private Context mContext;

    public SecondaryCatgoryAdapter(List<SecondaryCatgory> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView==null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_secondary_catgory,null);
            viewHolder.tv_catgory = (TextView) convertView.findViewById(R.id.tv_secondary_catgory);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tv_catgory.setText( mDatas.get(position).getSecondaryCatgory());

        return convertView;
    }

    final class ViewHolder{
        private TextView tv_catgory;
    }
}
