/**
 * 
 */
package com.sherlockkk.snail.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.services.route.WalkStep;
import com.sherlockkk.snail.R;
import com.sherlockkk.snail.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Simon
 * @category 步行详情页面适配器
 */
public class WalkSegmentListAdapter extends BaseAdapter {
    private List<WalkStep> list = new ArrayList<>();
    private final Context mContext;

    public WalkSegmentListAdapter(Context context, List<WalkStep> list) {
        this.mContext = context;
        this.list = list;
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
        return list.get(0);
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
        ViewHolder viewHolder = null;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.item_bus_segment,
                    null);
            viewHolder.lineNameTextView = (TextView) convertView
                    .findViewById(R.id.bus_line_name);
            viewHolder.dirIconDownImageView = (ImageView) convertView
                    .findViewById(R.id.bus_dir_icon_down);
            viewHolder.dirIconUpImageView = (ImageView) convertView
                    .findViewById(R.id.bus_dir_icon_up);
            viewHolder.spitLineImageView = (ImageView) convertView
                    .findViewById(R.id.bus_seg_split_line);
            viewHolder.dirIconImageView = (ImageView) convertView
                    .findViewById(R.id.bus_dir_icon);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        WalkStep walkStep = list.get(position);

        if (position == 0) {
            viewHolder.lineNameTextView.setText("起点");
            viewHolder.spitLineImageView.setVisibility(View.GONE);
            viewHolder.dirIconUpImageView.setVisibility(View.GONE);
            viewHolder.dirIconDownImageView.setVisibility(View.VISIBLE);
            viewHolder.dirIconImageView.setImageResource(R.drawable.dir_start);
            return convertView;
        } else if (position == list.size() - 1) {
            viewHolder.spitLineImageView.setVisibility(View.VISIBLE);
            viewHolder.dirIconDownImageView.setVisibility(View.GONE);
            viewHolder.lineNameTextView.setText("到达终点");
            viewHolder.dirIconUpImageView.setVisibility(View.VISIBLE);
            viewHolder.dirIconImageView.setImageResource(R.drawable.dir_end);
            return convertView;
        } else {
            String actionName = walkStep.getAction();
            int resId = Utils.getDriveActionID(actionName);
            viewHolder.dirIconImageView.setImageResource(resId);
            viewHolder.lineNameTextView.setText(walkStep.getInstruction());
            viewHolder.dirIconDownImageView.setVisibility(View.VISIBLE);
            viewHolder.dirIconUpImageView.setVisibility(View.VISIBLE);
            return convertView;
        }

    }

    private class ViewHolder {
        private TextView lineNameTextView;
        private ImageView dirIconImageView;
        private ImageView spitLineImageView;
        private ImageView dirIconUpImageView;
        private ImageView dirIconDownImageView;
    }
}
