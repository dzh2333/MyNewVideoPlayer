package com.mark.myapplication.ui.adapter;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter {

    protected Context mContext;

    protected static final int TYPE_HEADER = 0;
    protected static final int TYPE_FOOTER = 1;
    protected static final int TYPE_NORMAL = 2;

    protected boolean hasHeaderView;
    protected boolean hasfooterView;

    protected List<T> mData;
    protected String mTag;

    public BaseRecyclerViewAdapter(Context context, List<T> list){
        this.mContext = context;
        this.mData = list;
    }

    @Override
    public int getItemCount() {
        if(!hasHeaderView && !hasfooterView){
            return mData.size();
        }else if(!hasHeaderView && hasfooterView){
            return mData.size() + 1;
        }else if(hasHeaderView && !hasfooterView){
            return mData.size() + 1;
        }else{
            return mData.size() + 2;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (!hasHeaderView && !hasfooterView){
            return TYPE_NORMAL;
        }
        if (hasHeaderView) {
            if (position == 0) {
                return TYPE_HEADER;
            }
        }
        if (hasfooterView) {
            if (position == getItemCount() - 1) {
                return TYPE_FOOTER;
            }
        }
        return TYPE_NORMAL;
    }

    public static abstract class BaseViewHolder extends RecyclerView.ViewHolder{
        public BaseViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void changeListData(List<T> list){
        mData = list;
        notifyDataSetChanged();
    }


    public boolean isHasHeaderView() {
        return hasHeaderView;
    }

    public void setHasHeaderView(boolean hasHeaderView) {
        this.hasHeaderView = hasHeaderView;
    }


    public boolean isHasfooterView() {
        return hasfooterView;
    }

    public void setHasfooterView(boolean hasfooterView) {
        this.hasfooterView = hasfooterView;
    }

}
