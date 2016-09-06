package com.sherlockkk.snail.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sherlockkk.snail.R;
import com.sherlockkk.snail.model.Secondary;
import com.sherlockkk.snail.ui.PriceView;

import java.util.ArrayList;
import java.util.List;

/**
 * 瀑布流RecyclerView Adapter
 *
 * @author SongJian
 * @created 2016/3/10.
 * @e-mail 1129574214@qq.com
 */
public class SimpleRecyclerCardAdapter extends RecyclerView.Adapter<SimpleCardViewHolder> {

    private Context mCtx;
    private LayoutInflater mInflater;
    private List<Secondary> mDataSource = new ArrayList<>();
    private OnItemActionListener mOnItemActionListener;
    private DisplayImageOptions options;

    public SimpleRecyclerCardAdapter(Context mCtx) {
        super();
        this.mCtx = mCtx;
        mInflater = LayoutInflater.from(mCtx);
        options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).build();
    }

    public void addList(List<Secondary> list) {
        this.mDataSource = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mDataSource.size();
    }

    @Override
    public void onBindViewHolder(final SimpleCardViewHolder viewHolder, int i) {
        Secondary secondary = mDataSource.get(i);
        viewHolder.itemTv.setText(secondary.getName());
        viewHolder.itemPv.setText(secondary.getCururentPrice());
        List<String> picUrlList = secondary.getPicUrlList();
        String picUrl = picUrlList.get(0);
        ImageLoader.getInstance().displayImage(picUrl, viewHolder.itemIv, options);

        if (mOnItemActionListener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //注意这里必须使用viewHolder.getPosition()而不能用i，因为为了保证动画，没有使用NotifyDatasetChanged更新位置数据
                    mOnItemActionListener.onItemClickListener(v, viewHolder.getLayoutPosition());
                }
            });
            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //注意这里必须使用viewHolder.getPosition()而不能用i，因为为了保证动画，没有使用NotifyDatasetChanged更新位置数据
                    return mOnItemActionListener.onItemLongClickListener(v, viewHolder.getLayoutPosition());
                }
            });
        }
    }

    @Override
    public SimpleCardViewHolder onCreateViewHolder(ViewGroup viewgroup, int i) {

        View v = mInflater.inflate(R.layout.item_secondary, viewgroup, false);
        SimpleCardViewHolder simpleViewHolder = new SimpleCardViewHolder(v);
        simpleViewHolder.setIsRecyclable(true);

        return simpleViewHolder;
    }

    /**********
     * 定义点击事件
     **********/
    public interface OnItemActionListener {
        public void onItemClickListener(View v, int pos);

        public boolean onItemLongClickListener(View v, int pos);
    }

    public void setOnItemActionListener(OnItemActionListener onItemActionListener) {
        this.mOnItemActionListener = onItemActionListener;
    }
}

class SimpleCardViewHolder extends RecyclerView.ViewHolder {
    public TextView itemTv;
    public ImageView itemIv;
    public PriceView itemPv;

    public SimpleCardViewHolder(View layout) {
        super(layout);
        itemTv = (TextView) layout.findViewById(R.id.item_secondary_title);
        itemIv = (ImageView) layout.findViewById(R.id.item_secondary_img);
        itemPv = (PriceView) layout.findViewById(R.id.item_secondary_price);
    }
}
