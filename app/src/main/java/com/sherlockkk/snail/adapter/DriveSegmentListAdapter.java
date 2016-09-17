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

import com.amap.api.services.route.DriveStep;
import com.sherlockkk.snail.R;
import com.sherlockkk.snail.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Simon
 * @category 驾车路线详情页listview的adapter
 */
public class DriveSegmentListAdapter extends BaseAdapter {
    private final Context mContext;
    private List<DriveStep> list = new ArrayList<>();

    /*
     * 构造函数
     */
    public DriveSegmentListAdapter(Context context, List<DriveStep> list) {
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
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.item_bus_segment,
                    null);
            viewHolder.driveLineName = (TextView) convertView
                    .findViewById(R.id.bus_line_name);
            viewHolder.driveDirIcon = (ImageView) convertView
                    .findViewById(R.id.bus_dir_icon);
            viewHolder.driveDirDown = (ImageView) convertView
                    .findViewById(R.id.bus_dir_icon_down);
            viewHolder.driveDirUp = (ImageView) convertView
                    .findViewById(R.id.bus_dir_icon_up);
            viewHolder.splitLine = (ImageView) convertView
                    .findViewById(R.id.bus_seg_split_line);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        DriveStep driveStep = list.get(position);
        if (position == 0) {
            viewHolder.driveLineName.setText("出发");
            viewHolder.driveDirDown.setVisibility(View.VISIBLE);
            viewHolder.driveDirIcon.setImageResource(R.drawable.dir_start);
            viewHolder.driveDirUp.setVisibility(View.GONE);
            viewHolder.splitLine.setVisibility(View.GONE);
            return convertView;
        } else if (position == list.size() - 1) {
            viewHolder.driveDirIcon.setImageResource(R.drawable.dir_end);
            viewHolder.driveDirDown.setVisibility(View.GONE);
            viewHolder.driveLineName.setText("到达终点");
            viewHolder.driveDirUp.setVisibility(View.VISIBLE);
            viewHolder.splitLine.setVisibility(View.VISIBLE);
            return convertView;
        } else {
            String actionName = driveStep.getAction();
            int resId = Utils.getDriveActionID(actionName);
            viewHolder.driveDirIcon.setImageResource(resId);
            viewHolder.driveLineName.setText(driveStep.getInstruction());
            viewHolder.driveDirDown.setVisibility(View.VISIBLE);
            viewHolder.splitLine.setVisibility(View.VISIBLE);
            viewHolder.driveDirUp.setVisibility(View.VISIBLE);
            return convertView;
        }
    }

    private class ViewHolder {
        TextView driveLineName;
        ImageView driveDirIcon;
        ImageView driveDirUp;
        ImageView driveDirDown;
        ImageView splitLine;
    }

}
