package com.mark.myapplication.ui.fragment;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mark.myapplication.AppApplication;
import com.mark.myapplication.R;
import com.mark.myapplication.bean.MediaBean;
import com.mark.myapplication.databinding.FragemntMediaHomeBinding;
import com.mark.myapplication.ui.adapter.PlayerRecyclerAdapter;
import com.mark.myapplication.ui.dialog.AddMediaDialog;
import com.mark.myapplication.ui.dialog.BaseDialog;
import com.mark.myapplication.ui.dialog.InfoDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayerHomeFragment extends BaseFragment {

    private FragemntMediaHomeBinding mediaHomeBinding;

    private PlayerRecyclerAdapter adapter;
    private List<MediaBean> mediaBeans;

    protected View mRootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mediaHomeBinding = FragemntMediaHomeBinding.inflate(getLayoutInflater());
        mRootView = mediaHomeBinding.getRoot();
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void initUI() {
        initRV();
        mediaHomeBinding.addButton.setOnClickListener(v -> {
            AddMediaDialog addMediaDialog = new AddMediaDialog(getContext());
            addMediaDialog.setViewCallBack((view, objects) -> {
                String name = (String)objects[0];
                String url = (String)objects[1];
                MediaBean mediaBean = new MediaBean(name);
                mediaBean.setUrl(url);
//                mediaBean.setUrl("https://v3.douyinvod.com/566e3a57b8e579fb755f44b9ce4807ec/648aef3b/video/tos/cn/tos-cn-ve-0026/o80IBAxsALGEnUGyW1hfhZ2GIAot55C2vQzsAO/?a=1768&ch=0&cr=0&dr=0&er=0&cd=0%7C0%7C0%7C0&cv=1&br=1806&bt=1806&cs=0&ds=4&ft=_z7ehvvBQiAUEuyd8Z.BOMzJ4lcOSFeW2bLlSF9iiZmka&mime_type=video_mp4&qs=0&rc=PDo6PGhpaDc2ZWhkNmk3OUBpMzptZGU6ZnJkajMzNGQzM0BjNWMzYTNjNmExL2BeNTAtYSNfYTI0cjRvbW9gLS1kLi9zcw%3D%3D&l=20230615173630B2EA583FA9EF95290AF9&btag=e00038000");
                AppApplication.dbAdapter.insert(mediaBean);

                mediaBeans = AppApplication.dbAdapter.query();
                adapter.changeListData(mediaBeans);
                addMediaDialog.dismiss();
            });
            addMediaDialog.show();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }



    private void initRV(){
        mediaBeans = new ArrayList<>();

        mediaBeans = AppApplication.dbAdapter.query();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        adapter = new PlayerRecyclerAdapter(getContext(), mediaBeans);
        mediaHomeBinding.mediaHomeRv.setAdapter(adapter);
        mediaHomeBinding.mediaHomeRv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.onDraw(c, parent, state);
                Paint paint = new Paint();
                paint.setColor(Color.GRAY);
                c.drawRect(0, 0, 3000, 1000, paint);
            }
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int childAdapterPosition = parent.getChildAdapterPosition(view);
                if (childAdapterPosition == 0) {
                    outRect.set(0, 0, 0, 2);
                } else if (childAdapterPosition == parent.getAdapter().getItemCount() - 1) {
                    outRect.set(0, 0, 0, 2);
                } else {
                    outRect.set(0, 0, 0, 2);
                }
            }
        });
        adapter.setViewCallBack((view, data) -> {
            switch (view.getId()){
                case R.id.item_player_remove_icon:
                    AppApplication.dbAdapter.delete(data.getId());
                    mediaBeans = AppApplication.dbAdapter.query();
                    adapter.changeListData(mediaBeans);
                    break;
                case R.id.item_player_info_icon:
                    InfoDialog infoDialog = new InfoDialog(getContext());
                    infoDialog.setMediaBean(data);
                    infoDialog.show();
                    break;
                default:
                    break;
            }
        });
        mediaHomeBinding.mediaHomeRv.setLayoutManager(linearLayoutManager);
    }

}
