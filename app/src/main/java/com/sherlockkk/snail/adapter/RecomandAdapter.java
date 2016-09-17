/**  
 * Project Name:Android_Car_Example  
 * File Name:RecomandAdapter.java  
 * Package Name:com.amap.api.car.example  
 * Date:2015年4月3日上午11:29:45  
 *  
 */

package com.sherlockkk.snail.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sherlockkk.snail.R;
import com.sherlockkk.snail.model.PositionEntity;

import java.util.Arrays;
import java.util.List;

/**
 * ClassName:RecomandAdapter <br/>
 * Function: 显示的poi列表 <br/>
 */
public class RecomandAdapter extends BaseAdapter {

    PositionEntity[] entities = new PositionEntity[] {
            new PositionEntity(25.8191918650, 114.9644294034, "赣州火车站", "0797"),
            new PositionEntity(25.8547242364, 114.9290748751, "江西理工大学", "0797"),
            new PositionEntity(25.8356465779, 114.9189432093, "江西理工大学应科院",
                    "0797"),
            new PositionEntity(25.8716946210, 114.9414658407, "赣州八镜台", "0797"),
            new PositionEntity(25.8650615952, 114.9384883808, "赣州郁孤台", "0797"),
            new PositionEntity(25.9193427428, 114.9054742707, "赣州通天岩", "0797"),
            new PositionEntity(25.8157022037, 114.9258408502, "赣州万象城", "0797"),
            new PositionEntity(25.8500285530, 114.9390932569, "赣州南门口", "0797"),
            new PositionEntity(25.8595812523, 114.9142849826, "江西理工大学西区",
                    "0797"),
            new PositionEntity(25.8109532037, 114.9186068502, "赣州博物馆", "0797") };

    private List<PositionEntity> mPositionEntities;

    private final Context mContext;

    public RecomandAdapter(Context context) {
        mContext = context;
        mPositionEntities = Arrays.asList(entities);

    }

    public void setPositionEntities(List<PositionEntity> entities) {
        this.mPositionEntities = entities;

    }

    @Override
    public int getCount() {

        // TODO Auto-generated method stub
        return mPositionEntities.size();
    }

    @Override
    public Object getItem(int position) {

        return mPositionEntities.get(position);
    }

    @Override
    public long getItemId(int position) {

        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView textView = null;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            textView = (TextView) inflater.inflate(R.layout.view_recommond,
                    null);
        } else {
            textView = (TextView) convertView;
        }
        textView.setText(mPositionEntities.get(position).address);
        return textView;
    }

}
