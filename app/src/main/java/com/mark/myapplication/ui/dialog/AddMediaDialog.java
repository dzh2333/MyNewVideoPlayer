package com.mark.myapplication.ui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.TextureView;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mark.myapplication.R;
import com.mark.myapplication.databinding.DialogAddMediabeanBinding;
import com.mark.myapplication.util.ToastUtils;

public class AddMediaDialog extends BaseDialog{

    private DialogAddMediabeanBinding dialogAddMediabeanBinding;

    public AddMediaDialog(@NonNull Context context) {
        super(context);
    }

    public AddMediaDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected AddMediaDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialogAddMediabeanBinding = DialogAddMediabeanBinding.inflate(getLayoutInflater());
        setContentView(dialogAddMediabeanBinding.getRoot());

        dialogAddMediabeanBinding.addMediaCommit.setOnClickListener(v -> {
            if (TextUtils.isEmpty(dialogAddMediabeanBinding.addMediaMsgNameInput.getText().toString().trim())
                    || TextUtils.isEmpty(dialogAddMediabeanBinding.addMediaMsgUrlInput.getText().toString().trim())){
                ToastUtils.showToast("输入不能为空");
                return;
            }
            if (viewCallBack!= null){
                viewCallBack.ViewClick(dialogAddMediabeanBinding.addMediaCommit,
                        dialogAddMediabeanBinding.addMediaMsgNameInput.getText().toString().trim(),
                        dialogAddMediabeanBinding.addMediaMsgUrlInput.getText().toString().trim());
            }
        });
    }
}
