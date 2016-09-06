package com.sherlockkk.snail.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sherlockkk.snail.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SongJian
 * @created 2016/3/10.
 * @e-mail 1129574214@qq.com
 */
public class CampusGridviewAdapter extends RecyclerView.Adapter<CardViewHolder>{

    private Context mCtx;
    private LayoutInflater mInflater;
    private final List<String> mDataSource = new ArrayList<String>();
    private OnItemActionListener mOnItemActionListener;

    public CampusGridviewAdapter(Context mCtx, List<String> dataList) {
        super();
        this.mCtx = mCtx;
        mInflater = LayoutInflater.from(mCtx);
        this.mDataSource.addAll(dataList);
    }
    @Override
    public int getItemCount() {
        return mDataSource.size();
    }
    @Override
    public void onBindViewHolder(final CardViewHolder viewHolder,  int i) {
        viewHolder.itemTv.setText(mDataSource.get(i));
        int resId = mCtx.getResources().getIdentifier("img_"+i, "drawable", mCtx.getPackageName());
        if(resId!=0)
        {
            viewHolder.itemIv.setImageResource(resId);
        }
        if(mOnItemActionListener!=null)
        {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //注意这里必须使用viewHolder.getPosition()而不能用i，因为为了保证动画，没有使用NotifyDatasetChanged更新位置数据
                    mOnItemActionListener.onItemClickListener(v,viewHolder.getPosition());
                }
            });
            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //注意这里必须使用viewHolder.getPosition()而不能用i，因为为了保证动画，没有使用NotifyDatasetChanged更新位置数据
                    return mOnItemActionListener.onItemLongClickListener(v, viewHolder.getPosition());
                }
            });
        }
    }
    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup viewgroup, int i) {

        View v =  mInflater.inflate(R.layout.item_campus, viewgroup,false);
        CardViewHolder simpleViewHolder = new CardViewHolder(v);
        simpleViewHolder.setIsRecyclable(true);

        return simpleViewHolder;
    }
    /**********定义点击事件**********/
    public   interface OnItemActionListener
    {
        public   void onItemClickListener(View v,int pos);
        public   boolean onItemLongClickListener(View v, int pos);
    }
    public void setOnItemActionListener(OnItemActionListener onItemActionListener) {
        this.mOnItemActionListener = onItemActionListener;
    }
}
class CardViewHolder extends RecyclerView.ViewHolder
{
    public TextView itemTv;
    public ImageView itemIv;

    public CardViewHolder(View layout) {
        super(layout);
        itemTv = (TextView) layout.findViewById(R.id.item_campus_title);
        itemIv = (ImageView) layout.findViewById(R.id.item_campus_img);
    }
}
