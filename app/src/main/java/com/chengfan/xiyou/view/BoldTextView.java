package com.chengfan.xiyou.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-28/17:03
 * @Description:
 */
public class BoldTextView extends TextView {

    public BoldTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public BoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BoldTextView(Context context) {
        super(context);
    }

    public void setTypeface(Typeface tf, int style) {
        super.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Bold.ttf"));
    }
}
