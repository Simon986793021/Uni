package com.sherlockkk.snail.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sherlockkk.snail.R;


import java.io.File;
import java.util.ArrayList;



/**
 * @author SongJian
 * @created 2016/3/11.
 * @e-mail 1129574214@qq.com
 */
public class PhotoPickAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> listUrls;
    private int mMaxPosition;
    private LayoutInflater inflater;

    public PhotoPickAdapter(ArrayList<String> listUrls, Context context) {
        this.listUrls = listUrls;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (listUrls.size() == 9) {
            mMaxPosition = listUrls.size();//默认最多九张图
        } else {
            mMaxPosition = listUrls.size() + 1;
        }
        return mMaxPosition;
    }

    public int getMaxPosition() {
        return mMaxPosition;
    }

    @Override
    public Object getItem(int position) {
        return listUrls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_image, null);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.iv_photopick);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (position == mMaxPosition - 1) {
            viewHolder.image.setImageResource(R.drawable.icon_addpic);
            viewHolder.image.setVisibility(View.VISIBLE);
            if (position == 4 && mMaxPosition == 5) {
                viewHolder.image.setVisibility(View.GONE);
            }
        } else {
            final String path = listUrls.get(position);
            Glide.with(context).load(new File(path))
                    .placeholder(R.drawable.default_error)
                    .error(R.drawable.default_error)
                    .centerCrop()
                    .crossFade()
                    .into(viewHolder.image);
        }

        return convertView;
    }

    final class ViewHolder {
        public ImageView image;
    }
}
