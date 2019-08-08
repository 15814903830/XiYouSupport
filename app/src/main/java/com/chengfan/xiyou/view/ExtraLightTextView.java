package com.chengfan.xiyou.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-28/17:05
 * @Description:
 */
public class ExtraLightTextView extends TextView {
    public ExtraLightTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ExtraLightTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExtraLightTextView(Context context) {
        super(context);
    }

    public void setTypeface(Typeface tf, int style) {
        super.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/ExtraLight.ttf"));
    }
}
