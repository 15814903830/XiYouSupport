package com.chengfan.xiyou.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-28/17:07
 * @Description:
 */
public class RegularTextView extends TextView {
    public RegularTextView(Context context, AttributeSet attrs, int defStyle) {

        super(context, attrs, defStyle);
    }

    public RegularTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RegularTextView(Context context) {
        super(context);
    }

    public void setTypeface(Typeface tf, int style) {
        super.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Regular.ttf"));
    }
}
