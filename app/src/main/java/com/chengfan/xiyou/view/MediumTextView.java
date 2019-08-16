package com.chengfan.xiyou.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-28/17:06
 * @Description:
 */
public class MediumTextView extends android.support.v7.widget.AppCompatTextView {
    public MediumTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MediumTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MediumTextView(Context context) {
        super(context);
    }

    public void setTypeface(Typeface tf, int style) {
        super.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Medium.ttf"));
    }
}
