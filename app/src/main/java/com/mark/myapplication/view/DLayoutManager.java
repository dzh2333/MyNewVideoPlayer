package com.mark.myapplication.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class DLayoutManager extends StaggeredGridLayoutManager {

    public DLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public DLayoutManager(int spanCount, int orientation) {
        super(spanCount, orientation);
    }

}
