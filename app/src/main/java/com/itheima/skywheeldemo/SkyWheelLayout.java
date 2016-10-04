package com.itheima.skywheeldemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * 创建者: Leon
 * 创建时间: 2016/10/4 20:56
 * 描述： TODO
 */
public class SkyWheelLayout extends ViewGroup {
    private static final String TAG = "SkyWheelLayout";

    private int mCx;
    private int mCy;

    public SkyWheelLayout(Context context) {
        this(context, null);
    }

    public SkyWheelLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (height > width) {
            setMeasuredDimension(width, width);
        } else {
            setMeasuredDimension(height, height);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mCx = w / 2;
        mCy = h / 2;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            int childX = mCx;
            int childY = child.getMeasuredHeight() / 2;

            int left = childX - child.getMeasuredWidth() / 2;
            int top = childY - child.getMeasuredHeight() / 2;
            int right = childX + child.getMeasuredWidth() / 2;
            int bottom = childY + child.getMeasuredHeight() / 2;
            child.layout(left, top, right, bottom);
        }

    }
}
