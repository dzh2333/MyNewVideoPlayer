package com.mark.myapplication.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.mark.myapplication.R;
import com.mark.myapplication.bean.MediaBean;
import com.mark.myapplication.databinding.ItemPlayerHomeBinding;
import com.mark.myapplication.ui.player.PlayHomeActivity;
import com.mark.myapplication.util.AppExecutors;

import java.util.ArrayList;
import java.util.List;

public class PlayerRecyclerAdapter extends BaseRecyclerViewAdapter<MediaBean>{

    public PlayerRecyclerAdapter(Context context, List<MediaBean> list) {
        super(context, list);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_player_home, parent, false);
        return new PlayerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MediaBean mediaBean = mData.get(position);
        if (holder instanceof PlayerViewHolder){
            PlayerViewHolder playerViewHolder = (PlayerViewHolder) holder;
            playerViewHolder.text.setText(mediaBean.getName());

            playerViewHolder.text.setOnClickListener(v -> {
                PlayHomeActivity.lunchActivity(mContext, mData, position);
            });

            playerViewHolder.removeButton.setOnClickListener(v -> {
                if (viewCallBack != null){
                    viewCallBack.ViewClick(playerViewHolder.removeButton, mediaBean);
                }
            });
            playerViewHolder.infoButton.setOnClickListener(v -> {
                if (viewCallBack != null){
                    viewCallBack.ViewClick(playerViewHolder.infoButton, mediaBean);
                }
            });
        }
    }

    public class PlayerViewHolder extends BaseViewHolder{
        AppCompatImageView icon;
        AppCompatTextView text;
        ConstraintLayout layout;
        AppCompatImageView infoButton;
        AppCompatImageView removeButton;
        public PlayerViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.item_player_home_icon);
            text = itemView.findViewById(R.id.item_player_home_title);
            layout = itemView.findViewById(R.id.item_player_home_layout);
            infoButton = itemView.findViewById(R.id.item_player_info_icon);
            removeButton = itemView.findViewById(R.id.item_player_remove_icon);
        }
    }

    /**
     * View Click CallBack
     */
    private ViewCallBack viewCallBack;
    public void setViewCallBack(ViewCallBack viewCallBack) {
        this.viewCallBack = viewCallBack;
    }
    public interface ViewCallBack{
        void ViewClick(View view, MediaBean data);
    }
}
