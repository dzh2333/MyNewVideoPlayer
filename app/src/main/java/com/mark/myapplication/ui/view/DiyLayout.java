package com.mark.myapplication.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

public class DiyLayout extends ViewGroup {
    public DiyLayout(Context context) {
        super(context);
    }

    public DiyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DiyLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DiyLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
