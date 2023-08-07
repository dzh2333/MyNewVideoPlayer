package com.mark.myapplication.ui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mark.myapplication.bean.MediaBean;
import com.mark.myapplication.databinding.DialogInfoBinding;

public class InfoDialog extends BaseDialog{

    public void setMediaBean(MediaBean mediaBean) {
        this.mediaBean = mediaBean;
    }

    private MediaBean mediaBean;
    private DialogInfoBinding dialogInfoBinding;

    public InfoDialog(@NonNull Context context) {
        super(context);
    }

    public InfoDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected InfoDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialogInfoBinding = DialogInfoBinding.inflate(getLayoutInflater());
        setContentView(dialogInfoBinding.getRoot());

        dialogInfoBinding.dialogInfoMsgNameContent.setText(mediaBean.getName());
        dialogInfoBinding.dialogInfoMsgUrlContent.setText(mediaBean.getUrl());

        dialogInfoBinding.infoMediaCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
    }


}
