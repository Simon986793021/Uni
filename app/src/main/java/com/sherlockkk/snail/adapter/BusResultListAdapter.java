/**
 * 
 */
package com.sherlockkk.snail.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.amap.api.services.route.BusPath;
import com.amap.api.services.route.BusRouteResult;
import com.sherlockkk.snail.R;
import com.sherlockkk.snail.activity.BusRouteDetailActivity;
import com.sherlockkk.snail.utils.Utils;

import java.util.List;

/**
 * @author Simon
 * @category 公交搜索结果适配器
 */
public class BusResultListAdapter extends BaseAdapter {
    private final Context mContext;
    private final BusRouteResult mBusRouteResult;
    private final List<BusPath> list;

    public BusResultListAdapter(Context context, BusRouteResult busRouteResult) {
        mContext = context;
        mBusRouteResult = busRouteResult;
        list = busRouteResult.getPaths();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.widget.Adapter#getItem(int)
     */
    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.widget.Adapter#getItemId(int)
     */
    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.widget.Adapter#getView(int, android.view.View,
     * android.view.ViewGroup)
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View
                    .inflate(mContext, R.layout.item_bus_result, null);
            viewHolder.busdetailTextView = (TextView) convertView
                    .findViewById(R.id.tv_busdetail);
            viewHolder.busnumTextView = (TextView) convertView
                    .findViewById(R.id.tv_busnum);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final BusPath busPath = list.get(position);
        viewHolder.busnumTextView.setText(Utils.getBusPathTitle(busPath));
        viewHolder.busdetailTextView.setText(Utils.getBusPathDes(busPath));

        convertView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(mContext.getApplicationContext(),
                        BusRouteDetailActivity.class);
                intent.putExtra("buspath", busPath);
                intent.putExtra("busresult", mBusRouteResult);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }

    class ViewHolder {
        private TextView busnumTextView;
        private TextView busdetailTextView;
    }
}
