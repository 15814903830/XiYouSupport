package com.chengfan.xiyou.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-28/17:43
 * @Description:
 */
public class RegularEditText  extends EditText {
    public RegularEditText(Context context) {
        super(context);
    }

    public RegularEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RegularEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setTypeface(Typeface tf, int style) {
        super.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Regular.ttf"));
    }
}
