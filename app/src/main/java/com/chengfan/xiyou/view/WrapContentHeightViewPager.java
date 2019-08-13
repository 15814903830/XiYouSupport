package com.chengfan.xiyou.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-17/16:19
 * @Description:
 */
public class WrapContentHeightViewPager extends ViewPager {
    private int current;
    private int height = 0;

    private boolean scrollble = true;

    public WrapContentHeightViewPager(Context context) {
        super(context);
    }

    public WrapContentHeightViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childSize = getChildCount();
        int maxHeight = 0;
        for (int i = 0; i < childSize; i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            if (child.getMeasuredHeight() > maxHeight) {
                maxHeight = child.getMeasuredHeight();
            }
        }
        if (maxHeight > 0) {
            setMeasuredDimension(getMeasuredWidth(), maxHeight);
        }
    }


}
