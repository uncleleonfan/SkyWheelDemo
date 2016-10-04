package com.itheima.skywheeldemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
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
        Log.d(TAG, "onSizeChanged: " + mCx + " " + mCy);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            float radius = getMeasuredWidth() / 2 - child.getMeasuredWidth() / 2;
            double degree =  2 * Math.PI / 4;
            float childX = (float) (mCx + Math.sin(i * degree) * radius);
            float childY = (float) (mCy - Math.cos(i * degree) * radius);
            int left = (int) (childX - child.getMeasuredWidth() / 2);
            int top = (int) (childY - child.getMeasuredHeight() / 2);
            int right = (int) (childX + child.getMeasuredWidth() / 2);
            int bottom = (int) (childY + child.getMeasuredHeight() / 2);
            child.layout(left, top, right, bottom);
        }

    }
}
