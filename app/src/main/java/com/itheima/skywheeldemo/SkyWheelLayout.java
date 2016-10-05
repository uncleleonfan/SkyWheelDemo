package com.itheima.skywheeldemo;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

/**
 * 创建者: Leon
 * 创建时间: 2016/10/4 20:56
 * 描述： TODO
 */
public class SkyWheelLayout extends ViewGroup {
    private static final String TAG = "SkyWheelLayout";

    private int mCx;
    private int mCy;

    private float mDiffDegree;

    private GestureDetector mGestureDetector;
    private double mCellDegree;

    public SkyWheelLayout(Context context) {
        this(context, null);
    }

    public SkyWheelLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mGestureDetector = new GestureDetector(context, mOnGestureListener);
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
        mCellDegree = 2 * Math.PI / getChildCount();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            float radius = getMeasuredWidth() / 2 - child.getMeasuredWidth() / 2;
            float childX = (float) (mCx + Math.sin(i * mCellDegree + mDiffDegree) * radius);
            float childY = (float) (mCy - Math.cos(i * mCellDegree + mDiffDegree) * radius);
            int left = (int) (childX - child.getMeasuredWidth() / 2);
            int top = (int) (childY - child.getMeasuredHeight() / 2);
            int right = (int) (childX + child.getMeasuredWidth() / 2);
            int bottom = (int) (childY + child.getMeasuredHeight() / 2);
            child.layout(left, top, right, bottom);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    private GestureDetector.OnGestureListener mOnGestureListener = new GestureDetector.OnGestureListener() {


        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        /**
         *
         * @param e1 手指按下
         * @param e2 当前最新的移动事件
         * @param distanceX = 前一个移动事件的x的值 - 当前的移动事件的x值
         * @param distanceY = 前一个移动事件的y的值 - 当前的移动事件的y值
         * @return
         */
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            double endDegree = getDegree(e2.getY(), e2.getX());
            double startDegree = getDegree(e2.getY() + distanceY, e2.getX() + distanceX);
            double diffDegree = endDegree - startDegree;
            updateDegree((float) (mDiffDegree + diffDegree));
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            double startDegree = getDegree(e2);
            //1ms之后转过的角度
            double endDegree = getDegree(e2.getY() + velocityY / 1000, e2.getX() + velocityX / 1000 );
            double degreePerMilli = endDegree - startDegree;
            //ls之后转过的角度
            double degreePerSecond = degreePerMilli * 1000;

            //设置转动时间，角速度越大，时间越长
            long duration = (long) Math.abs(degreePerSecond * 1000) ; //以毫秒为单位
            if (duration > 800) {
                duration = 800;
            }
            double diffDegree = (float) (duration * degreePerMilli);
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(mDiffDegree, (float) (mDiffDegree + diffDegree));
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    updateDegree((float) animation.getAnimatedValue());
                }
            });
            valueAnimator.setDuration(duration);
            valueAnimator.setInterpolator(new DecelerateInterpolator());
            valueAnimator.start();
            return true;
        }
    };

    private void updateDegree(float diffDegree) {
        mDiffDegree =  diffDegree;
        requestLayout();
    }

    private double getDegree(float y, float x) {
        return Math.atan2(y - mCy, x - mCx);
    }

    private double getDegree(MotionEvent e) {
        return getDegree(e.getY(), e.getX());
    }


}
