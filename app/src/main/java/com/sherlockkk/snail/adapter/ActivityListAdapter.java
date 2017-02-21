package com.sherlockkk.snail.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sherlockkk.snail.R;
import com.sherlockkk.snail.model.SchoolActivity;

import java.util.List;

/**
 * Created by Simon on 2016/9/10.
 */
public class ActivityListAdapter extends BaseAdapter {
    private List<SchoolActivity> list;
    private LayoutInflater layoutInflater;

    public ActivityListAdapter(Context mcontext)
    {
        this.layoutInflater=LayoutInflater.from(mcontext);
    }
    public void addList(List<SchoolActivity> mlist) {
        list = mlist;
        notifyDataSetChanged();
    }
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
            convertView=layoutInflater.inflate(R.layout.listview_activity,null,false);
            viewHolder.titleTextView= (TextView) convertView.findViewById(R.id.tv_title_activity);
            viewHolder.descriptionTextView= (TextView) convertView.findViewById(R.id.tv_description);
            viewHolder.personacountTextView= (TextView) convertView.findViewById(R.id.tv_personacount);
            viewHolder.starttimeTextView= (TextView) convertView.findViewById(R.id.tv_starttime);
            viewHolder.imageView= (ImageView) convertView.findViewById(R.id.iv_title);
            viewHolder.deadtimeTextView= (TextView) convertView.findViewById(R.id.tv_deadtime);
            convertView.setTag(viewHolder);

        }
        else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.titleTextView.setText(list.get(position).getActivityTitle().trim().toString());
        viewHolder.starttimeTextView.setText("活动时间："+list.get(position).getActivityTime().trim().toString());
        int acount;
        if (list.get(position).getList("signupPhonenum")!=null)
        {
             acount= list.get(position).getList("signupPhonenum").size();
        }
        else
        {
            acount=0;
        }

        viewHolder.descriptionTextView.setText(list.get(position).getActivityDescription().trim().toString());
        viewHolder.personacountTextView.setText("报名人数："+acount+""+"/"+list.get(position).getActivityPersonAcount().trim().toString());
        viewHolder.deadtimeTextView.setText("报名截止："+list.get(position).getDeadTime().trim().toString());
        String PicBase64=list.get(position).getPic();
        byte[] bytes= Base64.decode(PicBase64.getBytes(),1);
        Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        viewHolder.imageView.setImageBitmap(bitmap);

        return convertView;
    }
    private  class ViewHolder{
        TextView titleTextView;
        TextView descriptionTextView;
        TextView personacountTextView;
        TextView deadtimeTextView;
        TextView starttimeTextView;
        ImageView imageView;

    }
}
