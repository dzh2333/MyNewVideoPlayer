package com.mark.myapplication.util;

import com.mark.myapplication.AppApplication;

public class StringUtils {

    public static String getStr(int id){
        return AppApplication.getApplication().getString(id);
    }
}
