package com.mark.myapplication.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BaseDialog extends Dialog {
    public BaseDialog(@NonNull Context context) {
        super(context);
    }

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected BaseDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public ViewCallBack getViewCallBack() {
        return viewCallBack;
    }

    public void setViewCallBack(ViewCallBack viewCallBack) {
        this.viewCallBack = viewCallBack;
    }

    public ViewCallBack viewCallBack;
    public interface ViewCallBack{
        void ViewClick(View view,Object... objects);
    }
}
