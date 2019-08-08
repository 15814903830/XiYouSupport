package com.chengfan.xiyou.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zero.ci.widget.logger.Logger;

/**
 * @author: Zero Yuan
 * @Email: zero.yuan.xin@gmail.com
 * @DATE : 2019-07-03/15:30
 * @Description: 自定义字体
 */
public class FontHelper {
    public static void applyFont(final Context context, final View root, final String fontName) {
        try {
            if (root instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) root;
                for (int i = 0; i < viewGroup.getChildCount(); i++)
                    applyFont(context, viewGroup.getChildAt(i), fontName);
            } else if (root instanceof TextView)
                ((TextView) root).setTypeface(Typeface.createFromAsset(context.getAssets(), fontName));
        } catch (Exception e) {
            Logger.e(String.format("Error occured when trying to apply %s font for %s view", fontName, root));
            e.printStackTrace();
        }

       // Logger.d("FontHelper===>>>  " + context + "  view root : " + root + "  fontName :  " + fontName);
    }
}
