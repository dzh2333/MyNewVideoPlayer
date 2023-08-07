package com.mark.myapplication.util;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.mark.myapplication.AppApplication;

public class ToastUtils {

    private static Toast toast;

    public static void showToast(String str){
        if (toast == null){
            toast = Toast.makeText(AppApplication.getApplication(), str, Toast.LENGTH_SHORT);
        }else {
            toast.setText(str);
        }
        toast.show();
    }

}
