package com.chengfan.xiyou.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chengfan.xiyou.widget.ninegridview.INineGridImageLoader;
import com.zero.ci.widget.imageloader.base.ImageLoaderManager;

/**
 *  九宫格的图片加载器
 */

public class GlideImageLoader implements INineGridImageLoader
{
    @Override
    public void displayNineGridImage(Context context, String url, ImageView imageView)
    {
       // Glide.with(context).load(url).into(imageView);
        ImageLoaderManager.getInstance().showImage(imageView,url);
    }

    @Override
    public void displayNineGridImage(Context context, String url, ImageView imageView, int width, int height)
    {
        Glide.with(context).load(url)
                .apply(new RequestOptions().override(width, height))
                .into(imageView);
    }
}
